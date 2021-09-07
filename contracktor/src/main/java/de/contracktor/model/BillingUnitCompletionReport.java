package de.contracktor.model;

import java.awt.Image;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class BillingUnitCompletionReport {

	@Getter @Setter @Column(nullable = false) @Id private int CRID;	
	@Getter @Setter @Column(nullable = false) @ManyToOne private Contract contract;
	@Getter @Setter @Column(nullable = false) @ManyToOne private Project project;
	@Getter @Setter private String comment;
	@Getter @Setter private String username;
	@Getter @Setter private ArrayList<BillingUnit> billingUnits;
	@Getter @Setter private ArrayList<Image> images;
	
	public BillingUnitCompletionReport() {
		
	}
	
	public BillingUnitCompletionReport(Contract contract, Project project, String comment, String username, 
			                           ArrayList<BillingUnit> billingUnits, ArrayList<Image> images) {
		this.contract = contract;
		this.project = project;
		this.comment = comment;
		this.username = username;
		this.billingUnits = billingUnits;
		this.images = images;
	}
}
