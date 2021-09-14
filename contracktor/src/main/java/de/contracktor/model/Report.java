package de.contracktor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Report {

	@Getter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Getter @Setter @OneToMany List<BillingItem> billingItems;
	@Getter @Setter @JoinColumn(nullable = false) @OneToOne Organisation organisation;
	@Getter @Setter @Column(nullable = false) @JsonIgnore LocalDate date;
	@Getter @Setter @Column(nullable = false) @NotEmpty String username;
	@Getter @Setter @Column(nullable = false) String comment;
	@Getter @Setter @OneToMany List<Picture> pictures;
	
	public Report() {
		
	}
	
	public Report(List<BillingItem> billingItems, Organisation organisation, LocalDate date,
			      String username, String comment, ArrayList<Picture> pictures) {
		this.billingItems = billingItems;
		this.organisation = organisation;
		this.date = date;
		this.username = username;
		this.comment = comment;
		this.pictures = pictures;
	}
}
