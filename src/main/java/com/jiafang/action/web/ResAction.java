package com.jiafang.action.web;

import com.jiafang.action.JSONAction;
import com.jiafang.web.service.WebResService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@ParentPackage("basePackage")
@Namespace("/res")
public class ResAction  extends JSONAction {
	
	@Autowired
	private WebResService resService;
	
	private String file;
	private String rr;
	
	private Integer type;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Action(value = "uploadResToQiNiu")
	public String uploadResToQiNiu() {
		if(StringUtils.isEmpty(file)){
			setData(null);
			return RETURN_JSON;
		}
		byte[] fileBytes = null;
		if(type!=null&&type==1){//表示上传视频文件
			fileBytes = file.getBytes();
		}else{
			fileBytes = Base64.decodeBase64(file.getBytes());
		}
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		setData(resService.uploadToQiniu(name, fileBytes));
		return RETURN_JSON;
	}

	public String getRr() {
		return rr;
	}

	public void setRr(String rr) {
		this.rr = rr;
	}

}
