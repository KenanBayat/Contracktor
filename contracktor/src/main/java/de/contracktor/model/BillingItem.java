package de.contracktor.model;

import java.util.ArrayList;
import java.util.List;
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
public class BillingItem {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)	@Column(name = "id") @JsonIgnore
	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @NotEmpty @JsonProperty("id")
	String billingItemID;
	@Getter @Setter @Column(nullable = false) private String billingUnit_ID;
	
	@Getter @Setter @Column(nullable = false) private String unit;
	@Getter @Setter @Column(nullable = false) @JsonProperty("quantities") private Double quantity;
	@Getter @Setter @Column @JsonProperty("unitPrice") private Double pricePerUnit;
	@Getter @Setter @Column(nullable = false) @JsonProperty("price") private Double totalPrice;
	@Getter @Setter @Column(nullable = false) @JsonProperty("shortDesLinkedIFC") private String IFC;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne @JsonProperty("status_") private State status;
	@Getter @Setter @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER) private List<BillingItem> billingItems;
	@Getter @Setter @Column(nullable = false) private String shortDescription;
	@Getter @Setter @Transient @JsonProperty("status") String statusName;
	@Getter @Setter @Column private long lastModified;
	
	public BillingItem() {}
	
	public BillingItem(String billingItemID, String billingUnit_ID, String unit, Double quantity, Double pricePerUnit,
			           Double totalPrice, String IFC, State status, String shortDescription ,ArrayList<BillingItem> billingItems) {
		this.billingItemID = billingItemID;
		this.billingUnit_ID = billingUnit_ID;
		this.unit = unit;
		this.quantity = quantity;
		this.pricePerUnit = pricePerUnit;
		this.totalPrice = totalPrice;
		this.IFC = IFC;
		this.status = status;
		this.billingItems = billingItems; 
		this.shortDescription = shortDescription;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillingItem other = (BillingItem) obj;
		return Objects.equals(billingItemID, other.billingItemID); //&& id == other.id;
	}

	public String getLowerDescription() {
		return this.shortDescription.toLowerCase();
	}

	public String getLowerID() {
		return this.billingItemID.toLowerCase();
	}

	public String getLowerStatus() {
		return this.status.getStateName().toLowerCase();
	}
}
