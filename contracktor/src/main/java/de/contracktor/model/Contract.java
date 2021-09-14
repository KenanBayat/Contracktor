package de.contracktor.model;

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
public class Contract {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @JsonProperty("id") private int contractID;
	@Getter @Setter @ManyToOne @JoinColumn(nullable = false) @JsonIgnore private Project project;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String name;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String consignee;
	// Allow null status for now
	@Getter @Setter @JoinColumn @ManyToOne private State status;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String contractor;
	@Getter @Setter @Column(nullable = false, length = 8192) private String description;
	@Getter @Setter @Transient private int projectId;
	// @Getter @Setter @Transient private String statusString;

	public Contract() {
		
	}
	
	public Contract(int contractID, Project project, String name, String consignee, State status, String contractor, 
			        String description) {
		this.contractID = contractID;
		this.project = project;
		this.name = name;
		this.consignee = consignee;
		this.status = status;
		this.contractor = contractor;
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
		Contract other = (Contract) obj;
		return contractID == other.contractID && id == other.id && projectId == other.projectId;
	}

	public String getLowerName() {
		return this.name.toLowerCase();
	}
	public String getLowerProject() {
		return this.project.getName().toLowerCase();
	}
	public String getLowerConsignee() {
		return this.consignee.toLowerCase();
	}
	public String getLowerContractor() {
		return this.contractor.toLowerCase();
	}
	public String getLowerStatus() {
		return this.status.getStateName().toLowerCase();
	}
}
