package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	BillingItem billingItemInList1;
	BillingItem billingItemInList2;
	
	ArrayList<BillingItem> billingItems;
		
	
	@BeforeEach
	public void init() {
		billingItems = new ArrayList<BillingItem>();
		state = new State("billingStatus");
		stateRepo.save(state);
	
	}
	
	@AfterEach
	public void delete() {
		billingItemRepo.delete(billingItem);
		//billingItemRepo.delete(billingItemInList1);
		//billingItemRepo.delete(billingItemInList2);
		stateRepo.delete(state);
	}
	
	@Test
	public void testSavingBillingItem() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		
		//billingItemRepo.delete(billingItemInList1);
		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0,
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItemRepo.save(billingItem);
				
		assertTrue(billingItemRepo.existsById(billingItem.getId()));
		
		billingItemRepo.delete(billingItem);
		assertFalse(billingItemRepo.existsById(billingItemInList1.getId()));
		assertFalse(billingItemRepo.existsById(billingItemInList2.getId()));
	}
	
	@Test
	public void testNullBillingItemID() {
		billingItem = new BillingItem(null, "meter", 1000.0, 105.0, 100050.0, 
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}
	
	@Test
	public void testNullIFC() {
		billingItem = new BillingItem("ID_3346", "meter", 1000.0, 105.0, 100050.0, 
									null, state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}
	
	@Test 
	public void testNullDescription() {
		billingItem = new BillingItem("ID_334", "meter", 1000.0, 105.0, 100050.0, 
                					"3m7_6h4uXAXvBoFEtks_QE", state, null, billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}	
	
	@Test
	public void testEmptyBillingItemID() {
		billingItem = new BillingItem("", "meter", 1000.0, 105.0, 100050.0, 
				"3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}
}
