package com.jiafang.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.PicDao;
import com.jiafang.model.Pic;
import com.jiafang.service.PicService;

@Service
@Transactional
public class PicServiceImpl implements PicService{
	@Autowired
	private PicDao picDao;
	
	
	@Override
	public BaseResp getProductPics(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Pic> pics = picDao.getProductPics(pic.getType(), pic.getProductId());
		resp.setData(pics);
		return resp;
	}

	@Override
	public BaseResp getCompanyPics(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Pic> pics = picDao.getCompanyPics(pic.getType(), pic.getCompanyId());
		resp.setData(pics);
		return resp;
	}

	@Override
	public BaseResp addPic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = picDao.savePic(pic);
		resp.setData(pic);
		return resp;
	}

	@Override
	public BaseResp updatePic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = picDao.updatePic(pic);
		resp.setData(pic);
		return resp;
	}

	@Override
	public BaseResp deletePic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = picDao.deletePic(pic);
		resp.setData(pic);
		return resp;
	}

}
