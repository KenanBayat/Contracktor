package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import de.contracktor.model.BillingItem;
import java.util.List;
import java.util.Optional;

public interface BillingItemRepository extends CrudRepository<BillingItem, Integer> {
    
	boolean existsByBillingItemID(String id);

    List<BillingItem> findAll();
    List<BillingItem> findByBillingItemIDContains(String substr);

    Optional<BillingItem> findByBillingItemID(String billingItemID);


}
