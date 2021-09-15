package de.contracktor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingUnit {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @JsonProperty("id") private String billingUnitID;
	@Getter @Setter @ManyToOne @JoinColumn(nullable = false) private Contract contract;
	@Getter @Setter @Column private String unit;
	@Getter @Setter @Column(nullable = false) @JsonIgnore private LocalDate completionDate;
	@Getter @Setter @Column(nullable = false) private Double totalPrice;
	@Getter @Setter @Column(nullable = false) private Double totalQuantity;
	@Getter @Setter @OneToMany(fetch = FetchType.EAGER) private List<BillingItem> billingItems;
	@Getter @Setter @Column private Boolean ownContractDefined;
	@Getter @Setter @Column(nullable = false, length = 8192) private String shortDescription;
	@Getter @Setter @Column(nullable = false, length = 8192) private String longDescription;
	@Getter @Setter @JoinColumn @ManyToOne private State status;
	@Getter @Setter @Transient @JsonProperty ("completionDate")
	private String completionDateString;

	
	public BillingUnit() {}
	
	public BillingUnit(String billingUnitID, String unit, LocalDate completionDate, Double totalPrice, 
			           Double totalQuantity, Contract contract,ArrayList<BillingItem> billingItems ,Boolean ownContractDefined, 
			           String shortDescription, String longDescription, State status) {
		this.billingUnitID = billingUnitID;
		this.unit = unit;
		this.completionDate = completionDate;
		this.totalPrice = totalPrice;
		this.totalQuantity = totalQuantity;
		this.contract = contract;
		this.billingItems = billingItems;
		this.ownContractDefined = ownContractDefined;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.status = status;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BillingUnit other = (BillingUnit) obj;
		return Objects.equals(billingUnitID, other.billingUnitID) && id == other.id;
	}	
}
