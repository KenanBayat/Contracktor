package de.contracktor.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Permission {
	
	@Getter @Setter @NotNull @Id private String permissionName;
	
	public Permission(String permissionName) {
		this.permissionName = permissionName;
	}

}
