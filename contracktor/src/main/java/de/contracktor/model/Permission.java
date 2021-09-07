package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Permission {
	
	@Getter @Setter @Column(nullable = false) @Id private String permissionName;
	
	public Permission() {
		
	}
	
	public Permission(String permissionName) {
		this.permissionName = permissionName;
	}

}
