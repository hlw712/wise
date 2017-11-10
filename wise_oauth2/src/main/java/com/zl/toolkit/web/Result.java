package com.zl.toolkit.web;
/**
 *  APP接口返回Result实体对象
 * @author jerry.xiang
 *
 */

public class Result {

	private Object data;
	/**
	 * 查询总数
	 */
	private long total;

	/**
	 * 当前页码
	 */
	private int pageNum;


	/**
	 * 每页显示数量
	 */
	private int pageSize;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Result(){
	}

	public Result(Object data){
		this.data =data;
	}

	public Result(Object data, long total, int pageNum, int pageSize) {
		super();
		this.data = data;
		this.total = total;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

}