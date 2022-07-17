package com.example.digiseq.repository;

import com.example.digiseq.domain.Organisation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganisationRepository extends CrudRepository<Organisation, Long> {

    //Returns a list containing all the organisations enabled in the system
    List<Organisation> findOrganisationsByEnabledTrue();

}
