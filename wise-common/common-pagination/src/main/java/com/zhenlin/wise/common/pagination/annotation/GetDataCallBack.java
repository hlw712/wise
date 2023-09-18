package com.zhenlin.wise.common.pagination.annotation;

import javafx.util.Pair;

import java.util.List;

/**
 * 回调执行 获取分页数据
 *
 * @author huanglinwei
 * @date 2023/9/8 21:08
 * @version 1.0.0
 */
public interface GetDataCallBack {
	
	/**
	 * 获取数据体
	 * @param pageNum
	 * @return
	 */
	Pair<Integer, List<String[]>> getPageBodyList(Integer pageNum);
}
