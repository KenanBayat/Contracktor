package de.sopro.model;

import java.util.ArrayList;

import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class BillingItem {

	@Getter @Setter @Id String billingItemID;
	@Getter @Setter @NotNull private String unit;
	@Getter @Setter @NotNull private Double quantity;
	@Getter @Setter @NotNull private Double pricePerUnit;
	@Getter @Setter @NotNull private Double totalPrice;
	@Getter @Setter @NotNull private String IFC;
	@Getter @Setter @NotNull private State status;
	@Getter @Setter private ArrayList<BillingItem> billingItems;
	@Getter @Setter private String shortDescription;
	
	public BillingItem(String billingItemID, String unit, Double quantity, Double pricePerUnit,
			           Double totalPrice, String IFC, State status, ArrayList<BillingItem> billingItems) {
		this.billingItemID = billingItemID;
		this.unit = unit;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.IFC = IFC;
		this.status = status;
		this.billingItems = billingItems;
	}
}
