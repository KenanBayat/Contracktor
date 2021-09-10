package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Address;
import de.contracktor.model.BillingItem;

public interface AddressRepository extends CrudRepository<Address, Integer> {

}
