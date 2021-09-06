package de.contracktor.model;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

public class StateTransition {

	@Getter @Setter @NotNull private State startState;
	@Getter @Setter @NotNull private State endState;
	
	public StateTransition(State startState, State endState) {
		this.startState = startState;
		this.endState = endState;
	}
}
