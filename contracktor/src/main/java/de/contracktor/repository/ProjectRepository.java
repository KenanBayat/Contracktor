package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
    List<Project> findByOwner_OrganisationNameIgnoreCase(String organisationName);


}
