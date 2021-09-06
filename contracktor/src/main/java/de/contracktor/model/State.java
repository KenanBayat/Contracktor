package de.contracktor.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class State {

	@Getter @Setter @NotNull @Id private String stateName;
	
	public State() {
		
	}
	
	public State(String stateName) {
		this.stateName = stateName;
	}
}
