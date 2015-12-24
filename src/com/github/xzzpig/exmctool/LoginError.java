package com.github.xzzpig.exmctool;

public enum LoginError
{
	NoClient("启动器未连接"),
	NoData("无数据"),
	DifferentAddress("IP与记录值不符"),
	UnStartLogin("未开始登录"),
	UnKnown("未知错误"),
	DifferentPass("密码错误"),
	DifferentKey("登录密钥错误"),
	DifferentID("玩家id错误");
	
	String errorMessage;
	
	LoginError(String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}

	@Override
	public String toString()
	{
		return errorMessage;
	}
	
}
