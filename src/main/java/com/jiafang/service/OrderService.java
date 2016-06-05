package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Order;

import javax.servlet.http.HttpServletRequest;

public interface OrderService extends Constants{

	BaseResp submitOrder(Integer userId, Order order);

	BaseResp cancelOrder(Integer userId, Integer orderId);

    BaseResp pay(Integer userId, Integer orderId, Integer payType);

    BaseResp queryPayStatus(Integer userId, Integer orderId, String payNum, String aliResult);

    BaseResp getOrders(Integer userId, Integer orderStatus, Page page);
    BaseResp getSellerOrders(Integer userId, Integer orderStatus, Page page);
    BaseResp getOrder(Integer userId, Integer orderId);
    BaseResp sendOrder(Integer userId, Integer orderId, String logistics);
    BaseResp receiveOrder(Integer userId, Integer orderId);

	BaseResp addCart(Integer userId, Integer subProductId, int count);
	
	BaseResp delCart(Integer userId, Integer subProductId);
	
	BaseResp getCarts(Integer userId);

    String handleAliPayCallback(HttpServletRequest request);
    String handleWeChatPayCallback(HttpServletRequest request);
}
