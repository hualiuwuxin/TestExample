package com.lomi.entity.in.validate;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserIn {
	
	@NotNull(message = "UserIn-id不能是null")
	private Long id;
	
	@NotBlank(message = "UserIn-name不能是null")
	private String name;
	
	@NotEmpty(message = "UserIn-skill不能是null")
	private List<String> skill;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getSkill() {
		return skill;
	}
	public void setSkill(List<String> skill) {
		this.skill = skill;
	}
	
	
	

}
