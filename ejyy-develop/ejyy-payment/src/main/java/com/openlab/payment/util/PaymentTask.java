package com.openlab.payment.util;

import com.openlab.payment.entity.PaymentOrder;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Component
public class PaymentTask {

   public void produce(PaymentOrder paymentOrder) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
       TransactionMQProducer producer = new TransactionMQProducer("payment");
       // 事务监听器
       TransactionListener transactionListener = new TransactionListenerImpl();
       producer.setNamesrvAddr("192.168.119.139:9876");

       producer.setRetryTimesWhenSendAsyncFailed(3);
       producer.setTransactionListener(transactionListener);

       producer.start();
       String[] tag = {"save"};
       String paymentTaskType = PaymentTaskType.SAVE.getId();
       PayMessageContext payMessageContext = PayMessageContext.builder()
               .paymentPrice(paymentOrder.getPaymentPrice())
               .paymentTime(paymentOrder.getPaymentTime())
               .paymentType(paymentOrder.getPaymentType())
               .paymentRemainPrice(paymentOrder.getPaymentRemainPrice())
               .communityId(paymentOrder.getCommunityId())
               .userId(paymentOrder.getUserId())
               .userName(paymentOrder.getUserName())
               .communityName(paymentOrder.getCommunityName())
               .id(paymentOrder.getId())
               .orderId(paymentOrder.getOrderId())
               .orderState(OrderState.DEALING.getState())
               .paymentTaskType(paymentTaskType)
               .build();
       System.out.println(payMessageContext);
       ByteArrayOutputStream obj = new ByteArrayOutputStream();
       ObjectOutputStream out = null;
       try {
           out = new ObjectOutputStream(obj);
           out.writeObject(payMessageContext);
       } catch (IOException e) {
           e.printStackTrace();
       }

       Message message = new Message("pay-save",tag[0],
               obj.toByteArray());
       SendResult result = producer.sendMessageInTransaction(message,null);

       System.out.println("发送消息："+result);
       producer.shutdown();
   }
}
