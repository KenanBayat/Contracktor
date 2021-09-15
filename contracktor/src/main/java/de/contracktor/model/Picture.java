package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Picture {

    @Getter @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int pictureID;
    @Getter @Setter @Column(nullable = false, unique = true) @NotEmpty Integer imageID;
    @Getter @Setter byte[] picture;
    @Getter @Setter @NotEmpty @ManyToOne Report report;

    protected Picture() {}

    public Picture(Integer imageId, byte[] picture, Report report) {
        this.imageID = imageId;
        this.picture = picture;
        this.report = report;
    }
}
