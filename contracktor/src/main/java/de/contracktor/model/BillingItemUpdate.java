package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;

public class BillingItemUpdate {
    @Getter @Setter
    private String billingItemID;
    @Getter @Setter
    private State newState;
    @Getter @Setter
    private long lastModified;

    public BillingItemUpdate(String billingItemID, State newState, long lastModified) {
        this.billingItemID = billingItemID;
        this.newState = newState;
        this.lastModified = lastModified;
    }
}
