package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.BillingItem;

public interface BillingItemRepository extends CrudRepository<BillingItem, String> {

}
