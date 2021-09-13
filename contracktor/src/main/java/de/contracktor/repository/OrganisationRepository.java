package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Organisation;

import java.util.List;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {
    Organisation findByOrganisationName(String organisationName);
    boolean existsByOrganisationName(String organisationName);

}
