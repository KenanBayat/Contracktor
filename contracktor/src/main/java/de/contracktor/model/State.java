package de.contracktor.model;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class State {

	@Getter @Setter @NotNull private String stateName;
	
	public State(String stateName) {
		this.stateName = stateName;
	}
}
