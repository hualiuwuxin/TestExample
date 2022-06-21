package com.lomi.entity.in.validate;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserPakeageIn {
	
	

	@NotNull(message = "UserPakeageIn-id不能是null")
	private Long id;
	
	@NotBlank(message = "UserPakeageIn-name不能是null")
	private String name;

	@Valid
	private UserIn user;
	
	//@Valid 标记验证向下传递,但是自己不验证
	//@Valid
	private List<UserIn> users;

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

	public UserIn getUser() {
		return user;
	}

	public void setUser(UserIn user) {
		this.user = user;
	}

	public List<UserIn> getUsers() {
		return users;
	}

	public void setUsers(List<UserIn> users) {
		this.users = users;
	}

		
	
}
