package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

public class User {
	
	@Getter @Setter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int loginID;
	@Getter @Setter @NotNull private String password;
	@Getter @Setter	@NotNull private String forename;
	@Getter @Setter @NotNull private String surname;
	@Getter @Setter	@NotNull private String organisationName;
	@Getter @Setter	@NotNull private Boolean isAdmin;
	@Getter @Setter	@NotNull private Boolean isApplicationAdmin;
	@Getter @Setter private ArrayList<Role> roles;
	
	public User(String password, String surname, String forename, String organisationName, 
			    Boolean isAdmin, Boolean isApplicationAdmin, ArrayList<Role> roles) {
		this.surname = surname;
		this.forename = forename;
		this.organisationName = organisationName;
		this.isAdmin = isAdmin;
		this.isApplicationAdmin = isApplicationAdmin;
		this.roles = roles;
	}
	
}
