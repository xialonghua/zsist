package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Order;
import com.jiafang.model.User;

import javax.servlet.http.HttpServletRequest;

public interface OrderService extends Constants{

	BaseResp submitOrder(Integer userId, Order order);

	BaseResp cancelOrder(User user, Integer orderId);

    BaseResp pay(Integer userId, Integer orderId, Integer payType, String payAccount);

    BaseResp queryStatus(User user, Integer orderId, Integer payType);
    BaseResp queryPayStatus(User user, Integer orderId);
    BaseResp queryWeChatPayStatus(User user, Integer orderId);

    BaseResp getOrders(Integer userId, Integer orderStatus, Page page);
    BaseResp getOrders(Integer userId, Integer orderStatus, Integer companyId, Page page);
    BaseResp getSellerOrders(Integer userId, Integer orderStatus, Page page);
    BaseResp getOrder(Integer userId, Integer orderId);
    BaseResp sendOrder(User user, Integer orderId, String logistics);
    BaseResp receiveOrder(User user, Integer orderId);

	BaseResp addCart(Integer userId, Integer subProductId, int count);
	
	BaseResp delCart(Integer userId, Integer subProductId);
	
	BaseResp getCarts(Integer userId);

    String handleAliPayCallback(HttpServletRequest request);
    String handleWeChatPayCallback(HttpServletRequest request);
}
