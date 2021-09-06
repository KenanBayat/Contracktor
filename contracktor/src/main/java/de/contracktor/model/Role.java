package de.contracktor.model;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Role {
	
	@Getter @Setter @NotNull private String roleName;
	
	public Role(String roleName) {
		this.roleName = roleName;
	}
}
