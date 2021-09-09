package de.contracktor.model;

import java.util.List;

public class APIUpdate {

    private List<BillingItem> billingItemList;
    private List<Report> reportList;

    public List<BillingItem> getBillingItemList() {
        return billingItemList;
    }

    public List<Report> getReportList() {
        return reportList;
    }

}
