package com.zj.order.message;

import com.zj.product.common.JsonUtil;
import com.zj.product.common.ProductOutput;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ProcuctReceiver {

    public static final String PRODUCT_STOCK_TEMPLATE= "product_stock_%s";

    public static final String PRODUCT_TEMPLATE= "product_%s";


    @Autowired
    private StringRedisTemplate redisTemplate;


    @RabbitListener(queuesToDeclare = @Queue("product"))
    public void process(String message){
        List<ProductOutput> productList = JsonUtil.string2Obj(message,
                new TypeReference<List<ProductOutput>>() {});
        log.info("从队列【{}】中，接受到消息{}","product",productList);
        //todo 将产品的状态和库存数量储存到redis中，下次订单服务获取商品数量时，直接从redis中取
        for (ProductOutput productOutput : productList) {
            redisTemplate.opsForValue().set(String.format(PRODUCT_STOCK_TEMPLATE,productOutput.getId()),
                    String.valueOf(productOutput.getStock()));

            Map map = new ConcurrentHashMap();
            map.put("stock",productOutput.getStock());
            map.put("status",productOutput.getStatus());
            map.put("name",productOutput.getName());
            redisTemplate.opsForHash().putAll(String.format(PRODUCT_TEMPLATE,productOutput.getId()),map);

        }

    }
}
