package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Organisation;

import java.util.List;

public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {
    List<Organisation> findByOrganisationName(String organisationName);

}
