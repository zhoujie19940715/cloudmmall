package com.zj.order.service;

import com.github.pagehelper.PageInfo;
import com.zj.order.common.ServerResponse;
import com.zj.order.pojo.Shipping;

/**
 * Created by geely
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId, Integer shippingId);

    ServerResponse update(Integer userId, Shipping shipping);

    ServerResponse<Shipping> select(Integer userId, Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
