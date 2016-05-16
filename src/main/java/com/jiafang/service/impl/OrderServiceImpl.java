package com.jiafang.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.gotye.entity.*;
import com.jiafang.dao.UserDao;
import com.jiafang.model.*;
import com.jiafang.service.bean.Ad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.CompanyDao;
import com.jiafang.dao.OrderDao;
import com.jiafang.dao.ProductDao;
import com.jiafang.service.OrderService;
import com.jiafang.service.bean.CartBean;

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
	public BaseResp submitOrder(Integer userId, String orderIds, Integer addressId) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);

        String[] ids = orderIds.split(",");
        List<Integer> idss = new ArrayList<>();
        for (String id : ids){
            idss.add(Integer.valueOf(id));
        }

        Address address = userDao.getAddressById(userId, addressId);
        if (address == null){
            resp.setCode(ADDRESS_NOT_FOUND);
            return resp;
        }

        List<Cart> carts = orderDao.getCartsByCartsId(userId, idss);
        List<CartBean> cbs = cartToCartbean(carts);

        for (CartBean bean : cbs){

            List<Cart> cs = bean.getCarts();
            StringBuffer sb = new StringBuffer();
            for (Cart c : cs){
                sb.append(c.getId()).append(",");
            }
            if (sb.toString().contains(",")){
                sb.substring(0, sb.length() - 1);
            }

            Order order = new Order();
            order.setAddress(address.getAddress());
            order.setName(address.getName());
            order.setTel(address.getTel());
            order.setZipcode(address.getZipcode());
            order.setCartIds(sb.toString());
            order.setUserId(userId);
            order.setTotalPrice(bean.getTotalPrice());
            order.setOrderState(OrderState.未付款.ordinal());
            order.setOrderNum(System.currentTimeMillis() + "" + userId);

            orderDao.saveOrder(order);
            orderDao.updateCartsOrderId(idss, order.getId());
        }
        resp.setCode(SUCCESS);
        return resp;
	}

	@Override
	public BaseResp cancelOrder(Integer userId, Integer orderId) {
		return null;
	}

	@Override
	public BaseResp getOrders(Integer userId) {
		return null;
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
