package de.contracktor.controller;

import de.contracktor.DatabaseService;
import de.contracktor.UserManager;
import de.contracktor.model.*;
import de.contracktor.repository.BillingItemRepository;
import de.contracktor.repository.BillingUnitRepository;
import de.contracktor.repository.StateRepository;
import de.contracktor.repository.StateTransitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.security.sasl.AuthenticationException;

@Controller
public class ItemController {

	@Autowired
	BillingItemRepository billingItemRepository;

	@Autowired
	StateRepository stateRepository;

	@Autowired
	StateTransitionRepository stateTransitionRepository;

	@Autowired
	DatabaseService databaseService;

	@Autowired
	UserManager userManager;

	@GetMapping("/billingitems")
	public String getBillingItems(Model model) {
		model.addAttribute("items", billingItemRepository.findAll());
		return "billingitem-list";
	}

	@PostMapping("/billingitems")
	public String getBillingItems(@RequestParam String search, Model model) {
		model.addAttribute("search", search);
		model.addAttribute("items", billingItemRepository.findByBillingItemIDContains(search));
		return "billingitem-list";
	}

	@GetMapping("/billingitem/{itemId}/details")
	public String getBillingItemDetails(@PathVariable String itemId, Model model) {
		BillingItem item = databaseService.getBillingItemByBillingItemID(itemId);
		List<BillingItem> subitems = databaseService.getChildOfBillingItem(item);
		Contract contract = databaseService.getContractOfBillingItem(item);

		Boolean isApplicationAdmin = false;
		String organisationNameOfUser = "";
		try {
			organisationNameOfUser = userManager.getCurrentOrganisation();
			isApplicationAdmin = userManager.isCurrentUserAppAdmin();
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

		List<StateTransition> transitions = stateTransitionRepository.findByStartState(item.getStatus());
		List<State> endstates = new ArrayList<>();
		for (StateTransition transition : transitions) {
			if (organisationNameOfUser.equals(contract.getContractor()) && transition.getContractor()
					|| organisationNameOfUser.equals(contract.getConsignee()) && transition.getConsignee() ||
					isApplicationAdmin) {
				endstates.add(transition.getEndState());
			}			
		}

		item = databaseService.getBillingItemByBillingItemID(itemId);

		model.addAttribute("states", endstates);
		model.addAttribute("contract", contract);
		model.addAttribute("item", item);
		model.addAttribute("subitems", subitems);
		model.addAttribute("pprice", CurrencyFormatter.format(item.getPricePerUnit()));
		model.addAttribute("total", CurrencyFormatter.format(item.getTotalPrice()));
		return "billingitem-details";
	}

	@PostMapping("/billingitem/{itemId}/details/edit")
	public String setBillingItemDetails(@RequestParam int stateId, @PathVariable String itemId, Model model) {
		BillingItem item = databaseService.getBillingItemByBillingItemID(itemId);
		List<BillingItem> subitems = databaseService.getChildOfBillingItem(item);
		Contract contract = databaseService.getContractOfBillingItem(item);

		List<StateTransition> transitions = stateTransitionRepository.findByStartState(item.getStatus());
		List<State> endstates = new ArrayList<>();
		for (StateTransition transition : transitions) {
			endstates.add(transition.getEndState());
		}
		model.addAttribute("states", endstates);
		State newState = stateRepository.findById(stateId).get();
		item.setStatus(newState);
		billingItemRepository.save(item);

		item = databaseService.getBillingItemByBillingItemID(itemId);

		model.addAttribute("item", item);
		model.addAttribute("subitems", subitems);
		model.addAttribute("contract", contract);
		model.addAttribute("pprice", CurrencyFormatter.format(item.getPricePerUnit()));
		model.addAttribute("total", CurrencyFormatter.format(item.getTotalPrice()));

		String redirect = "redirect:/" + "billingitem/" + itemId + "/details";
		return redirect;
	}
}
