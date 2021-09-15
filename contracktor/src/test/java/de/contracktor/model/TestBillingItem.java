package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.StateRepository;


@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace=Replace.NONE)
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
	public void testNullBillingItem() {
		billingItem = new BillingItem(null, "test", "meter", 1000.0, 105.0,
					100050.0, "3m7_6h4uXAXvBoFEtks_QE", state, "" ,billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_3346", null, "meter", 1000.0, 105.0,
				100050.0, "3m7_6h4uXAXvBoFEtks_QE", state, "" ,billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_3346", "test",null, 1000.0, 105.0, 100050.0, 
                "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_3346", "test","meter", null, 105.0, 100050.0, 
                "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_3346", "test","meter", 1000.0, 105.0, null, 
                "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_3346", "test","meter", 1000.0, 105.0, 100050.0, 
				null, state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_3346", "test","meter", 1000.0, 105.0, 100050.0, 
				  "3m7_6h4uXAXvBoFEtks_QE", null, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("ID_334", "test","meter", 1000.0, 105.0, 100050.0, 
				"3m7_6h4uXAXvBoFEtks_QE", state, null, billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
		billingItem = new BillingItem("", "test","meter", 1000.0, 105.0, 100050.0, 
				"3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		assertThrows(Exception.class, () -> billingItemRepo.save(billingItem));
	}

	@Test
	public void testSaveBillingItem() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "test","meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "test","meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		
		
		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "test","meter", 1000.0, 105.0, 100050.0,
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "shortDescripton", billingItems);
		billingItemRepo.save(billingItem);
		
		assertTrue(billingItemRepo.existsById(billingItemInList1.getId()));
		assertTrue(billingItemRepo.existsById(billingItemInList2.getId()));
		assertTrue(billingItemRepo.existsById(billingItem.getId()));
		
		Optional<BillingItem> billingItemC = billingItemRepo.findById(billingItem.getId());
		
		assertEquals(billingItemC.get().billingItemID,"ID_3346_2929_37");
		assertEquals(billingItemC.get().getBillingUnit_ID(),"test");
		assertEquals(billingItemC.get().getUnit(),"meter");
		assertEquals(billingItemC.get().getQuantity(),1000.0);
		assertEquals(billingItemC.get().getPricePerUnit(),105.0);
		assertEquals(billingItemC.get().getTotalPrice(),100050.0);
		assertEquals(billingItemC.get().getIFC(),"3m7_6h4uXAXvBoFEtks_QE");
		assertEquals(billingItemC.get().getShortDescription(),"shortDescripton");
		
		billingItemRepo.delete(billingItem);
		
		assertFalse(billingItemRepo.existsById(billingItem.getId()));
		assertFalse(billingItemRepo.existsById(billingItemInList1.getId()));
		assertFalse(billingItemRepo.existsById(billingItemInList2.getId()));
	}
	
	@Test
	public void testExistsByBillingItemID() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "test","meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "test","meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		
		
		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "test","meter", 1000.0, 105.0, 100050.0,
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
	
	@Test
	public void testFindALL() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "test","meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "test","meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		

		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "test","meter", 1000.0, 105.0, 100050.0,
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		
		List<BillingItem> billingItems2 = Arrays.asList(billingItem,billingItemInList1,billingItemInList2);
		
		assertFalse(billingItemRepo.findAll().containsAll(billingItems2));
		assertTrue(billingItemRepo.findAll().size()==0);
		
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		billingItemRepo.save(billingItem);
		
		assertTrue(billingItemRepo.findAll().size()==3);
		assertTrue(billingItemRepo.findAll().containsAll(billingItems2));

		billingItemRepo.delete(billingItem);
		
		assertFalse(billingItemRepo.findAll().containsAll(billingItems2));
		assertTrue(billingItemRepo.findAll().size()==0);

	}
	
	@Test
	public void testfindByBillingItemIDContains() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "test","meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "test","meter", 1000.0, 105.0, 100050.0, 
                "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());


		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);

		billingItem = new BillingItem("ID_3346_2929_37", "test","meter", 1000.0, 105.0, 100050.0,
				"3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);

		List<BillingItem> billingItems2 = Arrays.asList(billingItem,billingItemInList1,billingItemInList2);

		assertFalse(billingItemRepo.findByBillingItemIDContains("ID").containsAll(billingItems2));
		assertFalse(billingItemRepo.findByBillingItemIDContains("39").contains(billingItemInList2));
		
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		billingItemRepo.save(billingItem);
		assertTrue(billingItemRepo.findByBillingItemIDContains("39").contains(billingItemInList2));
		assertTrue(billingItemRepo.findByBillingItemIDContains("ID").containsAll(billingItems2));
		billingItemRepo.delete(billingItem);
		assertFalse(billingItemRepo.findByBillingItemIDContains("ID").containsAll(billingItems2));
		assertFalse(billingItemRepo.findByBillingItemIDContains("39").contains(billingItemInList2));
		
	}
	
	@Test
	public void testOptionalFindByBillingItemID() {
		billingItemInList1 = new BillingItem("ID_3346_2929_38", "test","meter", 1000.0, 105.0, 100050.0, 
				                             "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemInList2 = new BillingItem("ID_3346_2929_39", "test","meter", 1000.0, 105.0, 100050.0, 
                                             "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemRepo.save(billingItemInList1);
		billingItemRepo.save(billingItemInList2);
		
		
		billingItems.add(billingItemInList1);
		billingItems.add(billingItemInList2);
		
		billingItem = new BillingItem("ID_3346_2929_37", "test","meter", 1000.0, 105.0, 100050.0,
				                      "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItemRepo.save(billingItem);
				
		
		assertTrue(billingItemRepo.findByBillingItemID(billingItem.getBillingItemID()).isPresent());
		assertTrue(billingItemRepo.findByBillingItemID(billingItemInList1.getBillingItemID()).isPresent());
		assertTrue(billingItemRepo.findByBillingItemID(billingItemInList2.getBillingItemID()).isPresent());
		assertFalse(billingItemRepo.findByBillingItemID("Pablos weisse Weihnachten").isPresent());
		
		billingItemRepo.delete(billingItem);
		
		assertFalse(billingItemRepo.findByBillingItemID(billingItem.getBillingItemID()).isPresent());
		assertFalse(billingItemRepo.findByBillingItemID(billingItemInList1.getBillingItemID()).isPresent());
		assertFalse(billingItemRepo.findByBillingItemID(billingItemInList2.getBillingItemID()).isPresent());
		assertFalse(billingItemRepo.findByBillingItemID("Pablos weisse Weihnachten").isPresent());
		
	}
	
}
