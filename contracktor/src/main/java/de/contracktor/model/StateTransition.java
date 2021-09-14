package de.contracktor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
public class StateTransition implements Serializable {

	@Getter @Column(nullable = false) @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne State startState; 
	@Getter @Setter @JoinColumn(nullable = false) @ManyToOne State endState; 
	@Getter	@Setter	@Column(nullable = false) private Boolean contractor;
	@Getter	@Setter	@Column(nullable = false) private Boolean consignee;
	
	public StateTransition() {
		
	}
	
	public StateTransition(State startState, State endState, boolean contractor,boolean consignee) {
		if(contractor && consignee) {
			throw new IllegalArgumentException("Both, contractor and consignee "
					+ "cant manage the state transition");
		}
		
		this.startState = startState;
		this.endState = endState;
		this.contractor = contractor;
		this.consignee = consignee;
	}

	public String getStartStateName() {
		return this.startState.getStateName();
	}

	public String getEndStateName() {
		return this.endState.getStateName();
	}
}
