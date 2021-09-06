package de.contracktor.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
public class StateTransition {

	@Getter @Setter @NotNull @Id @GeneratedValue(strategy = GenerationType.AUTO) private int stateTranstionID;
	@Getter @Setter @NotNull @ManyToOne private State startState;
	@Getter @Setter @NotNull @ManyToOne private State endState;
	
	public StateTransition() {
		
	}
	
	public StateTransition(State startState, State endState) {
		this.startState = startState;
		this.endState = endState;
	}
}
