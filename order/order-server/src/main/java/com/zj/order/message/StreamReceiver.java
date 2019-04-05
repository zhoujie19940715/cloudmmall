package com.zj.order.message;

import com.zj.order.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    public void processInput(Object message){
        log.info("hhhh:message：{}",message);
    }

   /* @StreamListener(StreamClient.OUTPUT)
    public void process(Object message){
        log.info("message：{}",message);
    }*/

   @StreamListener(StreamClient.OUTPUT) //接收消息
   @SendTo(StreamClient.INPUT)
   public String process(Order order){
       log.info("message：{}",order);
       return "I got it.";
   }
}
