package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Permission {
	
	@Getter @Setter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Getter @Setter @Column(nullable = false, unique=true) private String permissionName;
	
	public Permission() {
		
	}
	
	public Permission(String permissionName) {
		this.permissionName = permissionName;
	}

}
