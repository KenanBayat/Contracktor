package de.contracktor.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Report {

	@Getter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Getter @Setter @JoinColumn(nullable = false) @OneToMany List<BillingItem> billingItems;
	@Getter @Setter @Column(nullable = false) LocalDate date;
	@Getter @Setter @Column(nullable = false) String username;
	@Getter @Setter String comment;
	@Getter @Setter @OneToMany List<Picture> pictures;
	
	public Report() {
		
	}
	
	public Report(List<BillingItem> billingItems, LocalDate date, String username, String comment, ArrayList<Picture> pictures) {
		this.billingItems = billingItems;
		this.date = date;
		this.username = username;
		this.comment = comment;
		this.pictures = pictures;
	}
}
