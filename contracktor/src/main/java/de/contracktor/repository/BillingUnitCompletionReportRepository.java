package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.BillingUnitCompletionReport;

public interface BillingUnitCompletionReportRepository extends CrudRepository<BillingUnitCompletionReport, Integer>{

	boolean existsByCRID(int id);
	
	BillingUnitCompletionReport findByCRID(int id);
}
