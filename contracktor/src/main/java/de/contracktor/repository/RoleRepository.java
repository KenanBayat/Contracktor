package de.contracktor.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import de.contracktor.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    List<Role> findAll();
    @SuppressWarnings("unchecked")
	Role save(Role newRole);
}
