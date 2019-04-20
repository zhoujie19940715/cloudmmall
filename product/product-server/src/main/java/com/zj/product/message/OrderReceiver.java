package com.zj.product.message;

import com.zj.order.service.IOrderService;
import com.zj.product.common.JsonUtil;
import com.zj.product.common.OrderItemInput;
import com.zj.product.common.ProductOutput;
import com.zj.product.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class OrderReceiver {
    @Autowired
    private IProductService productService;

//    @RabbitListener(queuesToDeclare = @Queue("orderItemInputs"))
//    public void process(String message){
//        List<OrderItemInput> orderItemInputs = JsonUtil.string2Obj(message,
//                new TypeReference<List<OrderItemInput>>() {});
//        log.info("从队列【{}】中，接受到消息{}","orderItemInputs",orderItemInputs);
//        //减库存
//        productService.reduceProductStock(orderItemInputs);
//
//    }
}
