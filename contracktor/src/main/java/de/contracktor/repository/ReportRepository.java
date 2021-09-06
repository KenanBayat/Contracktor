package de.contracktor.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.contracktor.model.Report;



@Repository
public interface ReportRepository extends CrudRepository<Report, Integer> {

}
