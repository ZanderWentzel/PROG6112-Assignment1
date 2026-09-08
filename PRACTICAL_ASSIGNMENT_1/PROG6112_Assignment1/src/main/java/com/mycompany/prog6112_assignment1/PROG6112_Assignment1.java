/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.prog6112_assignment1;

/**
 *
 * @author ST10482354
 */
import java.util.ArrayList;
import java.util.Scanner;

public class PROG6112_Assignment1
{

    public static ArrayList<Patient> patients = new ArrayList<>();
    private static String[][] beds = new String[4][5];
    private static boolean[][] occupied = new boolean[4][5];
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args)
    {
        setupBeds();

        int choice;
        do
        {
            System.out.println("\nHOSPITAL PATIENT ADMISSION SYSTEM");
            System.out.println("1. Patient Management");
            System.out.println("2. Bed Management");
            System.out.println("3. Reports");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1)
            {
                patientMenu();
            } else if (choice == 2)
            {
                bedMenu();
            } else if (choice == 3)
            {
                reportsMenu();
            } else if (choice == 4)
            {
                System.out.println("Exiting...");
            } else
            {
                System.out.println("Invalid choice");
            }
        } while (choice != 4);
    }

    public static void setupBeds()
    {
        int num = 1;
        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                beds[r][c] = "B" + String.format("%02d", num);
                occupied[r][c] = false;
                num++;
            }
        }
    }

    // ========== PATIENT MANAGEMENT ==========
    public static void patientMenu()
    {
        int choice;
        do
        {
            System.out.println("\n--- PATIENT MANAGEMENT ---");
            System.out.println("1. Register Patient");
            System.out.println("2. Search Patient");
            System.out.println("3. Update Patient");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Back");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1)
            {
                registerPatient();
            } else if (choice == 2)
            {
                searchPatient();
            } else if (choice == 3)
            {
                updatePatient();
            } else if (choice == 4)
            {
                deletePatient();
            } else if (choice == 5)
            {
                displayAllPatients();
            } else if (choice == 6)
            {
                System.out.println("Going back...");
            } else
            {
                System.out.println("Invalid choice");
            }
        } while (choice != 6);
    }

    public static void registerPatient()
    {
        System.out.print("Patient ID: ");
        String id = input.nextLine();

        if (findPatient(id) != null)
        {
            System.out.println("Duplicate Patient ID!");
            return;
        }

        System.out.print("First Name: ");
        String firstName = input.nextLine();
        System.out.print("Last Name: ");
        String lastName = input.nextLine();
        System.out.print("Age: ");
        int age = input.nextInt();
        input.nextLine();
        System.out.print("Gender: ");
        String gender = input.nextLine();
        System.out.print("Medical Condition: ");
        String condition = input.nextLine();
        System.out.print("Category (INPATIENT, OUTPATIENT, EMERGENCY): ");
        String catStr = input.nextLine().toUpperCase();

        PatientCategory category = PatientCategory.valueOf(catStr);

        if (category == PatientCategory.inpatient)
        {
            Inpatient inpatient = new Inpatient(id, firstName, lastName, age, gender, condition, 0, "");
            patients.add(inpatient);
            System.out.println("Patient registered!");
            allocateBed(id);
        } else
        {
            Patient patient = new Patient(id, firstName, lastName, age, gender, condition, category);
            patients.add(patient);
            System.out.println("Patient registered!");
        }
    }

    public static Patient findPatient(String id)
    {
        for (Patient p : patients)
        {
            if (p.getPatientID().equals(id))
            {
                return p;
            }
        }
        return null;
    }

    public static void searchPatient()
    {
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        Patient p = findPatient(id);

        if (p != null)
        {
            System.out.println("\n--- PATIENT FOUND ---");
            p.displayDetails();
        } else
        {
            System.out.println("Patient not found");
        }
    }

    public static void updatePatient()
    {
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        Patient p = findPatient(id);

        if (p == null)
        {
            System.out.println("Patient not found");
            return;
        }

        System.out.println("Current details:");
        p.displayDetails();
        System.out.println("\nEnter new details (press Enter to keep current):");

        System.out.print("First Name (" + p.getFirstName() + "): ");
        String firstName = input.nextLine();
        if (!firstName.isEmpty())
        {
            p.setFirstName(firstName);
        }

        System.out.print("Last Name (" + p.getLastName() + "): ");
        String lastName = input.nextLine();
        if (!lastName.isEmpty())
        {
            p.setLastName(lastName);
        }

        System.out.print("Age (" + p.getAge() + "): ");
        String ageStr = input.nextLine();
        if (!ageStr.isEmpty())
        {
            p.setAge(Integer.parseInt(ageStr));
        }

        System.out.print("Gender (" + p.getGender() + "): ");
        String gender = input.nextLine();
        if (!gender.isEmpty())
        {
            p.setGender(gender);
        }

        System.out.print("Medical Condition (" + p.getCondition() + "): ");
        String condition = input.nextLine();
        if (!condition.isEmpty())
        {
            p.setCondition(condition);
        }

        System.out.println("Patient updated!");
    }

    public static void deletePatient()
    {
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        Patient p = findPatient(id);

        if (p == null)
        {
            System.out.println("Patient not found");
            return;
        }

        if (p instanceof Inpatient)
        {
            releaseBed(id);
        }

        patients.remove(p);
        System.out.println("Patient deleted!");
    }

    public static void displayAllPatients()
    {
        if (patients.isEmpty())
        {
            System.out.println("No patients registered");
            return;
        }

        System.out.println("\nALL REGISTERED PATIENTS");
        for (Patient p : patients)
        {
            p.displayDetails();
        }
        System.out.println("Total Patients: " + patients.size());
    }

    // ========== BED MANAGEMENT ==========
    public static void bedMenu()
    {
        int choice;
        do
        {
            System.out.println("\n--- BED MANAGEMENT ---");
            System.out.println("1. Allocate Bed");
            System.out.println("2. Release Bed");
            System.out.println("3. Display Ward Layout");
            System.out.println("4. Display Available Beds");
            System.out.println("5. Display Occupied Beds");
            System.out.println("6. Back");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1)
            {
                allocateBedMenu();
            } else if (choice == 2)
            {
                releaseBedMenu();
            } else if (choice == 3)
            {
                displayWardLayout();
            } else if (choice == 4)
            {
                displayAvailableBeds();
            } else if (choice == 5)
            {
                displayOccupiedBeds();
            } else if (choice == 6)
            {
                System.out.println("Back");
            } else
            {
                System.out.println("Invalid choice");
            }
        } while (choice != 6);
    }

    public static void allocateBedMenu()
    {
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        allocateBed(id);
    }

    public static void allocateBed(String id)
    {
        Patient p = findPatient(id);

        if (p == null)
        {
            System.out.println("Patient not found");
            return;
        }

        if (!(p instanceof Inpatient))
        {
            System.out.println("Only Inpatients can be allocated a bed");
            return;
        }

        Inpatient inpatient = (Inpatient) p;

        if (!inpatient.getBedNumber().isEmpty())
        {
            System.out.println("Patient already has a bed");
            return;
        }

        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (!occupied[r][c])
                {
                    occupied[r][c] = true;
                    inpatient.setBedNumber(beds[r][c]);
                    inpatient.setWardNumber(1);
                    System.out.println("Bed " + beds[r][c] + " allocated");
                    return;
                }
            }
        }

        System.out.println("No beds available!");
    }

    public static void releaseBedMenu()
    {
        System.out.print("Enter Patient ID: ");
        String id = input.nextLine();
        releaseBed(id);
    }

    public static void releaseBed(String id)
    {
        Patient p = findPatient(id);

        if (p == null)
        {
            System.out.println("Patient not found");
            return;
        }

        if (!(p instanceof Inpatient))
        {
            System.out.println("Patient is not an inpatient");
            return;
        }

        Inpatient inpatient = (Inpatient) p;
        String bedNum = inpatient.getBedNumber();

        if (bedNum.isEmpty())
        {
            System.out.println("Patient has no bed");
            return;
        }

        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (beds[r][c].equals(bedNum))
                {
                    occupied[r][c] = false;
                    inpatient.setBedNumber("");
                    inpatient.setWardNumber(0);
                    System.out.println("Bed " + bedNum + " released");
                    return;
                }
            }
        }
    }

    public static void displayWardLayout()
    {
        System.out.println("\nWARD LAYOUT");
        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (occupied[r][c])
                {
                    System.out.print(beds[r][c] + "[X] ");
                } else
                {
                    System.out.print(beds[r][c] + "[ ] ");
                }
            }
            System.out.println();
        }
    }

    public static void displayAvailableBeds()
    {
        System.out.println("\nAVAILABLE BEDS");
        int count = 0;

        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (!occupied[r][c])
                {
                    System.out.print(beds[r][c] + " ");
                    count++;
                }
            }
        }

        System.out.println("\nTotal Available: " + count);
    }

    public static void displayOccupiedBeds()
    {
        System.out.println("\nOCCUPIED BEDS");
        int count = 0;

        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (occupied[r][c])
                {
                    System.out.print(beds[r][c] + " ");
                    count++;
                }
            }
        }

        System.out.println("\nTotal Occupied: " + count);
    }

    // ========== REPORTS ==========
    public static void reportsMenu()
    {
        int choice;
        do
        {
            System.out.println("\n--- REPORTS ---");
            System.out.println("1. Display All Patients");
            System.out.println("2. Display Available Beds");
            System.out.println("3. Display Occupied Beds");
            System.out.println("4. Display Total Registered Patients");
            System.out.println("5. Display Total Occupied Beds");
            System.out.println("6. Display Occupancy Percentage");
            System.out.println("7. Sort Patients by ID");
            System.out.println("8. Sort Patients by Surname");
            System.out.println("9. Back");
            System.out.print("Enter choice: ");
            choice = input.nextInt();
            input.nextLine();

            if (choice == 1)
            {
                displayAllPatients();
            } else if (choice == 2)
            {
                displayAvailableBeds();
            } else if (choice == 3)
            {
                displayOccupiedBeds();
            } else if (choice == 4)
            {
                System.out.println("Total Patients: " + patients.size());
            } else if (choice == 5)
            {
                System.out.println("Total Occupied Beds: " + countOccupiedBeds());
            } else if (choice == 6)
            {
                displayOccupancyPercentage();
            } else if (choice == 7)
            {
                sortById();
            } else if (choice == 8)
            {
                sortBySurname();
            } else if (choice == 9)
            {
                System.out.println("Going back...");
            } else
            {
                System.out.println("Invalid choice");
            }
        } while (choice != 9);
    }

    public static int countOccupiedBeds()
    {
        int count = 0;
        for (int r = 0; r < 4; r++)
        {
            for (int c = 0; c < 5; c++)
            {
                if (occupied[r][c])
                {
                    count++;
                }
            }
        }
        return count;
    }

    public static void displayOccupancyPercentage()
    {
        double percentage = (countOccupiedBeds() * 100.0) / 20;
        System.out.println("Occupancy Percentage: " + percentage + "%");
    }

    public static void sortById()
    {
        if (patients.isEmpty())
        {
            System.out.println("No patients to sort");
            return;
        }

        System.out.println("\nSORTED BY ID");
        for (int i = 0; i < patients.size() - 1; i++)
        {
            for (int j = i + 1; j < patients.size(); j++)
            {
                if (patients.get(i).getPatientID().compareTo(patients.get(j).getPatientID()) > 0)
                {
                    Patient temp = patients.get(i);
                    patients.set(i, patients.get(j));
                    patients.set(j, temp);
                }
            }
        }

        for (Patient p : patients)
        {
            p.displayDetails();
        }
    }

    public static void sortBySurname()
    {
        if (patients.isEmpty())
        {
            System.out.println("No patients to sort");
            return;
        }

        System.out.println("\nSORTED BY SURNAME");
        for (int i = 0; i < patients.size() - 1; i++)
        {
            for (int j = i + 1; j < patients.size(); j++)
            {
                if (patients.get(i).getLastName().compareTo(patients.get(j).getLastName()) > 0)
                {
                    Patient temp = patients.get(i);
                    patients.set(i, patients.get(j));
                    patients.set(j, temp);
                }
            }
        }

        for (Patient p : patients)
        {
            p.displayDetails();
        }
    }
}
