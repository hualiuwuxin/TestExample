package com.lomi.entity.in;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.lomi.controller.format.DateDeserializer;

public class FormatIn implements Serializable {
	
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@JsonDeserialize(using = DateDeserializer.class )
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
	

}
