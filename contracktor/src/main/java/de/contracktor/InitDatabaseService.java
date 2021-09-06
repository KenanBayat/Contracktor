package de.contracktor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.contracktor.model.Organisation;
import de.contracktor.model.User;
import de.contracktor.repository.UserRepository;

@Component
@Service
public class InitDatabaseService {

	@Autowired
	private UserRepository userRepository;
	
	public void init() {
		/*if(userRepository.count() == 0) {
			
			Organisation organisation = new Organisation("adesso", "straße", "21", "hamburg", "345", "Deutschland");
			
			User user1 = new User("password", "Meier", "Hans", organisation, true, true, null);
			user1 = userRepository.save(user1);
		}*/
	}
}
