package de.contracktor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

	@Getter	@Id @GeneratedValue(strategy = GenerationType.AUTO) @JsonIgnore private int id;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String street;
    @Getter @Setter @Column(nullable = false) @NotEmpty private String houseNumber;
    @Getter @Setter @Column(nullable = false) @NotEmpty private String city;
    @Getter @Setter @Column(nullable = false) @NotEmpty private String zipCode;
    @Getter @Setter @Column(nullable = false) @NotEmpty private String country;

    public Address() { 
    }
  
    public Address(String street, String houseNumber, String city, String zipCode, String country) {
    	this.street = street;
    	this.houseNumber = houseNumber;
    	this.city = city;
    	this.zipCode = zipCode;
    	this.country = country;
	}
}
