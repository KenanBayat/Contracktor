package de.contracktor.model;

import java.util.List;

public class APIResponse {

    private List<Project> projects;
    private List<Contract> contracts;
    private List<BillingUnit> billingUnits;
    private List<State> states;
    private List<StateTransition> stateTransitions;
    private List<Report> reports;

    boolean writePerm;

    public String status;

    public APIResponse(List<Project> projects, List<Contract> contracts, List<BillingUnit> billingUnits,
                       List<State> states, List<StateTransition> stateTransitions, List<Report> reports,
                       boolean writePerm, String status) {
        this.projects = projects;
        this.contracts = contracts;
        this.billingUnits = billingUnits;
        this.states = states;
        this.stateTransitions = stateTransitions;
        this.reports = reports;
        this.writePerm = writePerm;
        this.status = status;
    }

    public APIResponse() {
    }

    public APIResponse(String status) {
        this.status = status;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<BillingUnit> getBillingUnits() {
        return billingUnits;
    }

    public void setBillingUnits(List<BillingUnit> billingUnits) {
        this.billingUnits = billingUnits;
    }

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<StateTransition> getStateTransitions() {
        return stateTransitions;
    }

    public void setStateTransitions(List<StateTransition> stateTransitions) {
        this.stateTransitions = stateTransitions;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public boolean isWritePerm() {
        return writePerm;
    }

    public void setWritePerm(boolean writePerm) {
        this.writePerm = writePerm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
