package de.contracktor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingUnitCompletionReport {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @JsonProperty("id")
	private int CRID;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne @JsonIgnore private Contract contract;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne @JsonIgnore private Project project;
	@Getter @Setter @Column(nullable = false) private String comment;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String username;

	@Getter @Setter @Column(nullable = false) @OneToMany @JsonIgnore private List<BillingUnit> billingUnits;
	@Getter @Setter @Column(nullable = false) @OneToMany @JsonIgnore private List<Picture> images;
	@Getter @Setter @Transient private int contractId;
	@Getter @Setter @Transient private int projectId;
	@Getter @Setter @Transient private String[] billingUnitIds;
	@Getter @Setter @Transient private String[] filenames;

	public BillingUnitCompletionReport() {
		
	}
	
	public BillingUnitCompletionReport(Contract contract, Project project, String comment, String username, 
			                           ArrayList<BillingUnit> billingUnits, ArrayList<Picture> images) {
		this.contract = contract;
		this.project = project;
		this.comment = comment;
		this.username = username;
		this.billingUnits = billingUnits;
		this.images = images;
	}

}
