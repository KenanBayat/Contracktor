package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TestBillingUnit{

	
	@Autowired
	BillingItemRepository billingItemRepo;
	
	@Autowired
	StateRepository stateRepo;
	
	@Autowired
	BillingUnitRepository billingUnitRepo;
	
	@Autowired
	ContractRepository contractRepo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	OrganisationRepository organisationRepo;
	
	@Autowired
	AddressRepository addressRepo;
	
	@Autowired
	PictureRepository pictureRepo;
	
	private final long creationDate = 11111111;
	private final long completionDate = 10101010;
	
	BillingItem billingItem1;
	BillingItem billingItem2;
	BillingItem billingItem3;
	ArrayList<BillingItem> billingItemsi;
	ArrayList<BillingItem> billingItems;
	
	State state;

	Contract contract1;
	Contract contract2;
	
	Organisation organisation;
	
	Project project;
	Project project2;
	
	Picture picture;
	
	Address address1;
	Address address2;
	
	BillingUnit unit1;
	BillingUnit unit2;
	BillingUnit unit3;
	
		
	@BeforeEach
	public void init() {
		state = new State("BillingUnitState");
		stateRepo.save(state);
		
		billingItem1 =  new BillingItem("ID_3346_2929_38", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItem2 = new BillingItem("ID_3346_2929_39", "id", "meter", 1000.0, 105.0, 100050.0, 
                "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemsi = new ArrayList<BillingItem>();
		billingItemsi.add(billingItem1);
		billingItemsi.add(billingItem2);
		billingItem3 = new BillingItem("ID_3346_2929_37", "meter", "id", 1000.0, 105.0, 100050.0, 
				"3m7_6h4uXAXvBoFEtks_QE", state, "", billingItemsi);
		
		billingItems = new ArrayList<BillingItem>();
		billingItems.add(billingItem3);
		
		billingItemRepo.save(billingItem1);
		billingItemRepo.save(billingItem2);
		billingItemRepo.save(billingItem3);
		
		address1 = new Address(1000, "straße", "houseNumber", "city", "12345", "country");
		address2 = new Address(100, "street", "42", "hamburg", "187", "de");
		
		addressRepo.save(address1);
		addressRepo.save(address2);
		
		organisation = new Organisation("organisation1");
		organisationRepo.save(organisation);
		
		picture = new Picture(null,null);
		pictureRepo.save(picture);
		
		project = new Project(200, "project", creationDate, completionDate, address2, 100.0, organisation, "hans", state, picture, "");
		projectRepo.save(project);
		
		project2 = new Project(300, "project", creationDate, completionDate, address2, 100.0, organisation, "hans", state, picture, "");
		projectRepo.save(project2);
		
		contract1 = new Contract(42, project, "contract", "consignee", state, "Contractor", "test");
		contractRepo.save(contract1);
		contract2 = new Contract(420, project2, "contract", "consignee", state, "Contractor", "test");
		contractRepo.save(contract2);
	}
	
	@AfterEach
	public void delete() {
		
		contractRepo.delete(contract1);
		contractRepo.delete(contract2);
		
		projectRepo.delete(project);
		projectRepo.delete(project2);
		
		pictureRepo.delete(picture);
		
		organisationRepo.delete(organisation);
		
		billingItemRepo.delete(billingItem3);
		
		stateRepo.delete(state);
		
		addressRepo.delete(address1);
		addressRepo.delete(address2);
	}
	
	@Test
	public void testNullBillingUnit() {
		unit1 = new BillingUnit(null, "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit1));
		
		unit1 = new BillingUnit("1234", "Meter", completionDate ,null, 
		           1337.0 ,contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit1));
		
		unit1 = new BillingUnit("1234", "Meter", completionDate ,42.42, 
		           null ,contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit1));
		
		unit1 = new BillingUnit("1234", "Meter", completionDate ,42.42, 
		           1337.0 ,contract1, billingItems ,true, 
		           null, "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit1));
		
		unit1 = new BillingUnit("1234", "Meter", completionDate ,42.42, 
		           1337.0 ,contract1, billingItems ,true, 
		           "shortDescription", null, state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit1));
	}
	
	@Test
	public void testSaveBillingUnit() {
		unit1 = new BillingUnit("BillingUnit", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		
		billingUnitRepo.save(unit1);
		assertTrue(billingUnitRepo.existsById(unit1.getId()));
		
		Optional<BillingUnit> billingUnit = billingUnitRepo.findById(unit1.getId());
		
		assertEquals(unit1.getBillingUnitID(),billingUnit.get().getBillingUnitID());
		assertEquals(unit1.getUnit(),billingUnit.get().getUnit());
		assertEquals(unit1.getTotalPrice(),billingUnit.get().getTotalPrice());
		assertEquals(unit1.getTotalQuantity(),billingUnit.get().getTotalQuantity());
		assertEquals(unit1.getOwnContractDefined(),billingUnit.get().getOwnContractDefined());
		assertEquals(unit1.getShortDescription(),billingUnit.get().getShortDescription());
		assertEquals(unit1.getLongDescription(),billingUnit.get().getLongDescription());
		
		
		billingUnitRepo.delete(unit1);
		assertFalse(billingUnitRepo.existsById(unit1.getId()));
	}
	
	@Test
	public void testfindByContractIsIn() {
		unit1 = new BillingUnit("BillingUnit1", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		unit2 = new BillingUnit("BillingUnit2", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		unit3 = new BillingUnit("BillingUnit3", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		
		List<BillingUnit> listBU = Arrays.asList(unit1,unit2,unit3);
		
		assertFalse(billingUnitRepo.findByContractIsIn(Arrays.asList(contract1,contract2)).containsAll(listBU));
		assertFalse(billingUnitRepo.findByContractIsIn(Arrays.asList(contract1)).contains(unit1));
		
		billingUnitRepo.save(unit1);
		billingUnitRepo.save(unit2);
		billingUnitRepo.save(unit3);
		
		assertTrue(billingUnitRepo.findByContractIsIn(Arrays.asList(contract1,contract2)).containsAll(listBU));
		assertTrue(billingUnitRepo.findByContractIsIn(Arrays.asList(contract1)).contains(unit1));
		
		billingUnitRepo.delete(unit1);
		billingUnitRepo.delete(unit2);
		billingUnitRepo.delete(unit3);
		
		assertFalse(billingUnitRepo.findByContractIsIn(Arrays.asList(contract1,contract2)).containsAll(listBU));
		assertFalse(billingUnitRepo.findByContractIsIn(Arrays.asList(contract1)).contains(unit1));
	}
	@Test
	public void testexistsByBillingUnitID() {
		unit1 = new BillingUnit("BillingUnit1", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		unit2 = new BillingUnit("BillingUnit2", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		unit3 = new BillingUnit("BillingUnit3", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		
		assertFalse(billingUnitRepo.existsByBillingUnitID(unit1.getBillingUnitID()));
		assertFalse(billingUnitRepo.existsByBillingUnitID(unit2.getBillingUnitID()));
		assertFalse(billingUnitRepo.existsByBillingUnitID(unit3.getBillingUnitID()));
		
		billingUnitRepo.save(unit1);
		billingUnitRepo.save(unit2);
		billingUnitRepo.save(unit3);
		
		assertTrue(billingUnitRepo.existsByBillingUnitID(unit1.getBillingUnitID()));
		assertTrue(billingUnitRepo.existsByBillingUnitID(unit2.getBillingUnitID()));
		assertTrue(billingUnitRepo.existsByBillingUnitID(unit3.getBillingUnitID()));
		
		billingUnitRepo.delete(unit1);
		billingUnitRepo.delete(unit2);
		billingUnitRepo.delete(unit3);
		
		assertFalse(billingUnitRepo.existsByBillingUnitID(unit1.getBillingUnitID()));
		assertFalse(billingUnitRepo.existsByBillingUnitID(unit2.getBillingUnitID()));
		assertFalse(billingUnitRepo.existsByBillingUnitID(unit3.getBillingUnitID()));
	}
	@Test
	public void testfindByBillingUnitID() {
		unit1 = new BillingUnit("BillingUnit1", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		unit2 = new BillingUnit("BillingUnit2", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		unit3 = new BillingUnit("BillingUnit3", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		
		billingUnitRepo.save(unit1);
		billingUnitRepo.save(unit2);
		billingUnitRepo.save(unit3);
		
		assertEquals(billingUnitRepo.findByBillingUnitID(unit1.getBillingUnitID()),unit1);
		assertEquals(billingUnitRepo.findByBillingUnitID(unit2.getBillingUnitID()),unit2);
		assertEquals(billingUnitRepo.findByBillingUnitID(unit3.getBillingUnitID()),unit3);
		
		billingUnitRepo.delete(unit1);
		billingUnitRepo.delete(unit2);
		billingUnitRepo.delete(unit3);
		
	}
	@Test
	public void findByContract() {
		unit1 = new BillingUnit("BillingUnit1", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		unit2 = new BillingUnit("BillingUnit2", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		unit3 = new BillingUnit("BillingUnit3", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		
		assertFalse(billingUnitRepo.findByContract(contract1).contains(unit1));
		assertFalse(billingUnitRepo.findByContract(contract2).containsAll(Arrays.asList(unit2,unit3)));
		
		billingUnitRepo.save(unit1);
		billingUnitRepo.save(unit2);
		billingUnitRepo.save(unit3);
		
		assertTrue(billingUnitRepo.findByContract(contract1).contains(unit1));
		assertTrue(billingUnitRepo.findByContract(contract2).containsAll(Arrays.asList(unit2,unit3)));
		
		billingUnitRepo.delete(unit1);
		billingUnitRepo.delete(unit2);
		billingUnitRepo.delete(unit3);
		
		assertFalse(billingUnitRepo.findByContract(contract1).contains(unit1));
		assertFalse(billingUnitRepo.findByContract(contract2).containsAll(Arrays.asList(unit2,unit3)));
	}
	@Test
	public void testfindByid() {
		unit1 = new BillingUnit("BillingUnit1", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		unit2 = new BillingUnit("BillingUnit2", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		unit3 = new BillingUnit("BillingUnit3", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		
		billingUnitRepo.save(unit1);
		billingUnitRepo.save(unit2);
		billingUnitRepo.save(unit3);
		
		assertEquals(billingUnitRepo.findByid(unit1.getId()),unit1);
		assertEquals(billingUnitRepo.findByid(unit2.getId()),unit2);
		assertEquals(billingUnitRepo.findByid(unit3.getId()),unit3);
		
		billingUnitRepo.delete(unit1);
		billingUnitRepo.delete(unit2);
		billingUnitRepo.delete(unit3);
	}
	@Test
	public void findAllByContract() {
		unit1 = new BillingUnit("BillingUnit1", "Meter", completionDate , 42.42, 
		           1337.0 , contract1, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		unit2 = new BillingUnit("BillingUnit2", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		unit3 = new BillingUnit("BillingUnit3", "Meter", completionDate , 42.42, 
		           1337.0 , contract2, new ArrayList<BillingItem>() ,true, 
		           "shortDescription", "longDescription", state);
		
		billingUnitRepo.save(unit1);
		billingUnitRepo.save(unit2);
		billingUnitRepo.save(unit3);
		
		assertEquals(billingUnitRepo.findAllByContract(contract1),Arrays.asList(unit1));
		assertEquals(billingUnitRepo.findAllByContract(contract2),Arrays.asList(unit2,unit3));
		
		billingUnitRepo.delete(unit1);
		billingUnitRepo.delete(unit2);
		billingUnitRepo.delete(unit3);
	}
	
	
}
