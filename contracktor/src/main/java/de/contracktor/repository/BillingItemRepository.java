package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.BillingItem;

import java.util.Optional;

public interface BillingItemRepository extends CrudRepository<BillingItem, Integer> {
    Optional<BillingItem> findByBillingItemID(String billingItemID);

	
}
