package com.jiafang.model;
// Generated 2015-10-5 22:12:51 by Hibernate Tools 4.3.1.Final

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Company generated by hbm2java
 */
@Entity
@Table(name = "CompanyPayInfo")
public class CompanyPayInfo extends BaseTime implements java.io.Serializable {

	private Integer id;
	private Integer payType;
	private String pk;
	private String sk;
	private String partnerId;
	private Integer companyId;

	public CompanyPayInfo() {
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

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(length = 1024)
	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}
	@Column(length = 1024)
	public String getSk() {
		return sk;
	}

	public void setSk(String sk) {
		this.sk = sk;
	}
	@Column(length = 1024)
	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
}