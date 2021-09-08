package de.contracktor;

import de.contracktor.model.BillingItem;
import de.contracktor.model.Contract;
import de.contracktor.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduledREST {

    String url = "http://localhost:3000/api/v1/";
    final String credentials = "Bearer 123";
    static HttpEntity entity;

    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new org.springframework.http.HttpHeaders();

    public void initializeHeader() {
        headers.set("Authorization", credentials);
        entity = new HttpEntity(headers);
    }

    @Scheduled(fixedRate = 10000)
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

        List<Integer> projectIDs = new ArrayList<Integer>();

        if (!projectList.isEmpty()) {
            for (Project project : projectList) {
                System.out.println("Project number" + project.getProjectID() + ": " + project.getName());
                projectIDs.add(project.getProjectID());
            }
        }
        // Fetch all contracts
        // --------------------------------------------------------------------

        List<Integer> contractIDs = new ArrayList<Integer>();

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
                            + " belongs to project number: " + contract.getProject());
                    contractIDs.add(contract.getContractID());
                }
            }
        }

    }

}
