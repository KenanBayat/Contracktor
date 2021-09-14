package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.ReportRepository;
import de.contracktor.repository.StateRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TestReport {

	@Autowired
	private BillingItemRepository billingItemRepo;
	
	@Autowired
	private ReportRepository reportRepo;
	
	@Autowired
	private StateRepository stateRepo;
	
	@Autowired
	private OrganisationRepository organisationRepo;
	
	@Autowired
	private BillingUnitRepository billingUnitRepo;
	
	@Autowired
	private TestEntityManager em;
	
	Organisation organisation;
	
	private Report report;
	
	private BillingItem billingItem;
	
	private ArrayList<BillingItem> billingItems;
	
	private State state;
	
	private LocalDate date = LocalDate.of(2021, 9, 8);
	
	@Autowired
	private AddressRepository addressRepo;
	
	Address address;
	
	@BeforeEach
	public void init() {
		state = new State("Processing");
		state = em.persistAndFlush(state);
		organisation = new Organisation("testOrganisation");
		organisation = em.persistAndFlush(organisation);
		billingItems = new ArrayList<BillingItem>();
		billingItem = new BillingItem("ID_3346_2929_37", "meter", 1000.0, 105.0, 100050.0, "3m5_6h4uXAXvBoFEtks_QE", state, "", billingItems);
		billingItem = billingItemRepo.save(billingItem);
	}
	
	@AfterEach
	public void delete() {
		billingItemRepo.delete(billingItem);
		stateRepo.delete(state);
		reportRepo.delete(report);
		organisationRepo.delete(organisation);
	}
	
	@Test
	public void testNullDate() {		
		// Test null date.
		report = new Report(billingItems, organisation, null, "hans", "", null);
		assertThrows(Exception.class, () -> reportRepo.save(report));		
	}
	
	@Test
	public void testNullUsername() {
		// Test null username.
		report = new Report(billingItems, organisation, date, null, "", null);
		assertThrows(Exception.class, () -> reportRepo.save(report));
	}
	
	@Test
	public void testNullComment() {
		// Test null username.
		report = new Report(billingItems, organisation, date, "hans", null, null);
		assertThrows(Exception.class, () -> reportRepo.save(report));
	}
	
	@Test
	public void testEmptyUsername() {
		// Test empty username
		report = new Report(billingItems, organisation, date, "", "", null);
		assertThrows(Exception.class, () -> reportRepo.save(report));
	}
}
