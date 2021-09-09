package de.contracktor.controller.dato;

import lombok.Getter;
import lombok.Setter;

public class RegisterUserDato {
    @Getter @Setter private String username;
    @Getter @Setter private String forename;
    @Getter @Setter private String surname;
    @Getter @Setter private String organisation;
    @Getter @Setter private String password;
    @Getter @Setter private String passwordCheck;
    @Getter @Setter private Boolean isAdmin;
    @Getter @Setter private Boolean isSysadmin;
}
