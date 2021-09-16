package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class APIResponse {

    @Getter @Setter
    private List<Project> projects;

    @Getter @Setter
    private List<Contract> contracts;

    @Getter @Setter
    private List<BillingUnit> billingUnits;

    @Getter @Setter
    private List<State> states;

    @Getter @Setter
    private List<StateTransition> stateTransitions;

    @Getter @Setter
    private List<Picture> pictures;

    @Getter @Setter
    boolean writePerm;

    @Getter @Setter
    public String status;

    public APIResponse(List<Project> projects, List<Contract> contracts, List<BillingUnit> billingUnits,
                       List<State> states, List<StateTransition> stateTransitions, List<Picture> pictures,
                       boolean writePerm, String status) {
        this.projects = projects;
        this.contracts = contracts;
        this.billingUnits = billingUnits;
        this.states = states;
        this.stateTransitions = stateTransitions;
        this.pictures = pictures;
        this.writePerm = writePerm;
        this.status = status;
    }

    public APIResponse() {
    }

    public APIResponse(String status) {
        this.status = status;
    }
}
