package de.contracktor.model;

import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public class APIUpdate {

    private Map<String, Pair<State, Long>> billingItemUpdates;
    private List<Report> reportList;

    public Map<String,Pair<State, Long>> getBillingItemIDList() {
        return billingItemUpdates;
    }

    public List<Report> getReportList() {
        return reportList;
    }

}
