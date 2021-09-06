package de.contracktor.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class BillingUnit {

	@Getter @Setter @Id private String billingUnitID;
	@Getter @Setter @NotNull private String unit;
	@Getter @Setter @NotNull private Date completionDate;
	@Getter @Setter @NotNull private Double totalPrice;
	@Getter @Setter @NotNull private Double totalQuantity;
	@Getter @Setter @OneToMany(mappedBy = "BillingUnit", cascade = CascadeType.ALL) private ArrayList<BillingItem> billingItems;
	@Getter @Setter private Boolean ownContractDefined;
	@Getter @Setter private String shortDescription;
	@Getter @Setter private String longDescription;
	
	public BillingUnit(String billingUnitID, String unit, Date completionDate, Double totalPrice, 
			           Double totalQuantity, ArrayList<BillingItem> billingItems, Boolean ownContractDefined, 
			           String shortDescription, String longDescription) {
		this.billingUnitID = billingUnitID;
		this.unit = unit;
		this.completionDate = completionDate;
		this.totalPrice = totalPrice;
		this.totalQuantity = totalQuantity;
		this.billingItems = billingItems;
		this.ownContractDefined = ownContractDefined;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}
}
