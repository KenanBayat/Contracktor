package de.contracktor.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Role {
	
	@Getter @Setter @NotNull @Id private String roleName;
	@Getter @Setter @ManyToOne private Permission permission;
	@Getter @Setter @NotNull @ManyToOne Organisation organisation;
	
	public Role() {
		
	}
	
	public Role(String roleName, Permission permission, Organisation organisation) {
		this.roleName = roleName;
		this.permission = permission;
		this.organisation = organisation;
	}
}
