package de.contracktor.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @JsonProperty("id")
	private int projectID;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String name;
    @Getter @Setter @Column(nullable = false) @JsonIgnore private LocalDate creationDate;
	@Getter @Setter @Column(nullable = false) @JsonIgnore private LocalDate completionDate;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private Address address;
	@Getter @Setter @Column(nullable = false) @JsonProperty("overallCost") private Double totalPrice;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private Organisation owner;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String creator;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private State status;
	@Getter @Setter @OneToOne private Picture image;
	@Getter @Setter @Column(nullable = false, length = 8192) private String description;
	@Getter @Setter @Transient @JsonProperty("creationDate") private String creationDateString;
	@Getter @Setter @Transient @JsonProperty("completionDate") private String completionDateString;
	@Getter @Setter @Transient private String ownerGroupIdentifier;


	public Project() {
		
	}
	
	public Project(int projectID, String name, LocalDate creationDate, LocalDate completionDate, Address address, Double totalPrice, Organisation owner, String creator,
			       State status, Picture image, String description) {
		this.projectID = projectID;
		this.name = name;
		this.creationDate = creationDate;
		this.completionDate = completionDate;
		this.address = address;
		this.totalPrice = totalPrice;
		this.owner = owner;
		this.creator = creator;
		this.status = status;
		this.image = image;
		this.description = description;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return id == other.id && projectID == other.projectID;
	}
}
