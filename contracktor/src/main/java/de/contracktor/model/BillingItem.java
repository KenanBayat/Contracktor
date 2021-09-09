package de.contracktor.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingItem {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	@Column(name = "id") @JsonIgnore
	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @JsonProperty("id")
	String billingItemID;
	@Getter @Setter @Column(nullable = false) private String unit;
	@Getter @Setter @Column(nullable = false) private Double quantity;
	@Getter @Setter @Column(nullable = false) private Double pricePerUnit;
	@Getter @Setter @Column(nullable = false) private Double totalPrice;
	@Getter @Setter @Column(nullable = false) private String IFC;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private State status;
	@Getter @Setter @OneToMany(cascade = CascadeType.REMOVE) private List<BillingItem> billingItems;
	@Getter @Setter @Column(nullable = false) private String shortDescription;
	
	public BillingItem() {
		
	}
	
	public BillingItem(String billingItemID, String unit, Double quantity, Double pricePerUnit,
			           Double totalPrice, String IFC, State status, String shortDescription, ArrayList<BillingItem> billingItems) {
		this.billingItemID = billingItemID;
		this.unit = unit;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.IFC = IFC;
		this.status = status;
		this.billingItems = billingItems; 
		this.shortDescription = shortDescription;
	}
}
