package de.contracktor.model;

import javax.persistence.Entity;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class StateTransition {

	@Getter @Setter @NotNull private State startState;
	@Getter @Setter @NotNull private State endState;
	
	public StateTransition(State startState, State endState) {
		this.startState = startState;
		this.endState = endState;
	}
}
