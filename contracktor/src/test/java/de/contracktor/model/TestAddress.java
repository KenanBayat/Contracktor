package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import de.contracktor.repository.AddressRepository;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestAddress {
		
	@Autowired
	AddressRepository addressRepo;
	
	Address address;
	
	@Test
	public void testNullStreet() {
		address = new Address(100000, null, "42", "city", "12345", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testNullHouseNumber() {
		address = new Address(10000, "street", null, "city", "12345", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testNullCity() {
		address = new Address(10000, "street", "42", null, "12345", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testNullZipCode() {
		address = new Address(10000, "street", "42", "city", null, "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testNullCountry() {
		address = new Address(10000, "street", "42", "city", "12345", null);
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testEmptyStreet() {
		address = new Address(10000, "", "42", "city", "12345", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testEmptyHouseNumber() {
		address = new Address(10000, "street", "", "city", "12345", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testEmptyCity() {
		address = new Address(10000, "street", "42", "", "12345", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testEmptyZipCode() {
		address = new Address(10000, "street", "42", "city", "", "Land");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
	
	@Test
	public void testEmptyCountry() {
		address = new Address(10000, "street", "42", "city", "12345", "");
		assertThrows(Exception.class, () -> addressRepo.save(address));
	}
}
