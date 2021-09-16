package de.contracktor.repository;

import de.contracktor.model.Contract;
import org.springframework.data.repository.CrudRepository;
import de.contracktor.model.BillingUnit;
import java.util.Collection;
import java.util.List;

public interface BillingUnitRepository extends CrudRepository<BillingUnit, Integer>{
    List<BillingUnit> findByContractIsIn(Collection<Contract> contracts);
    
    boolean existsByBillingUnitID(String id);
    
    BillingUnit findByBillingUnitID(String id);
    
    List<BillingUnit> findByContract(Contract contract);
    
    BillingUnit findByid(int id);
    
    List<BillingUnit> findAllByContract(Contract contract);

    List<BillingUnit> findAllBy();
}
