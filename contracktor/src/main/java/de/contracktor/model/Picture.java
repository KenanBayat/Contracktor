package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Picture {

    @Getter @Id @GeneratedValue(strategy = GenerationType.AUTO) private int pictureID;
    @Getter @Setter String picturePath;

    protected Picture() {}

    public Picture(String picturePath) {
        this.picturePath = picturePath;
    }
}