package com.jiafang.service.bean;

import com.jiafang.model.Area;
import com.jiafang.model.City;
import com.jiafang.model.Province;

import java.util.List;

public class PayInfo {

	private Integer payType;//0-weixin 1-ali
	private String orderNum;
    private String payKey;


    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }
}
