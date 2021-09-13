package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Contract;
import de.contracktor.model.Project;

import java.util.List;

public interface ContractRepository extends CrudRepository<Contract, Integer>{
    List<Contract> findByContractorIgnoreCaseOrConsigneeIgnoreCase(String contractor, String consignee);

    boolean existsByContractID(int id);
    
    Contract findByContractID(int id);
    
    List<Contract> findByProject(Project project);
}
