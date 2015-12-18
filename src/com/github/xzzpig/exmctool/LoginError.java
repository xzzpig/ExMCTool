package com.github.xzzpig.exmctool;

public enum LoginError
{
	NoCilent("启动器未连接"),
	DifferentAddress("IP与记录值不符");
	
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
