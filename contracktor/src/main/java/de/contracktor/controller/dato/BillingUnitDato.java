package de.contracktor.controller.dato;

import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillingUnitDato {

    @Getter @Setter private List<BillingUnit> bunits;

    private static BillingUnitDato instance = new BillingUnitDato();

    private BillingUnitDato(){
        StateDato state = StateDato.getInstance();
        ProjectsDato projects = ProjectsDato.getInstance();
        ContractDato contracts = ContractDato.getInstance();

        ArrayList<BillingItem> billingItems11 = (ArrayList<BillingItem>) List.of(
                new BillingItem("item1.1","item1.1",42.0, 12.5, 37.5, "IFC1.1", state.inProgress(), "shortItem1.1", null),
                new BillingItem("item1.2","item1.2",42.0, 12.5, 37.5, "IFC1.2", state.inProgress(), "shortItem1.2", null)
        );

        ArrayList<BillingItem> billingItems1 = (ArrayList<BillingItem>) List.of(
                new BillingItem("item1","item1",42.0, 12.5, 37.5, "IFC1", state.inProgress(), "shortItem", null),
                new BillingItem("item2","item2",42.0, 12.5, 37.5, "IFC2", state.inProgress(), "shortItem", billingItems11)
        );

        ArrayList<BillingItem> billingItems2 = (ArrayList<BillingItem>) List.of(
                new BillingItem("item21","item21",42.0, 12.5, 37.5, "IFC1", state.inProgress(), "shortItem", null),
                new BillingItem("item22","item22",42.0, 12.5, 37.5, "IFC2", state.inProgress(), "shortItem", null)
        );

        List<BillingUnit> bunitList = List.of(
              new BillingUnit("11-11", "unit1", LocalDate.of(2000,9,9),5.0, 69.0,contracts.contracts.get(0),billingItems1,true, "short", "long" ),
                new BillingUnit("11-12", "unit1", LocalDate.of(2000,9,9),5.0, 69.0,contracts.contracts.get(0),billingItems2,true, "short2", "long2" )
        );
        bunits = bunitList;
    }

    public static BillingUnitDato getInstance(){
        return instance;
    }

}
