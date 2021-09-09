package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Contract;

import java.util.List;

public interface ContractRepository extends CrudRepository<Contract, Integer>{
    List<Contract> findByContractorIgnoreCaseOrConsigneeIgnoreCase(String contractor, String consignee);

}
