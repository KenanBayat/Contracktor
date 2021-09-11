package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.BillingItem;

import java.util.List;
import java.util.Optional;

public interface BillingItemRepository extends CrudRepository<BillingItem, Integer> {
    List<BillingItem> findAll();
    Optional<BillingItem> findByBillingItemID(String billingItemID);

	
}
