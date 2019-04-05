package com.zj.product.service;

import com.github.pagehelper.PageInfo;
import com.zj.product.common.ServerResponse;
import com.zj.order.pojo.OrderItem;
import com.zj.product.pojo.Product;
import com.zj.product.vo.ProductDetailVo;

import java.util.List;

/**
 * Created by geely
 */
public interface IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> search(String productName, Integer productId, int pageNum, int pageSize);

    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy);


    /**
     * 减少库存
     * @param orderItemList
     */
    void reduceProductStock(List<OrderItem> orderItemList);

    Product getProductById(Integer id);


}
