package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Project;

import java.util.List;

public interface ProjectRepository extends CrudRepository<Project, Integer>{
    List<Project> findByNameContains(String substr);

    List<Project> findByOwner_OrganisationNameIgnoreCase(String organisationName);

    boolean existsByProjectID(int id);

    Project findByProjectID(int id);

    List<Project> findAll();

}
