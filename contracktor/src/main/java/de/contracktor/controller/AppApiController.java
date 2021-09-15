package de.contracktor.controller;

import de.contracktor.UserManager;
import de.contracktor.model.*;
import de.contracktor.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class AppApiController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BillingUnitRepository billingUnitRepository;

    @Autowired
    private BillingItemRepository billingItemRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateTransitionRepository stateTransitionRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Autowired
    UserManager userManager;

    //REMOVE BEFORE END
    @GetMapping("/api/download")
    @ResponseBody
    public APIResponse getController() {
        try {
            return apiDownloadConstructor(userManager.getCurrentUserName());
        } catch (Exception e) {
            return new APIResponse("ERROR");
        }

    }

    @PostMapping("/api/update")
    @ResponseBody
    public APIResponse updateController(@RequestBody APIUpdate update) {
        List<BillingItemUpdate> billingItemUpdates = update.getBillingItemUpdates();
        List<Picture> pictureUpdates = update.getPictureList();

        try {
            String username = userManager.getCurrentUserName();

            if (billingItemUpdates != null) {
                for (BillingItemUpdate billingItemUpdate : billingItemUpdates) {
                    Optional<BillingItem> savedItem = billingItemRepository.findByBillingItemID(billingItemUpdate.getBillingItemID());
                    if (savedItem.isEmpty()) {
                        return new APIResponse("UNKNOWN_BILLINGITEM");
                    }

                    if (savedItem.get().getLastModified() <= billingItemUpdate.getLastModified()) {
                        savedItem.get().setStatus(billingItemUpdate.getNewState());
                        savedItem.get().setLastModified(billingItemUpdate.getLastModified());
                        billingItemRepository.save(savedItem.get());
                    }
                }
            }

            if (pictureUpdates != null) {
                Report maxReport = reportRepository.findFirstByOrderByReportIDDesc();
                Picture maxPicture = pictureRepository.findFirstByOrderByImageIDDesc();

                int maxReportID = maxReport == null ? 0 : maxReport.getReportID();
                int maxImageID = maxPicture == null ? 0 : maxPicture.getImageID();

                List<Report> reportUpdates = new ArrayList<>();
                for (Picture picture : pictureUpdates) {
                    picture.setImageID(++maxImageID);
                    Report report = picture.getReport();
                    if (!reportUpdates.contains(report)) {
                        report.setReportID(++maxReportID);
                        reportUpdates.add(report);
                    }
                }
                reportRepository.saveAll(reportUpdates);
                pictureRepository.saveAll(pictureUpdates);
            }

            return apiDownloadConstructor(username);
        } catch (AuthenticationException e) {
            return new APIResponse("NOT_AUTHENTICATED");
        }
    }

    @RequestMapping("/api/login")
    @ResponseBody
    public void loginController() {
    }

    private APIResponse apiDownloadConstructor(String username) throws AuthenticationException{
        if (!userManager.hasCurrentUserReadPerm() && !userManager.hasCurrentUserWritePerm()) {
            return new APIResponse("NO_READ_PERM");
        }

        APIResponse response = new APIResponse();
        String organisation = userManager.getCurrentOrganisation();
        response.setWritePerm(userManager.hasCurrentUserWritePerm());
        response.setProjects(projectRepository.findByOwner_OrganisationNameIgnoreCase(organisation));
        response.setContracts(contractRepository.findByContractorIgnoreCaseOrConsigneeIgnoreCase(organisation,
                organisation));
        response.setBillingUnits(billingUnitRepository.findByContractIsIn(response.getContracts()));
        response.setStates(stateRepository.findAll());
        response.setStateTransitions(stateTransitionRepository.findAll());
        response.setReports(reportRepository.findByOrganisation_OrganisationNameIgnoreCase(organisation));
        response.setStatus("OK");
        return response;
    }

}
