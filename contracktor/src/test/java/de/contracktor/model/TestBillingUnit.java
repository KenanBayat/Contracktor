package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.ContractRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PictureRepository;
import de.contracktor.repository.ProjectRepository;
import de.contracktor.repository.StateRepository;

@SpringBootTest
public class TestBillingUnit {

	
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
	
	private final LocalDate creationDate = LocalDate.of(2021, 9, 8);
	private final LocalDate completionDate = LocalDate.of(2022, 12, 12);
	
	BillingItem billingItem1;
	BillingItem billingItem2;
	BillingItem billingItem3;
	ArrayList<BillingItem> billingItemsi;
	ArrayList<BillingItem> billingItems;
	
	State state;

	Contract contract;
	
	Organisation organisation;
	
	Project project;
	
	Picture picture;
	
	Address address1;
	Address address2;
	
		
	@BeforeEach
	public void init() {
		state = new State("BillingUnitState");
		stateRepo.save(state);
		
		billingItem1 =  new BillingItem("ID_3346_2929_38", "meter", 1000.0, 105.0, 100050.0, 
                "3m5_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItem2 = new BillingItem("ID_3346_2929_39", "meter", 1000.0, 105.0, 100050.0, 
                "3m6_6h4uXAXvBoFEtks_QE", state, "", new ArrayList<BillingItem>());
		billingItemsi = new ArrayList<BillingItem>();
		billingItemsi.add(billingItem1);
		billingItemsi.add(billingItem2);
		billingItem3 = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0, "3m7_6h4uXAXvBoFEtks_QE", state, "", billingItemsi);
		
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
		
		contract = new Contract(42, project, "contract", "consignee", state, "Contractor", "test");
		contractRepo.save(contract);
	}
	
	@AfterEach
	public void delete() {
		
		contractRepo.delete(contract);
		
		projectRepo.delete(project);
		
		pictureRepo.delete(picture);
		
		organisationRepo.delete(organisation);
		
		billingItemRepo.delete(billingItem3);
		
		stateRepo.delete(state);
	}
	
	@Test
	public void null_ID_Test() {
		BillingUnit unit = new BillingUnit(null, "Meter", completionDate , 42.42, 
		           1337.0 , contract, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit));
	}
	
	@Test
	public void null_TotalPrice_Test() {
		BillingUnit unit = new BillingUnit("1", "Meter", completionDate ,null, 
		           1337.0 ,contract, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit));
	}
	
	@Test
	public void null_TotalQuantity_Test() {
		BillingUnit unit = new BillingUnit("1", "Meter", completionDate ,42.42, 
		           null ,contract, billingItems ,true, 
		           "shortDescription", "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit));
	}

	@Test
	public void null_shortDescriptionDefined_Test() {
		BillingUnit unit = new BillingUnit("1", "Meter", completionDate ,42.42, 
		           1337.0 ,contract, billingItems ,true, 
		           null, "longDescription", state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit));
	}
	@Test
	public void null_longDescriptionDefined_Test() {
		BillingUnit unit = new BillingUnit("1", "Meter", completionDate ,42.42, 
		           1337.0 ,contract, billingItems ,true, 
		           "shortDescription", null, state);
		assertThrows(Exception.class, () -> billingUnitRepo.save(unit));
	}
}
