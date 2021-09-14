package de.contracktor.repository;

import de.contracktor.model.Organisation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer> {
    List<Permission> findAll();

    Permission findByPermissionName(String permissionName);

}
