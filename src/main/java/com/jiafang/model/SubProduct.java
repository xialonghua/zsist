package com.jiafang.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "SubProduct")
public class SubProduct extends BaseTime implements java.io.Serializable {

	private Integer id;
	private String name;
	private String avatar;
	private String video;
	private Double price;
	private Double discountPrice;
	private Integer saleCount;
	private Integer count;
	private String description;
	private String num;
	private Integer productId;

	private List<ProductSize> productSizes;
	
	private String url;//等同于avatar，主要为了兼容老客户端
	
	public SubProduct() {
	}

	public SubProduct(String name, String avatar, String video, Double price, Double discountPrice, Integer saleCount,
			Integer count, String description) {
		this.name = name;
		this.avatar = avatar;
		this.video = video;
		this.price = price;
		this.discountPrice = discountPrice;
		this.saleCount = saleCount;
		this.count = count;
		this.description = description;
	}

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

	@Column(name = "saleCount")
	public Integer getSaleCount() {
		return this.saleCount;
	}

	public void setSaleCount(Integer saleCount) {
		this.saleCount = saleCount;
	}

	@Column(name = "count")
	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "description", length = 512)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 货号
	 * @return
	 */
	@Column
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Column
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Transient
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    @Transient
    public List<ProductSize> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }
}
