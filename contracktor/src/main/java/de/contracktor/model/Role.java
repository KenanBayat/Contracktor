package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Role {
	
	@Getter @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String roleName;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private Permission permission;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne Organisation organisation;
	
	public Role() {
		
	}
	
	public Role(String roleName, Permission permission, Organisation organisation) {
		this.roleName = roleName;
		this.permission = permission;
		this.organisation = organisation;
	}

	public boolean existsOrganisationWithRole(Organisation organisation, List<Role> roles) {
		return roles.stream().filter(role -> role.getOrganisation().getOrganisationName().equals(organisation.getOrganisationName())).collect(Collectors.toList()).isEmpty();
	}

	public List<Role> getRolesForOrganisation(Organisation organisation, List<Role> roles) {
		return roles.stream().filter(role -> role.getOrganisation().getOrganisationName().equals(organisation.getOrganisationName())).collect(Collectors.toList());
	}
}
