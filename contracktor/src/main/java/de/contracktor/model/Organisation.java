package de.contracktor.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Organisation {
	
	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @NotEmpty private String organisationName;
	
	public Organisation() {
		
	}
	
	public Organisation(String organisationName) {
		this.organisationName = organisationName;
	}

	public List<UserAccount> getUsers(List<UserAccount> users) {
		List<UserAccount> result = users.stream().filter(user -> user.getOrganisation().getOrganisationName().equals(this.organisationName))
				.collect(Collectors.toList());
		return result.stream().sorted(Comparator.comparing(UserAccount::getUsername)).collect(Collectors.toList());
	}
	
}
