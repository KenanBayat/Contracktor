package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Permission;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, String> {

}
