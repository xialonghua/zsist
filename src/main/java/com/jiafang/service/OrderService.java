package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;

public interface OrderService extends Constants{

	BaseResp submitOrder(Integer userId, String orderIds, Integer addressId);

    BaseResp cancelOrder(Integer userId, Integer orderId);

    BaseResp getOrders(Integer userId);

	BaseResp addCart(Integer userId, Integer subProductId, int count);
	
	BaseResp delCart(Integer userId, Integer subProductId);
	
	BaseResp getCarts(Integer userId);
}
