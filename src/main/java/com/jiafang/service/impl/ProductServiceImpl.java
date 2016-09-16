package com.jiafang.service.impl;

import java.util.List;

import com.jiafang.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.CategoryDao;
import com.jiafang.dao.ProductDao;
import com.jiafang.service.Page;
import com.jiafang.service.ProductService;
import com.jiafang.dao.CompanyDao;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao productDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public BaseResp getProductsByCategory(Page page, Integer categoryId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByCatrgoryId(page, categoryId, platform);
		resp.setData(products);
		return resp;
	}

	@Override
	public BaseResp getProductsByCategory(Page page, Integer categoryId, Integer companyId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByCatrgoryId(page, categoryId, companyId, platform);
		resp.setData(products);
		return resp;
	}


	@Override
	public BaseResp searchProducts(Page page, String name, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByName(page, name, platform);
		resp.setData(products);
		return resp;
	}

	@Override
	public BaseResp searchCompanies(Page page, String name) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Company> companies = companyDao.queryByName(page, name);
		resp.setData(companies);
		return resp;
	}

	@Override
	public BaseResp searchProductsByCategory(Page page, String name, Integer categoryId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByNameAndCategoryId(page, name, categoryId, platform);
		resp.setData(products);
		return resp;
	}

	@Override
	public BaseResp searchProductsByCategory(Page page, String name, Integer categoryId, Integer companyId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByNameAndCategoryId(page, name, categoryId, companyId, platform);
		resp.setData(products);
		return resp;
	}


	@Override
	public BaseResp getProductsByCompanyId(Page page, Integer companyId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByCompanyId(page, companyId, platform);
		for(Product obj : products){
			List<CategoryRelationship> ships = categoryDao.queryRelationshipsByProductId(obj.getId());
			obj.setCategoryShips(ships);
		}
		resp.setData(products);
		return resp;
	}


	@Override
	public BaseResp searchProductsByCompany(Page page, String name, Integer companyId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		List<Product> products = productDao.queryByNameAndCompanyId(page, name, companyId, platform);
		resp.setData(products);
		return resp;
	}
	
	@Override
	public BaseResp getProductsByCompanyUserId(Page page, Integer userId, int platform) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		Company company = companyDao.queryByUserId(userId);
		List<Product> products = productDao.queryByCompanyId(page, company.getId(), platform);
		for(Product obj : products){
			List<CategoryRelationship> ships = categoryDao.queryRelationshipsByProductId(obj.getId());
			for(CategoryRelationship temp : ships){
				Category cat = categoryDao.queryById(temp.getCategoryId());
				temp.setCategoryName(cat.getName());
			}
			obj.setCategoryShips(ships);
			
			if(company.getBrands()!=null&&company.getBrands().size()>0){
				obj.setBrandId(company.getBrands().get(0).getId());
				obj.setBrand(company.getBrands().get(0).getName());
			}
		}
		resp.setData(products);
		return resp;
	}


	@Override
	public BaseResp addProduct(Product product) {
		BaseResp resp = new BaseResp();
		product = productDao.saveProduct(product);
		List<CategoryRelationship> ships = product.getCategoryShips();
		for(CategoryRelationship ship : ships){
			if(ship!=null&&ship.getCategoryId()!=null){
				ship.setProductId(product.getId());
				ship.setAvatar(product.getAvatar());
				ship.setVideo(product.getVideo());
				ship.setCompanyId(product.getCompanyId());
				categoryDao.saveRelationship(ship);
			}
		}
		
		resp.setCode(SUCCESS);
		resp.setData(product);
		return resp;
	}


	@Override
	public BaseResp getProductById(Integer productId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		Product product = productDao.queryById(productId);
		Company company = companyDao.queryById(product.getCompanyId());
		if(company.getBrands()!=null&&company.getBrands().size()>0){
			product.setBrandId(company.getBrands().get(0).getId());
			product.setBrand(company.getBrands().get(0).getName());
		}

		product.setCategoryShips(categoryDao.queryRelationshipsByProductId(productId));
		resp.setData(product);
		return resp;
	}


	@Override
	public BaseResp modifyProduct(Product product) {
		BaseResp resp = new BaseResp();
		/*List<CategoryRelationship> ships = product.getCategoryShips();
		for(CategoryRelationship ship : ships){
			if(ship!=null&&ship.getCategoryId()!=null){
				//categoryDao.
			}
		}*/
		Product temp = productDao.queryById(product.getId());
		if(!StringUtils.isEmpty(product.getNum()) && !temp.getNum().equals(product.getNum())){
			int count = productDao.getProdunctNumberCount(product.getNum());
			if(count>0){
				resp.setCode(PRODUCT_NUM_EXSITS);
				return resp;
			}
		}
		
		product = productDao.updateProduct(product);
		resp.setCode(SUCCESS);
		resp.setData(product);
		return resp;
	}
	
	@Override
	public BaseResp deleteProduct(Product product){
		BaseResp resp = new BaseResp();
		product = productDao.queryById(product.getId());
		List<Pic> pics = product.getPics();
		List<Pic> descPics = product.getDescPics();
		List<Param> params = product.getParams();
		List<SubProduct> subProducts = product.getSubProduct();
		List<CategoryRelationship> ships = categoryDao.queryRelationshipsByProductId(product.getId());

        for(SubProduct obj : subProducts){
            for(ProductSize size : obj.getProductSizes()){
                productDao.deleteProductSize(size);
            }

            productDao.deleteSubProduct(obj);
        }
		for(Pic obj : pics){
			productDao.deletePic(obj);
		}
		for(Pic obj : descPics){
			productDao.deletePic(obj);
		}
		for(Param obj : params){
			productDao.deleteParam(obj);
		}
		for(CategoryRelationship obj : ships){
			categoryDao.deleteRelationship(obj);
		}
		productDao.deleteProduct(product);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp getProductParamById(Integer paramId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		Param param = productDao.getParamById(paramId);
		resp.setData(param);
		return resp;
	}


	@Override
	public BaseResp addProductParam(Param param) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		param = productDao.saveParam(param);
		resp.setData(param);
		return resp;
	}


	@Override
	public BaseResp updateProductParam(Param param) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		param = productDao.updateParam(param);
		resp.setData(param);
		return resp;
	}
	
	@Override
	public BaseResp deleteProductParam(Param param) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		param = productDao.deleteParam(param);
		resp.setData(param);
		return resp;
	}

	@Override
	public BaseResp addProductSize(ProductSize param) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		param = productDao.saveProductSize(param);
		resp.setData(param);
		return resp;
	}

	@Override
	public BaseResp updateProductSize(ProductSize param) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		param = productDao.updateProductSize(param);
		resp.setData(param);
		return resp;
	}

	@Override
	public BaseResp updateProductPlatform(Product product) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		productDao.updateProductPlatform(product);
		return resp;
	}

	@Override
	public BaseResp deleteProductSize(ProductSize param) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		param = productDao.deleteProductSize(param);
		resp.setData(param);
		return resp;
	}


	@Override
	public BaseResp addPic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = productDao.savePic(pic);
		resp.setData(pic);
		return resp;
	}
	
	@Override
	public BaseResp addPics(List<Pic> pics) {
		BaseResp resp = new BaseResp();
		productDao.savePics(pics);
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp updatePic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = productDao.updatePic(pic);
		resp.setData(pic);
		return resp;
	}
	
	@Override
	public BaseResp deletePic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = productDao.deletePic(pic);
		resp.setData(pic);
		return resp;
	}

	@Override
	public BaseResp getProductPics(Integer productId,Integer type) {
		BaseResp resp = new BaseResp();
		Product product = productDao.queryProductPics(productId,type);
		resp.setData(product);
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp getProducts(Page page) {
		BaseResp resp = new BaseResp();
		List<Product> products = productDao.queryProducts(page);
		resp.setData(products);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp getProdunctNumberCount(String num) {
		BaseResp resp = new BaseResp();
		Integer count = productDao.getProdunctNumberCount(num);
		resp.setData(count);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	@Override
	public BaseResp getSubProduct(Integer subId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		SubProduct sub = productDao.getSubProduct(subId);
		resp.setData(sub);
		return resp;
	}

	@Override
	public BaseResp saveSubProduct(SubProduct sub) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		sub = productDao.saveSubProduct(sub);
		resp.setData(sub);
		return resp;
	}


	@Override
	public BaseResp updateSubProduct(SubProduct sub) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		sub = productDao.updateSubProduct(sub);
		resp.setData(sub);
		return resp;
	}


	@Override
	public BaseResp deleteSubProduct(SubProduct sub) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		sub = productDao.deleteSubProduct(sub);
		resp.setData(sub);
		return resp;
	}
}
