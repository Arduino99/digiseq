package com.example.digiseq.service;

import com.example.digiseq.domain.Employee;
import com.example.digiseq.domain.Organisation;
import com.example.digiseq.exceptions.EmployeeNotFoundException;
import com.example.digiseq.exceptions.OrganisationNotFoundException;
import com.example.digiseq.repository.EmployeeRepository;
import com.example.digiseq.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    //gets all organisations
    public List<Organisation> getAllOrganisations() {
        return (List<Organisation>) organisationRepository.findAll();
    }

    // save a new organisation
    public void saveOrganisation(Organisation org) {
        //		save employee data to database
        organisationRepository.save(org);
    }

    //	get organisation by id
    public Organisation getOrganisationById(Long id) {
        return organisationRepository.findById(id).orElseThrow(() -> new OrganisationNotFoundException(id));
    }

    //	get number of enabled organisations
//    public int getNumberOfEnabledOrganisations() {
//        return organisationRepository.findOrganisationsByEnabledTrue().size();
//    }

    public List<Organisation> getNumberOfEnabledOrganisations() {
        return organisationRepository.findOrganisationsByEnabledTrue();
    }



    //delete organisation by ID
    public void deleteOrganisation(Long id){
        organisationRepository.deleteById(id);
    }

    //new employee record
    public Organisation newAppointment(Organisation org){
        return organisationRepository.save(org);
    }

    //edit organisation
}
