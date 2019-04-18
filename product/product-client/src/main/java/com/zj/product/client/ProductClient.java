package com.zj.product.client;
import com.zj.product.common.OrderItemInput;
import com.zj.product.common.ProductOutput;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
//name = "product" name表示访问哪个应用的接口
@FeignClient(name = "product",fallback = ProductClient.ProductClientFallback.class )
public interface ProductClient {

    @GetMapping("/product/msg")
    String msg();

    @PostMapping("/product/reduceProductStock")
    void reduceProductStock(@RequestBody List<OrderItemInput> orderItemList);

    @GetMapping("/product/getProductById")
    ProductOutput getProcutById(@RequestParam("id") Integer id);

    //接口出现问题就会访问一下触发以下相应服务降级方法
    @Component
    static class ProductClientFallback implements ProductClient{

        @Override
        public String msg() {
            return null;
        }

        @Override
        public void reduceProductStock(List<OrderItemInput> orderItemList) {

        }

        @Override
        public ProductOutput getProcutById(Integer id) {
            return null;
        }
    }
}
