package com.zhenlin.toolkit.web;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import java.util.List;

/**
 * 用于ajax返回json列表数据构造
 * @author yanghongquan
 *
 */
public class Pagination {

	//当前第几页
	private int currNum;
	//每页显示行数
	private int rowsNum;
	//总数
	private int total;
	//结果集
	private List<?> list;

	public Pagination(boolean noPage) {
		currNum = 1;
		rowsNum = 1000;
	}

    //添加构造函数，按照参入的参数分页查询
	public Pagination(int pageNum, int pageCount) {
		PageHelper.startPage(pageNum, pageCount);
	}

	public Pagination() {
		//ServletRequestUtils.getStringParameter()
		Result result= RequestResponseContext.getTempPageInfo();
		if (result!=null){
			currNum = RequestResponseContext.getTempPageInfo().getPageNum();
			rowsNum = RequestResponseContext.getTempPageInfo().getPageSize();
		}else {
			currNum = 1;
			rowsNum = 10;
		}
		PageHelper.startPage(currNum, rowsNum);
	}

	/*public Pagination(Page page, List<?> list) {
		this.list = list;
		this.pageNum = page.getCurrentPage();
		this.pageCount = page.getPageSize();
		this.total = (int) page.getTotalCount();
	}

	public Page toPage() {
		return toPage(false);
	}

	public Page toPage(boolean forceTotal) {
		Page page = new Page();
		page.setPageSize(pageCount);
		page.setCurrentPage(pageNum);
		if(forceTotal && total != 0)
			page.setTotalCount(total);
		return page;
	}*/
	
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
		if (list instanceof Page){
			this.total=(int)((Page)list).getTotal();
		}
	}

	public int getCurrNum() {
		return currNum;
	}

	public void setCurrNum(int currNum) {
		this.currNum = currNum;
	}

	public int getRowsNum() {
		return rowsNum;
	}

	public void setRowsNum(int rowsNum) {
		this.rowsNum = rowsNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	/** 获取总页数 */
	public int getTotalPage() {
		if(rowsNum == 0) {
			return 0;
		}
		int temp = total / rowsNum;
		return total % rowsNum == 0 ? temp : temp + 1;
	}
}
