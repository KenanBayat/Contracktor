package de.contracktor.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;
import de.contracktor.repository.ProjectRepository;

@SpringBootTest
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
