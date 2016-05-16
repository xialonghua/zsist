package com.jiafang.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.CategoryDao;
import com.jiafang.dao.CompanyDao;
import com.jiafang.dao.ProductDao;
import com.jiafang.model.Category;
import com.jiafang.model.CategoryRelationship;
import com.jiafang.model.CategoryType;
import com.jiafang.model.Categoryable;
import com.jiafang.model.Company;
import com.jiafang.model.Pic;
import com.jiafang.model.Product;
import com.jiafang.model.SubProduct;
import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;
import com.jiafang.service.bean.Ad;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private CompanyDao companyDao;
	
	
	@Override
	public BaseResp getCategorieById(int id) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(categoryDao.queryById(id));
		return resp;
	}
	
	@Override
	public BaseResp getCategories(Page page) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(categoryDao.queryByType(CategoryType.COMMON.ordinal(), new Page(0, 9999)));
		return resp;
	}
	
	@Override
	public BaseResp getCategoriesByType(Integer type,Page page) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(categoryDao.queryByType(type, new Page(0, 9999)));
		return resp;
	}
	
	@Override
	public BaseResp getCategoriesOfMark(Integer type,Integer productId) {
		BaseResp resp = new BaseResp();
		List<Category> cats = categoryDao.queryByType(type, new Page(0, 9999));
		for(Category temp : cats){
			if(categoryDao.queryShipIdByProductCategory(productId, temp.getId())!=null){
				temp.setMark("mark");
			}
		}
		resp.setData(cats);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp getZones(Integer count) {
		List<Category> cats = categoryDao.queryByType(CategoryType.ZONE.ordinal(), new Page(0, 9999));
		List<Ad> ads = new ArrayList<Ad>();
		if(count == null){
			count = 4;
		}
		for(Category cat : cats){
			Ad ad = new Ad();
			ad.setCategory(cat);
			List<Categoryable> catable = new ArrayList<Categoryable>();
			List<CategoryRelationship> ships = categoryDao.queryRelationshipByCatrgoryId(new Page(0, count), cat.getId());
			for(CategoryRelationship ship : ships){
				if(ship.getProductId() != null){
					Product p = productDao.queryById(ship.getProductId());
					p.setCategoryShips(categoryDao.queryRelationshipsByProductId(p.getId()));
					ship.setAvatar(p.getAvatar());
					ship.setVideo(p.getVideo());
					p.setCategoryShip(ship);
					catable.add(p);
				}else if(ship.getCompanyId() != null){
					Company c = companyDao.queryById(ship.getCompanyId());
					c.setCategoryShips(categoryDao.queryRelationshipsByCompanyId(c.getId()));
					ship.setAvatar(c.getAvatar());
					ship.setVideo(c.getVideo());
					c.setCategoryShip(ship);
					catable.add(c);
				}
			}
			
			ad.setItems(catable);
			ads.add(ad);
		}
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(ads);
		return resp;
	}

	@Override
	public BaseResp getAdsProduct() {
		
//		List<Pic> pics = productDao.queryAllPicsByType(1);
//		for(Pic p : pics){
//			SubProduct subProduct = new SubProduct();
//			subProduct.setAvatar(p.getUrl());
//			subProduct.setVideo(p.getVideo());
//			subProduct.setName(p.getName());
//			subProduct.setProductId(p.getProductId());
//			productDao.saveSubProduct(subProduct);
//		}
		
		List<Category> cats = categoryDao.queryByType(CategoryType.AD_PRODUCT.ordinal(), new Page(0, 9999));
		List<Ad> ads = new ArrayList<Ad>();
		
		for(Category cat : cats){
			Ad ad = new Ad();
			ad.setCategory(cat);
			List<Categoryable> catable = new ArrayList<Categoryable>();
			List<CategoryRelationship> ships = categoryDao.queryRelationshipByCatrgoryId(new Page(0, 999), cat.getId());
			for(CategoryRelationship ship : ships){
				if(ship.getProductId() != null){
					Product p = productDao.queryById(ship.getProductId());
					p.setCategoryShips(categoryDao.queryRelationshipsByProductId(p.getId()));
					
					ship.setAvatar(p.getAvatar());
					ship.setVideo(p.getVideo());
					
					p.setCategoryShip(ship);
					catable.add(p);
				}else if(ship.getCompanyId() != null){
					Company c = companyDao.queryById(ship.getCompanyId());
					c.setCategoryShips(categoryDao.queryRelationshipsByCompanyId(c.getId()));
					ship.setAvatar(c.getAvatar());
					ship.setVideo(c.getVideo());
					
					c.setCategoryShip(ship);
					catable.add(c);
				}
			}
			
			ad.setItems(catable);
			ads.add(ad);
		}
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(ads);
		return resp;
	}

	@Override
	public BaseResp getAdsCompany() {
		List<Category> cats = categoryDao.queryByType(CategoryType.AD_COMPANY.ordinal(), new Page(0, 9999));
		List<Ad> ads = new ArrayList<Ad>();
		
		for(Category cat : cats){
			Ad ad = new Ad();
			ad.setCategory(cat);
			List<Categoryable> catable = new ArrayList<Categoryable>();
			List<CategoryRelationship> ships = categoryDao.queryRelationshipByCatrgoryId(new Page(0, 999), cat.getId());
			for(CategoryRelationship ship : ships){
				if(ship.getProductId() != null){
					Product p = productDao.queryById(ship.getProductId());
					p.setCategoryShips(categoryDao.queryRelationshipsByProductId(p.getId()));

					ship.setAvatar(p.getAvatar());
					ship.setVideo(p.getVideo());
					
					p.setCategoryShip(ship);
					catable.add(p);
				}else if(ship.getCompanyId() != null){
					Company c = companyDao.queryById(ship.getCompanyId());
					c.setCategoryShips(categoryDao.queryRelationshipsByCompanyId(c.getId()));
					
					ship.setAvatar(c.getAvatar());
					ship.setVideo(c.getVideo());
					
					c.setCategoryShip(ship);
					catable.add(c);
				}
			}
			
			ad.setItems(catable);
			ads.add(ad);
		}
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(ads);
		return resp;
	}
	
	
	
	
	
	
	@Override
	public BaseResp saveCategory(Category category) {
		BaseResp resp = new BaseResp();
		category = categoryDao.saveCategory(category);
		resp.setData(category);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp updateCategory(Category category) {
		BaseResp resp = new BaseResp();
		category = categoryDao.updateCategory(category);
		resp.setData(category);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	
	@Override
	public BaseResp deleteCategory(Category category) {
		BaseResp resp = new BaseResp();
		categoryDao.deleteRelationshipByCategoryId(category.getId());
		categoryDao.deleteCategory(category);
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp getItemsById(int id, Page page) {
		Category cat = categoryDao.queryById(id);
		List<Ad> ads = new ArrayList<Ad>();
		
		Ad ad = new Ad();
		ad.setCategory(cat);
		List<Categoryable> catable = new ArrayList<Categoryable>();
		List<CategoryRelationship> ships = categoryDao.queryRelationshipByCatrgoryId(new Page(0, 999), cat.getId());
		for(CategoryRelationship ship : ships){
			if(ship.getProductId() != null){
				Product p = productDao.queryById(ship.getProductId());
				p.setCategoryShips(categoryDao.queryRelationshipsByProductId(p.getId()));
				
				ship.setAvatar(p.getAvatar());
				ship.setVideo(p.getVideo());
				
				p.setCategoryShip(ship);
				catable.add(p);
			}else if(ship.getCompanyId() != null){
				Company c = companyDao.queryById(ship.getCompanyId());
				c.setCategoryShips(categoryDao.queryRelationshipsByCompanyId(c.getId()));
				
				ship.setAvatar(c.getAvatar());
				ship.setVideo(c.getVideo());
				
				c.setCategoryShip(ship);
				catable.add(c);
			}
		}
		
		ad.setItems(catable);
		ads.add(ad);
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(ads);
		return resp;
	}
	
	@Override
	public BaseResp getItemByShipId(int shipId) {
		Categoryable categoryable = null;
		CategoryRelationship ship = categoryDao.queryRelationshipByShipId(shipId);
		if(ship.getProductId() != null){
			Product p = productDao.queryById(ship.getProductId());
			p.setCategoryShips(categoryDao.queryRelationshipsByProductId(p.getId()));
			
			ship.setAvatar(p.getAvatar());
			ship.setVideo(p.getVideo());
			
			p.setCategoryShip(ship);
			categoryable=p;
			
		}else if(ship.getCompanyId() != null){
			Company c = companyDao.queryById(ship.getCompanyId());
			c.setCategoryShips(categoryDao.queryRelationshipsByCompanyId(c.getId()));
			
			ship.setAvatar(c.getAvatar());
			ship.setVideo(c.getVideo());
			
			c.setCategoryShip(ship);
			categoryable=c;
		}
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(categoryable);
		return resp;
	}


	@Override
	public BaseResp addRelationship(CategoryRelationship ship) {
		BaseResp resp = new BaseResp();
		Integer pId = ship.getProductId();
		Integer cId = ship.getCompanyId();
		if(pId!=null){
			if(categoryDao.queryShipIdByProductCategory(pId,ship.getCategoryId())!=null){
				resp.setDescription("已经绑定到类中");
				resp.setCode(500);
				return resp;
			}
		}
		if(cId!=null){
			if(categoryDao.queryShipIdByCompanyCategory(cId,ship.getCategoryId())!=null){
				resp.setDescription("已经绑定到类中");
				resp.setCode(500);
				return resp;
			}
		}
		ship = categoryDao.saveRelationship(ship);
		resp.setData(ship);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp addAdvertisementRelationship(int categoryId,String productIds) {
		BaseResp resp = new BaseResp();
		Set<Integer> pIdSet = new HashSet<Integer>();
		String[] pIds = productIds.split(",");
		for(int i=0;i<pIds.length;i++){
			pIdSet.add(Integer.valueOf(pIds[i]));
		}
		
		for(Integer pId : pIdSet){
			if(categoryDao.queryShipIdByProductCategory(pId,categoryId)==null){
				CategoryRelationship ship = new CategoryRelationship();
				ship.setCategoryId(categoryId);
				ship.setProductId(pId);
				categoryDao.saveRelationship(ship);
			}
		}
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp deleteRelationship(CategoryRelationship ship) {
		BaseResp resp = new BaseResp();
		categoryDao.deleteRelationship(ship);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp deleteRelationshipById(Integer categoryId,Integer productId){
		BaseResp resp = new BaseResp();
		categoryDao.deleteRelationshipByCategoryIdProductId(categoryId, productId);
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp updateRelationshipWeight(CategoryRelationship ship) {
		BaseResp resp = new BaseResp();
		categoryDao.updateRelationshipWeight(ship);
		resp.setCode(SUCCESS);
		return resp;
	}

}
