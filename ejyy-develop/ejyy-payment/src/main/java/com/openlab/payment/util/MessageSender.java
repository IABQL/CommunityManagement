package com.openlab.payment.util;

import com.openlab.payment.entity.PaymentOrder;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@Component
public class MessageSender {

    @Autowired
    ConsumerTask consumerTask;

    @Autowired
    PaymentTask paymentTask;


    @Async
    public Future<Boolean> send(PaymentOrder paymentOrder){
        try {
            consumerTask.consumer();
            paymentTask.produce(paymentOrder);
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(true);
    }
}
