package com.mycompany.prog6112_assignment1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {
    
    private Patient patient1;
    private Patient patient2;
    private Inpatient inpatient1;
    private Inpatient inpatient2;

    @BeforeEach
    public void setUp() {
        patient1 = new Patient("P001", "John", "Doe", 45, "Male", "Flu", PatientCategory.outpatient);
        patient2 = new Patient("P002", "Jane", "Smith", 30, "Female", "Cold", PatientCategory.emergency);
        inpatient1 = new Inpatient("P003", "Bob", "Johnson", 60, "Male", "Heart", 0, "");
        inpatient2 = new Inpatient("P004", "Alice", "Brown", 55, "Female", "Surgery", 0, "");
        
        PROG6112_Assignment1.patients.clear();
        PROG6112_Assignment1.setupBeds();
    }

    @Test
    public void testRegisterPatient() {
        PROG6112_Assignment1.patients.add(patient1);
        assertEquals(1, PROG6112_Assignment1.patients.size());
        assertNotNull(PROG6112_Assignment1.findPatient("P001"));
    }

    @Test
    public void testSearchPatient() {
        PROG6112_Assignment1.patients.add(patient1);
        Patient found = PROG6112_Assignment1.findPatient("P001");
        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());
    }

    @Test
    public void testUpdatePatient() {
        PROG6112_Assignment1.patients.add(patient1);
        Patient found = PROG6112_Assignment1.findPatient("P001");
        found.setFirstName("Jonathan");
        found.setAge(46);
        
        assertEquals("Jonathan", found.getFirstName());
        assertEquals(46, found.getAge());
    }

    @Test
    public void testDeletePatient() {
        PROG6112_Assignment1.patients.add(patient1);
        assertEquals(1, PROG6112_Assignment1.patients.size());
        
        PROG6112_Assignment1.patients.remove(patient1);
        assertEquals(0, PROG6112_Assignment1.patients.size());
        assertNull(PROG6112_Assignment1.findPatient("P001"));
    }

    @Test
    public void testAllocateBed() {
        PROG6112_Assignment1.patients.add(inpatient1);
        PROG6112_Assignment1.allocateBed("P003");
        
        assertEquals("B01", inpatient1.getBedNumber());
        assertEquals(1, inpatient1.getWardNumber());
        assertEquals(1, PROG6112_Assignment1.countOccupiedBeds());
    }

    @Test
    public void testReleaseBed() {
        PROG6112_Assignment1.patients.add(inpatient1);
        PROG6112_Assignment1.allocateBed("P003");
        assertEquals(1, PROG6112_Assignment1.countOccupiedBeds());
        
        PROG6112_Assignment1.releaseBed("P003");
        assertEquals(0, PROG6112_Assignment1.countOccupiedBeds());
        assertEquals("", inpatient1.getBedNumber());
        assertEquals(0, inpatient1.getWardNumber());
    }

    @Test
    public void testPreventDuplicatePatientIDs() {
        PROG6112_Assignment1.patients.add(patient1);
        
        Patient duplicate = new Patient("P001", "Jane", "Smith", 25, "Female", "Cold", PatientCategory.outpatient);
        PROG6112_Assignment1.patients.add(duplicate);
        
        Patient found = PROG6112_Assignment1.findPatient("P001");
        assertEquals("John", found.getFirstName());
        assertEquals(2, PROG6112_Assignment1.patients.size());
    }

    @Test
    public void testPreventOccupiedBedAllocation() {
        PROG6112_Assignment1.patients.add(inpatient1);
        PROG6112_Assignment1.allocateBed("P003");
        assertEquals("B01", inpatient1.getBedNumber());
        
        PROG6112_Assignment1.patients.add(inpatient2);
        PROG6112_Assignment1.allocateBed("P004");
        
        assertNotEquals("B01", inpatient2.getBedNumber());
        assertEquals("B02", inpatient2.getBedNumber());
    }

    @Test
    public void testPreventAllocateWhenAllBedsOccupied() {
        for (int i = 0; i < 20; i++) {
            String id = "P" + String.format("%03d", i);
            Inpatient inpatient = new Inpatient(id, "Test", "User" + i, 30, "Male", "Test", 0, "");
            PROG6112_Assignment1.patients.add(inpatient);
            PROG6112_Assignment1.allocateBed(id);
        }
        
        assertEquals(20, PROG6112_Assignment1.countOccupiedBeds());
        
        Inpatient extra = new Inpatient("P999", "Extra", "Patient", 30, "Male", "Test", 0, "");
        PROG6112_Assignment1.patients.add(extra);
        PROG6112_Assignment1.allocateBed("P999");
        
        assertEquals("", extra.getBedNumber());
        assertEquals(0, extra.getWardNumber());
        assertEquals(20, PROG6112_Assignment1.countOccupiedBeds());
    }

    @Test
    public void testSortBySurname() {
        PROG6112_Assignment1.patients.add(patient2);
        PROG6112_Assignment1.patients.add(patient1);
        PROG6112_Assignment1.patients.add(inpatient1);
        
        PROG6112_Assignment1.sortBySurname();
        
        assertEquals("Doe", PROG6112_Assignment1.patients.get(0).getLastName());
        assertEquals("Johnson", PROG6112_Assignment1.patients.get(1).getLastName());
        assertEquals("Smith", PROG6112_Assignment1.patients.get(2).getLastName());
    }

    @Test
    public void testSortById() {
        PROG6112_Assignment1.patients.add(patient2);
        PROG6112_Assignment1.patients.add(inpatient1);
        PROG6112_Assignment1.patients.add(patient1);
        
        PROG6112_Assignment1.sortById();
        
        assertEquals("P001", PROG6112_Assignment1.patients.get(0).getPatientID());
        assertEquals("P002", PROG6112_Assignment1.patients.get(1).getPatientID());
        assertEquals("P003", PROG6112_Assignment1.patients.get(2).getPatientID());
    }

    @Test
    public void testOutpatientCannotGetBed() {
        PROG6112_Assignment1.patients.add(patient1);
        PROG6112_Assignment1.allocateBed("P001");
        assertEquals(0, PROG6112_Assignment1.countOccupiedBeds());
    }

    @Test
    public void testPatientCannotGetTwoBeds() {
        PROG6112_Assignment1.patients.add(inpatient1);
        PROG6112_Assignment1.allocateBed("P003");
        assertEquals("B01", inpatient1.getBedNumber());
        
        PROG6112_Assignment1.allocateBed("P003");
        assertEquals("B01", inpatient1.getBedNumber());
        assertEquals(1, PROG6112_Assignment1.countOccupiedBeds());
    }

    @Test
    public void testCountOccupiedBeds() {
        assertEquals(0, PROG6112_Assignment1.countOccupiedBeds());
        
        PROG6112_Assignment1.patients.add(inpatient1);
        PROG6112_Assignment1.allocateBed("P003");
        assertEquals(1, PROG6112_Assignment1.countOccupiedBeds());
        
        PROG6112_Assignment1.patients.add(inpatient2);
        PROG6112_Assignment1.allocateBed("P004");
        assertEquals(2, PROG6112_Assignment1.countOccupiedBeds());
    }
}