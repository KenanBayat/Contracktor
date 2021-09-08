package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;

import de.contracktor.model.Picture;

public interface PictureRepository extends CrudRepository<Picture, Integer> {

}
