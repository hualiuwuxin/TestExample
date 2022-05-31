package com.lomi.entity.in;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("多次执行入参")
public class ExecuteIn implements Serializable {
	private static final long serialVersionUID = -3418074600644269522L;

	@ApiModelProperty("执行次数")
	private int executeCount;
	
	
    /**
     * 创建时间起点
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;


	public int getExecuteCount() {
		return executeCount;
	}

	public void setExecuteCount(int executeCount) {
		this.executeCount = executeCount;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	
	

}
