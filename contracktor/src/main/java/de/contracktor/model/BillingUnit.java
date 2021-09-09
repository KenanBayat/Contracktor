package de.contracktor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
public class BillingUnit {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) private String billingUnitID;
	@Getter @Setter @ManyToOne @JoinColumn(nullable = false) private Contract contract;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String unit;
	@Getter @Setter @Column(nullable = false) private Date completionDate;
	@Getter @Setter @Column(nullable = false) private Double totalPrice;
	@Getter @Setter @Column(nullable = false) private Double totalQuantity;
	@Getter @Setter @OneToMany(cascade = CascadeType.ALL) private List<BillingItem> billingItems;
	@Getter @Setter @Column(nullable = false) private Boolean ownContractDefined;
	@Getter @Setter @Column(nullable = false) private String shortDescription;
	@Getter @Setter @Column(nullable = false) private String longDescription;
	
	public BillingUnit() {
		
	}
	
	public BillingUnit(String billingUnitID, String unit, Date completionDate, Double totalPrice, 
			           Double totalQuantity, Boolean ownContractDefined, 
			           String shortDescription, String longDescription) {
		this.billingUnitID = billingUnitID;
		this.unit = unit;
		this.completionDate = completionDate;
		this.totalPrice = totalPrice;
		this.totalQuantity = totalQuantity;
		this.billingItems = new ArrayList<BillingItem>();
		this.ownContractDefined = ownContractDefined;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
	}
}
