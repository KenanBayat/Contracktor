package de.contracktor.controller.dato;

import de.contracktor.model.Organisation;
import de.contracktor.model.UserAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class ManageUserDato {
    @Getter @Setter private List<Organisation> organisationList;
    @Getter @Setter private List<UserAccount> userList;

    public ManageUserDato(List<Organisation> organisations, List<UserAccount> users) {
        this.organisationList = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());
        this.userList = users.stream().sorted(Comparator.comparing(UserAccount::getUsername)).collect(Collectors.toList());
    }

    public List<UserAccount> getUserOfOrganisation(Organisation organisation) {
        List<UserAccount> result = this.userList.stream().filter(user -> user.getOrganisation().getOrganisationName().equals(organisation.getOrganisationName()))
                .collect(Collectors.toList());
        return result.stream().sorted(Comparator.comparing(UserAccount::getUsername)).collect(Collectors.toList());
    }

    public List<UserAccount> getFilteredUserList(String search) {
        String param = search.toLowerCase();
        return this.userList.stream().filter(user -> (user.getUsername().toLowerCase().contains(param) | user.getForename().toLowerCase().contains(param) | user.getSurname().toLowerCase().contains(param) | user.getOrganisation().getOrganisationName().toLowerCase().contains(param)))
                .collect(Collectors.toList());
    }
}
