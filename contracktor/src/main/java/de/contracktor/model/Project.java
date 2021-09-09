package de.contracktor.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO) @JsonIgnore
	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @JsonProperty("id")
	private int projectID;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String name;
    @Getter @Setter @Column(nullable = false) @JsonIgnore private LocalDate creationDate;
	@Getter @Setter @Column(nullable = false) @JsonIgnore private LocalDate completionDate;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String street;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String houseNumber;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String city;
	@Getter @Setter @Column(nullable = false) @NotEmpty @JsonProperty("zipCode") private String postcode;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String country;
	@Getter @Setter @Column(nullable = false) @JsonProperty("overallCost") private Double totalPrice;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne @JsonProperty("ownerGroupIdentifier") private Organisation owner;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String creator;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private State status;
	@Getter @Setter @OneToOne private Picture image;
	@Getter @Setter @Column(nullable = false) private String description;
	@Getter @Setter @Transient @JsonProperty("creationDate") private String creationDateString;
	@Getter @Setter @Transient @JsonProperty("completionDate") private String completionDateString;
	@Getter @Setter @Transient @JsonProperty("address") private List<String> address;


	public Project() {
		
	}
	
	public Project(int projectID, String name, LocalDate creationDate, LocalDate completionDate, String street, String houseNumber, String city, 
			       String postcode, String country, Double totalPrice, Organisation owner, String creator,
			       State status, Picture image, String description) {
		this.projectID = projectID;
		this.name = name;
		this.creationDate = creationDate;
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
