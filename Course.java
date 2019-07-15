package sms;

/*
Team Project Part 1
Authors and Roles: Griffin Cosgrove Lead CDF & FX Developer, Brace Brillhart FX Developer, John Gregory CDF Developer,
                   Alex Rizzi Logic, Code Testing, and CDF Consultant, Trey Rustand Code Testing.
Due Date: 4/24/2019
The purpose of this CDF is to define the Course class which includes relevant data fields and class methods.
 */

import java.util.ArrayList;

public class Course {
    //class non static data fields
    private String courseName;
    private String courseBuilding;
    private String courseBldgRoom;
    private int courseCapacity;
    private int courseID;
    private ArrayList<Student> enrolledStudents;
    private Instructor courseInstructor;

    //static data fields
    private static int nextCourseID = 0;

    //constructor from requirements
    public Course(String courseName, String courseBuilding, String courseBldgRoom, int courseCapacity)
    {
        this.courseName = courseName;
        this.courseBuilding = courseBuilding;
        this.courseBldgRoom = courseBldgRoom;
        this.courseCapacity = courseCapacity;
        this.courseID = nextCourseID++;
        this.enrolledStudents = new ArrayList<Student>(this.courseCapacity);
        this.courseInstructor = null;
    }

    //class methods

    //method to enroll a student
    public void enrollStudent(Student newStudent)
    {
        this.enrolledStudents.add(newStudent);
    }

    //method to remove a student
    public boolean removeStudent(int studentID)
    {
        for (int i = 0; i < this.enrolledStudents.size(); i++)
        {
            Student student = enrolledStudents.get(i);
            if(student.getStudentID() == studentID)
            {
                enrolledStudents.remove(i);
                return true;
            }
        }
        return false;
    }

    //set instructor
    public void assignInstructor(Instructor newInstr)
    {
        this.courseInstructor = newInstr;
    }
    
    public int getCourseID() {
        return this.courseID;
    }
    
    public String getCourseBLDG() {
        return this.courseBuilding;
    }
    
    public String getCourseBLDGRoom() {
        return this.courseBldgRoom;
    }

    //set room
    public void setCourseBldgRoom(String room)
    {
        this.courseBldgRoom = room;
    }

    //set building
    public void setCourseBuilding(String building)
    {
        this.courseBuilding = building;
    }

    //set capacity
    public void setCapacity(int capacity)
    {
        this.courseCapacity = capacity;
    }

    //set course name
    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    //get course name
    public String getCourseName()
    {
        return this.courseName;
    }

    //override the toString() method to display useful information about each student.
    @Override
    public String toString()
    {
        String str = "";
        //String instructorTitle = this.courseInstructor.getTitle();
        str += ("Course: " + this.courseName + ", ");
        str += (" Location: " + this.courseBuilding + ", " + " Room: " + this.courseBldgRoom + ", ");
        str += (" Capacity: " + this.courseCapacity + ", ");
        if (this.courseInstructor == null)
        {
            str += " Instructor: Staff";
        }
        else
        {
            str += (" Instructor: " + this.courseInstructor.getTitle());
        }
        return str;
    }

    //get course capacity
    public int getCourseCapacity() {
        return this.courseCapacity;
    }

    //get course instructor
    public Instructor getCourseInstructor() {
        return this.courseInstructor;
    }

    //get an enrolled student
    public ArrayList<Student> getEnrolledStudents() {
        return this.enrolledStudents;
    }

    //return all enrolled students for a course
    public String getRoster()
    {
        String str = "";

        for (int i = 0; i < this.enrolledStudents.size(); i++)
        {
            str += this.enrolledStudents.get(i).toString();
            str += "\n";
        }
        return str;
    }
}