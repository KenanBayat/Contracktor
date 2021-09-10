package de.contracktor.model;

import java.util.List;
import java.util.Map;

public class APIUpdate {

    private Map<String,String> billingItemUpdates;
    private List<Report> reportList;

    public Map<String,String> getBillingItemIDList() {
        return billingItemUpdates;
    }

    public List<Report> getReportList() {
        return reportList;
    }

}
