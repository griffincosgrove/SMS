package sms;

/*
Team Project Part 1
Authors and Roles: Griffin Cosgrove Lead CDF & FX Developer, Brace Brillhart FX Developer, John Gregory CDF Developer,
                   Alex Rizzi Logic, Code Testing, and CDF Consultant, Trey Rustand Code Testing.
Due Date: 4/24/2019
The purpose of this CDF is to define the Instructor class which includes relevant data fields and class methods.
 */

public class Instructor {

    //Data Fields
    private String name;
    private String prefix;
    private String officeLocation;
    private String department;
    private String email;
    private int instructorID;

    //static data field
    private static int nextInstructorID = 0;



    //Constructor

    public Instructor (String name, String prefix, String office, String dept, String email) {
        this.name = name;
        this.officeLocation = office;
        this.department = dept;
        this.email = email;
        this.prefix = prefix;
        this.instructorID = instructorID++;;

    }



    //Setters and Getters

    public void setName(String name){
        this.name = name;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDepartment(String dept) {
        this.department = dept;
    }

    // set email with validation
    public void setEmail(String email) {
        boolean validEmail = false;
        for (int i = 0; i<email.length(); i++) {

            if (email.charAt(i) == '@') {
                validEmail = true;
            }
            else {
                continue;
            }
        }
        if (validEmail == true){
            this.email = email;
        }
        else {
            System.out.println("Error: The email address must contain '@'.");
        }
    }

    //set office
    public void setOfficeLocation(String office) {
        this.officeLocation = office;
    }

    //get title
    public String getTitle() {
        return(this.prefix + " " + this.name);
    }

    //override of the toString() method to display useful information about a instructor
    @Override
    public String toString() {
        String returnString = "";
        returnString += ("Instructor Name: " + this.getTitle() + ", ");
        returnString += ("Department: " + this.department + ", ");
        returnString += ("Office: " + this.officeLocation + ", ");
        returnString += ("Email: " + this.email);
        return returnString;

    }
    //id getter
    public int getID()
    {
        return this.instructorID;
    }
    
    //name getter
    public String getName()
    {
        return this.name;
    }
    
    //prefix getter
    public String getPrefix()
    {
        return this.prefix;
    }
    
    //getter for office
    public String getOfficeLocation()
    {
        return this.officeLocation;
    }
    
    //dept getter
    public String getDepartment()
    {
        return this.department;
    }
    
    //email getter
    public String getEmail()
    {
        return this.email;
    }
}
