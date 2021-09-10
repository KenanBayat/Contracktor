package de.contracktor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillingModel {

    private List<BillingUnit> billingUnits;

    public List<BillingUnit> getBillingUnits() {
        return billingUnits;
    }

    public void setBillingUnits(List<BillingUnit> billingUnits) {
        this.billingUnits = billingUnits;
    }

}
