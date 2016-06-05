package com.jiafang.action.resp;

import com.jiafang.common.Constants;
import com.jiafang.service.Page;

public class BaseResp implements Constants {

    private Integer code;
    private Object data;
    private String description;
    private Page page;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
        description = covertCode(code);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String covertCode(int code) {
        switch (code) {
            case SUCCESS:
                return "成功";
            case USER_NOT_FOUND:
                return "用户不存在";
            case WRONG_PASSWORD:
                return "密码错误";
            case USER_EXSITS:
                return "用户已存在";
            case INVALIDAT_REQUEST:
                return "非法请求";
            case USER_NOT_LOGIN:
                return "用户未登录";
            case INVALIDAT_CODE:
                return "无效的验证码";
            case CODE_EXPIRED:
                return "验证码过期";
            case PRODUCT_NUM_EXSITS:
                return "货号已存在";
            case COMPANY_NOT_FOUND:
                return "找不到公司";
            case PRODUCT_NOT_FOUND:
                return "找不到产品";
            case SUB_PRODUCT_NOT_FOUND:
                return "找不到分类";
            case ADDRESS_NOT_FOUND:
                return "找不到地址";
            case ORDER_NOT_FOUND:
                return "订单不存在";
            case ORDER_ALREADY_CANCEL:
                return "订单已取消";
            case ORDER_ALREADY_CLOSE:
                return "订单已关闭";
            case ORDER_ALREADY_FINISHED:
                return "订单已结束";
            case ORDER_ALREADY_DISPATCH:
                return "订单已发货";

            default:
                break;
        }
        return "";
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
