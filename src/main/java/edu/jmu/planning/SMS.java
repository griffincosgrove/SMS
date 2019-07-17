package edu.jmu.planning;
/*
Team Project Part 1
Authors and Roles: Griffin Cosgrove Lead CDF & FX Developer, Brace Brillhart FX Developer, John Gregory CDF Developer,
                   Alex Rizzi Logic, Code Testing, and CDF Consultant, Trey Rustand Code Testing.
Due Date: 4/24/2019
The purpose of this file is to set up the UI for the student management system. The UI system includes error checking
 */


// Needed Imports
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.scene.shape.*;

import java.sql.*;
import oracle.jdbc.pool.*;
import java.util.*;
import javafx.scene.control.Alert.AlertType;

public class SMS extends Application {

    // create ArrayLists for classes (records)
    public static ArrayList<Student> students = new ArrayList<Student>();
    public static ArrayList<Course> courses = new ArrayList<Course>();
    public static ArrayList<Instructor> instructors = new ArrayList<Instructor>();

    // Controls at class level
    GridPane primaryPane = new GridPane();
    
    // Database variables
    Connection dbConn;
    Statement commStmt;
    ResultSet dbResults;
    int DBID = 0;
    
    // student labels
    Label lblAddStudent = new Label("Choose Student:");
    Label lblName = new Label("Name:");
    Label lblYear = new Label("Year:");
    Label lblMajor = new Label("Major:");
    Label lblGPA = new Label("GPA:");
    Label lblEmail = new Label("Email:");

    // course labels
    Label lblAddCourse = new Label("Add Course:");
    Label lblCourseName = new Label("Name:");
    Label lblBuilding = new Label("Building:");
    Label lblRoom = new Label("Room:");
    Label lblMax = new Label("Max Capacity:");

    // Instructor labels
    Label lblAddInstructor = new Label("Add Instructor:");
    Label lblInstructorName = new Label("Name:");
    Label lblPrefix = new Label("Prefix:");
    Label lblOffice = new Label("Office:");
    Label lblDepartment = new Label("Department:");
    Label lblInstructorEmail = new Label("Email:");

    // update labels
    Label lblBuildCourse = new Label("Build a Course");
    Label lblBuildAddStudent = new Label("Select Student:");
    Label lblBuildToCourse = new Label("Choose Course:");
    Label lblInstructorIs = new Label("Instructor is:");
    Label lblNewInstructor = new Label("New Instructor?");
    Label lblblank = new Label("");

    // student text
    TextField txtName = new TextField();
    TextField txtMajor = new TextField();
    TextField txtGPA = new TextField();
    TextField txtEmail = new TextField();

    // course text
    TextField txtCourseName = new TextField();
    TextField txtBuildingName = new TextField();
    TextField txtRoom = new TextField();
    TextField txtMax = new TextField();

    // Instructor text
    TextField txtInstructorName = new TextField();
    TextField txtOffice = new TextField();
    TextField txtDepartment = new TextField();
    TextField txtInstructorEmail = new TextField();

    // big text box
    TextArea txtOutput = new TextArea();

    // course button
    Button btnCourse = new Button("Add Course ->");

    // student button
    Button btnStudent = new Button("Add Student ->");

    // instructor button
    Button btnInstructor = new Button("Add Instructor ->");

    // update button
    Button btnUpdate = new Button("Save Changes");
    
    // radio buttons
    RadioButton rdoAddStudent = new RadioButton("Add Student");
    RadioButton rdoRemoveStudent = new RadioButton("Remove Student");
    ToggleGroup rdoAddRemoveGroup = new ToggleGroup();

    //printAll buttons
    Button btnStudents = new Button("Display All Students");
    Button btnCourses = new Button("Display All Courses");
    Button btnInstructors = new Button("Display All Instructors");

    // student list
    ObservableList obsYear = FXCollections.observableArrayList();
    ComboBox<String> cmboYear = new ComboBox<>(obsYear);

    // course list
    ObservableList obsBuilding = FXCollections.observableArrayList();
    ComboBox<String> cmboBuilding = new ComboBox<>(obsBuilding);

    // instructor list
    ObservableList obsPrefix = FXCollections.observableArrayList();
    ComboBox<String> cmboPrefix = new ComboBox<>(obsPrefix);

    // update lists
    ObservableList obsAddStudent = FXCollections.observableArrayList();
    ComboBox<String> cmboAddStudent = new ComboBox<>(obsAddStudent);

    ObservableList obsToCourse = FXCollections.observableArrayList();
    ComboBox<String> cmboToCourse = new ComboBox<>(obsToCourse);

    ObservableList obsInstructorIs = FXCollections.observableArrayList();
    ComboBox<String> cmboInstructorIs = new ComboBox<>(obsInstructorIs);

    // update checkbox
    CheckBox chkInstructor = new CheckBox("New Instructor?");
    
    // add student checkbox
    CheckBox chkAddStudent = new CheckBox("Add Student");
    
    // update checkbox
    CheckBox chkRemoveStudent = new CheckBox("Remove Student");

    @Override
    public void start(Stage primaryStage) {

        //set position of pane
        primaryPane.setAlignment(Pos.CENTER);

        //set size of text area
        txtOutput.setPrefWidth(550);
        txtOutput.setPrefHeight(200);
        
        
        rdoAddStudent.setToggleGroup(rdoAddRemoveGroup);
        rdoRemoveStudent.setToggleGroup(rdoAddRemoveGroup);
        
        // add data to combo box for Year
        obsYear.add("Freshman");
        obsYear.add("Sophomore");
        obsYear.add("Junior");
        obsYear.add("Senior");

        // add data to combo box for building
        obsBuilding.add("Showker");
        obsBuilding.add("Chandler");
        obsBuilding.add("Burrus Hall");

        // add data to combo box for prefix
        obsPrefix.add("Dr.");
        obsPrefix.add("Ms.");
        obsPrefix.add("Mrs.");
        obsPrefix.add("Mr.");

        // adding student to pane
        primaryPane.add(lblAddStudent, 1, 1, 2, 1);
        primaryPane.add(lblName, 1, 2);
        primaryPane.add(txtName, 2, 2);
        primaryPane.add(lblYear, 1, 4);
        primaryPane.add(cmboYear, 2, 4);
        primaryPane.add(lblMajor, 1, 5);
        primaryPane.add(txtMajor, 2, 5);
        primaryPane.add(lblGPA, 1, 8);
        primaryPane.add(txtGPA, 2, 8);
        primaryPane.add(lblEmail, 1, 10);
        primaryPane.add(txtEmail, 2, 10);
        primaryPane.add(btnStudent, 2, 12);

        // adding course to pane
        primaryPane.add(lblAddCourse, 4, 1, 2, 1);
        primaryPane.add(lblCourseName, 4, 2);
        primaryPane.add(txtCourseName, 5, 2);
        primaryPane.add(lblBuilding, 4, 4);
        primaryPane.add(cmboBuilding, 5, 4);
        primaryPane.add(lblRoom, 4, 5);
        primaryPane.add(txtRoom, 5, 5);
        primaryPane.add(lblMax, 4, 8);
        primaryPane.add(txtMax, 5, 8);
        primaryPane.add(btnCourse, 5, 10);

        // adding instructor to pane
        primaryPane.add(lblAddInstructor, 7, 1, 2, 1);
        primaryPane.add(lblInstructorName, 7, 2);
        primaryPane.add(txtInstructorName, 8, 2);
        primaryPane.add(lblPrefix, 7, 4);
        primaryPane.add(cmboPrefix, 8, 4);
        primaryPane.add(lblOffice, 7, 5);
        primaryPane.add(txtOffice, 8, 5);
        primaryPane.add(lblDepartment, 7, 8);
        primaryPane.add(txtDepartment, 8, 8);
        primaryPane.add(lblInstructorEmail, 7, 10);
        primaryPane.add(txtInstructorEmail, 8, 10);
        primaryPane.add(btnInstructor, 8, 12);

        // adding update to pane
        primaryPane.add(lblblank, 1, 16, 2, 1);
        primaryPane.add(lblBuildCourse, 1, 17, 2, 1);
        primaryPane.add(rdoAddStudent, 1, 18);
        primaryPane.add(rdoRemoveStudent, 2, 18);
        primaryPane.add(lblBuildAddStudent, 1, 19);
        primaryPane.add(cmboAddStudent, 2, 19);
        primaryPane.add(lblBuildToCourse, 1, 20);
        primaryPane.add(cmboToCourse, 2, 20);
        primaryPane.add(chkInstructor, 1, 21, 2, 1);
        primaryPane.add(lblInstructorIs, 1, 22);
        primaryPane.add(cmboInstructorIs, 2, 22);
        primaryPane.add(btnUpdate, 2, 25);
        primaryPane.add(btnStudents,2,26 );
        primaryPane.add(btnInstructors, 2, 27);
        primaryPane.add(btnCourses, 2, 28);
        primaryPane.add(txtOutput, 3, 17, 6, 12);


        // setting Hgap
        primaryPane.setHgap(10);

        // deploy scene
        Scene primaryScene = new Scene(primaryPane, 900, 450);

        //display stage
        primaryStage.setScene(primaryScene);
        primaryStage.setTitle("SMS App V.3");
        primaryStage.show();
        


        // student button logic
        btnStudent.setOnAction(e -> {
            //error check logic
            if (cmboYear.getSelectionModel().getSelectedIndex() == -1 || txtName.getText().equals("") || txtMajor.getText().equals("") || txtGPA.getText().equals("") || txtEmail.getText().equals("")) {
                txtOutput.setText("Please fill out all fields");
            } else {

                txtOutput.clear();
                // variables
                String email = txtEmail.getText();

                boolean validEmail = false;
                boolean validGPA = false;
                boolean validName = validateName();

                //name check
                if (validName == false) {
                    txtOutput.setText("Please enter valid name");
                }

                // email validation
                for (int i = 0; i < email.length(); i++) {

                    if (email.charAt(i) == '@') {
                        validEmail = true;
                    } else {
                        continue;
                    }
                }

                boolean numeric = isNumeric(txtGPA.getText());

                // GPA validation
                if (numeric) {
                    if (Double.parseDouble(txtGPA.getText()) >= 0 && Double.parseDouble(txtGPA.getText()) <= 5.0) {
                        validGPA = true;
                    } else {
                        validGPA = false;
                    }

                } else {
                    txtOutput.setText("Please enter valid GPA");
                }
                if (validGPA == true && validEmail == true && validName == true) {
                    int year = 1;
                    // convert year to string
                    switch (cmboYear.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            year = 1;
                            break;
                        case 1:
                            year = 2;
                            break;
                        case 2:
                            year = 3;
                            break;
                        case 3:
                            year = 4;
                            break;
                    }
                    // create student
                    Student std = new Student(txtName.getText(), year, txtMajor.getText(), Double.parseDouble(txtGPA.getText()), txtEmail.getText());

                    // add student to combo box
                    obsAddStudent.add(std.getName());

                    // add student to records
                    students.add(std);

                    // clear fields
                    txtName.clear();

                    txtMajor.clear();
                    txtGPA.clear();
                    txtEmail.clear();
                    cmboYear.getSelectionModel().select(-1);
                    txtOutput.clear();

                    //error check logic
                } else if (validEmail == true && validGPA == false) {
                    txtOutput.setText("Please enter a valid GPA");
                } else if (validEmail == false && validGPA == true) {
                    txtOutput.setText("Please enter a valid Email address");
                } else if (validEmail == false && validGPA == false) {
                    txtOutput.setText("Please enter a valid GPA and Email Address");
                } else if (validEmail == true && validGPA == false) {
                    txtOutput.setText("Please enter a valid GPA");
                }
            }
        });

        // course button logic
        btnCourse.setOnAction(e -> {
            //error check logic
            if (cmboBuilding.getSelectionModel().getSelectedIndex() == -1 || txtCourseName.getText().equals("") || txtRoom.getText().equals("") || txtMax.getText().equals("")) {
                txtOutput.setText("Please fill out all fields");
            } else {
                boolean numeric = isCapNumeric(txtMax.getText());
                if (numeric) {
                    txtOutput.clear();
                    String building = "";

                    // getting values from combobox
                    switch (cmboBuilding.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            building = "Showker";
                            break;
                        case 1:
                            building = "Chandler";
                            break;
                        case 2:
                            building = "Burrus Hall";
                            break;
                    }

                    // create new course
                    Course crs = new Course(txtCourseName.getText(), building, txtRoom.getText(), Integer.parseInt(txtMax.getText()));

                    // adding courses to combo box
                    obsToCourse.add(crs.getCourseName());

                    // adding course to records
                    courses.add(crs);

                    // clearing fields
                    txtCourseName.clear();
                    //cmboBuilding.getItems().clear(); (issues)
                    txtRoom.clear();
                    txtMax.clear();
                    cmboBuilding.getSelectionModel().select(-1);
                    txtOutput.clear();
                    //error check logic
                } else if (numeric == false) {
                    txtOutput.setText("Please enter valid Capacity");
                }
            }

        });

        // Instrcutor button logic
        btnInstructor.setOnAction(e -> {
            //error check logic
            boolean validInstructorName = validateInstructorName();
            if (cmboPrefix.getSelectionModel().getSelectedIndex() == -1 || txtInstructorName.getText().equals("") || txtOffice.getText().equals("") || txtDepartment.getText().equals("") || txtInstructorEmail.getText().equals("")) {
                txtOutput.setText("Please fill out all fields");
            } else {

                txtOutput.clear();
                String prefix = "";
                // getting prefix values
                switch (cmboPrefix.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        prefix = "Dr.";
                        break;
                    case 1:
                        prefix = "Ms.";
                        break;
                    case 2:
                        prefix = "Mrs.";
                        break;
                    case 3:
                        prefix = "Mr.";
                        break;
                }
                //email error check logic
                String email = txtInstructorEmail.getText();
                boolean validEmail = false;
                for (int i = 0; i < email.length(); i++) {

                    if (email.charAt(i) == '@') {
                        validEmail = true;
                    } else {
                        continue;
                    }
                }

                if (validEmail == true && validInstructorName == true) {
                    // create new instructor
                    Instructor inst = new Instructor(txtInstructorName.getText(), prefix, txtOffice.getText(), txtDepartment.getText(), txtInstructorEmail.getText());

                    // add instructor to combo box
                    obsInstructorIs.add(inst.getTitle());

                    // add instructor to combo box
                    instructors.add(inst);

                    // clear fields
                    txtInstructorName.clear();
                    txtOffice.clear();
                    //cmboPrefix.getItems().clear(); (issues)
                    txtDepartment.clear();
                    txtInstructorEmail.clear();
                    cmboPrefix.getSelectionModel().select(-1);

                        //error check logic
                } else if (validEmail == false && validInstructorName == true) {
                    txtOutput.setText("Please enter valid Email");
                } else if (validEmail == true && validInstructorName == false) {
                    txtOutput.setText("Please enter valid name");
                } else {
                    txtOutput.setText("Please enter valid Email and name");
                }
            }
        });

        // lambda expression for check instructor button
        chkInstructor.setOnAction(e -> {
            if (cmboInstructorIs.isDisabled()) {
                cmboInstructorIs.setDisable(false);
            } else {
                cmboInstructorIs.setDisable(true);
            }
        });

        //lambda expression for update button
        btnUpdate.setOnAction(e -> {
            // if box is checked
            if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() != -1 && rdoAddStudent.isSelected()) {
                boolean valid = studentValidate();
                boolean instValid = instructorValidate();
                if (valid == true && instValid == true) {
                    // getting index of combo box

                    int studentPos = cmboAddStudent.getSelectionModel().getSelectedIndex();
                    int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
                    int instructorPos = cmboInstructorIs.getSelectionModel().getSelectedIndex();


                    // enrolling student
                    boolean full = isCourseFull();
                    if (!full) {
                        courses.get(coursePos).enrollStudent(students.get(studentPos));
                        rdoAddStudent.setSelected(false);
                        
                        
                    } else {
                        displayAlert("Error", "Class is full", "Course capacity met");
                    }

                    // adding instructor to course
                    courses.get(coursePos).assignInstructor(instructors.get(instructorPos));                    

                    // printing results
                    String results = "";
                    String results2 = "";
                    results = courses.get(coursePos).toString();
                    results2 = courses.get(coursePos).getRoster();
                    txtOutput.setText(results + "\n" + results2);
                    cmboAddStudent.getSelectionModel().select(-1);
                    cmboToCourse.getSelectionModel().select(-1);
                    cmboInstructorIs.getSelectionModel().select(-1);
                    cmboInstructorIs.setDisable(true);
                    chkInstructor.setSelected(false);
                } else if (valid == false && instValid == true) {
                    int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
                    int instructorPos = cmboInstructorIs.getSelectionModel().getSelectedIndex();
                    courses.get(coursePos).assignInstructor(instructors.get(instructorPos));
                    String results = "";
                    String results2 = "";
                    results = courses.get(coursePos).toString();
                    results2 = courses.get(coursePos).getRoster();
                    txtOutput.setText(results + "\n" + results2);
                    cmboAddStudent.getSelectionModel().select(-1);
                    cmboToCourse.getSelectionModel().select(-1);
                    cmboInstructorIs.getSelectionModel().select(-1);
                    cmboInstructorIs.setDisable(true);
                    chkInstructor.setSelected(false);
                    //error check and alert window logic
                } else if (valid == true && instValid == false) {
                    displayAlert("Error", "Duplicate entry", "Instructor is already assigned to this course");
                    //error check and alert window logic
                } else if (valid == false && instValid == false) {
                    displayAlert("Error", "Duplicate entry", "Student & Instructor are already in this course");
                }

            } else if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() == -1 && cmboToCourse.getSelectionModel().getSelectedIndex() != -1 && cmboInstructorIs.getSelectionModel().getSelectedIndex() != -1) {
                boolean instValid = instructorValidate();
                if (instValid == true) {
                    // getting index of combo box

                    int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
                    int instructorPos = cmboInstructorIs.getSelectionModel().getSelectedIndex();

                    
                    // adding instructor to course
                    courses.get(coursePos).assignInstructor(instructors.get(instructorPos));

                    // printing results
                    String results = "";
                    String results2 = "";
                    results = courses.get(coursePos).toString();
                    results2 = courses.get(coursePos).getRoster();
                    txtOutput.setText(results + "\n" + results2);
                    cmboAddStudent.getSelectionModel().select(-1);
                    cmboToCourse.getSelectionModel().select(-1);
                    cmboInstructorIs.getSelectionModel().select(-1);
                    cmboInstructorIs.setDisable(true);
                    chkInstructor.setSelected(false);
                    //error check and alert window logic
                } else {
                    displayAlert("Error", "Duplicate Entry", "Instructor is already assigned to this course");
                }
                //error check and alert window logic
            } else if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() == -1 && cmboInstructorIs.getSelectionModel().getSelectedIndex() != -1) {
                displayAlert("Error", "Course Needed", "Please select a course");
                //error check and alert window logic
            } else if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() == -1 && cmboToCourse.getSelectionModel().getSelectedIndex() == -1 && cmboInstructorIs.getSelectionModel().getSelectedIndex() == -1) {
                displayAlert("Error", "Empty fields", "Please make a selection");
                //error check and alert window logic
            } else if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() == -1 && cmboToCourse.getSelectionModel().getSelectedIndex() == -1 && cmboInstructorIs.getSelectionModel().getSelectedIndex() != -1) {
                displayAlert("Error", "Empty fields", "Please select a course");
                //error check and alert window logic
            } else if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() == -1 && cmboInstructorIs.getSelectionModel().getSelectedIndex() == -1) {
                displayAlert("Error", "Empty fields", "Please select a course");
            } else if (chkInstructor.isSelected() == false && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() != -1 && rdoAddStudent.isSelected()) {
                boolean valid = studentValidate();

                if (valid) {
                    // get index of course and student
                    int studentPos = cmboAddStudent.getSelectionModel().getSelectedIndex();
                    int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();

                    //verify course is not full
                    boolean full = isCourseFull();
                    if (!full) {
                        courses.get(coursePos).enrollStudent(students.get(studentPos));
                        rdoAddStudent.setSelected(false);                       
                    }
                    //error check and alert window logic for class capacity
                    else {
                        displayAlert("Error", "Class is full", "Course capacity met");
                    }

                    // display results
                    String results = "";
                    String results2 = "";
                    results = courses.get(coursePos).toString();
                    results2 = courses.get(coursePos).getRoster();
                    txtOutput.setText(results + "\n" + results2);
                    cmboAddStudent.getSelectionModel().select(-1);
                    cmboToCourse.getSelectionModel().select(-1);
                    //error check and alert window logic for duplicate student enrollment
                } else {
                    displayAlert("Error", "Duplicare entry", "Student already enrolled in this course");
                }
                //error check and alert window logic for invalid student
            } else if (chkInstructor.isSelected() == false && cmboAddStudent.getSelectionModel().getSelectedIndex() == -1 && cmboToCourse.getSelectionModel().getSelectedIndex() != -1) {
                displayAlert("Error", "Invalid entry", "Please select a student");
                //error check and alert window logic for invalid course
            } else if (chkInstructor.isSelected() == false && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() == -1) {
                displayAlert("Error", "Invalid entry", "Please select a course");
                //error check and alert window logic for invalid course and student
            } else if (chkInstructor.isSelected() == false && cmboAddStudent.getSelectionModel().getSelectedIndex() == -1 && cmboToCourse.getSelectionModel().getSelectedIndex() == -1) {
                displayAlert("Error", "invalid entry", "Please select a course and student");
            } else if (chkInstructor.isSelected() && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() != -1 && rdoRemoveStudent.isSelected()) {
                int studentPos = cmboAddStudent.getSelectionModel().getSelectedIndex();
                int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
                
                
                //validate student is enrolled
                if(checkEnrolled(students.get(studentPos), courses.get(coursePos)))
                {
                    courses.get(coursePos).removeStudent(students.get(studentPos).getStudentID());
                }
                else
                {
                    displayAlert("Error", "Not Enrolled", "This student is not enrolled in this course");
                }
                
                String results = "";
                    String results2 = "";
                    results = courses.get(coursePos).toString();
                    results2 = courses.get(coursePos).getRoster();
                    txtOutput.setText(results + "\n" + results2);
                    cmboAddStudent.getSelectionModel().select(-1);
                    cmboToCourse.getSelectionModel().select(-1);
                    
            } else if (chkInstructor.isSelected() == false && cmboAddStudent.getSelectionModel().getSelectedIndex() != -1 && cmboToCourse.getSelectionModel().getSelectedIndex() != -1 && rdoRemoveStudent.isSelected()) {
                int studentPos = cmboAddStudent.getSelectionModel().getSelectedIndex();
                int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
                
                if(checkEnrolled(students.get(studentPos), courses.get(coursePos)))
                {
                    courses.get(coursePos).removeStudent(students.get(studentPos).getStudentID());
                }
                else
                {
                    displayAlert("Error", "Not Enrolled", "This student is not enrolled in this course");
                }
                
                String results = "";
                    String results2 = "";
                    results = courses.get(coursePos).toString();
                    results2 = courses.get(coursePos).getRoster();
                    txtOutput.setText(results + "\n" + results2);
                    cmboAddStudent.getSelectionModel().select(-1);
                    cmboToCourse.getSelectionModel().select(-1);                                  
                    rdoRemoveStudent.setSelected(false);
                    
            }

        });

        cmboInstructorIs.setDisable(true);

        //lambda exp for display all students button
        btnStudents.setOnAction(e -> {
            if(students.size() > 0)
            {
                txtOutput.setText(printAllStudents(students));
            }
            //verify a student exists
            else
            {
                displayAlert("Error", "Invalid Number of students", "No students exist");
            }
        });

        //lambda expression for display all courses button
        btnCourses.setOnAction(e -> {
            if(courses.size() > 0)
            {
                txtOutput.setText(printAllCourses(courses));
            }
            else
            {
                displayAlert("Error", "Invalid Number of courses", "No courses exist");
            }
        });

        // lambda expression for display all instructors button
        btnInstructors.setOnAction(e -> {
            if(instructors.size() > 0)
            {
                txtOutput.setText(printAllInstructors(instructors));
            }
            //verify an instructor exits
            else
            {
                displayAlert("Error", "Invalid number of instructors", "No instructors exist");
            }
        });
        
        loadDataFromDB();
        //Check if any students are inserted into the ArrayList
        //if true then the combo box will be populated with the students.
        if(students.size() > 0)
            {
                addStudentsOnLaunch(students);
                Student.setNextID(students.size());              
                DBID = (students.size());   
            }                      
    }
    
    //method to delete records in the table on start. so records are not duplicated
    //this is called after they are loaded in though.
    public void deleteStudentdsOnLaunch()
    {
        //String sqlQuery = "";
        String sqlQuery = "DELETE FROM JAVAUSER.STUDENT;";
        //sqlQuery += "WHERE STUDENTID <= ";
        //sqlQuery += (students.s ize() - 1) + ";";
        sendDBCommand(sqlQuery);
        
    }
    
       //method to add students to the ArrayList students on launch
        //if any students are being imported from the table this method
        //will essentially do that by adding them to the array list.
       public void addStudentsOnLaunch(ArrayList<Student> students)
    {
        for(int i = 0; i < students.size(); i++)
        {
            obsAddStudent.add(students.get(i).getName());
        }
    }
        @Override
        public void stop() {
           //inserts courses to the STUDENTENROLLMENT table *not needed for to-be
        /*for (int i = 0; i < courses.size(); i++) 
        {
            for (int j = 0; j < courses.get(i).getEnrolledStudents().size(); j++) 
            {
                //insertItemStudent(courses.get(i).getEnrolledStudents().get(j));
                insertItemEnrollment(courses.get(i), courses.get(i).getEnrolledStudents().get(j));
            }
            insertItemCourse(courses.get(i));
        }
        */
        //inserts students to the STUDENT table.
        for (int i = DBID; i < students.size(); i++)
        {
            insertItemStudent(students.get(i));
        }
        
        /*/inserts courses to the COURSE table *not needed for to-be
        for (int i = 0; i < courses.size(); i++)
        {
            insertItemCourse(courses.get(i));
        }
        */
        
        
        /*inserts instructors to the INSTRUCTOR table *not needed for to-be
        for(int i = 0; i < instructors.size(); i++)
        {
            insertItemInstructor(instructors.get(i));
        }
        */
        sendDBCommand("Commit work");
        
    }
        //method to check if a student is enrolled in a certain course
        public boolean checkEnrolled(Student stu, Course c)
        {
            boolean check = false;
            for(int i = 0; i < c.getEnrolledStudents().size(); i++)
            {
                if(stu.getStudentID() == c.getEnrolledStudents().get(i).getStudentID())
                {
                    check = true;
                }
                    
            }
            return check;
        }
        
        //method to create the INSERT statement and send to SQL and populate the STUDENT table
        public void insertItemStudent(Student std) {
        String sqlQuery = "";
        sqlQuery += "INSERT INTO JAVAUSER.STUDENT (STUDENTID, STUDENTNAME, STUDENTYEAR, STUDENTMAJOR, STUDENTGPA, STUDENTEMAIL) VALUES ";
        sqlQuery += "(" + std.getStudentID() + ",";
        sqlQuery += "\'" + std.getName() + "\'" + ",";
        String yearString = std.getStudentYear();
        int year = 0;
        if (yearString.equalsIgnoreCase("Freshman")) 
        {
            year = 1;
        } 
        else if (yearString.equalsIgnoreCase("Sophomore")) 
        {
            year = 2;
        } 
        else if (yearString.equalsIgnoreCase("Junior")) 
        {
            year = 3;
        } 
        else if (yearString.equalsIgnoreCase("Senior")) 
        {
            year = 4;
        }
    
 
        sqlQuery += year + ",";
        sqlQuery += "\'" + std.getStudentMajor() + "\'" + ",";
        sqlQuery += std.getGPA() + ",";
        sqlQuery += "\'" + std.getStudentEmail() + "\'" + ")";
        sendDBCommand(sqlQuery);
    }
        /*
        *not needed for the to-be program
        //method to create the INSERT statement and send to SQL and populate the COURSE table
        public void insertItemCourse(Course c) {
        String sqlQuery = "";
        sqlQuery += "INSERT INTO JAVAUSER.COURSE (COURSEID, COURSENAME, COURSEBLDG, COURSEROOM, COURSECAPACITY) VALUES ";
        sqlQuery += "(" + c.getCourseID()+ ",";
        sqlQuery += "\'" + c.getCourseName() + "\'" + ",";
        sqlQuery += "\'" + c.getCourseBLDG() + "\'" + ",";
        sqlQuery += "\'" + c.getCourseBLDGRoom() + "\'" + ",";
        sqlQuery += "\'" + c.getCourseCapacity() + "\'" + ")";
        sendDBCommand(sqlQuery);
    }
    */
        /*method to create the INSERT statement for the STUDENTENROLLMENT
        public void insertItemEnrollment(Course c, Student std) {
        String sqlQuery = "";
        sqlQuery += "INSERT INTO JAVAUSER.STUDENTENROLLMENT (COURSEID, STUDENTID) VALUES ";
        sqlQuery += "(" + c.getCourseID()+ ",";
        sqlQuery += std.getStudentID() + ")";
        sendDBCommand(sqlQuery);
    }
     */
        
        /*
        public void insertItemInstructor(Instructor i)
        {
            String sqlQuery = "";
            sqlQuery += "INSERT INTO JAVAUSER.INSTRUCTOR (instrID, instrName, instrPrefix, instrOffice, instrDept, instrEmail) VALUES ";
            sqlQuery += "(" + i.getID() + ",";
            sqlQuery += "\'" + i.getName() + "\'" + ",";
            sqlQuery += "\'" + i.getPrefix() + "\'" + ",";
            sqlQuery += "\'" + i.getOfficeLocation() + "\'" + ",";
            sqlQuery += "\'" + i.getDepartment() + "\'" + ",";
            sqlQuery += "\'" + i.getEmail() + "\')";
            sendDBCommand(sqlQuery);
        }
        */
        
        //this method loads records from the sql database
        public void loadDataFromDB() {
        String sqlQuery = "SELECT * FROM JAVAUSER.STUDENT ORDER BY STUDENTID";
        sendDBCommand(sqlQuery);
         
        try {
             txtOutput.clear();
             while (dbResults.next()) 
             {
                int id = dbResults.getInt(1);
                String name = dbResults.getString(2);
                int year = dbResults.getInt(3);              
                String major = dbResults.getString(4);
                double gpa = dbResults.getFloat(5);
                String email = dbResults.getString(6);
                
                
                Student std = new Student(name, year, major, gpa, email, id);
                students.add(std);
             }
         } catch (SQLException sqle) {
             System.out.println(sqle.toString());
         }
        }

    //validate student data entry
    public boolean studentValidate() {
        int studentPos = cmboAddStudent.getSelectionModel().getSelectedIndex();
        int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
        boolean validStudent = true;
        // email validation
        if (courses.get(coursePos).getEnrolledStudents().size() >= 2) {
            for (int i = 0; i <= courses.get(coursePos).getEnrolledStudents().size() - 1; i++) {
                if (students.get(studentPos) == courses.get(coursePos).getEnrolledStudents().get(i)) {
                    validStudent = false;
                } else {
                    continue;
                }
            }
        } else if (courses.get(coursePos).getEnrolledStudents().isEmpty()) {
            validStudent = true;
        } else if (courses.get(coursePos).getEnrolledStudents().size() == 1) {
            validStudent = students.get(studentPos) != courses.get(coursePos).getEnrolledStudents().get(0);
        } else {
            validStudent = true;
        }

        return validStudent;
    }

    //validate instructor data entry
    public boolean instructorValidate() {
        int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
        int instructorPos = cmboInstructorIs.getSelectionModel().getSelectedIndex();
        boolean validInst = false;
        if (courses.get(coursePos).getCourseInstructor() != null) {
            if(courses.get(coursePos).getCourseInstructor() != instructors.get(instructorPos)) {
                validInst = true;

            } else {
                validInst = false;
            }
            return validInst;
        } else {
            return true;
        }

    }

    // method to verify inputted data is double
    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // method to verify capacity is integer
    public boolean isCapNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //method to check if course is full or not full
    public boolean isCourseFull() {
        int studentPos = cmboAddStudent.getSelectionModel().getSelectedIndex();
        int coursePos = cmboToCourse.getSelectionModel().getSelectedIndex();
        boolean isFull = false;
        if (courses.get(coursePos).getCourseCapacity() == courses.get(coursePos).getEnrolledStudents().size()) {
            isFull = true;
        } else {
            isFull = false;
        }
        return isFull;
    }


    //method to validate name has a space, to ensure a first and last name is entered
    public boolean validateName() {
        String name = txtName.getText();
        boolean valid = false;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                valid = true;
            }
        }
        return valid;

    }

    //method to validate name has a space, to ensure a first and last name is entered
    public boolean validateInstructorName() {
        String name = txtInstructorName.getText();
        boolean valid = false;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == ' ') {
                valid = true;
            }
        }
        return valid;

    }

    //displays all students to the textArea
    public String printAllStudents(ArrayList<Student> students)
    {
        int j = 1;
        String str = "";
        for(int i = 0; i < students.size(); i++)
        {
            str += j++ + ": ";
            str += students.get(i).toString();
            str += " Enrolled in: " + displayAllClassesEnrolled(students.get(i));
            str +=  "\n";
        }
        return str;
    }

    //displays all courses to the textArea
    public String printAllCourses(ArrayList<Course> courses)
    {
        int j = 1;
        String str = "";
        for(int i = 0; i < courses.size(); i++)
        {
            str += j++ + ": ";
            str += courses.get(i).toString();
            str += "\n";
        }
        return str;
    }

    //displays all instructors to the textArea
    public String printAllInstructors(ArrayList<Instructor> instructors)
    {
        String str = "";
        int j = 1;
        for(int i = 0; i < instructors.size(); i++)
        {
            str += j++ + ": ";
            str += instructors.get(i).toString();
            str += "\n";
        }
        return str;
    }

    //method to display all the classes a student is enrolled in
    public String displayAllClassesEnrolled(Student student)
    {
        String str = "";
        for(int i = 0; i < courses.size(); i++)
        {
            for(int j = 0; j < courses.get(i).getEnrolledStudents().size(); j++) {
                if (courses.get(i).getEnrolledStudents().get(j).getStudentID() == student.getStudentID()) {
                    str += courses.get(i).getCourseName() + " ";
                }
            }
        }
        return str;
    }
    
    //method to display an alert/error window
    public void displayAlert(String title, String header, String context) 
        {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        }

    public static void main(String[] args) {
        launch(args);
    }
    //method to send a query to SQL. Code from the Dr. Ezell general public license
    public void sendDBCommand(String sqlQuery)
    {
        String URL = "jdbc:oracle:thin:@localhost:1521:XE";
        String userID = "javauser"; // Change to YOUR Oracle username
        String userPASS = "javapass"; // Change to YOUR Oracle password
        OracleDataSource ds;
        try
        {
            ds = new OracleDataSource();
            ds.setURL(URL);
            dbConn = ds.getConnection(userID,userPASS);
            commStmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            dbResults = commStmt.executeQuery(sqlQuery); // Sends the Query to the DB
        }
        catch (SQLException e)
        {
            System.out.println(e.toString());
        }
    }
}