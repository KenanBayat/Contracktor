package de.contracktor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
public class State {

	@Getter @Setter @Column(nullable = false) @Id private String stateName;
	
	public State() {
		
	}
	
	public State(String stateName) {
		this.stateName = stateName;
	}
}
