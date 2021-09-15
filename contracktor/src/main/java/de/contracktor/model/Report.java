package de.contracktor.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Report {

	@Getter @Setter @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @NotEmpty Integer reportID;
	@Getter @Setter @ManyToOne BillingItem billingItem;
	@Getter @Setter @JoinColumn(nullable = false) @OneToOne Organisation organisation;
	@Getter @Setter @Column(nullable = false) @JsonIgnore Long date;
	@Getter @Setter @Column(nullable = false) @NotEmpty String username;
	@Getter @Setter @Column(nullable = false) String comment;
	
	public Report() {}
	
	public Report(Integer reportID,BillingItem billingItem, Organisation organisation, Long date,
			      String username, String comment) {
		this.reportID=reportID;
		this.billingItem = billingItem;
		this.organisation = organisation;
		this.date = date;
		this.username = username;
		this.comment = comment;
	}
}
