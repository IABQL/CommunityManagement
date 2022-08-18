package com.openlab.payment.util;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.openlab.payment.entity.PaymentOrder;
import com.openlab.payment.service.PaymentOrderService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

@Component
public class ConsumerTask {

    @Autowired
    private PaymentOrderService paymentOrderService;

    public void consumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("payment");

        consumer.setNamesrvAddr("1.14.75.238:9876");

        consumer.subscribe("pay-save","*");

        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置回调函数来消费消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                boolean isSuccess = false;
                MessageExt messageExt = list.get(0);

                UpdateWrapper<PaymentOrder> updateWrapper = new UpdateWrapper<>();
                Integer state = null;
                String orderId = null;
                try {
                    // 将对象反序列化
                    byte[] body = messageExt.getBody();
                    ByteArrayInputStream is = new ByteArrayInputStream(body);
                    ObjectInputStream ois = new ObjectInputStream(is);
                    PayMessageContext payMessageContext =(PayMessageContext) ois.readObject();
                    String paymentTaskType = payMessageContext.getPaymentTaskType();

                    orderId = payMessageContext.getOrderId();
                    PaymentOrder paymentOrder = PaymentOrder.builder()
                            .paymentPrice(payMessageContext.getPaymentPrice())
                            .paymentTime(payMessageContext.getPaymentTime())
                            .paymentType(payMessageContext.getPaymentType())
                            .paymentRemainPrice(payMessageContext.getPaymentRemainPrice())
                            .communityId(payMessageContext.getCommunityId())
                            .userId(payMessageContext.getUserId())
                            .userName(payMessageContext.getUserName())
                            .communityName(payMessageContext.getCommunityName())
                            .id(payMessageContext.getId())
                            .orderId(payMessageContext.getOrderId())
                            .orderState(payMessageContext.getOrderState())
                            .build();
                    if (paymentTaskType.equals(PaymentTaskType.SAVE.getId())) {
                        System.out.println("保存中.......");
                        isSuccess = paymentOrderService.save(paymentOrder);
                    }
                }catch (Exception e){
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                if(isSuccess) {
                  updateWrapper.eq("order_id",orderId);
                  updateWrapper.set("order_state",state);
                  state = 3;
                }
                if(state == 3) {
                    paymentOrderService.update(updateWrapper);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
    }
}
