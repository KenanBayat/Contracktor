package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Entity
public class State {

	@Getter	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)	private int id;
	@Getter @Setter @Column(nullable = false, unique = true) @NotEmpty private String stateName;
	
	public State() {
		
	}
	
	public State(String stateName) {
		this.stateName = stateName;
	}
}
