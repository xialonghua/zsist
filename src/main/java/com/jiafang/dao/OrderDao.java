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

    Order getOrder(Integer userId, Integer orderId);

    List<Order> getOrders(Integer userId, Page page);
    List<Order> getOrders(Integer userId, Integer orderStatus, Page page);

    void updateOrderStatus(Integer userId, Integer orderId, Integer orderStatus);

}
