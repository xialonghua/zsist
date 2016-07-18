package com.jiafang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.jiafang.model.Order;
import com.jiafang.model.OrderProduct;
import com.jiafang.service.Page;
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
		query.add(Restrictions.and(Restrictions.eq("id", orderId), Restrictions.or(Restrictions.eq("sellerId", userId), Restrictions.eq("userId", userId))));
		return (Order) query.uniqueResult();
	}

	@Override
	public Order getSellerOrder(Integer sellerId, Integer orderId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.and(Restrictions.eq("sellerId", sellerId), Restrictions.eq("id", orderId)));
		return (Order) query.uniqueResult();
	}

	@Override
	public Order getOrder(String orderNum) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
		query.add(Restrictions.and(Restrictions.eq("orderNum", orderNum)));
		return (Order) query.uniqueResult();
	}

	@Override
    public List<OrderProduct> getOrderProducts(Integer orderId) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(OrderProduct.class);
        query.add(Restrictions.and(Restrictions.eq("orderId", orderId)));
        return query.list();
    }

    @Override
    public List<Order> getOrders(Integer userId, Page page) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
        query.add(Restrictions.and(Restrictions.eq("userId", userId))).addOrder(org.hibernate.criterion.Order.desc("createTime"));
        query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
        return query.list();
    }

    @Override
    public List<Order> getSellerOrders(Integer userId, Page page) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
        query.add(Restrictions.and(Restrictions.eq("sellerId", userId))).addOrder(org.hibernate.criterion.Order.desc("createTime"));
        query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
        return query.list();
    }

    @Override
    public List<Order> getOrders(Integer userId, Integer orderStatus, Page page) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
        query.add(Restrictions.and(Restrictions.eq("userId", userId), Restrictions.eq("orderState", orderStatus))).addOrder(org.hibernate.criterion.Order.desc("createTime"));
        query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
        return query.list();
    }

    @Override
    public List<Order> getSellerOrders(Integer userId, Integer orderStatus, Page page) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(Order.class);
        query.add(Restrictions.and(Restrictions.eq("sellerId", userId), Restrictions.eq("orderState", orderStatus))).addOrder(org.hibernate.criterion.Order.desc("createTime"));
        query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
        return query.list();
    }

	@Override
	public void updateOrderStatus(Integer userId, Integer orderId, Integer orderStatus) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		String hql="update Orders set orderState=? where id=? and userId=?";
		Query query=session.createSQLQuery(hql);
		query.setInteger(0, orderStatus);
		query.setInteger(1, orderId);
		query.setInteger(2, userId);
		query.executeUpdate();
		session.getTransaction().commit();

	}

	@Override
	public void updateOrderStatusBySeller(Integer userId, Integer orderId, Integer orderStatus) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		String hql="update Orders set orderState=? where id=? and sellerId=?";
		Query query=session.createSQLQuery(hql);
		query.setInteger(0, orderStatus);
		query.setInteger(1, orderId);
		query.setInteger(2, userId);
		query.executeUpdate();
		session.getTransaction().commit();

	}

    @Override
    public void updateOrderLogisticsInfo(Integer userId, Integer orderId, String logisticsInfo) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        String hql="update Orders set logisticsInfo=? where id=? and sellerId=?";
        Query query=session.createSQLQuery(hql);
        query.setString(0, logisticsInfo);
        query.setInteger(1, orderId);
        query.setInteger(2, userId);
        query.executeUpdate();
        session.getTransaction().commit();

    }

	@Override
	public void updateOrderPayInfo(Integer userId, Integer orderId, String payAccount, String payNo, Integer payType, Long payTime) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		String hql="update Orders set payAccount=?, payNum=?, payType=?, payTime=? where id=? and userId=?";
		Query query=session.createSQLQuery(hql);
		query.setString(0, payAccount);
		query.setString(1, payNo);
        query.setInteger(2, payType);
        query.setLong(3, payTime == null ? 0 : payTime);
		query.setInteger(4, orderId);
		query.setInteger(5, userId);
		query.executeUpdate();
		session.getTransaction().commit();

	}


}
