package com.jiafang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.jiafang.model.Order;
import com.jiafang.model.OrderProduct;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.OrderDao;
import com.jiafang.model.Cart;


@Repository
public class OrderDaoImpl implements OrderDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public OrderDaoImpl() {
	}

	@Override
	public Cart addCart(Integer userId, Integer subProductId, Integer productId, Integer companyId, int count) {
		Session query = sessionFactory.getCurrentSession();
		Cart cart = new Cart();
		cart.setCompanyId(companyId);
		cart.setProductId(productId);
		cart.setCount(count);
		cart.setSubProductId(subProductId);
		cart.setUserId(userId);
		query.save(cart);
		return cart;
	}

	@Override
	public void delCart(Integer userId, Integer cartId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		String hql="delete Cart as p where p.id=? and p.userId=? and p.orderId is null";
		Query query=session.createQuery(hql);
		query.setInteger(0, cartId);
		query.setInteger(1, userId);
		query.executeUpdate();
		session.getTransaction().commit();
	}


	@Override
	public Cart modifyCart(Integer userId, Integer cartId, Integer subProductId, Integer productId, Integer companyId, int count) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();  
		Cart cart = (Cart) session.load(Cart.class, cartId);
		cart.setId(cartId);
		cart.setCompanyId(companyId);
		cart.setProductId(productId);
		cart.setCount(count);
		cart.setSubProductId(subProductId);
		cart.setUserId(userId);
		session.flush();
	    session.saveOrUpdate(cart);
	    session.getTransaction().commit();
		return cart;
	}


	@Override
	public List<Cart> getCarts(Integer userId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Cart.class);
		query.add(Restrictions.and(Restrictions.eq("userId", userId), Restrictions.isNull("orderId")));
		return query.list();
	}


	@Override
	public Cart getCart(Integer userId, Integer cartId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Cart.class);
		query.add(Restrictions.and(Restrictions.eq("userId", userId), Restrictions.idEq(cartId), Restrictions.isNotNull("orderId")));
		return (Cart) query.uniqueResult();
	}


	@Override
	public Cart getCartBySubProductId(Integer userId, Integer subProductId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Cart.class);
		query.add(Restrictions.and(Restrictions.eq("userId", userId), Restrictions.eq("subProductId", subProductId), Restrictions.isNotNull("orderId")));
		return (Cart) query.uniqueResult();
	}

    @Override
    public Order saveOrder(Order order) {
        Session query = sessionFactory.getCurrentSession();
		Transaction transaction = query.beginTransaction();
        query.save(order);
		for (OrderProduct p : order.getProducts()){
			p.setOrderId(order.getId());
			query.save(p);
		}
		transaction.commit();
        return order;
    }

    @Override
    public Order getOrder(Integer userId, Integer orderId) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
        query.add(Restrictions.and(Restrictions.eq("userId", userId), Restrictions.eq("id", orderId)));
        return (Order) query.uniqueResult();
    }

    @Override
    public void updateOrderStatus(Integer userId, Integer orderId, Integer orderStatus) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql="update Orders set orderState=? where id=? and userId=?";
        Query query=session.createQuery(hql);
        query.setInteger(0, orderStatus);
        query.setInteger(1, orderId);
        query.setInteger(2, userId);
        query.executeUpdate();
        session.getTransaction().commit();

    }


}
