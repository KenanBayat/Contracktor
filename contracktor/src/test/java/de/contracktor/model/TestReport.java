package de.contracktor.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.ReportRepository;
import de.contracktor.repository.StateRepository;

@SpringBootTest
public class TestReport {

	@Autowired
	BillingItemRepository billingItemRepo;
	
	@Autowired
	ReportRepository reportRepo;
	
	@Autowired
	StateRepository stateRepo;
	
	Report report;
	
	BillingItem billingItem;
	
	State state;
	
	@BeforeEach
	public void init() {
		state = new State("Processing");
		stateRepo.save(state);
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0, "3m5_6h4uXAXvBoFEtks_QE", state);
		billingItemRepo.save(billingItem);
	}
	
	@AfterEach
	public void delete() {
		billingItemRepo.delete(billingItem);
	}
	
	@Test
	public void testNullValue() {
		
	}
}
