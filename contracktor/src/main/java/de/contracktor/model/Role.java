package de.contracktor.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Role {
	
	@Getter @Setter @NotNull @Id private String roleName;
	
	public Role(String roleName) {
		this.roleName = roleName;
	}
}
