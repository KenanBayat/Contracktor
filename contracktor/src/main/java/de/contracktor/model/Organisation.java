package de.contracktor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Organisation {
	
	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	private int id;
	@Getter @Column(nullable = false) @NotEmpty private String organisationName;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String street;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String houseNumber;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String city;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String postcode;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String country;
	
	public Organisation() {
		
	}
	
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
