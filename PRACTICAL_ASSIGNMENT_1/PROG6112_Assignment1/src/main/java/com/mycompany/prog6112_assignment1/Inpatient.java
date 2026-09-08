/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.prog6112_assignment1;

/**
 *
 * @author ST104823
 */
public class Inpatient extends Patient
{

    private int wardNumber;
    private String bedNumber;

    public Inpatient(String patientId, String firstName, String lastName, int age,
            String gender, String medicalCondition, int wardNumber, String bedNumber)
    {
        super(patientId, firstName, lastName, age, gender, medicalCondition, PatientCategory.inpatient);
        this.wardNumber = wardNumber;
        this.bedNumber = bedNumber;
    }

    public int getWardNumber()
    {
        return wardNumber;
    }

    public void setWardNumber(int wardNumber)
    {
        this.wardNumber = wardNumber;
    }

    public String getBedNumber()
    {
        return bedNumber;
    }

    public void setBedNumber(String bedNumber)
    {
        this.bedNumber = bedNumber;
    }

    @Override
    public void displayDetails()
    {
        super.displayDetails();
        System.out.println("Ward Number: " + wardNumber);
        System.out.println("Bed Number: " + bedNumber);
    }

}
