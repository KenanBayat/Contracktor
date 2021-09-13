package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.model.BillingItem;
import de.contracktor.model.State;
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
		stateRepo.delete(state);
	}
	

	
	@Test
	public void testNullBillingItemID() {
		billingItem = new BillingItem(null, "meter", 1000.0, 105.0, 100050.0, 
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}
	
	@Test
	public void testNullQuantity() {
		billingItem = new BillingItem("ID_3346", "meter", null, 105.0, 100050.0, 
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}
	
	@Test
	public void testNullTotalPrice() {
		billingItem = new BillingItem("ID_3346", "meter", 1000.0, 105.0, null, 
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
	public void testNullState() {
		billingItem = new BillingItem("ID_3346", "meter", 1000.0, 105.0, 100050.0, 
									  "3m7_6h4uXAXvBoFEtks_QE", null, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}
	
	@Test 
	public void testNullShortDescription() {
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
	
	@Test
	public void testSaveBillingItem() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		
		
		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0,
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItemRepo.save(billingItem);
				
		assertTrue(billingItemRepo.existsById(billingItemInList1.getId()));
		assertTrue(billingItemRepo.existsById(billingItemInList2.getId()));
		assertTrue(billingItemRepo.existsById(billingItem.getId()));
		
		billingItemRepo.delete(billingItem);
		
		assertFalse(billingItemRepo.existsById(billingItem.getId()));
		assertFalse(billingItemRepo.existsById(billingItemInList1.getId()));
		assertFalse(billingItemRepo.existsById(billingItemInList2.getId()));
	}
	
	@Test
	public void testRepositoryFunctionsBillingItem() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		
		
		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0,
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItemRepo.save(billingItem);
				
		assertTrue(billingItemRepo.existsByBillingItemID(billingItem.getBillingItemID()));
		assertTrue(billingItemRepo.existsByBillingItemID(billingItemInList1.getBillingItemID()));
		assertTrue(billingItemRepo.existsByBillingItemID(billingItemInList2.getBillingItemID()));
		
		billingItemRepo.delete(billingItem);
		
		assertFalse(billingItemRepo.existsByBillingItemID(billingItem.getBillingItemID()));
		assertFalse(billingItemRepo.existsByBillingItemID(billingItemInList1.getBillingItemID()));
		assertFalse(billingItemRepo.existsByBillingItemID(billingItemInList2.getBillingItemID()));
	}
	
}
