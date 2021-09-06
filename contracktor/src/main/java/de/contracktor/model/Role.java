package de.contracktor.model;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Role {
	
	@Getter @Setter @NotNull private String roleName;
	
	public Role(String roleName) {
		this.roleName = roleName;
	}
}
