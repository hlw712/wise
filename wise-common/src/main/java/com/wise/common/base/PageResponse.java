package com.wise.common.base;

import java.util.List;

/**
 * 分页返回对象
 *
 * @author huanglw
 * @date 2017/11/15
 * @version 1.0.0
 *
 * History:
 */
public class PageResponse<T> extends Response {

    private Long total;
    private Long begin;
    private int size;

    public PageResponse() {
        this.success();
    }

    private void setResponseHeader() {
        if(this.begin != null && this.total != null) {
            ///NxHttpResponseUtil.getResponse().setHeader("Content-Range", "items=" + this.begin + "-" + (this.begin.longValue() + (long)this.size) + "/" + this.total);
        }
    }

    public List getRows() {
        return (List)this.getData();
    }

    public PageResponse total(Long total) {
        this.total = total;
        this.setResponseHeader();
        return this;
    }

    public PageResponse begin(Long begin) {
        this.begin = begin;
        this.setResponseHeader();
        return this;
    }

    public PageResponse rows(List data) {
        this.setData(data);
        this.size = data.size();
        this.setResponseHeader();
        return this;
    }
}
