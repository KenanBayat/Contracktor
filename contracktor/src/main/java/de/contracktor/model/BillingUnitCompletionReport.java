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
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class BillingUnitCompletionReport {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) private int CRID;	
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private Contract contract;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private Project project;
	@Getter @Setter @Column(nullable = false) private String comment;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String username;
	@Getter @Setter @Column(nullable = false) @OneToMany private List<BillingUnit> billingUnits;
	@Getter @Setter @Column(nullable = false) @OneToMany private List<Picture> images;
	
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
