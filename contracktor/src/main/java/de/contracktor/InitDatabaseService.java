package de.contracktor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.contracktor.model.Permission;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.UserRepository;

@Component
@Service
public class InitDatabaseService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PermissionRepository permissionRepository;
	
	public void init() {
		if(permissionRepository.count() == 0) {
			Permission permission = new Permission("Read-Only");
			permission = permissionRepository.save(permission);
		}
	}
}
