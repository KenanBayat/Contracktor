package de.contracktor.model;

import java.awt.Image;
import java.util.ArrayList;

import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class BillingUnitCompletionReport {

	@Getter @Setter @NotNull @Id private String CRID;	
	@Getter @Setter @NotNull @Id private int contractID;
	@Getter @Setter @NotNull @Id private int projectID;
	@Getter @Setter private String comment;
	@Getter @Setter private String username;
	@Getter @Setter private ArrayList<BillingUnit> billingUnits;
	@Getter @Setter private ArrayList<Image> images;
	
	public BillingUnitCompletionReport(int contractID, int projectID, String comment, String username, 
			                           ArrayList<BillingUnit> billingUnits, ArrayList<Image> images) {
		this.contractID = contractID;
		this.projectID = projectID;
		this.comment = comment;
		this.username = username;
		this.billingUnits = billingUnits;
		this.images = images;
	}
}
