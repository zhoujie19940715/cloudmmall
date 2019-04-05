package com.zj.order.controller;

import com.zj.order.message.StreamClient;
import com.zj.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    /*@GetMapping("/sendMessage")
    public void process1(){
        streamClient.output().send(MessageBuilder.withPayload("now:"+new Date()).build());

    }*/
   @GetMapping("/sendMessage")
   public void process1(){
       Order order = new Order();
       order.setId(888888);
       streamClient.output().send(MessageBuilder.withPayload(order).build());
   }
}
