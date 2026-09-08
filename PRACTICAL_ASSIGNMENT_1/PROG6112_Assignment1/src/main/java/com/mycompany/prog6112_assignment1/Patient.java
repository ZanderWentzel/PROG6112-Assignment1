/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog6112_assignment1;

/**
 *
 * @author ST10482354
 */
public class Patient
{
    //Declaration
    private String patientID;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String medicalCondition;
    private PatientCategory category;

    public Patient(String paptientID, String firstName, String lastName, int age, String gender, String condition, PatientCategory category)
    {
        this.patientID = paptientID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.medicalCondition = medicalCondition;
        this.category = category;
    }

    public String getPatientID()
    {
        return patientID;
    }

    public void setPatientID(String paptientID)
    {
        this.patientID = paptientID;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getCondition()
    {
        return medicalCondition;
    }

    public void setCondition(String condition)
    {
        this.medicalCondition = condition;
    }

    public PatientCategory getCategory()
    {
        return category;
    }

    public void setCategory(PatientCategory category)
    {
        this.category = category;
    }

    public void displayDetails()
    {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Medical Condition: " + medicalCondition);
        System.out.println("Category: " + category);
    }


}
