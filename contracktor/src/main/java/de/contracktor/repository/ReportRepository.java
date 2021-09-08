package de.contracktor.repository;

import de.contracktor.model.Organisation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Report;

import java.util.List;


@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {
    List<Report> findByOrganisation(Organisation organisation);

}
