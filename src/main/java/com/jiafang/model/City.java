package com.jiafang.model;
// Generated 2015-10-5 22:12:51 by Hibernate Tools 4.3.1.Final

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Pic generated by hbm2java
 */
@Entity
@Table(name = "Cities")
public class City extends BaseTime implements java.io.Serializable {

	private Integer id;
	private Integer cityId;
	private Integer provinceId;
	private String name;

	@Column(name = "city", length = 45)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City() {
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

	@Column(name = "provinceid")
	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	@Column(name = "cityid")
	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}