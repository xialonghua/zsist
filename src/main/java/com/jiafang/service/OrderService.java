package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Order;

public interface OrderService extends Constants{

	BaseResp submitOrder(Integer userId, Order order);

	BaseResp cancelOrder(Integer userId, Integer orderId);

	BaseResp pay(Integer userId, Integer orderId, Integer payType);

    BaseResp getOrders(Integer userId, Integer orderStatus, Page page);

	BaseResp addCart(Integer userId, Integer subProductId, int count);
	
	BaseResp delCart(Integer userId, Integer subProductId);
	
	BaseResp getCarts(Integer userId);
}
