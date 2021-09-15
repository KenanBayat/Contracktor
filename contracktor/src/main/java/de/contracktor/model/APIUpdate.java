package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.Map;

public class APIUpdate {

    @Getter @Setter
    private List<BillingItemUpdate> billingItemUpdates;

    @Getter @Setter
    private List<Picture> pictureList;

}
