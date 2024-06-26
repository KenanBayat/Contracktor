package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class UserAccount {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)	private int id;
	@Getter	@Setter	@Column(nullable = false, unique = true) @NotEmpty private String username;
	@Getter	@Setter	@Column(nullable = false) @NotEmpty private String password;
	@Getter	@Setter	@Column(nullable = false) @NotEmpty private String forename;
	@Getter	@Setter	@Column(nullable = false) @NotEmpty private String surname;
	@Getter	@Setter	@JoinColumn(nullable = false, name = "organisation_id") @ManyToOne private Organisation organisation;
	@Getter	@Setter	@Column(nullable = false) private Boolean isAdmin;
	@Getter	@Setter	@Column(nullable = false) private Boolean isApplicationAdmin;
	@Getter	@Setter @ManyToMany(fetch = FetchType.EAGER) private List<Role> roles;

	public UserAccount(){}
	
	public UserAccount(String username, String password, String forename, String surname, Organisation organisation, 
			 Boolean isAdmin, Boolean isApplicationAdmin, ArrayList<Role> roles) {
		this.username = username;
		this.password = password;
		this.surname = surname;
		this.forename = forename;
		this.organisation = organisation;
		this.isAdmin = isAdmin;
		this.isApplicationAdmin = isApplicationAdmin;
		this.roles = roles;
	}	
}
