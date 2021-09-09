package de.contracktor;

import de.contracktor.model.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static HttpEntity entity;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new org.springframework.http.HttpHeaders();

    @PostConstruct
    public void initializeHeader() {
        headers.set("Authorization", credentials);
        entity = new HttpEntity(headers);
    }

    @Scheduled(fixedRate = 5000)
    public void refreshDB() {

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
                System.out.println("Project number" + project.getProjectID() + ": " + project.getName());
                projectIDs.add(project.getProjectID());
            }
        }
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
                    System.out.println("Contract number" + contract.getContractID() + ": " + contract.getName()
                            + " belongs to project number: " + contract.getProjectId());
                    contractIDs.add(contract.getContractID());
                }
            }
        }
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

                    for (BillingItem billingItem : billingItems ) {
                        System.out.println(billingItem.getBillingItemID());
                        List<BillingItem> billingItemItems = billingItem.getBillingItems();

                        for (BillingItem billingItemItem : billingItemItems) {
                            System.out.println(billingItemItem.getBillingItemID());
                        }
                    }

                }

            }

        }

    }

}
