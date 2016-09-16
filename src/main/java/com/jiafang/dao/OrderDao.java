package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Cart;
import com.jiafang.model.Order;
import com.jiafang.model.OrderProduct;
import com.jiafang.service.Page;

public interface OrderDao {

	Cart addCart(Integer userId, Integer subProductId, Integer productId, Integer companyId, int count);
	
	void delCart(Integer userId, Integer cartId);
	
	Cart modifyCart(Integer userId, Integer cartId, Integer subProductId, Integer productId, Integer companyId, int count);

	List<Cart> getCarts(Integer userId);

	Cart getCart(Integer userId, Integer cartId);
	
	Cart getCartBySubProductId(Integer userId, Integer subProductId);

	List<OrderProduct> getOrderProducts(Integer orderId);
	Order saveOrder(Order order);

    public void updateOrderLogisticsInfo(Integer userId, Integer orderId, String logisticsInfo);

	Order getOrder(Integer userId, Integer orderId);
	public Order getSellerOrder(Integer sellerId, Integer orderId);

	Order getOrder(String orderNum);

	List<Order> getOrders(Integer userId, Page page, Integer platform);
	List<Order> getOrdersByCompanyId(Integer userId, Integer companyId, Page page, Integer platform);
    List<Order> getSellerOrders(Integer userId, Page page, Integer platform);
	List<Order> getOrders(Integer userId, Integer orderStatus, Page page, Integer platform);
	List<Order> getOrdersByCompanyId(Integer userId, Integer orderStatus, Integer companyId, Page page, Integer platform);
    List<Order> getSellerOrders(Integer userId, Integer orderStatus, Page page, Integer platform);

	public void updateOrderStatusBySeller(Integer userId, Integer orderId, Integer orderStatus);
    void updateOrderStatus(Integer userId, Integer orderId, Integer orderStatus);
    void updateOrderPayInfo(Integer userId, Integer orderId, String payAccount, String payNo, Integer payType, Long payTime, String from);

}
