package com.zj.order.controller;

import com.zj.product.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class ClientController {

    @Autowired
    private ProductClient productClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;



//    @GetMapping("/msg")
//    public String getProductMsg(){
//        String response = productClient.msg();
//        log.info("response:={}",response);
//        return "ok";
//    }
    @GetMapping("/msg")
    public String getProductMsg(){
        //1.第一种
//        RestTemplate restTemplate = new RestTemplate();
//        String response = restTemplate.getForObject("http://localhost:8082/msg", String.class);

        RestTemplate restTemplate = new RestTemplate();
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");
        String url = String.format("http://%s:%s",serviceInstance.getHost(),serviceInstance.getPort());
        String response = restTemplate.getForObject(url,String.class);
        log.info("response:",response);
        return response;
    }

}
