package com.jiafang.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jiafang.dao.UserDao;
import com.jiafang.model.*;
import com.jiafang.service.Page;
import com.jiafang.service.bean.PayInfo;
import com.jiafang.util.AliPayUtils;
import com.jiafang.util.PushUtil;
import com.jiafang.util.StringUtil;
import com.jiafang.util.weixin.com.tenpay.ClientRequestHandler;
import com.jiafang.util.weixin.com.tenpay.PrepayIdRequestHandler;
import com.jiafang.util.weixin.com.tenpay.util.ConstantUtil;
import com.jiafang.util.weixin.com.tenpay.util.WXUtil;
import com.jiafang.util.weixin.com.tenpay.util.XMLUtil;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.jdom.JDOMException;
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

import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

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
        if (sub == null) {
            resp.setCode(SUB_PRODUCT_NOT_FOUND);
            return resp;
        }
        Product product = productDao.querySimpleById(sub.getProductId());
        if (product == null) {
            resp.setCode(PRODUCT_NOT_FOUND);
            return resp;
        }
        Company company = companyDao.querySimpleById(product.getCompanyId());
        if (company == null) {
            resp.setCode(COMPANY_NOT_FOUND);
            return resp;
        }
        Cart cart = orderDao.getCartBySubProductId(userId, subProductId);
        if (cart == null) {
            cart = orderDao.addCart(userId, subProductId, sub.getProductId(), product.getCompanyId(), count);
        } else {
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
        order.setOrderNum((1000000000L + userId) + "" + System.currentTimeMillis() + "" + (int) (Math.random() * 100));
        order.setCreateTime(System.currentTimeMillis());

        if (StringUtils.isEmpty(order.getProvince())
                || StringUtils.isEmpty(order.getCity())
                || StringUtils.isEmpty(order.getArea())
                || StringUtils.isEmpty(order.getAddress())) {
            resp.setCode(ADDRESS_NOT_FOUND);
            return resp;
        }
        List<OrderProduct> products = order.getProducts();
        if (products == null || products.size() == 0) {
            resp.setCode(INVALIDAT_REQUEST);
            return resp;
        }

        BigDecimal totalPrice = new BigDecimal(0);
        Integer companyId = null;
        Integer sellerId = null;
        for (OrderProduct orderProduct : products) {
            Product p = productDao.queryById(orderProduct.getProductId());
            if (p == null) {
                resp.setCode(PRODUCT_NOT_FOUND);
                return resp;
            }
            if (companyId != null && companyId != p.getCompanyId()) {
                if (p == null) {
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
            if (discountPrice == null) {
                discountPrice = p.getPrice();
            }
            if (discountPrice == null) {
                discountPrice = 0.0;
            }
            Integer count = orderProduct.getCount();
            if (count == null) {
                count = 0;
            }

            totalPrice = totalPrice.add(new BigDecimal(discountPrice).multiply(new BigDecimal(count)));
        }
        order.setTotalPrice(totalPrice.doubleValue());
        order.setCompanyId(companyId);
        order.setPayType(null);

        Company company = companyDao.queryById(companyId);
        if (company == null){
            resp.setCode(COMPANY_NOT_FOUND);
            return resp;
        }
        sellerId =  company.getUserId();

        order.setSellerId(sellerId);

        orderDao.saveOrder(order);

        PushUtil.newOrder(sellerId, order.getId());
        resp.setData(order);
        return resp;
    }

    @Override
    public BaseResp cancelOrder(User user, Integer orderId) {
        Integer userId = user.getId();
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        Order order = orderDao.getOrder(userId, orderId);
        if (order == null) {
            resp.setCode(ORDER_NOT_FOUND);
            return resp;
        }
        Integer status = order.getOrderState();
        if (status.intValue() == OrderState.已完成.ordinal()) {

            resp.setCode(ORDER_ALREADY_FINISHED);
            return resp;
        }
        if (status.intValue() == OrderState.已发货.ordinal()) {

            resp.setCode(ORDER_ALREADY_DISPATCH);
            return resp;
        }
        if (status.intValue() == OrderState.订单关闭.ordinal()) {

            resp.setCode(ORDER_ALREADY_CLOSE);
            return resp;
        }
        if (status.intValue() == OrderState.订单取消.ordinal()) {

            resp.setCode(ORDER_ALREADY_CANCEL);
            return resp;
        }
        if (status.intValue() == OrderState.已付款.ordinal()) {

            resp.setCode(ORDER_ALREADY_PAY);
            return resp;
        }
        orderDao.updateOrderStatus(userId, orderId, OrderState.订单取消.ordinal());

        PushUtil.cancelOrder(order.getSellerId(), orderId, user.getNickname());
        resp.setCode(SUCCESS);
        return resp;
    }

    @Override
    public BaseResp pay(Integer userId, Integer orderId, Integer payType) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        Order order = orderDao.getOrder(userId, orderId);
        if (order == null) {
            resp.setCode(ORDER_NOT_FOUND);
            return resp;
        }
        Integer status = order.getOrderState();
        if (status.intValue() == OrderState.已完成.ordinal()) {

            resp.setCode(ORDER_ALREADY_FINISHED);
            return resp;
        }
        if (status.intValue() == OrderState.已发货.ordinal()) {

            resp.setCode(ORDER_ALREADY_DISPATCH);
            return resp;
        }
        if (status.intValue() == OrderState.订单关闭.ordinal()) {

            resp.setCode(ORDER_ALREADY_CLOSE);
            return resp;
        }
        if (status.intValue() == OrderState.订单取消.ordinal()) {

            resp.setCode(ORDER_ALREADY_CANCEL);
            return resp;
        }
        if (status.intValue() == OrderState.已付款.ordinal()) {

            resp.setCode(ORDER_ALREADY_PAY);
            return resp;
        }
        StringBuffer sb = new StringBuffer();
        //TODO 创建订单
        PayInfo info = new PayInfo();
        info.setPayType(payType);
        if (payType == PAY_ALI) {
            resp.setCode(PAY_UNKOWN_TYPE);
            return resp;
        } else if (payType == PAY_WEIXIN){

            PrepayIdRequestHandler prepayReqHandler = new PrepayIdRequestHandler(ServletActionContext.getRequest(), ServletActionContext.getResponse());//获取prepayid的请求类
            ClientRequestHandler clientHandler = new ClientRequestHandler(ServletActionContext.getRequest(), ServletActionContext.getResponse());//返回客户端支付参数的请求类

            int retcode;
            String retmsg = "";
            String xml_body = "";
            //获取token值
            String noncestr = WXUtil.getNonceStr();
            String timestamp = WXUtil.getTimeStamp();
            String traceid = "";
            ////设置获取prepayid支付参数
            prepayReqHandler.setParameter("appid", ConstantUtil.APP_ID);
            prepayReqHandler.setParameter("mch_id", ConstantUtil.PARTNER);
            prepayReqHandler.setParameter("nonce_str", noncestr);
            prepayReqHandler.setParameter("body", order.getExtraInfo());
            prepayReqHandler.setParameter("out_trade_no", order.getOrderNum());
            BigDecimal   b   =   new   BigDecimal(order.getTotalPrice());
            prepayReqHandler.setParameter("total_fee", "1");
            prepayReqHandler.setParameter("spbill_create_ip", "180.173.10.80");//ServletActionContext.getRequest().getRemoteAddr());
            prepayReqHandler.setParameter("notify_url", "http://180.173.10.80:22900/zsist/order/wechatPayCallback");
            prepayReqHandler.setParameter("trade_type", "APP");


            //生成获取预支付签名
            String sign = prepayReqHandler.createMD5();
            //增加非参与签名的额外参数
            prepayReqHandler.setParameter("sign", sign);
            String gateUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            prepayReqHandler.setGateUrl(gateUrl);

            //获取prepayId
            String prepayid = null;
            Map<String, String> map = null;
            try {
                prepayid = prepayReqHandler.sendPrepay();
                map = XMLUtil.doXMLParse(prepayid);
            } catch (Exception e) {
                e.printStackTrace();
                resp.setCode(SYSTEM_ERROR);
                return resp;
            }
            String code = map.get("return_code");
            if ("SUCCESS".equals(code)){

                String id = map.get("prepay_id");
                Map<String, Object> map1 = new HashMap<>();
                map1.put("prepay_id", id);
                map1.put("wechatResult", map);

                resp.setData(map1);
                resp.setCode(SUCCESS);
                return resp;
            }else {
                resp.setCode(PAY_DISCONNECT);
                return resp;
            }

        } else {
            resp.setCode(PAY_UNKOWN_TYPE);
            return resp;
        }
    }

    @Override
    public BaseResp queryPayStatus(User user, Integer orderId, String payNum, String aliResult) {
        BaseResp resp = new BaseResp();

        Integer userId = user.getId();

        Order order = orderDao.getOrder(userId, orderId);
        if (order == null) {
            resp.setCode(ORDER_NOT_FOUND);
            return resp;
        }

        if (order.getOrderState() != OrderState.未付款.ordinal()){
            resp.setCode(SUCCESS);
            resp.setData(order);
            return resp;
        }else {
            Map<String, String> params = StringUtil.parseParam(aliResult);
            //TODO check sign
            String orderNum = params.get("out_trade_no");
            if (!order.getOrderNum().equals(orderNum)){
                resp.setCode(INVALIDAT_REQUEST);
                return resp;
            }
            AlipayTradeQueryResponse response = AliPayUtils.query(orderNum);
            if (response == null){
                resp.setCode(SYSTEM_ERROR);
                return resp;
            }
            String tradeStatus = response.getTradeStatus();
            if (tradeStatus.equals("TRADE_SUCCESS")){
                order.setPayNum(response.getTradeNo());
                order.setPayAccount(response.getBuyerUserId());
                order.setPayType(PAY_ALI);
                order.setPayTime(System.currentTimeMillis());
                order.setOrderState(OrderState.已付款.ordinal());

                orderDao.updateOrderStatus(order.getUserId(), order.getId(), OrderState.已付款.ordinal());
                orderDao.updateOrderPayInfo(order.getUserId(), order.getId(), order.getPayAccount(), order.getPayNum(), order.getPayType(), order.getPayTime());
                PushUtil.payOrder(order.getSellerId(), order.getId(), user.getNickname());
                resp.setCode(SUCCESS);
                resp.setData(order);
                return resp;
            }
        }

        resp.setCode(SUCCESS);
        resp.setData(order);
        return resp;
    }

    @Override
    public BaseResp getOrders(Integer userId, Integer orderStatus, Page page) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        List<Order> orders = null;
        if (orderStatus == null || orderStatus.intValue() == -1) {
            orders = orderDao.getOrders(userId, page);
        } else {
            orders = orderDao.getOrders(userId, orderStatus, page);

        }

        if (orders == null) {
            orders = new ArrayList<>();
        }
        for (Order o : orders) {
            o.setProducts(orderDao.getOrderProducts(o.getId()));
        }

        resp.setData(orders);
        return resp;
    }

    @Override
    public BaseResp getSellerOrders(Integer userId, Integer orderStatus, Page page) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        List<Order> orders = null;
        if (orderStatus == null || orderStatus.intValue() == -1) {
            orders = orderDao.getSellerOrders(userId, page);
        } else {
            orders = orderDao.getSellerOrders(userId, orderStatus, page);

        }

        if (orders == null) {
            orders = new ArrayList<>();
        }
        for (Order o : orders) {
            o.setProducts(orderDao.getOrderProducts(o.getId()));
        }

        resp.setData(orders);
        return resp;
    }

    @Override
    public BaseResp getOrder(Integer userId, Integer orderId) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        Order order = orderDao.getOrder(userId, orderId);
        if (order == null){
            resp.setCode(ORDER_NOT_FOUND);
            return resp;
        }
        order.setProducts(orderDao.getOrderProducts(order.getId()));

        resp.setData(order);
        return resp;
    }

    @Override
    public BaseResp sendOrder(User user, Integer orderId, String logistics) {
        Integer userId = user.getId();
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        Order order = orderDao.getSellerOrder(userId, orderId);
        if (order == null) {
            resp.setCode(ORDER_NOT_FOUND);
            return resp;
        }
        Integer status = order.getOrderState();
        if (status.intValue() == OrderState.已完成.ordinal()) {

            resp.setCode(ORDER_ALREADY_FINISHED);
            return resp;
        }
        if (status.intValue() == OrderState.已发货.ordinal()) {

            resp.setCode(ORDER_ALREADY_DISPATCH);
            return resp;
        }
        if (status.intValue() == OrderState.订单关闭.ordinal()) {

            resp.setCode(ORDER_ALREADY_CLOSE);
            return resp;
        }
        if (status.intValue() == OrderState.订单取消.ordinal()) {

            resp.setCode(ORDER_ALREADY_CANCEL);
            return resp;
        }
        if (status.intValue() == OrderState.未付款.ordinal()) {

            resp.setCode(ORDER_ALREADY_NOT_PAY);
            return resp;
        }
        if (status.intValue() == OrderState.订单关闭.ordinal()) {

            resp.setCode(ORDER_ALREADY_CLOSE);
            return resp;
        }

        orderDao.updateOrderStatusBySeller(userId, orderId, OrderState.已发货.ordinal());

        orderDao.updateOrderLogisticsInfo(userId, orderId, logistics);

        PushUtil.sendOrder(order.getUserId(), order.getId(), order.getExtraInfo(), user.getNickname());
        resp.setCode(SUCCESS);
        return resp;
    }

    @Override
    public BaseResp receiveOrder(User user, Integer orderId) {
        Integer userId = user.getId();
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        Order order = orderDao.getOrder(userId, orderId);
        if (order == null) {
            resp.setCode(ORDER_NOT_FOUND);
            return resp;
        }
        Integer status = order.getOrderState();
        if (status.intValue() == OrderState.已完成.ordinal()) {

            resp.setCode(ORDER_ALREADY_FINISHED);
            return resp;
        }
        if (status.intValue() == OrderState.订单关闭.ordinal()) {

            resp.setCode(ORDER_ALREADY_CLOSE);
            return resp;
        }
        if (status.intValue() == OrderState.订单取消.ordinal()) {

            resp.setCode(ORDER_ALREADY_CANCEL);
            return resp;
        }
        if (status.intValue() == OrderState.未付款.ordinal()) {

            resp.setCode(ORDER_ALREADY_NOT_PAY);
            return resp;
        }
        if (status.intValue() == OrderState.订单关闭.ordinal()) {

            resp.setCode(ORDER_ALREADY_CLOSE);
            return resp;
        }

        orderDao.updateOrderStatus(userId, orderId, OrderState.已完成.ordinal());

        PushUtil.receiveOrder(order.getSellerId(), orderId, order.getExtraInfo(), user.getNickname());
        resp.setCode(SUCCESS);
        return resp;
    }

    private List<CartBean> cartToCartbean(List<Cart> carts) {
        List<CartBean> cbs = new ArrayList<>();

        for (Cart c : carts) {
            CartBean cb = new CartBean();
            cb.setCompany(companyDao.querySimpleById(c.getCompanyId()));
            int index = cbs.indexOf(cb);
            if (index < 0) {
                cbs.add(cb);
            } else {
                cb = cbs.get(index);
            }
            cb.getCarts().add(c);
            Product p = productDao.querySimpleById(c.getProductId());
            if (!cb.getProducts().contains(p)) {
                cb.getProducts().add(p);
            }

            SubProduct sp = productDao.getSubProduct(c.getSubProductId());
            if (!cb.getSubProduts().contains(p)) {
                cb.getSubProduts().add(sp);
                cb.setTotalPrice(cb.getTotalPrice() + sp.getDiscountPrice() * c.getCount());
            }
        }
        return cbs;
    }

    public String handleAliPayCallback(HttpServletRequest request){
        String sign = request.getParameter("sign");
        String totalFee = request.getParameter("total_fee");
        String tradeNo = request.getParameter("trade_no");
        String tradeStatus = request.getParameter("trade_status");
        String outTradeNo = request.getParameter("out_trade_no");
        String buyerEmail = request.getParameter("buyer_email");
        String buyerId = request.getParameter("buyer_id");

        if (tradeStatus.equals("TRADE_FINISHED")){
            return "SUCCESS";
        }

        Map<String, String[]> map = request.getParameterMap();

        Iterator<String> it = map.keySet().iterator();
        List<String> ps = new ArrayList<>();
        while (it.hasNext()){
            String key = it.next();
            String param = request.getParameter(key);

            if (key.equals("sign") || key.equals("sign_type")){
                continue;
            }
            ps.add(key);
        }
        Collections.sort(ps);

        StringBuffer sb = null;
        for (String s : ps){
            if (sb == null){
                sb = new StringBuffer();
                sb.append(s + "=" + request.getParameter(s));
            }else {
                sb.append("&").append(s + "=" + request.getParameter(s));
            }
        }
//        try {
//            sign = URLDecoder.decode(sign, "utf-8");
//            if (!StringUtil.verify(sb.toString().getBytes("utf-8"), secret, sign)){
//                return "SUCCESS";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Map<String, String> p = new HashMap<>();
//        p.put("service", "notify_verify");
//        p.put("partner", "2088121694110155");
//        p.put("notify_id", request.getParameter("notify_id"));
//        String str = ApiCall.get("https://mapi.alipay.com/gateway.do", p);
//        if (!"true".equalsIgnoreCase(str)){
//            return "SUCCESS";
//        }


        Order order = orderDao.getOrder(outTradeNo);
        if (order == null){
            return "SUCCESS";
        }
        if (order.getOrderState() != OrderState.未付款.ordinal()){
            return "SUCCESS";
        }

//        if (!totalFee.equals(String.valueOf(order.getTotalPrice()))){
//            return "SUCCESS";
//        }
        if (tradeStatus.equals("TRADE_SUCCESS")){
            order.setPayNum(tradeNo);
            order.setPayAccount(buyerId);
            order.setPayType(PAY_ALI);
            order.setPayTime(System.currentTimeMillis());

            orderDao.updateOrderStatus(order.getUserId(), order.getId(), OrderState.已付款.ordinal());
            orderDao.updateOrderPayInfo(order.getUserId(), order.getId(), order.getPayAccount(), order.getPayNum(), order.getPayType(), order.getPayTime());

            User buyer = userDao.queryByUserId(order.getUserId());
            String nickname = "";
            if (buyer == null){
                nickname = "";
            }else {
                nickname = buyer.getNickname();
            }


            PushUtil.payOrder(order.getSellerId(), order.getId(), nickname);

            return "SUCCESS";
        }
//		Map<String, Object> session = getSession();
//
//		User user = (User) session.get("user");
//
//		setData(orderService.pay(user.getId(), order.getId(), order.getPayType()));
        return "SUCCESS";
    }

    @Override
    public String handleWeChatPayCallback(HttpServletRequest request) {
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();

            byte[] buffer = new byte[2048];
            int readBytes = 0;
            StringBuilder stringBuilder = new StringBuilder();
            while((readBytes = inputStream.read(buffer)) > 0){
                stringBuilder.append(new String(buffer, 0, readBytes));
            }
            System.out.println(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        PushUtil.payOrder(order.getSellerId(), order.getId(), nickname);
        return "SUCCESS";
    }
}
