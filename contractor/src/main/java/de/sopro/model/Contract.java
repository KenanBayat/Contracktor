package de.sopro.model;

import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class Contract {

	@Getter @Setter @Id      private Long contractID;
	@Getter @Setter @NotNull private Long projectID;
	@Getter @Setter @NotNull private String name;
	@Getter @Setter @NotNull private String consignee;
	@Getter @Setter @NotNull private State status;
	@Getter @Setter @NotNull private String contractor;
	@Getter @Setter private String description;

	public Contract(Long projectID, String name, String consignee, State status, String contractor, 
			        String description) {
		this.projectID = projectID;
		this.name = name;
		this.consignee = consignee;
		this.status = status;
		this.contractor = contractor;
		this.description = description;
	}
}
