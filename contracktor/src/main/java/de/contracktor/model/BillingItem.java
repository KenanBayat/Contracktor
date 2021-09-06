package de.contracktor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class BillingItem {

	@Getter @Setter @Id String billingItemID;
	@Getter @Setter @NotNull private String unit;
	@Getter @Setter @NotNull private Double quantity;
	@Getter @Setter @NotNull private Double pricePerUnit;
	@Getter @Setter @NotNull private Double totalPrice;
	@Getter @Setter @NotNull private String IFC;
	@Getter @Setter @NotNull @ManyToOne private State status;
	@Getter @Setter @OneToMany private List<BillingItem> billingItems;
	@Getter @Setter private String shortDescription;
	
	public BillingItem() {
		
	}
	
	public BillingItem(String billingItemID, String unit, Double quantity, Double pricePerUnit,
			           Double totalPrice, String IFC, State status) {
		this.billingItemID = billingItemID;
		this.unit = unit;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.IFC = IFC;
		this.status = status;
		this.billingItems = new ArrayList<BillingItem>(); 
	}
}
