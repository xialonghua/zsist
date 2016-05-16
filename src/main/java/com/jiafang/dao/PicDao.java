package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Pic;

public interface PicDao {
	
	public List<Pic> getProductPics(Integer type, Integer productId);
	
	public List<Pic> getCompanyPics(Integer type, Integer companyId);
	
	public Pic savePic(Pic pic);
	
	public Pic updatePic(Pic pic);
	
	public Pic deletePic(Pic pic);
	
	public void deletePicsByCompanyId(Integer companyId);
	public void deletePicsByProductId(Integer productId);
}
