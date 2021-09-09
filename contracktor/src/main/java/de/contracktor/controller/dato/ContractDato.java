package de.contracktor.controller.dato;

import de.contracktor.model.*;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import de.contracktor.model.Organisation;
import de.contracktor.model.Project;
import de.contracktor.model.State;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

public class ContractDato {
    @Getter @Setter List<Contract> contracts;

    public ContractDato(){
        State finished = new State("finished");
        State inProgress = new State("in progress");
        Organisation hochtief = new Organisation("Hochtief", "Elbstraße", "7", "Hamburg", "22406", "Deutschland");
        Organisation züblin = new Organisation("Züblin", "Haupstraße", "26", "Hamburg", "22317", "Deutschland");
        Project p1 = new Project("Hausbau", new Date(2000,9,27), "Straße","4","Stadt","24106","Deutschland", 420.69, hochtief,"Günther", finished,null, "house");
        Project p2 = new Project("Bahnhof",new Date(2004,7,14),"Street","7","Stadt","24106","Deutschland", 1337.69, züblin,"Eberhardt", finished,null,"trains");
        Project p3 =new Project("Berliner Flughafen",new Date(2099,11,31),"Alee","8","Stadt","24106","Deutschland", 9999.99, züblin,"Sabine", inProgress,null,"money");
        List<Project> projectList = List.of(
                p1,p2,p3
        );

        List<Contract> contractList = List.of(
                new Contract(p1, "Vertrag1", "Consi" , inProgress,"Arbeiter", "test0"),
                new Contract(p1, "Vertrag2", "ConsiD" , inProgress,"Arbeiter", "test1"),
                new Contract(p1, "Vertrag3", "ConsiD" , finished,"Arbeiter", "test2"),
                new Contract(p1, "Vertrag4", "ConsiD" , finished,"Arbeiter", "test3"),
                new Contract(p3, "Vertrag4", "ConsiD" , finished,"Arbeiter", "test3")
        );
        contracts = contractList;
    }
}