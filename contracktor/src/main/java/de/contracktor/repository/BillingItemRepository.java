package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.BillingItem;
import de.contracktor.model.BillingUnit;
import de.contracktor.model.Contract;
import de.contracktor.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BillingItemRepository extends CrudRepository<BillingItem, Integer> {
    
	boolean existsByBillingItemID(String id);

    List<BillingItem> findAll();

    Optional<BillingItem> findByBillingItemID(String billingItemID);

}
