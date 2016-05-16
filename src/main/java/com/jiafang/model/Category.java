package com.jiafang.model;
// Generated 2015-10-5 22:12:51 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "Category")
public class Category extends BaseTime implements java.io.Serializable {

	private Integer id;
	private String name;
	private Integer type;
	private String avatar;
	private String video;
	
	private String mark;//表示某个product是否在类中

	public Category() {
	}

	public Category(String name, Integer type, String avatar, String video) {
		this.name = name;
		this.type = type;
		this.avatar = avatar;
		this.video = video;
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
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", length = 45)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
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
	
	
	@Transient
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
