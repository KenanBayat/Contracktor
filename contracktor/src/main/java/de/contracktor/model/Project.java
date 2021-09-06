package de.contracktor.model;

import java.awt.Image;
import java.util.Date;

import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Project {

	@Getter @Setter @Id      private Long projectID;
	@Getter @Setter @NotNull private String name;
    @Getter @Setter @NotNull private Date creationDate;
	@Getter @Setter @NotNull private Date completionDate;
	@Getter @Setter @NotNull private String street;
	@Getter @Setter @NotNull private String houseNumber;
	@Getter @Setter @NotNull private String city;
	@Getter @Setter @NotNull private String postcode;
	@Getter @Setter @NotNull private String country;
	@Getter @Setter @NotNull private Double totalCosts;
	@Getter @Setter @NotNull private String owner;
	@Getter @Setter @NotNull private State status;
	@Getter @Setter  private Image image;
	@Getter @Setter  private String description;
	
	public Project(String name, Date completionDate, String street, String houseNumber, String city, 
			       String postcode, String country, Double totalCosts, String owner, State status, Image image, String description) {
		this.name = name;
		this.completionDate = completionDate;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
		this.totalCosts = totalCosts;
		this.owner = owner;
		this.status = status;
		this.image = image;
		this.description = description;
	}
	
}
