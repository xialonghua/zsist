package com.jiafang.action.client;

import java.util.Map;

import com.jiafang.action.JSONAction;
import com.jiafang.model.Order;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.model.User;
import com.jiafang.service.OrderService;
import com.jiafang.service.ProductService;
import com.opensymphony.xwork2.ActionContext;

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

	@Action(value = "submitOrder", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
    public String submitOrder() {
        Map<String, Object> session = getSession();

        User user = (User) session.get("user");
        setData(orderService.submitOrder(user.getId(), order));
        return RETURN_JSON;
    }

    @Action(value = "getOrders", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
    public String getOrders() {
        Map<String, Object> session = getSession();

        User user = (User) session.get("user");
        setData(orderService.getOrders(user.getId()));
        return RETURN_JSON;
    }

    @Action(value = "cancelOrders", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
    public String cancelOrder() {
        Map<String, Object> session = getSession();

        User user = (User) session.get("user");
        setData(orderService.cancelOrder(user.getId(), order.getId()));
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
}
