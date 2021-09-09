package de.contracktor.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import de.contracktor.repository.AddressRepository;

@SpringBootTest
public class TestAddress {

	@Autowired
	AddressRepository addressRepo;
	
	Address address;
	
	@Test
	public void testNullStreet() {
		address = new Address(null, "42", "city", "12345", "Land");
	}
	
	@Test
	public void testNullHouseNumber() {
		address = new Address("street", null, "city", "12345", "Land");
	}
	
	@Test
	public void testNullCity() {
		address = new Address("street", "42", null, "12345", "Land");
	}
	
	@Test
	public void testNullZipCode() {
		address = new Address("street", "42", "city", null, "Land");
	}
	
	@Test
	public void testNullCountry() {
		address = new Address("street", "42", "city", "12345", null);
	}
	
	@Test
	public void testEmptyStreet() {
		address = new Address("", "42", "city", "12345", "Land");
	}
	
	@Test
	public void testEmptyHouseNumber() {
		address = new Address("street", "", "city", "12345", "Land");
	}
	
	@Test
	public void testEmptyCity() {
		address = new Address("street", "42", "", "12345", "Land");
	}
	
	@Test
	public void testEmptyZipCode() {
		address = new Address("street", "42", "city", "", "Land");
	}
	
	@Test
	public void testEmptyCountry() {
		address = new Address("street", "42", "city", "12345", "");
	}
}
