package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Contract {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.AUTO)	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) private int contractID;
	@Getter @Setter @ManyToOne @JoinColumn(nullable = false) private Project project;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String name;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String consignee;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne private State status;
	@Getter @Setter @Column(nullable = false) @NotEmpty private String contractor;
	@Getter @Setter @Column(nullable = false) private String description;

	public Contract() {
		
	}
	
	public Contract(int contractID, Project project, String name, String consignee, State status, String contractor, 
			        String description) {
		this.contractID = contractID;
		this.project = project;
		this.name = name;
		this.consignee = consignee;
		this.status = status;
		this.contractor = contractor;
		this.description = description;
	}
}
