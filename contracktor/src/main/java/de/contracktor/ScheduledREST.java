package de.contracktor;

import de.contracktor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduledREST {

    private String url = "http://localhost:3000/api/v1/";
    private final String credentials = "Bearer 123";
    @SuppressWarnings("rawtypes")
	private static HttpEntity entity;
    
    @Autowired
    private AdessoAPIService adesso;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new org.springframework.http.HttpHeaders();

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@PostConstruct
    @DependsOn("init")
    public void initializeHeader() {
        headers.set("Authorization", credentials);
        entity = new HttpEntity(headers);
    }

    @Scheduled(fixedRate = 300000)
    public void refreshDB() {

        List<Integer> projectIDs = fetchProjects();
        List<Integer> contractIDs = fetchContracts(projectIDs);
        fetchBillingModels(contractIDs);
        fetchBillingUnitCompletionReports(contractIDs);

    }

    private List<Integer> fetchProjects() {
        // Fetch all Projects
        // --------------------------------------------------------------------
        // Build the URL
        UriComponentsBuilder projectBuilder = UriComponentsBuilder.fromUriString(url)
                .pathSegment("project").pathSegment("list");
        // Execute the GET-request
        ResponseEntity<Project[]> projectResponse = restTemplate.exchange(
                projectBuilder.build().toUri(), HttpMethod.GET, entity, Project[].class);
        // Retrieve the Project objects
        List<Project> projectList = Arrays.asList(projectResponse.getBody());

        List<Integer> projectIDs = new ArrayList<>();

        if (!projectList.isEmpty()) {
            for (Project project : projectList) {
                System.out.println("Project number" + project.getProjectID() + ":  " + project.getName());
                projectIDs.add(project.getProjectID());
                System.out.println("Address :" + project.getAddress().getStreet() + " " + project.getAddress().getHouseNumber());

                adesso.save(project);
            }
        }

        return projectIDs;
    }

    private List<Integer> fetchContracts(List<Integer> projectIDs) {
        // Fetch all contracts
        // --------------------------------------------------------------------

        List<Integer> contractIDs = new ArrayList<>();

        for (int projectID : projectIDs) {
            // Build the URL
            UriComponentsBuilder contractBuilder = UriComponentsBuilder.fromUriString(url)
                    .pathSegment("contracts").pathSegment(Integer.toString(projectID));

            ResponseEntity<Contract[]> contractResponse = restTemplate.exchange(
                    contractBuilder.build().toUri(), HttpMethod.GET, entity, Contract[].class);
            // Retrieve the Contract objects
            List<Contract> contractList = Arrays.asList(contractResponse.getBody());

            if (!contractList.isEmpty()) {
                for (Contract contract : contractList) {
                    System.out.println("Contract number" + contract.getContractID() + ": " + contract.getStatus().getStateName()
                            + " belongs to project number: " + contract.getProjectId());
                    contractIDs.add(contract.getContractID());

                    adesso.save(contract);

                }
            }
        }

        return contractIDs;
    }

    private void fetchBillingModels(List<Integer> contractIDs) {

        // Fetch all billing items
        // --------------------------------------------------------------------


        for (int contractID : contractIDs) {

            System.out.println("Now retrieving billing model for contract number: " + contractID);

            // Build the URL
            UriComponentsBuilder billingModelBuilder = UriComponentsBuilder.fromUriString(url)
                    .pathSegment("billingmodel").pathSegment("loadAndParseForContractConfiguration")
                    .queryParam("contractId", contractID);
            //System.out.println(billingModelBuilder.build().toUri());

            ResponseEntity<BillingModel>  billingModelResponse = restTemplate.exchange(
                    billingModelBuilder.build().toUri(), HttpMethod.GET, entity, BillingModel.class);

            BillingModel billingModel = billingModelResponse.getBody();

            System.out.println("The number of billing units in this contract is: " + billingModel.getBillingUnits().size());

            // Should probably test for/catch Rest client exception instead
            if (billingModel != null) {
                List<BillingUnit> billingUnits = billingModel.getBillingUnits();

                for (BillingUnit billingUnit : billingUnits) {
                    System.out.println(billingUnit.getBillingUnitID());
                    List<BillingItem> billingItems = billingUnit.getBillingItems();

                    adesso.save(billingUnit, contractID);

                    try {
                        UriComponentsBuilder latestStatusBuilder = UriComponentsBuilder.fromUriString(url)
                                .pathSegment("billingUnitCompletion")
                                .pathSegment("latestStatus")
                                .pathSegment(Integer.toString(contractID))
                                .pathSegment(billingUnit.getBillingUnitID());

                        ResponseEntity<String> StatusResponse = restTemplate.exchange(
                                latestStatusBuilder.build().toUri(), HttpMethod.GET, entity, String.class);

                        adesso.save(StatusResponse.getBody(), billingUnit.getBillingUnitID());

                        System.out.println(StatusResponse.getBody());

                    } catch (Exception e) {

                    }

                    for (BillingItem billingItem : billingItems ) {

                        adesso.save(billingItem, billingUnit.getBillingUnitID());

                        System.out.println(billingItem.getBillingItemID());
                        List<BillingItem> billingItemItems = billingItem.getBillingItems();
                        // Fetch latest Status
                        // --------------------------------------------------------------------
                        try {
                            UriComponentsBuilder latestStatusBuilder1 = UriComponentsBuilder.fromUriString(url)
                                    .pathSegment("billingUnitCompletion")
                                    .pathSegment("latestStatus")
                                    .pathSegment(Integer.toString(contractID))
                                    .pathSegment(billingItem.getBillingItemID());

                            ResponseEntity<String> StatusResponse1 = restTemplate.exchange(
                                    latestStatusBuilder1.build().toUri(), HttpMethod.GET, entity, String.class);

                            System.out.println(StatusResponse1.getBody());

                            // adesso.save(StatusResponse1.getBody(), billingItem.getBillingItemID());


                        } catch (Exception e) {

                        }

                        for (BillingItem billingItemItem : billingItemItems) {

                            adesso.save(billingItemItem, billingUnit.getBillingUnitID());

                            System.out.println(billingItemItem.getBillingItemID());

                            try {

                                UriComponentsBuilder latestStatusBuilder2 = UriComponentsBuilder.fromUriString(url)
                                        .pathSegment("billingUnitCompletion")
                                        .pathSegment("latestStatus")
                                        .pathSegment(Integer.toString(contractID))
                                        .pathSegment(billingItemItem.getBillingItemID());

                                ResponseEntity<String> StatusResponse2 = restTemplate.exchange(
                                        latestStatusBuilder2.build().toUri(), HttpMethod.GET, entity, String.class);

                                // adesso.save(StatusResponse2.getBody(), billingItemItem.getBillingItemID());

                                System.out.println(StatusResponse2.getBody());
                            } catch(Exception e) {

                            }

                        }
                    }

                }

            }
        }

    }

    private void fetchBillingUnitCompletionReports(List<Integer> contractIDs) {
        // Fetch Billing Unit Completion Reports
        // --------------------------------------------------------------------
        for (int contractID : contractIDs) {

            System.out.println("Now retrieving billing unit completion report for contract number: " + contractID);

            //String url2 = "https://6137538deac1410017c1829d.mockapi.io/api/mock/billingUnitCompletionReports";

//            ResponseEntity<BillingUnitCompletionReport[]> billingUnitCompletionReportResponse = restTemplate.exchange(
//                    url2, HttpMethod.GET, entity, BillingUnitCompletionReport[].class);

//            BillingUnitCompletionReport[] billingUnitCompletionReports = billingUnitCompletionReportResponse.getBody();
//            BillingUnitCompletionReport billingUnitCompletionReport = billingUnitCompletionReports[0];
//            System.out.println("Das hier ist eine CRID: " + billingUnitCompletionReport.getCRID());

            // Build the URL
            UriComponentsBuilder billingUnitCompletionReportBuilder = UriComponentsBuilder.fromUriString(url)
                    .pathSegment("contractCompletion").pathSegment("all").pathSegment(Integer.toString(contractID));

            ResponseEntity<BillingUnitCompletionReport[]> billingUnitCompletionReportResponse = restTemplate.exchange(
                    billingUnitCompletionReportBuilder.build().toUri(), HttpMethod.GET, entity, BillingUnitCompletionReport[].class);

            BillingUnitCompletionReport[] billingUnitCompletionReports = billingUnitCompletionReportResponse.getBody();

            // Only attempt to save reports in the database if the returned list isn't empty
            if (billingUnitCompletionReports.length > 0) {
                for (BillingUnitCompletionReport billingUnitCompletionReport : billingUnitCompletionReports) {
                    adesso.save(billingUnitCompletionReport);
                }
            }

        }
    }

}
