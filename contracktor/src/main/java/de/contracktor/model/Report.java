package de.contracktor.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Report {

	@Getter @Setter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int reportID;
	@Getter @Setter @Column(nullable = false) String billingItemID;
	@Getter @Setter @Column(nullable = false) Date date;
	@Getter @Setter @Column(nullable = false) String username;
	@Getter @Setter String comment;
	@Getter @Setter ArrayList<Image> images;
	
	public Report() {
		
	}
	
	public Report(String billingItemID, Date date, String username, String comment, ArrayList<Image> images) {
		this.billingItemID = billingItemID;
		this.date = date;
		this.username = username;
		this.comment = comment;
		this.images = images;
	}
}
