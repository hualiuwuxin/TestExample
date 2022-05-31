package com.lomi.entity.out;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class FormatOut implements Serializable {
	
	/**
	 * JSON返回数据 时间 如果需要单个格式化解决办法1
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date = new Date();
	
	private Date date1 = new Date();
	
	/**
	 * long 类型丢精度问题解决办法1
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private Long id = Long.MAX_VALUE;
	
	
	/**
	 * long 类型丢精度问题解决办法2
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id2 = Long.MAX_VALUE;
	
	
	
	private Long id3 = Long.MAX_VALUE;
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId2() {
		return id2;
	}
	public void setId2(Long id2) {
		this.id2 = id2;
	}
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Long getId3() {
		return id3;
	}
	public void setId3(Long id3) {
		this.id3 = id3;
	}
	
	
	
	
	
	

}
