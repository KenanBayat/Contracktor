package de.contracktor.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Report {

	@Getter @Setter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int reportID;
	@Getter @Setter @NotNull String billingItemID;
	@Getter @Setter @NotNull Date date;
	@Getter @Setter @NotNull String username;
	@Getter @Setter String comment;
	@Getter @Setter ArrayList<Image> images;
	
	public Report(String billingItemID, Date date, String username, String comment, ArrayList<Image> images) {
		this.billingItemID = billingItemID;
		this.date = date;
		this.username = username;
		this.comment = comment;
		this.images = images;
	}
}
