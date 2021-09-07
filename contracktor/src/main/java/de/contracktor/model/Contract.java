package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Contract {

	@Getter @Setter @Column(nullable = false) @Id    private int contractID;
	@Getter @Setter @ManyToOne @Column(nullable = false) private Project project;
	@Getter @Setter @Column(nullable = false) private String name;
	@Getter @Setter @Column(nullable = false) private String consignee;
	@Getter @Setter @Column(nullable = false) @ManyToOne private State status;
	@Getter @Setter @Column(nullable = false) private String contractor;
	@Getter @Setter private String description;

	public Contract() {
		
	}
	
	public Contract(Project project, String name, String consignee, State status, String contractor, 
			        String description) {
		this.project = project;
		this.name = name;
		this.consignee = consignee;
		this.status = status;
		this.contractor = contractor;
		this.description = description;
	}
}
