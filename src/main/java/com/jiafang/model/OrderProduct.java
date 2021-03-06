package com.jiafang.model;
// Generated 2015-10-5 22:12:51 by Hibernate Tools 4.3.1.Final

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Product generated by hbm2java
 */
@Entity
@Table(name = "OrderProduct")
public class OrderProduct extends BaseTime implements java.io.Serializable {

    private String orderNum;
    private Integer productId;
    private Integer productSizeId;
    private Integer orderId;

    private Integer id;
    private String name;
    private String avatar;
    private String video;
    private Double price;
    private Double discountPrice;
    private Integer count;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name", length = 45)
    public String getName() {
        //return StringUtil.decode(this.name);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "avatar", length = 45)
    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column(name = "video", length = 45)
    public String getVideo() {
        return this.video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Column(name = "price", precision = 22, scale = 0)
    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "discountPrice", precision = 22, scale = 0)
    public Double getDiscountPrice() {
        return this.discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getProductSizeId() {
        return productSizeId;
    }

    public void setProductSizeId(Integer productSizeId) {
        this.productSizeId = productSizeId;
    }

}
