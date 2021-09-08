package de.contracktor.model;

import java.util.Date;

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

@Entity
public class Project {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	private int id;
	@Getter @Setter @Column(nullable = false) private int projectID;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String name;
    @Getter @Setter @Column(nullable = false) private Date creationDate;
	@Getter @Setter @Column(nullable = false) private Date completionDate;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String street;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String houseNumber;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String city;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String postcode;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String country;
	@Getter @Setter @Column(nullable = false) private Double totalPrice;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private Organisation owner;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String creator;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private State status;
	@Getter @Setter private String image;
	@Getter @Setter private String description;
	
	public Project() {
		
	}
	
	public Project(String name, Date completionDate, String street, String houseNumber, String city, 
			       String postcode, String country, Double totalPrice, Organisation owner, String creator,
			       State status, String image, String description) {
		this.name = name;
		this.completionDate = completionDate;
		this.street = street;
		this.houseNumber = houseNumber;
		this.city = city;
		this.postcode = postcode;
		this.country = country;
		this.totalPrice = totalPrice;
		this.owner = owner;
		this.creator = creator;
		this.status = status;
		this.image = image;
		this.description = description;
	}
	
}
