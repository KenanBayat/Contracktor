package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	ArrayList<BillingItem> billingItems;
	
	State state;
	
	@BeforeEach
	public void init() {
		state = new State("Processing");
		stateRepo.save(state);
		billingItems = new ArrayList<BillingItem>();
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0, "3m5_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItem = billingItemRepo.save(billingItem);
	
		//billingItems.add(billingItem);
	}
	
	@AfterEach
	public void delete() {
		billingItemRepo.delete(billingItem);
		stateRepo.delete(state);
		reportRepo.delete(report);
	}
	
	@Test
	public void testNullValue() {
		LocalDate date = LocalDate.of(2021, 9, 8);
		
		// Test null date.
		report = new Report(billingItems, null, "hans", null, null);
		assertThrows(Exception.class, () -> reportRepo.save(report));
			
		// Test null username.
		report = new Report(billingItems, date, null, null, null);
		assertThrows(Exception.class, () -> reportRepo.save(report));
		
		
	}
}
