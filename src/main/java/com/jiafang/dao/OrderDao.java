package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Cart;
import com.jiafang.model.Order;

public interface OrderDao {

	Cart addCart(Integer userId, Integer subProductId, Integer productId, Integer companyId, int count);
	
	void delCart(Integer userId, Integer cartId);
	
	Cart modifyCart(Integer userId, Integer cartId, Integer subProductId, Integer productId, Integer companyId, int count);

	List<Cart> getCarts(Integer userId);

    List<Cart> getCartsByCartsId(Integer userId, List<Integer> cartsId);

    List<Order> getOrdersByUserId(Integer userId);
	
	Cart getCart(Integer userId, Integer cartId);
	
	Cart getCartBySubProductId(Integer userId, Integer subProductId);
	
	void delCartsByUserId(Integer userId);

    void updateCartsOrderId(List<Integer> cartsId, Integer orderId);

    Order saveOrder(Order order);
}
