package com.zj.product.client;
import com.zj.product.common.OrderItemInput;
import com.zj.product.common.ProductOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/product/msg")
    String msg();

    @PostMapping("/product/reduceProductStock")
    void reduceProductStock(@RequestBody List<OrderItemInput> orderItemList);

    @GetMapping("/product/getProductById")
    ProductOutput getProcutById(@RequestParam("id") Integer id);
}
