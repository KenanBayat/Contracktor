package de.contracktor.controller.dato;

import de.contracktor.model.Organisation;
import de.contracktor.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ManageUserDato {
    @Getter @Setter private List<Organisation> organisationList;
    @Getter @Setter private List<User> userList;

    public ManageUserDato(List<Organisation> organisations, List<User> users) {
        this.organisationList = organisations.stream().sorted(Comparator.comparing(Organisation::getOrganisationName)).collect(Collectors.toList());
        this.userList = users.stream().sorted(Comparator.comparing(User::getUsername)).collect(Collectors.toList());
    }

    public List<User> getUserOfOrganisation(Organisation organisation) {
        List<User> result = this.userList.stream().filter(user -> user.getOrganisation().getOrganisationName().equals(organisation.getOrganisationName()))
                .collect(Collectors.toList());
        return result.stream().sorted(Comparator.comparing(User::getUsername)).collect(Collectors.toList());
    }

    public List<User> getFilteredUserList(String search) {
        return this.userList.stream().filter(user -> (user.getUsername().contains(search) || user.getForename().contains(search) || user.getSurname().contains(search) || user.getOrganisation().getOrganisationName().contains(search)))
                .collect(Collectors.toList());
    }
}
