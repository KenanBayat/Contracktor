package de.contracktor.controller.dato;

import lombok.Getter;
import lombok.Setter;

public class EditUserDato {
    @Getter @Setter private String oldUsername;
    @Getter @Setter private String newUsername;
    @Getter @Setter private String newForename;
    @Getter @Setter private String newSurname;
    @Getter @Setter private String newPassword;
    @Getter @Setter private String newPasswordCheck;
}
