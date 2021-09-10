package de.contracktor.controller.dato;

import de.contracktor.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class ContractDato {
    @Getter @Setter
    List<Contract> contracts;

    private static ContractDato instance = new ContractDato();

    private ContractDato(){
        ProjectsDato projects = ProjectsDato.getInstance();
        StateDato state = StateDato.getInstance();

        List<Contract> contractList = List.of(
                new Contract(4, projects.getProjects().get(1), "Vertrag1", "Consi" , state.inProgress(),"Arbeiter", "test0"),
                new Contract(5, projects.getProjects().get(1), "Vertrag1", "Consi" , state.inProgress(),"Arbeiter", "test0"),
                new Contract(6, projects.getProjects().get(1), "Vertrag1", "Consi" , state.finished(),"Arbeiter", "test2")
        );
        contracts = contractList;
    }

    public static ContractDato getInstance(){
        return instance;
    }

    public List<Contract> filterByProjectID(int projectId){
        List<Contract> filterList = new ArrayList<>();
        for (Contract contract: contracts){
            if(projectId == contract.getProjectId()+1){
                filterList.add(contract);
            }
        }

        return filterList;
    }

    public Contract findById(int contractId){
        for(Contract contract: contracts){
            if(contractId == contract.getContractID()){
                return contract;
            }
        }
        return null;
    }
}