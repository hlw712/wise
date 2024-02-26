package com.zhenlin.wise.common.redis;

/**
 * 限制器类型
 *
 * @author huanglinwei
 * @version 1.0.0
 * @date 2021/8/13 11:39 PM
 */
public enum LimiterType {

	IP("IP limit"),
	CUSTOMER("Customer limit"),
	;

	private String desc;

	LimiterType(String desc) {
		this.desc = desc;
	}
	
	public String getCode() {
		return name();
	}

	public String getDesc() {
		return desc;
	}
}
