package com.jiafang.action.client;

import java.net.URLDecoder;
import java.util.*;

import com.jiafang.action.JSONAction;
import com.jiafang.model.Order;
import com.jiafang.service.Page;
import com.jiafang.util.ApiCall;
import com.jiafang.util.StringUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.json.bridge.StringBridge;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.model.User;
import com.jiafang.service.OrderService;
import com.jiafang.service.ProductService;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

@ParentPackage("basePackage")
@Namespace("/order")
public class OrderAction extends JSONAction {

	@Autowired
	private ProductService productService;
	@Autowired
	private OrderService orderService;
	
	private Integer subProductId;
	private Integer count;
	private Integer cartId;

    private Integer addressId;
    private String cartIds;//逗号隔开
	private Order order;
    private Page page;
	private String aliResult;

	@Action(value = "submitOrder", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String submitOrder() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");
		setData(orderService.submitOrder(user.getId(), order));
		return RETURN_JSON;
	}

	@Action(value = "sendOrder", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String sendOrder() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");
		setData(orderService.sendOrder(user, order.getId(), order.getLogisticsInfo()));
		return RETURN_JSON;
	}

	@Action(value = "receiveOrder", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String receiveOrder() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");
		setData(orderService.receiveOrder(user, order.getId()));
		return RETURN_JSON;
	}

	@Action(value = "getOrders", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String getOrders() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");
		Integer orderStatus = null;
		if (order == null){
			orderStatus = -1;
		}else {
			orderStatus = order.getOrderState();
		}
		setData(orderService.getOrders(user.getId(), orderStatus, page));
		return RETURN_JSON;
	}

	@Action(value = "getSellerOrders", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String getSellerOrders() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");
		Integer orderStatus = null;
		if (order == null){
			orderStatus = -1;
		}else {
			orderStatus = order.getOrderState();
		}
		setData(orderService.getSellerOrders(user.getId(), orderStatus, page));
		return RETURN_JSON;
	}


	@Action(value = "getOrderDetail", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String getOrderDetail() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");
		setData(orderService.getOrder(user.getId(), order.getId()));
		return RETURN_JSON;
	}

    @Action(value = "cancelOrder", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
    public String cancelOrder() {
        Map<String, Object> session = getSession();

        User user = (User) session.get("user");
        setData(orderService.cancelOrder(user, order.getId()));
        return RETURN_JSON;
    }
	
	@Action(value = "addCart", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String addCart() {
	    Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");
		setData(orderService.addCart(user.getId(), subProductId, count));
		return RETURN_JSON;
	}
	
	@Action(value = "delCart", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String delCart() {
		Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");
		setData(orderService.delCart(user.getId(), cartId));
		return RETURN_JSON;
	}
	
	@Action(value = "modifyCart", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String modifyCart() {
		Map<String, Object> session = getSession();
	    
	    User user = (User) session.get("user");
		setData(orderService.addCart(user.getId(), subProductId, count));
		return RETURN_JSON;
	}

    @Action(value = "getCarts", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
    public String getCarts() {
        Map<String, Object> session = getSession();

        User user = (User) session.get("user");

        setData(orderService.getCarts(user.getId()));
        return RETURN_JSON;
    }

	@Action(value = "pay", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String pay() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");

		setData(orderService.pay(user.getId(), order.getId(), order.getPayType()));
		return RETURN_JSON;
	}

	@Action(value = "queryPayStatus", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
	public String queryPayStatus() {
		Map<String, Object> session = getSession();

		User user = (User) session.get("user");

		setData(orderService.queryPayStatus(user, order.getId(), order.getPayNum(), aliResult));
		return RETURN_JSON;
	}

	@Action(value = "aliPayCallback", results = { @Result(name = "SUCCESS", location = "/success.html") })
	public String aliPayCallback() {
		HttpServletRequest request = ServletActionContext.getRequest();

		return orderService.handleAliPayCallback(request);
	}

	@Action(value = "wechatPayCallback", results = { @Result(name = "SUCCESS", location = "/success.html") })
	public String wechatPayCallback() {
		HttpServletRequest request = ServletActionContext.getRequest();

		return orderService.handleWeChatPayCallback(request);
	}

	public Integer getSubProductId() {
		return subProductId;
	}

	public void setSubProductId(Integer subProductId) {
		this.subProductId = subProductId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCartId() {
		return cartId;
	}

	public void setCartId(Integer cartId) {
		this.cartId = cartId;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getCartIds() {
        return cartIds;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

	public String getAliResult() {
		return aliResult;
	}

	public void setAliResult(String aliResult) {
		this.aliResult = aliResult;
	}
}
