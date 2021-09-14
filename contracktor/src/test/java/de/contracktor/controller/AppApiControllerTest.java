package de.contracktor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.contracktor.model.*;
import de.contracktor.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Organisation testOrganisation = new Organisation("Testorg");
        Permission permission = permissionRepository.
        Role role = new Role("Test",permission,testOrganisation);
        ArrayList<Role> roles = new ArrayList<Role>(List.of(role));
        UserAccount userAccount = new UserAccount("Testo","Test","Test","Test",
                testOrganisation,false,false,roles);
        Address address = new Address(1,"Test","2","Test","1234","Test");
        State state = new State("TestState");
        State state2 = new State("TestState2");
        StateTransition stateTransition = new StateTransition(state,state2);
        Project project = new Project(1,"testProject", LocalDate.of(2000,2,2),
                LocalDate.of(2000,2,2),address,100.0,testOrganisation, "Test", state,null,"Test");
        Contract contract = new Contract(1,project,"Test","Testorg",state,"test","Test");
        BillingItem billingItem = new BillingItem("1","m",100.0,20.0,300.0,
                "Test",state,"Test",null);
        ArrayList<BillingItem> billingItemList = new ArrayList<>();
        billingItemList.add(billingItem);
        BillingUnit billingUnit = new BillingUnit("1","m",LocalDate.of(2000,2,2),
                200.0,100.0,contract,billingItemList,false,"Test","Teest",state);
        byte[] image = "Nice picture".getBytes(StandardCharsets.UTF_8);
        Picture picture = new Picture("Test", image);
        Report report = new Report(billingItemList,testOrganisation,LocalDate.of(2000,2,2),
                "Testo","Test", new ArrayList(List.of(picture)));

        roleRepository.save(role);
        organisationRepository.save(testOrganisation);
        userRepository.save(userAccount);
        addressRepository.save(address);
        stateRepository.save(state);
        stateRepository.save(state2);
        stateTransitionRepository.save(stateTransition);
        projectRepository.save(project);
        contractRepository.save(contract);
        billingItemRepository.save(billingItem);
        pictureRepository.save(picture);
        billingUnitRepository.save(billingUnit);
        reportRepository.save(report);

        List<Project> projectList = List.of(project);
        List<Contract> contractList = List.of(contract);
        List<BillingUnit> billingUnitList = List.of(billingUnit);
        List<State> stateList = List.of(state,state2);
        List<StateTransition> stateTransitionList = List.of(stateTransition);
        List<Report> reportList = List.of(report);

        apiResponse = new APIResponse(projectList,contractList,billingUnitList,stateList,stateTransitionList,reportList,false,"OK");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("Testo","test",
                List.of(new SimpleGrantedAuthority("USER"))));

    }

    @Test
    void testDownload() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/download")).andReturn();
        APIResponse response = objectMapper.readValue(result.getResponse().getContentAsString(), APIResponse.class);
        assertEquals(apiResponse,response);

    }
/**
    @Test
    void testUpdate() throws Exception {
        APIUpdate apiUpdate = new APIUpdate();
        BillingItemUpdate billingItemUpdate = new BillingItemUpdate("1",)
        apiUpdate.setBillingItemUpdates();
        mockMvc.perform(post("/api/download").content(objectMapper.writeValueAsString()))
    }
    **/
}