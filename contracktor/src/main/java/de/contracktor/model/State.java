package de.contracktor.model;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class State {

	@Getter @Setter @NotNull private String stateName;
	
	public State(String stateName) {
		this.stateName = stateName;
	}
}
