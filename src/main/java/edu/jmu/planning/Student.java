package sms;
/*
Team Project Part 1
Authors and Roles: Griffin Cosgrove Lead CDF & FX Developer, Brace Brillhart FX Developer, John Gregory CDF Developer,
                   Alex Rizzi Logic, Code Testing, and CDF Consultant, Trey Rustand Code Testing.
Due Date: 4/24/2019
The purpose of this CDF is to define the Student class which includes relevant data fields and class methods.
 */

public class Student {
    //non static data fields
    private String firstName;
    private String lastName;
    private String studentYear;
    private String studentMajor;
    private double GPA;
    private String studentEmail;
    private int studentID;

    //static data fields
    private static int nextStudentID = 0;

    //constructor elicited in requirements
    public Student(String name, int studentYear, String studentMajor, double GPA, String studentEmail)
    {
        int indexOfSpace = name.indexOf(' ');
        this.firstName = name.substring(0, indexOfSpace);
        this.lastName = name.substring((indexOfSpace + 1), (name.length()));
        //switch statement to convert passed in integer to String studentYear

        //logic to convert student year int to a String year
        switch (studentYear)
        {
            case 1: this.studentYear = "Freshman"; break;
            case 2: this.studentYear = "Sophomore"; break;
            case 3: this.studentYear = "Junior"; break;
            case 4: this.studentYear = "Senior"; break;
            //default: this.studentYear  = "Invalid Year";
        }
        this.studentMajor = studentMajor;
        this.GPA = GPA;
        this.studentEmail = studentEmail;
        this.studentID = nextStudentID++;
    }
    
    public Student(String name, int studentYear, String studentMajor, double GPA, String studentEmail, int id)
    {
        int indexOfSpace = name.indexOf(' ');
        this.firstName = name.substring(0, indexOfSpace);
        this.lastName = name.substring((indexOfSpace + 1), (name.length()));
        //switch statement to convert passed in integer to String studentYear

        //logic to convert student year int to a String year
        switch (studentYear)
        {
            case 1: this.studentYear = "Freshman"; break;
            case 2: this.studentYear = "Sophomore"; break;
            case 3: this.studentYear = "Junior"; break;
            case 4: this.studentYear = "Senior"; break;
            //default: this.studentYear  = "Invalid Year";
        }
        this.studentMajor = studentMajor;
        this.GPA = GPA;
        this.studentEmail = studentEmail;
        this.studentID = id;
        nextStudentID = id + 1;
    }

    //class methods
    public void setGPA(double GPA)
    {
        if(GPA >= 0.0 && GPA <= 5.0)
        {
            this.GPA = GPA;
        }
        else
        {
            this.GPA = 0.0;
        }
    }

    public double getGPA()
    {
        return this.GPA;
    }

    public String getName()
    {
        return (this.firstName + " " + this.lastName);
    }

    public int getStudentID()
    {
        return this.studentID;
    }

    public void setStudentEmail(String email)
    {
        this.studentEmail = email;
    }

    public String getStudentYear()
    {
        return this.studentYear;
    }

    public void setStudentYear(int studentYear)
    {
        switch (studentYear)
        {
            case 1: this.studentYear = "Freshman"; break;
            case 2: this.studentYear = "Sophomore"; break;
            case 3: this.studentYear = "Junior"; break;
            case 4: this.studentYear = "Senior"; break;
            //default: this.studentYear  = "Invalid Year"; break;
        }
    }

    public String getStudentMajor()
    {
        return this.studentMajor;
    }
    
    public String getStudentEmail()
    {
        return this.studentEmail;
    }

    public void setStudentMajor(String studentMajor)
    {
        this.studentMajor = studentMajor;
    }

    // override toString() method to display useful information about a student
    @Override
    public String toString()
    {
        String str = "";
        str += ("Student ID# : " + this.studentID + ", ");
        str += ("Name: ");
        str += (this.firstName + " " + this.lastName + ", ");
        str += (" Major: " + this.getStudentMajor() + ", ");
        str += (" Year: " + this.studentYear);
        return str;
    }
    
    public static void setNextID(int integer)
    {
        Student.nextStudentID = integer;
    }
}
