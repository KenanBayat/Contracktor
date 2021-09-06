package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Organisation;

public interface OrganisationRepository extends CrudRepository<Organisation, String> {

}
