package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Picture;

import java.util.Optional;

public interface PictureRepository extends CrudRepository<Picture, Integer> {
    Picture findFirstByOrderByImageIDDesc();




}
