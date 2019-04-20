package com.zj.order.controller;

import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.cloud.netflix.hystrix.HystrixProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HystrixController {

    //服务熔断配置
    //    @HystrixCommand(commandProperties ={
//            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //开启熔断
//            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
//            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "1000"),
//            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
//    })
//超时配置
//    @HystrixCommand(commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//    })
    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/getProduct")
    public String getProcut(@RequestParam("num") Integer num) {
        if (num % 2 == 0) {
            return "success";
        } else {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject("http://127.0.0.1:8082/product/getProductById?id=26", String.class);
            return response;
        }
    }

    public String fallback() {

        return "太拥挤了，请稍后重试！";

    }

}
