package com.lomi.entity.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多线程入参")
public class MultiThreadIn extends ExecuteIn {
	private static final long serialVersionUID = 1294785535402023579L;
	
	@ApiModelProperty("线程数")
	private int threadCount;

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	

}
