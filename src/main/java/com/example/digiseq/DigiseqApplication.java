package com.example.digiseq;

import com.example.digiseq.domain.Employee;
import com.example.digiseq.domain.Organisation;
import com.example.digiseq.repository.EmployeeRepository;
import com.example.digiseq.repository.OrganisationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SpringBootApplication
@EnableScheduling
public class DigiseqApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigiseqApplication.class, args);
    }

    @Bean
    public CommandLineRunner mappingDemo(OrganisationRepository organisationRepository,
                                         EmployeeRepository employeeRepository) {
        return args -> {

            Calendar cal1 = new GregorianCalendar(2022, 6, 14);
            Date date1 = cal1.getTime();
            Calendar cal2 = new GregorianCalendar(2024, 7, 15);
            Date date2 = cal2.getTime();
            Calendar cal3 = new GregorianCalendar(2023, 5, 11);
            Date date3 = cal2.getTime();



            Organisation digiseq = new Organisation("Digiseq",
                                                    new Date(),
                                                    date1,
                                                    true);

            Organisation microsoft = new Organisation("Microsoft",
                                                    new Date(),
                                                    date2,
                                                    true);

            Organisation tesla = new Organisation("Tesla",
                    new Date(),
                    date3,
                    true);

//            Organisation digiseq = new Organisation("Digiseq",
//                                                    LocalDate.now(),
//                                                    LocalDate.of(2022,06,12),
//                                                    true);
//
//            Organisation microsoft = new Organisation("Microsoft",
//                                                    LocalDate.now(),
//                                                    LocalDate.of(2022,06,12),
//                                                    true);
//
//            Organisation tesla = new Organisation("Tesla",
//                                                    LocalDate.now(),
//                                                    LocalDate.of(2022,06,12),
//                                                    true);



            // save the organisations
            organisationRepository.save(digiseq);
            organisationRepository.save(microsoft);
            organisationRepository.save(tesla);

            // create and save employees
            employeeRepository.save(new Employee("Douglas", "Grant", "Douglas", "GrantDouglas","douglasgrant@digiseq.com", "07377292804", digiseq));
            employeeRepository.save(new Employee("Stephen", "Higgins", "Steph", "Stephen89","stephenhig@digiseq.com", "07372832804", digiseq));
            employeeRepository.save(new Employee("Diana", "White", "Diana78", "Diana23Red","dianawhite@digiseq.com", "07377237404", digiseq));
            employeeRepository.save(new Employee("Matthew", "Weiss", "Matt", "Matt82Weis$","matthew@digiseq.com", "07383742804", digiseq));
            employeeRepository.save(new Employee("John", "Stiles", "John", "JoStiHn","johnstiles@digiseq.com", "07374826804", digiseq));
            employeeRepository.save(new Employee("James", "Markle", "Jms", "MrMarkleRoyal","jamesm@digiseq.com", "07373948804", digiseq));

            employeeRepository.save(new Employee("Melissa", "Gates", "Melissa", "Meli$$a","melissagates@microsoft.com", "0733838833", microsoft));
            employeeRepository.save(new Employee("Bill", "Gates", "TheBill", "MrBillBi$$","bill@microsoft.com", "0737393833", microsoft));

        };
    }

    //Update the records every 12h checking if the subscription expired
    //Approach #1
//    @Scheduled(cron = "0 */12 * * *")
//    public static void updateDatabase() {
//        LocalDate now = LocalDate.now();
//    }

    //Update the records upon startup
    //Approach #2
//    @PostLoad
//    private void updateStatus() {
        //if (date.before(new Date())) status = "expired";
//    }
}
