package de.contracktor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Embeddable
@Entity
public class StateTransition implements Serializable {

	@Getter @Column(nullable = false) @Id @GeneratedValue(strategy = GenerationType.AUTO) private int id;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne State startState; 
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne State endState; 
	
	public StateTransition() {
		
	}
	
	public StateTransition(State startState, State endState) {
		this.startState = startState;
		this.endState = endState;
	}
}
