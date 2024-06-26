package de.contracktor.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Picture {

    @Getter @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int pictureID;
    @Getter @Setter @Column(nullable = false, unique = true) Integer imageID;
    @Getter @Setter @Lob @Column(columnDefinition="BLOB") byte[] picture;
    @Getter @Setter @JoinColumn(nullable = false, name = "report_id")@ManyToOne Report report;

    protected Picture() {}

    public Picture(Integer imageId, byte[] picture, Report report) {
        this.imageID = imageId;
        this.picture = picture;
        this.report = report;
    }
}
