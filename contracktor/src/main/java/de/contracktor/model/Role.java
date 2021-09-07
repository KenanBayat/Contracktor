package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Role {
	
	@Getter @Setter @Column(nullable = false) @Id private String roleName;
	@Getter @Setter @ManyToOne private Permission permission;
	@Getter @Setter @Column(nullable = false) @ManyToOne Organisation organisation;
	
	public Role() {
		
	}
	
	public Role(String roleName, Permission permission, Organisation organisation) {
		this.roleName = roleName;
		this.permission = permission;
		this.organisation = organisation;
	}
}
