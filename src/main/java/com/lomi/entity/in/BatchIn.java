package com.lomi.entity.in;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("批量入参")
public class BatchIn extends MultiThreadIn {
	private static final long serialVersionUID = -3620458832957491201L;
	
	@ApiModelProperty("每批执行次数量")
	private int batchCount;


	public int getBatchCount() {
		return batchCount;
	}


	public void setBatchCount(int batchCount) {
		this.batchCount = batchCount;
	}

	
}
