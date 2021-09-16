package de.contracktor.security;

import de.contracktor.model.Organisation;
import de.contracktor.model.Permission;
import de.contracktor.model.Role;
import de.contracktor.model.UserAccount;
import de.contracktor.repository.OrganisationRepository;
import de.contracktor.repository.PermissionRepository;
import de.contracktor.repository.RoleRepository;
import de.contracktor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TestAPIToken {
    @SuppressWarnings("unused")
	private UserAccount user;

    @Autowired
    private PermissionRepository permissionRepository;


    @SuppressWarnings("unused")
	private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    void setUp() {
        Organisation testOrganisation = new Organisation("Testorg");
        Permission permission = permissionRepository.findByPermissionName("r");
        Role role = new Role("Test",permission,testOrganisation);
        ArrayList<Role> roles = new ArrayList<Role>(List.of(role));
        UserAccount userAccount = new UserAccount("Testo",encoder.encode("Test"),"Test","Test",
                testOrganisation,false,false,roles);

        organisationRepository.save(testOrganisation);
        roleRepository.save(role);
        userRepository.save(userAccount);
        this.user = userAccount;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }
/**
    @Test
    public void testGetTokenValidCreds() throws Exception{
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString("Testo:Test".getBytes(StandardCharsets.UTF_8));

        MvcResult result = mockMvc.perform(post("/api/login").header("Authorisation",basicAuth)).
                andExpect(status().is(200)).andReturn();
    }
**/
}
