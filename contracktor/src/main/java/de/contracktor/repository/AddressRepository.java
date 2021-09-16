package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import de.contracktor.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {

	Address findByAddressId(int id);

	boolean existsByAddressId(int id);
}
