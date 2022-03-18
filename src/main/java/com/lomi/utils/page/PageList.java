package com.lomi.utils.page;

import java.io.Serializable;
import java.util.List;

public class PageList<T> implements Serializable {
	private static final long serialVersionUID = -3214121682485271750L;
	
	/**
	 * 当前是第几页
	 */
	private int pageNum = 1;
	
	/**
	 * 每页多少条
	 */
	private int pageSize = 20;
	
	/**
	 * 一共有多少条
	 */
    private long total;
    
    
    /**
     * 一共有多少页
     */
    private int pages;
    
    /**
     * 数据
     */
    private List<T> list;
    
    

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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
    
	
    
	

}
