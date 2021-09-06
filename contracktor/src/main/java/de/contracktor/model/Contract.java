package de.contracktor.model;

import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Contract {

	@Getter @Setter @NotNull @Id    private int contractID;
	@Getter @Setter @ManyToOne @NotNull private int projectID;
	@Getter @Setter @NotNull private String name;
	@Getter @Setter @NotNull private String consignee;
	@Getter @Setter @NotNull private State status;
	@Getter @Setter @NotNull private String contractor;
	@Getter @Setter private String description;

	public Contract(int projectID, String name, String consignee, State status, String contractor, 
			        String description) {
		this.projectID = projectID;
		this.name = name;
		this.consignee = consignee;
		this.status = status;
		this.contractor = contractor;
		this.description = description;
	}
}
