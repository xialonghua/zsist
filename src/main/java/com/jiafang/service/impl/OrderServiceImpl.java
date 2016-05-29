package com.jiafang.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.gotye.entity.*;
import com.jiafang.dao.UserDao;
import com.jiafang.model.*;
import com.jiafang.service.Page;
import com.jiafang.service.bean.Ad;
import com.jiafang.service.bean.PayInfo;
import com.jiafang.util.StringUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.CompanyDao;
import com.jiafang.dao.OrderDao;
import com.jiafang.dao.ProductDao;
import com.jiafang.service.OrderService;
import com.jiafang.service.bean.CartBean;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class OrderServiceImpl implements OrderService{
    @Autowired
    private UserDao userDao;

	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ProductDao productDao;
	
	@Override
	public BaseResp addCart(Integer userId, Integer subProductId, int count) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		SubProduct sub = productDao.getSubProduct(subProductId);
		if(sub == null){
			resp.setCode(SUB_PRODUCT_NOT_FOUND);
			return resp;
		}
		Product product = productDao.querySimpleById(sub.getProductId());
		if(product == null){
			resp.setCode(PRODUCT_NOT_FOUND);
			return resp;
		}
		Company company = companyDao.querySimpleById(product.getCompanyId());
		if(company == null){
			resp.setCode(COMPANY_NOT_FOUND);
			return resp;
		}
		Cart cart = orderDao.getCartBySubProductId(userId, subProductId);
		if(cart == null){
			cart = orderDao.addCart(userId, subProductId, sub.getProductId(), product.getCompanyId(), count);
		}else {
			cart = orderDao.modifyCart(userId, cart.getId(), subProductId, sub.getProductId(), product.getCompanyId(), count);
		}
		
		CartBean b = new CartBean();
		
		List<SubProduct> subProduts = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		List<Cart> carts = new ArrayList<>();
		
		carts.add(cart);
		subProduts.add(sub);
		products.add(product);
		
		b.setCarts(carts);
		b.setCompany(company);
		b.setProducts(products);
		b.setSubProduts(subProduts);
		
		resp.setData(b);
		return resp;
	}
	@Override
	public BaseResp delCart(Integer userId, Integer cartId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		
		Cart cart = orderDao.getCart(userId, cartId);
		if (cart == null) {
			resp.setCode(SUCCESS);
			return resp;
		}
		orderDao.delCart(userId, cartId);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp getCarts(Integer userId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Cart> carts = orderDao.getCarts(userId);
        List<CartBean> cbs = cartToCartbean(carts);
		resp.setData(cbs);
		return resp;
	}

	@Override
	public BaseResp submitOrder(Integer userId, Order order) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);

		order.setUserId(userId);
		order.setOrderState(OrderState.未付款.ordinal());
		order.setOrderNum((10000000L + userId) + "" + System.currentTimeMillis() + "" + (int)(Math.random() * 100));
		order.setCreateTime(System.currentTimeMillis());

		if (StringUtils.isEmpty(order.getProvince())
				|| StringUtils.isEmpty(order.getCity())
				|| StringUtils.isEmpty(order.getArea())
				|| StringUtils.isEmpty(order.getAddress())){
			resp.setCode(ADDRESS_NOT_FOUND);
			return resp;
		}
		List<OrderProduct> products = order.getProducts();
		if (products == null || products.size() == 0){
			resp.setCode(INVALIDAT_REQUEST);
			return resp;
		}

		BigDecimal totalPrice = new BigDecimal(0);
		Integer companyId = null;
		for (OrderProduct orderProduct : products){
			Product p = productDao.queryById(orderProduct.getProductId());
			if (p == null){
				resp.setCode(PRODUCT_NOT_FOUND);
				return resp;
			}
			if (companyId != null && companyId != p.getCompanyId()){
				if (p == null){
					resp.setCode(INVALIDAT_REQUEST);
					return resp;
				}
			}
			companyId = p.getCompanyId();

			orderProduct.setDiscountPrice(p.getDiscountPrice());
			orderProduct.setPrice(p.getPrice());
			orderProduct.setName(p.getName());
			orderProduct.setAvatar(p.getAvatar());
			orderProduct.setVideo(p.getVideo());
			orderProduct.setOrderNum(order.getOrderNum());

			Double discountPrice = p.getDiscountPrice();
			if (discountPrice == null){
				discountPrice = p.getPrice();
			}
			if (discountPrice == null){
				discountPrice = 0.0;
			}
			Integer count = p.getCount();
			if (count == null){
				count = 0;
			}

			totalPrice = totalPrice.add(new BigDecimal(discountPrice).multiply(new BigDecimal(count)));
		}
		order.setTotalPrice(totalPrice.doubleValue());
		order.setCompanyId(companyId);
		order.setPayType(null);

		orderDao.saveOrder(order);
		resp.setData(order);
        return resp;
	}

	@Override
	public BaseResp cancelOrder(Integer userId, Integer orderId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		Order order = orderDao.getOrder(userId, orderId);
		if (order == null){
			resp.setCode(ORDER_NOT_FOUND);
			return resp;
		}
		Integer status = order.getOrderState();
		if (status.intValue() == OrderState.已完成.ordinal()){

			resp.setCode(ORDER_ALREADY_FINISHED);
			return resp;
		}
		if (status.intValue() == OrderState.已发货.ordinal()){

			resp.setCode(ORDER_ALREADY_DISPATCH);
			return resp;
		}
		if (status.intValue() == OrderState.订单关闭.ordinal()){

			resp.setCode(ORDER_ALREADY_CLOSE);
			return resp;
		}
		if (status.intValue() == OrderState.订单取消.ordinal()){

			resp.setCode(ORDER_ALREADY_CANCEL);
			return resp;
		}
		if (status.intValue() == OrderState.已付款.ordinal()){

			resp.setCode(ORDER_ALREADY_PAY);
			return resp;
		}
		orderDao.updateOrderStatus(userId, orderId, OrderState.订单取消.ordinal());
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp pay(Integer userId, Integer orderId, Integer payType) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		Order order = orderDao.getOrder(userId, orderId);
		if (order == null){
			resp.setCode(ORDER_NOT_FOUND);
			return resp;
		}
		Integer status = order.getOrderState();
		if (status.intValue() == OrderState.已完成.ordinal()){

			resp.setCode(ORDER_ALREADY_FINISHED);
			return resp;
		}
		if (status.intValue() == OrderState.已发货.ordinal()){

			resp.setCode(ORDER_ALREADY_DISPATCH);
			return resp;
		}
		if (status.intValue() == OrderState.订单关闭.ordinal()){

			resp.setCode(ORDER_ALREADY_CLOSE);
			return resp;
		}
		if (status.intValue() == OrderState.订单取消.ordinal()){

			resp.setCode(ORDER_ALREADY_CANCEL);
			return resp;
		}
		if (status.intValue() == OrderState.已付款.ordinal()){

			resp.setCode(ORDER_ALREADY_PAY);
			return resp;
		}
		//TODO 创建订单

		PayInfo info = new PayInfo();
		info.setPayType(payType);
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp getOrders(Integer userId, Integer orderStatus, Page page) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Order> orders = null;
		if (orderStatus == null || orderStatus.intValue() == -1){
			orders = orderDao.getOrders(userId, page);
		}else {
			orders = orderDao.getOrders(userId, orderStatus, page);

		}

		if (orders == null){
			orders = new ArrayList<>();
		}
		for (Order o : orders){
			o.setProducts(orderDao.getOrderProducts(o.getId()));
		}

		resp.setData(orders);
		return resp;
	}

    private List<CartBean> cartToCartbean(List<Cart> carts){
        List<CartBean> cbs = new ArrayList<>();

        for(Cart c : carts){
            CartBean cb = new CartBean();
            cb.setCompany(companyDao.querySimpleById(c.getCompanyId()));
            int index = cbs.indexOf(cb);
            if(index < 0){
                cbs.add(cb);
            }else {
                cb = cbs.get(index);
            }
            cb.getCarts().add(c);
            Product p = productDao.querySimpleById(c.getProductId());
            if(!cb.getProducts().contains(p)){
                cb.getProducts().add(p);
            }

            SubProduct sp = productDao.getSubProduct(c.getSubProductId());
            if(!cb.getSubProduts().contains(p)){
                cb.getSubProduts().add(sp);
                cb.setTotalPrice(cb.getTotalPrice() + sp.getDiscountPrice() * c.getCount());
            }
        }
        return cbs;
    }
}
