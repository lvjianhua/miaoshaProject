package com.lv_miaoshaProject.common.enmus;

/**
 * 提示错误枚举
 */
public enum ErrorCode implements ErrorCodeInterface {

	SUCCESS(0, "Success"),

	ERROR(1, "Error"),

	UNKNOWN(2000, "未知错误!"),

	WRONG_ONSERVER(5000, "您的网络不太给力啊!");

	private int code;
	private String message;

	ErrorCode(int code, String message) {
		this.message = message;
		this.code = code;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
