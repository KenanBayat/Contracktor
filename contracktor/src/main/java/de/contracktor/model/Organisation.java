package de.contracktor.model;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Organisation {

	@Getter @Setter @NotNull private String organisationName;
	@Getter @Setter @NotNull private String street;
	@Getter @Setter @NotNull private String houseNumber;
	@Getter @Setter @NotNull private String city;
	@Getter @Setter @NotNull private String postcode;
	@Getter @Setter @NotNull private String country;
	
	public Organisation(String organisationName, String street, String houseNumber, String city,
			            String postcode, String country) {
		this.organisationName = organisationName;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
	}
	
}
