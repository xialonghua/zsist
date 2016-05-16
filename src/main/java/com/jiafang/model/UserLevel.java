package com.jiafang.model;

public enum UserLevel {

	COMMON, COMPANY, MANAGER, ADMIN;

	public static String levelToString(int level) {
		switch (level) {
		case 0:

			return "普通用户";
		case 1:

			return "公司";
		case 2:

			return "管理员";
		case 3:

			return "系统管理员";

		default:
			break;
		}
		return "sb";
	}
}
