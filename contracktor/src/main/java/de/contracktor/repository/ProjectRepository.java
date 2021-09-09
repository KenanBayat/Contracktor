package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Integer>{

}
