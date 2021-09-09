package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Contract;

public interface ContractRepository extends CrudRepository<Contract, Integer>{

}
