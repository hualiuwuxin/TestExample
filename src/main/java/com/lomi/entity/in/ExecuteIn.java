package com.lomi.entity.in;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多次执行入参")
public class ExecuteIn implements Serializable {
	private static final long serialVersionUID = -3418074600644269522L;

	@ApiModelProperty("执行次数")
	private int executeCount;

	public int getExecuteCount() {
		return executeCount;
	}

	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}

}
