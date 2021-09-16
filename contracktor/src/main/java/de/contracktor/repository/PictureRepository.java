package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import de.contracktor.model.Picture;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Integer> {
    Picture findFirstByOrderByImageIDDesc();

    List<Picture> findByReport_Organisation_OrganisationNameIgnoreCase(String organisationName);



}
