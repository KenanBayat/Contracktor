package de.contracktor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Organisation {

	@Getter @Setter @NotNull @Id private String organisationName;
	@Getter @Setter @NotNull private String street;
	@Getter @Setter @NotNull private String houseNumber;
	@Getter @Setter @NotNull private String city;
	@Getter @Setter @NotNull private String postcode;
	@Getter @Setter @NotNull private String country;
	@Getter @Setter @OneToMany(cascade = CascadeType.ALL) private List<User> users;
	
	public Organisation(String organisationName, String street, String houseNumber, String city,
			            String postcode, String country) {
		this.organisationName = organisationName;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
		this.users = new ArrayList<User>();
	}
	
}
