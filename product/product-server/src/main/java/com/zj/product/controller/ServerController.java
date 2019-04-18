package com.zj.product.controller;

import com.zj.product.common.OrderItemInput;
import com.zj.product.common.ProductOutput;
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
    public void reduceProductStock(@RequestBody List<OrderItemInput> orderItemList){
        productService.reduceProductStock(orderItemList);
    }

    @GetMapping("/getProductById")
    public ProductOutput getProcutById(@RequestParam("id") Integer id){

        return productService.getProductById(id);
    }


}
