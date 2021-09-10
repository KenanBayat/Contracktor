package de.contracktor.controller.dato;

import de.contracktor.model.BillingItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class BillingItemsDato {

    private static BillingItemsDato instance = new BillingItemsDato();
    @Getter @Setter private List<BillingItem> billingItems;
    @Getter @Setter private ArrayList<BillingItem> billingItems1;
    @Getter @Setter private ArrayList<BillingItem> billingItems11;
    @Getter @Setter private ArrayList<BillingItem> billingItems2;

    public static BillingItemsDato getInstance(){
        return instance;
    }

    private BillingItemsDato(){
        StateDato state = StateDato.getInstance();
        BillingItem billingItem11 = new BillingItem("item1.1","item1.1",42.0, 12.5, 37.5, "IFC1.1", state.inProgress(), "shortItem1.1", null);
        BillingItem billingItem12 = new BillingItem("item1.2","item1.2",42.0, 12.5, 37.5, "IFC1.2", state.inProgress(), "shortItem1.2", null);
        billingItems11 = (ArrayList<BillingItem>) List.of(
            billingItem11,billingItem12
        );

        BillingItem billingItem1 = new BillingItem("item1","item1",42.0, 12.5, 37.5, "IFC1", state.inProgress(), "shortItem", null);
        BillingItem billingItem2 = new BillingItem("item2","item2",42.0, 12.5, 37.5, "IFC2", state.inProgress(), "shortItem", billingItems11);

        billingItems1 = (ArrayList<BillingItem>) List.of(
                billingItem1,billingItem2
        );

        BillingItem billingItem21 = new BillingItem("item21","item21",42.0, 12.5, 37.5, "IFC1", state.inProgress(), "shortItem", null);
        BillingItem billingItem22 = new BillingItem("item22","item22",42.0, 12.5, 37.5, "IFC2", state.inProgress(), "shortItem", null);
        billingItems2 = (ArrayList<BillingItem>) List.of(
            billingItem21,billingItem22
        );

        List<BillingItem> billingItems = new ArrayList<>();
        billingItems.add(billingItem1);
        billingItems.add(billingItem2);
        billingItems.add(billingItem11);
        billingItems.add(billingItem12);
        billingItems.add(billingItem21);
        billingItems.add(billingItem22);
    }


    public BillingItem findById(String itemId) {
        if(itemId == null){return null;}
        for(BillingItem item: billingItems){
            if(itemId.equals(item.getBillingItemID())){
                return item;
            }
        }
        return null;
    }
}
