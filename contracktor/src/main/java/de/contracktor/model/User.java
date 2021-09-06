package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

@Entity
public class User {
	
	@Getter @Setter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int loginID;
	@Getter @Setter @NotNull private String password;
	@Getter @Setter	@NotNull private String forename;
	@Getter @Setter @NotNull private String surname;
	@Getter @Setter	@NotNull @ManyToOne private Organisation organisation;
	@Getter @Setter	@NotNull private Boolean isAdmin;
	@Getter @Setter	@NotNull private Boolean isApplicationAdmin;
	@Getter @Setter private ArrayList<Role> roles;
	
	public User() {
		
	}
	
	public User(String password, String forename, String surname, Organisation organisation, 
			    Boolean isAdmin, Boolean isApplicationAdmin, ArrayList<Role> roles) {
		this.surname = surname;
		this.forename = forename;
		this.organisation = organisation;
		this.isAdmin = isAdmin;
		this.isApplicationAdmin = isApplicationAdmin;
		this.roles = roles;
	}
	
}