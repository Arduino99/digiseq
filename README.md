# Coding Task for Digiseq

• The backend of the Application has been developed in Java 11 and Spring Boot

• The frontend has been developed with Thymeleaf, which allowed me to include the views in the same project simplifying the delivery


-Before running the application, head to application.properties and change the username/password according to the mysql credentials setted on your machine. 
Please also check that the schema (digiseq) do not alter any schema with the same name that might be already present in your mysql database instance, 
as upon creation all the tables will get dropped and recreated.

The Application has been modeled based on the following database diagram. I decided to change the table name from 'personnel' to 'employees' for sake of clarity, 
as it can be both singular and plural leading to confusion. Worth to mention that a one-to-many relationship has also been modeled accordingly,
where one organisation might have one or more employees associated. 
<img width="1113" alt="Screenshot 2022-07-12 at 09 50 53" src="https://user-images.githubusercontent.com/43646408/179429429-fb625eb0-9f75-4628-ae75-ef03ee0d4bb2.png">

Once the application is up and running you can head to your browser at the following URL http://localhost:8080. Should you have any other process runnning 
at this port you can easily change it in application.properties.

Once there you should be able to see the following UI. From here you can edit, delete or add new personnel to the database. 
<img width="1247" alt="Screenshot 2022-07-18 at 00 02 53" src="https://user-images.githubusercontent.com/43646408/179429575-5452beb0-468b-4d27-bbca-ab0489c7dd96.png">

Editing the employees records though the front-end exposes the passwords, but they are safely stored in the database in an encrypted format (as per 
specification). You can easily verify that looking into the mySQL table that will be created by Spring once we start the application:
<img width="1053" alt="Screenshot 2022-07-18 at 00 13 30" src="https://user-images.githubusercontent.com/43646408/179429700-6f1975be-0eff-4614-b717-e99e29815839.png">

There's also a hidden view that allows us to view the employees belonging to a specific organisation, we can access it at the following URL
http://localhost:8080/getEmployeesByOrganisation/{id}, where {id} is, the id of the organisation we are referring to. (In this case we are displaying all 
the employees that do have an association witht the Organisation 'Microsoft').

<img width="1268" alt="Screenshot 2022-07-18 at 00 19 37" src="https://user-images.githubusercontent.com/43646408/179430385-2913b8d0-f277-4e9c-b918-d36f829a9055.png">


Heading to the organisations view, we can see that a counter indicates the number of organisation currently active. We see a similar button to add a new 
organisation and two more buttons for each record that allows us to edit or delete a record. When editing the date please bear in mind that the month comes 
before the day, eg. MM/DD/YYYY. Given more time I would have implemented a date picker, definitely a more user-friendly solution.
<img width="1241" alt="Screenshot 2022-07-18 at 00 11 12" src="https://user-images.githubusercontent.com/43646408/179429851-f2254210-b880-4b68-84ac-efe73c5b53f1.png">

The organisations that are about to expire and are in the 7-days window frame will have the 'Expiry date' displayed in yellow. 

<img width="1268" alt="Screenshot 2022-07-18 at 00 25 51" src="https://user-images.githubusercontent.com/43646408/179430010-2c4bd9d6-7b1e-485b-a8e5-6d2599f8631d.png">

Once we reach the expiration date, the records will be disabled. The method that handle that is located in the OrganisationController, it's currently scheduled to run 
every minute, but for performance reasons in productions should run no more than once every 12h (I also included an commented annotation for that).

We can try editing an expired record to 'true' on enabled, and after refreshing the page the method will eventually revert it back to 'false'.

```
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
```
