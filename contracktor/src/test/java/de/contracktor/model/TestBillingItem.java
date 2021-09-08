package de.contracktor.model;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.StateRepository;

@SpringBootTest
public class TestBillingItem {

	@Autowired
	BillingItemRepository billingItemRepo;
	
	@Autowired
	StateRepository stateRepo;
	
	State state;
	
	BillingItem billingItem;
	
	ArrayList<BillingItem> billingItems;
	
	
	@BeforeEach
	public void init() {
		state = new State("billingStatus");
		stateRepo.save(state);
		billingItems = new ArrayList<BillingItem>();
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0, "3m5_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItemRepo.save(billingItem);
	}
	
	@AfterEach
	public void delete() {
		billingItemRepo.delete(billingItem);
		stateRepo.delete(state);
	}
	
	@Test
	public void test() {
		System.out.println("hello");
	}
}
