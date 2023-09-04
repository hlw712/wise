package com.zl.wise.common.core;

import javafx.util.Pair;

import java.util.List;

/**
 * @author zhou.liu
 * @version : GetDataCallBack.java, v 0.1 25/07/2021 19:17 liuzhou Exp $
 */
public interface GetDataCallBack {
	
	/**
	 * 获取数据体
	 * @param pageNum
	 * @return
	 */
	Pair<Integer, List<String[]>> getPageNumAndBodyList(Integer pageNum);
}
