package com.jiafang.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jiafang.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.CompanyDao;
import com.jiafang.dao.ProductDao;
import com.jiafang.dao.UserDao;
import com.jiafang.service.Page;
import com.jiafang.service.UserService;
import com.jiafang.util.IMUtil;
import com.jiafang.util.StringUtil;
import com.jiafang.util.sendsms;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ProductDao productDao;



	@Override
	public BaseResp login(String account, String password, Map<String, Object> session) {
		BaseResp resp = new BaseResp();
		User user = userDao.queryByUsername(account, account);
		if (user == null) {
			resp.setCode(USER_NOT_FOUND);
			return resp;
		}
		if (!user.getPassword().equals(password)) {
			resp.setCode(WRONG_PASSWORD);
			return resp;
		}

		resp.setCode(SUCCESS);

		if(user.getLevel() == UserLevel.COMPANY.ordinal()){
			Company company = companyDao.queryByUserId(user.getId());
			session.put("user_level_1_company", company);
		}
		session.put("user", user);

		resp.setData(user);
		return resp;
	}

	@Override
	public BaseResp registCompanyUserByWeb(User user, Company company, int verifyCode, Map<String, Object> session) {
		BaseResp resp = new BaseResp();

		Integer code = (Integer) session.get("verify_code_" + VerifyCodeType.REGIST.ordinal());
		Long expireTime = (Long) session.get("verify_code_create_time_" + VerifyCodeType.REGIST.ordinal());
		String tel = (String) session.get("verify_code_tel_" + VerifyCodeType.REGIST.ordinal());
		if (user.getTel() == null || !user.getTel().equals(tel)) {
			resp.setCode(INVALIDAT_REQUEST);
			return resp;
		}
		if (code == null || !code.equals(verifyCode)) {
			resp.setCode(INVALIDAT_CODE);
			return resp;
		}
		if (expireTime == null || System.currentTimeMillis() > expireTime) {
			resp.setCode(CODE_EXPIRED);
			return resp;
		}
		
		
		User dbUser = userDao.queryByUsername(user.getUsername(), user.getTel());
		if (dbUser != null) {
			resp.setCode(USER_EXSITS);
			resp.setDescription("用户已经存在");
			return resp;
		}
		user.setLevel(UserLevel.COMPANY.ordinal());
		user = userDao.saveUser(user);
		company.setUserId(user.getId());
		companyDao.saveCompany(company);
		resp.setCode(SUCCESS);
		resp.setData(company);
		IMUtil.importUser(user.getId());
        user.setId(company.getUserId());
        session.put("user", user);
        session.put("user_level_1_company", company);
		return resp;
	}
	
	
	@Override
	public BaseResp registCompanyUser(User user, Company company) {
		BaseResp resp = new BaseResp();
		User dbUser = userDao.queryByUsername(user.getUsername(), user.getTel());
		if (dbUser != null) {
			resp.setCode(USER_EXSITS);
			resp.setDescription("用户已经存在");
			return resp;
		}
		user.setLevel(UserLevel.COMPANY.ordinal());
		user = userDao.saveUser(user);
		company.setUserId(user.getId());
		companyDao.saveCompany(company);
		resp.setCode(SUCCESS);
		resp.setData(company);
		IMUtil.importUser(user.getId());
		return resp;
	}
	
	@Override
	public BaseResp registCommonUser(User user, int verifyCode, Map<String, Object> session) {
		BaseResp resp = new BaseResp();

		Integer code = (Integer) session.get("verify_code_" + VerifyCodeType.REGIST.ordinal());
		Long expireTime = (Long) session.get("verify_code_create_time_" + VerifyCodeType.REGIST.ordinal());
		String tel = (String) session.get("verify_code_tel_" + VerifyCodeType.REGIST.ordinal());
		if (user.getTel() == null || !user.getTel().equals(tel)) {
			resp.setCode(INVALIDAT_REQUEST);
			return resp;
		}
		if (code == null || !code.equals(verifyCode)) {
			resp.setCode(INVALIDAT_CODE);
			return resp;
		}
		if (expireTime == null || System.currentTimeMillis() > expireTime) {
			resp.setCode(CODE_EXPIRED);
			return resp;
		}

		User dbUser = userDao.queryByUsername(user.getUsername(), user.getTel());
		if (dbUser != null) {
			resp.setCode(USER_EXSITS);
			return resp;
		}
		user.setLevel(UserLevel.COMMON.ordinal());
		user = userDao.saveUser(user);
		session.put("user", resp.getData());

		session.put("verify_code_tel_" + VerifyCodeType.REGIST.ordinal(), null);
		session.put("verify_code_" + VerifyCodeType.REGIST.ordinal(), null);
		session.put("verify_code_create_time_" + VerifyCodeType.REGIST.ordinal(), null);
		resp.setCode(SUCCESS);
		IMUtil.importUser(user.getId());
		return resp;
	}

    @Override
    public BaseResp getVerifyCodeByType(String tel, Integer verifyCodeType, Map<String, Object> session) {
        BaseResp resp;
        if(verifyCodeType == null){
            resp = new BaseResp();
            resp.setCode(INVALIDAT_REQUEST);
            return resp;
        }
        if(verifyCodeType == VerifyCodeType.REGIST.ordinal()){
            resp = getRedistVerifyCode(tel, session);
            return resp;
        }else if(verifyCodeType == VerifyCodeType.RESET.ordinal()){
            resp = getResetVerifyCode(tel, session);
            return resp;
        }else {
            resp = new BaseResp();
            resp.setCode(INVALIDAT_REQUEST);
            return resp;
        }
    }

	private BaseResp getRedistVerifyCode(String tel, Map<String, Object> session) {
		BaseResp resp = new BaseResp();
		
		int code = StringUtil.getVerifyCode();
		try {
			if (sendsms.sendMsg(tel, code)) {

				session.put("verify_code_tel_" + VerifyCodeType.REGIST.ordinal(), tel);
				session.put("verify_code_" + VerifyCodeType.REGIST.ordinal(), code);
				session.put("verify_code_create_time_" + VerifyCodeType.REGIST.ordinal(),
						System.currentTimeMillis() + 5 * 60 * 1000);
				resp.setCode(SUCCESS);
				return resp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setCode(SEND_MSG_FAILED);
		return resp;
	}

    private BaseResp getResetVerifyCode(String tel, Map<String, Object> session) {
		BaseResp resp = new BaseResp();
		User user = userDao.queryByUsername(tel, tel);
		if(user == null){
			resp.setCode(USER_NOT_FOUND);
			return resp;
		}
		Integer code = (Integer) session.get("verify_code_" + VerifyCodeType.RESET.ordinal());
		Long expireTime = (Long) session.get("verify_code_create_time_" + VerifyCodeType.RESET.ordinal());
		String userTel = (String) session.get("verify_code_tel_" + VerifyCodeType.RESET.ordinal());
		if(userTel != null && !tel.equals(userTel)){
			resp.setCode(INVALIDAT_REQUEST);
			return resp;
		}
		
		if (expireTime == null || System.currentTimeMillis() > expireTime || code == null) {
			code = StringUtil.getVerifyCode();
			try {
				if (sendsms.sendMsg(tel, code)) {
					
					session.put("verify_code_tel_" + VerifyCodeType.RESET.ordinal(), tel);
					session.put("verify_code_" + VerifyCodeType.RESET.ordinal(), code);
					session.put("verify_code_create_time_" + VerifyCodeType.RESET.ordinal(),
							System.currentTimeMillis() + 5 * 60 * 1000);
					resp.setCode(SUCCESS);
					return resp;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			resp.setCode(SEND_MSG_FAILED);
			return resp;
		}else {

			session.put("verify_code_tel_" + VerifyCodeType.RESET.ordinal(), tel);
			session.put("verify_code_" + VerifyCodeType.RESET.ordinal(), code);
			session.put("verify_code_create_time_" + VerifyCodeType.RESET.ordinal(),
					System.currentTimeMillis() + 5 * 60 * 1000);
			resp.setCode(SUCCESS);
			return resp;
		}
		
	}

	@Override
	public BaseResp resetPassword(String tel, int verifyCode, String password, Map<String, Object> session) {
		BaseResp resp = new BaseResp();
		
		Integer code = (Integer) session.get("verify_code_" + VerifyCodeType.RESET.ordinal());
		Long expireTime = (Long) session.get("verify_code_create_time_" + VerifyCodeType.RESET.ordinal());
		String userTel = (String) session.get("verify_code_tel_" + VerifyCodeType.RESET.ordinal());
		if (userTel == null || !userTel.equals(tel)) {
			resp.setCode(INVALIDAT_REQUEST);
			return resp;
		}
		if (code == null || !code.equals(verifyCode)) {
			resp.setCode(INVALIDAT_CODE);
			return resp;
		}
		if (expireTime == null || System.currentTimeMillis() > expireTime) {
			resp.setCode(CODE_EXPIRED);
			return resp;
		}

		User dbUser = userDao.queryByUsername(null, userTel);
		if (dbUser == null) {
			resp.setCode(USER_NOT_FOUND);
			return resp;
		}
		userDao.updateUserPwd(userTel, password);
		
		session.put("verify_code_tel_" + VerifyCodeType.RESET.ordinal(), null);
		session.put("verify_code_" + VerifyCodeType.RESET.ordinal(), null);
		session.put("verify_code_create_time_" + VerifyCodeType.RESET.ordinal(), null);
		resp.setCode(SUCCESS);
		return resp;
	}
	
	
	@Override
	public BaseResp modifyPassword(String tel,String password,String oldPwd,Map<String, Object> session) {
		BaseResp resp = new BaseResp();
		User user = (User) session.get("user");
		if(!oldPwd.equals(user.getPassword())){
			resp.setCode(404);
			resp.setDescription("旧密码输入有误 ");
			return resp;
		}
		user=userDao.updateUserPwd(tel, password);
		session.put("user", user);
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp getUserInfo(Integer userId) {
		BaseResp resp = new BaseResp();
		User user = userDao.queryByUserId(userId);
		if (user == null) {
			resp.setCode(USER_NOT_FOUND);
			return resp;
		}
		user.setPassword(null);
		resp.setCode(SUCCESS);
		resp.setData(user);
		return resp;
	}

	@Override
	public BaseResp getUsers(Page page) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
        List<User> users = userDao.getUsers(page);
		resp.setData(users);
		resp.setPage(page);
		return resp;
	}

    @Override
    public BaseResp setUserCompanyBind(UserCompanyBind bind) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        if(userDao.queryUserCompanyBind(bind.getUserId(), bind.getCompanyId()) == null){
            userDao.saveUserCompanyBind(bind);
        }
        return resp;
    }

    @Override
    public BaseResp getUserCompanyBinds(Integer companyId, Page page) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        List<UserCompanyBind> users = userDao.queryUserCompanyBinds(companyId, page);

        List<User> user = new ArrayList<>();

        for (UserCompanyBind bind : users){
            User user1 = new User();
            User u = userDao.queryByUserId(bind.getUserId());
            if (u == null){
                continue;
            }
            user1.setTel(u.getTel());
            user1.setId(u.getId());
            user1.setAvatar(u.getAvatar());
            user1.setNickname(u.getNickname());
            user.add(user1);
        }
        resp.setData(user);
        resp.setPage(page);
        return resp;
    }

    @Override
    public BaseResp getUserTags(Integer userId) {
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        resp.setData(userDao.getUserTags(userId));
        return resp;
    }

    @Override
	public BaseResp modifyUserInfo(User user) {
		BaseResp resp = new BaseResp();
		userDao.updateUserInfo(user.getId(), user.getNickname(), user.getAvatar());
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp addAddress(Address address) {
		return modifyAddress(address);
	}

	@Override
	public BaseResp delAddress(Address address) {
		BaseResp resp = new BaseResp();
		userDao.delAddress(address.getId(), address.getUserId());
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp modifyAddress(Address address) {
		BaseResp resp = new BaseResp();
		address = userDao.saveOrModifyAddress(address);
		resp.setCode(SUCCESS);
		resp.setData(address);
		return resp;
	}

	@Override
	public BaseResp getAddresses(Integer userId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(userDao.getAddresses(userId));
		return resp;
	}
	
	@Override
	public BaseResp setDefaultAddress(Address address) {
		BaseResp resp = new BaseResp();
		userDao.setDefaultAddress(address.getId(), address.getUserId());
		resp.setCode(SUCCESS);
		return resp;
	}

	@Override
	public BaseResp delUser(String tel) {
		BaseResp resp = new BaseResp();
		User user = userDao.queryByUsername(tel, tel);
		Company company = companyDao.queryByUserId(user.getId());
		if(company == null){
			company = new Company();
			company.setId(0);
		}
		Page page = new Page(0, 999999);
		List<Product> product = productDao.queryByCompanyId(page, company.getId());
		userDao.delUser(user.getId(), company.getId(), product);
		resp.setCode(SUCCESS);
		return resp;
	}

    @Override
    public BaseResp uploadLocation(User user) {
        BaseResp resp = new BaseResp();
        userDao.updateUserLocation(user.getId(), user.getCountry(), user.getProvince(), user.getCity(), user.getCityCode(), user.getAdCode(), user.getAddress(), user.getRoad(), user.getPoiName(), user.getDistrict(), user.getLng(), user.getLat());
        resp.setCode(SUCCESS);
        return resp;
    }

	@Override
	public BaseResp getProvinces() {
		BaseResp resp = new BaseResp();
		resp.setData(userDao.getProvinces());
		resp.setCode(SUCCESS);
		return resp;
	}


}
