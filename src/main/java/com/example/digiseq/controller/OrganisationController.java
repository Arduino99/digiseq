package com.example.digiseq.controller;

import com.example.digiseq.domain.Organisation;
import com.example.digiseq.service.OrganisationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/organisations")
public class OrganisationController {

    OrganisationService organisationService;

    public OrganisationController(OrganisationService organisationService) {
        this.organisationService = organisationService;
    }

    @GetMapping("/")
    public String organisationList(Model model) {
        model.addAttribute("listOrganisations", organisationService.getAllOrganisations());
        model.addAttribute("enabled", organisationService.getNumberOfEnabledOrganisations().size());
        model.addAttribute("localDateNow", LocalDate.now().minusDays(7));
        return "organisations";
    }

    @GetMapping("/showNewOrganisationForm")
    public String showNewOrganisationForm(Model model) {
        Organisation organisation = new Organisation();
        model.addAttribute("organisation", organisation);
        return "newOrganisation";
    }

    //add new organisation
    @PostMapping("/saveOrganisation")
    public String saveOrganisation(@ModelAttribute("organisation") Organisation organisation) {
        organisationService.saveOrganisation(organisation);
        return "redirect:/organisations/";
    }

    //update organisation
    @GetMapping("/updateOrganisation/{id}")
    public String showUpdateOrganisationForm(@PathVariable Long id, Model model) {
        Organisation organisation = organisationService.getOrganisationById(id);
        model.addAttribute("organisation", organisation);
        return "updateOrganisation";
    }

    //	delete organisation by id
    @GetMapping("/deleteOrganisation/{id}")
    public String deleteOrganisationById(@PathVariable Long id, Model model) {
        organisationService.deleteOrganisation(id);
        return "redirect:/organisations/";
    }

    @RequestMapping(value = "/enabledNum", method = RequestMethod.GET)
    @ResponseBody
    public String numberOfEnabledOrganisations() {
        return String.valueOf(organisationService.getNumberOfEnabledOrganisations().size());
    }

    @RequestMapping(value = "/disableOrg/{id}", method = RequestMethod.POST)
    @ResponseBody
    public void disableOrganisation(@PathVariable Long id) {
        Organisation org = organisationService.getOrganisationById(id);
        org.setEnabled(false);
        organisationService.saveOrganisation(org);
    }

    //Disable records if past expiration Date
    //Checks
    //@Scheduled(cron = "0 */12 * * *")     //in production should run checks every 12h, for testing purposes every min
    @Scheduled(cron = "*/1 * * * * *")
    public void updateRecordsBasedOnTime(){
        List<Organisation> orgs = (List<Organisation>) organisationService.getAllOrganisations();
        Date today = new Date();

        for (Organisation org: orgs) {
            if(today.after(org.getExpiry_date())){
                disableOrganisation(org.getId());
            }
        }
    }
}
