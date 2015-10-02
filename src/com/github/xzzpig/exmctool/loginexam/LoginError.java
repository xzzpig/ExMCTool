package com.github.xzzpig.exmctool.loginexam;

public class LoginError {
	public static String getError(int id){
		String error = "";
		switch(id){
		case 1:
			error = "无法连接启动器";
			break;
		case 2:
			error = "验证错误";
			break;
		case 3:
			error = "密码错误";
			break;
		}
		
		return error;
	}
}
