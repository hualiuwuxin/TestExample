package com.lomi.utils.page;

import java.io.Serializable;

/**
 * 带有分页参数的请求对象
 * @author ZHANGYUKUN
 *
 */
public class PageIn implements Serializable {

	private static final long serialVersionUID = 7746000867522641520L;

	/**
	 * 当前是第几页
	 */
	private int pageNum = 1;
	
	/**
	 * 每页多少条
	 */
	private int pageSize = 20;
	
	

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
	
	

}
