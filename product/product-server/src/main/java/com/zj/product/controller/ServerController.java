package com.zj.product.controller;

import com.zj.order.pojo.OrderItem;
import com.zj.product.pojo.Product;
import com.zj.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ServerController {
    @Autowired
    private IProductService productService;

    @GetMapping("/msg")
    public String msg(){
        return "we will we will rock you!";
    }


    @PostMapping("/reduceProductStock")
    public void reduceProductStock(@RequestBody List<OrderItem> orderItemList){
        productService.reduceProductStock(orderItemList);
    }

    @GetMapping("/getProductById")
    public Product getProcutById(@RequestParam("id") Integer id){

        return productService.getProductById(id);
    }


}
