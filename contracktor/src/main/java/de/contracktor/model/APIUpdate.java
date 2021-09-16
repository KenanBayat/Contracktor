package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

public class APIUpdate {

    @Getter @Setter
    private List<BillingItemUpdate> billingItemUpdates;

    @Getter @Setter
    private List<Picture> pictureList;

    @Getter @Setter
    private List<Report> reportList;

}
