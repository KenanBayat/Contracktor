package de.contracktor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contracktor.model.*;
import de.contracktor.repository.*;
import de.contracktor.security.ContracktorUserDetails;
import de.contracktor.security.UserDetailsServiceH2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;DB_CLOSE_ON_EXIT=FALSE;",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AppApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrganisationRepository organisationRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private BillingUnitRepository billingUnitRepository;
    @Autowired
    private BillingItemRepository billingItemRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private StateTransitionRepository stateTransitionRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private ReportRepository reportRepository;

    private APIResponse apiResponse;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserDetailsServiceH2 userDetailsServiceH2;

	@Autowired
	private PictureRepository pictureRepo;

	private final long creationDate = 22222222;
	private final long completionDate = 33333333;
    Organisation testOrganisation;
    BillingItem billingItem;
	
    @BeforeEach
    void setUp() {
        byte[] image = "Nice picture".getBytes(StandardCharsets.UTF_8);

        testOrganisation = new Organisation("Testorg");

        Permission permission = permissionRepository.findByPermissionName("r");

        Role role = new Role("Test",permission,testOrganisation);

        ArrayList<Role> roles = new ArrayList<Role>(List.of(role));

        UserAccount userAccount = new UserAccount("Testo","Test","Test","Test",
                testOrganisation,false,false,roles);

        Address address = new Address(1,"Test","2","Test","1234","Test");

        State state = stateRepository.findByStateName("OPEN");

        Project project = new Project(200000, "test", creationDate, completionDate, address,
				100.0, testOrganisation, "hans", state, image, "");

        Contract contract = new Contract(32,project,"Test","Testorg",state,"test","Test");
        contract.setProjectId(200000);

        billingItem = new BillingItem("1","1","m",100.0,20.0,300.0,
                "Test",state,"Test",new ArrayList<>());

        ArrayList<BillingItem> billingItemList = new ArrayList<>();

        billingItemList.add(billingItem);
        BillingUnit billingUnit = new BillingUnit("1","m",11223344,
                200.0,100.0,contract,billingItemList,false,"Test","Teest",state);
        Report report = new Report(22,billingItem,testOrganisation, creationDate,
                "Testo","Test");
        Picture picture = new Picture(3, image, report);

        
        stateRepository.save(state);
        organisationRepository.save(testOrganisation);
        billingItemRepository.save(billingItem);
        reportRepository.save(report);
        pictureRepo.save(picture);
        roleRepository.save(role);
        userRepository.save(userAccount);
        addressRepository.save(address);
        projectRepository.save(project);
        contractRepository.save(contract);
        pictureRepository.save(picture);
        billingUnitRepository.save(billingUnit);

        List<Project> projectList = List.of(project);
        List<Contract> contractList = List.of(contract);
        List<BillingUnit> billingUnitList = List.of(billingUnit);
        List<State> stateList = stateRepository.findAll();
        List<StateTransition> stateTransitionList = stateTransitionRepository.findAll();
        List<Picture> pictureList = List.of(picture);

        apiResponse = new APIResponse(projectList,contractList,billingUnitList,stateList,stateTransitionList,pictureList,false,"OK");

        ContracktorUserDetails contracktorUserDetails = userDetailsServiceH2.loadUserByUsername("Testo");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(contracktorUserDetails,"test",
                List.of(new SimpleGrantedAuthority("USER"))));

    }

    @Test
    void testValidDownload() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/update").content("{}").contentType("application/json")).andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(apiResponse);
        assertEquals(expectedResponse,actualResponse);
    }


    @Test
    void testValidBillingItemUpdate() throws Exception {
        @SuppressWarnings("unused")
		BillingItem oldItem = billingItemRepository.findByBillingItemID("1").get();
        APIUpdate apiUpdate = new APIUpdate();
        State newState = stateRepository.findByStateName("DENY");
        BillingItemUpdate billingItemUpdate = new BillingItemUpdate("1",newState,2000);
        apiUpdate.setBillingItemUpdates(List.of(billingItemUpdate));
        apiUpdate.setPictureList(new ArrayList<>());
        mockMvc.perform(post("/api/update").content(objectMapper.writeValueAsString(apiUpdate)).contentType("application/json")).andReturn();
        BillingItem updatedItem = billingItemRepository.findByBillingItemID("1").get();
        assertTrue(updatedItem.getStatus().getStateName() == newState.getStateName() && updatedItem.getLastModified() == 2000);
    }

    @Test
    void testValidImageUpdate() throws Exception{
        APIUpdate apiUpdate = new APIUpdate();
        apiUpdate.setBillingItemUpdates(new ArrayList<>());
        Report report = new Report(25, billingItem, testOrganisation, creationDate,"Testo","Bla");
        byte[] image = "Test".getBytes(StandardCharsets.UTF_8);
        Picture newPicture = new Picture(123,image,report);
        apiUpdate.setPictureList(List.of(newPicture));
        mockMvc.perform(post("/api/update").content(objectMapper.writeValueAsString(apiUpdate)).contentType("application/json")).andReturn();
        Optional<Report> savedReport = reportRepository.findById(1);
        Optional<Picture> savedPicture = pictureRepo.findById(1);
        assertTrue(savedPicture.isPresent() && savedReport.isPresent());
    }

    @Test
    void testUndefinedBillingItemUpdate() throws Exception{
        APIUpdate apiUpdate = new APIUpdate();
        State newState = stateRepository.findByStateName("DENY");
        BillingItemUpdate billingItemUpdate = new BillingItemUpdate("420",newState,2000);
        apiUpdate.setBillingItemUpdates(List.of(billingItemUpdate));
        apiUpdate.setPictureList(new ArrayList<>());
        MvcResult result = mockMvc.perform(post("/api/update").content(objectMapper.writeValueAsString(apiUpdate))
                .contentType("application/json")).andReturn();
        assertEquals(objectMapper.writeValueAsString(new APIResponse("UNKNOWN_BILLINGITEM")), result.getResponse().getContentAsString());
    }


    @Test
    void testNoReadPerm() throws Exception {
        UserAccount user =  userRepository.findByUsername("Testo").get();
        user.setRoles(new ArrayList<>());
        userRepository.save(user);
        ContracktorUserDetails contracktorUserDetails = userDetailsServiceH2.loadUserByUsername("Testo");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(contracktorUserDetails,"test",
                List.of(new SimpleGrantedAuthority("USER"))));
        userRepository.save(user);
        MvcResult result = mockMvc.perform(post("/api/update").content("{}").contentType("application/json")).andReturn();
        String actualResponse = result.getResponse().getContentAsString();
        String expectedResponse = objectMapper.writeValueAsString(new APIResponse("NO_READ_PERM"));
        assertEquals(expectedResponse,actualResponse);
    }


    @Test
    void testNoOverride() throws Exception {
        ContracktorUserDetails contracktorUserDetails = userDetailsServiceH2.loadUserByUsername("Testo");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(contracktorUserDetails,"test",
                List.of(new SimpleGrantedAuthority("USER"))));
        APIUpdate apiUpdate = new APIUpdate();
        State newState = stateRepository.findByStateName("DENY");
        BillingItemUpdate billingItemUpdate = new BillingItemUpdate("1",newState,2000);
        apiUpdate.setBillingItemUpdates(List.of(billingItemUpdate));
        apiUpdate.setPictureList(new ArrayList<>());

        APIUpdate apiUpdate2 = new APIUpdate();
        State newState2 = stateRepository.findByStateName("OK");
        BillingItemUpdate billingItemUpdate2 = new BillingItemUpdate("1",newState2,1000);
        apiUpdate2.setBillingItemUpdates(List.of(billingItemUpdate2));
        apiUpdate2.setPictureList(new ArrayList<>());

        mockMvc.perform(post("/api/update").content(objectMapper.writeValueAsString(apiUpdate)).contentType("application/json")).andReturn();
        mockMvc.perform(post("/api/update").content(objectMapper.writeValueAsString(apiUpdate2)).contentType("application/json")).andReturn();

        BillingItem updatedItem = billingItemRepository.findByBillingItemID("1").get();
        assertTrue(updatedItem.getStatus().getStateName() == newState.getStateName() && updatedItem.getLastModified() == 2000);
    }



}