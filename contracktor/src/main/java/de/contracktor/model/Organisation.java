package de.contracktor.model;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Organisation {
	
	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	private int id;
	@Getter @Column(nullable = false) @NotEmpty private String organisationName;
	@Getter @Setter @JoinColumn(nullable = false) @OneToOne(cascade = CascadeType.REMOVE) private Address address;
	
	public Organisation() {
		
	}
	
	public Organisation(String organisationName, Address address) {
		this.organisationName = organisationName;
		this.address = address;
	}
	
}
