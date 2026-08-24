/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package medicarehospitalsystem;

import java.util.Scanner;

public class Medicarehospitalsystem {
    private static final HospitalSystem system = new HospitalSystem();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) { 
            System.out.println("\n****************************************");
            System.out.println("       MEDICARE HOSPITAL ADMISSION        ");
            System.out.println("******************************************");
            System.out.println("1. Patient Management");
            System.out.println("2. Bed Management");
            System.out.println("3. Reports");
            System.out.println("0. Exit");
            
            int choice = getInt("Select an option: ");
            switch (choice) {
                case 1 -> patientManagementMenu();
                case 2 -> bedManagementMenu();
                case 3 -> reportsMenu();
                case 0 -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Exiting Medicare Hospital System. Goodbye!");
    }

    private static void patientManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nPATIENT MANAGEMENT");
            System.out.println("1. Register New Patient");
            System.out.println("2. Search Patient by ID");
            System.out.println("3. Update Patient Details");
            System.out.println("4. Delete Patient");
            System.out.println("5. Display All Patients");
            System.out.println("6. Return to Main Menu");
            
            int choice = getInt("Enter choice: ");
            switch (choice) {
                case 1 -> registerPatient();
                case 2 -> searchPatient();
                case 3 -> updatePatient();
                case 4 -> deletePatient();
                case 5 -> system.displayAllPatients();
                case 6 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void bedManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nBED MANAGEMENT");
            System.out.println("1. Allocate Bed");
            System.out.println("2. Release Bed");
            System.out.println("3. Display Ward Layout");
            System.out.println("4. Return to Main Menu");
            
            int choice = getInt("Enter choice: ");
            switch (choice) {
                case 1 -> allocateBed();
                case 2 -> releaseBed();
                case 3 -> system.displayWardLayout();
                case 4 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nREPORTS");
            System.out.println("1. Display All Patients");
            System.out.println("2. Display Patients Sorted by Surname");
            System.out.println("3. Display Patients Sorted by ID");
            System.out.println("4. Total Available Beds");
            System.out.println("5. Total Occupied Beds");
            System.out.println("6. Ward Occupancy Percentage");
            System.out.println("7. Complete Ward Report");
            System.out.println("8. Return to Main Menu");
            
            int choice = getInt("Enter choice: ");
            switch (choice) {
                case 1 -> system.displayAllPatients();
                case 2 -> {
                    system.sortPatientsByLastName();
                    system.displayAllPatients();
                }
                case 3 -> {
                    system.sortPatientsById();
                    system.displayAllPatients();
                }
                case 4 -> System.out.println("Total Available Beds: " + system.getAvailableBedCount());
                case 5 -> System.out.println("Total Occupied Beds: " + system.getOccupiedBedCount());
                case 6 -> System.out.printf("Ward Occupancy Percentage: %.2f%%\n", (system.getOccupiedBedCount() / 20.0) * 100);
                case 7 -> system.displayWardReport();
                case 8 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void registerPatient() {
        String id = getString("Enter Patient ID: ");
        String fn = getString("Enter First Name: ");
        String ln = getString("Enter Last Name: ");
        int age = getInt("Enter Age: ");
        String gender = getString("Enter Gender: ");
        String condition = getString("Enter Medical Condition: ");
        PatientCategory category = getCategory();

        if (category == PatientCategory.INPATIENT) {
            String ward = getString("Enter Ward Number: ");
            system.registerPatient(new Inpatient(id, fn, ln, age, gender, condition, ward, null));
        } else {
            system.registerPatient(new Patient(id, fn, ln, age, gender, condition, category));
        }
    }

    private static void searchPatient() {
        String id = getString("Enter Patient ID to Search: ");
        Patient p = system.searchPatient(id);
        if (p != null) p.displayDetails();
        else System.out.println("Patient not found.");
    }

    private static void updatePatient() {
        String id = getString("Enter Patient ID to update: ");
        String fn = getString("New First Name: ");
        String ln = getString("New Last Name: ");
        int age = getInt("New Age: ");
        String cond = getString("New Condition: ");
        system.updatePatientDetails(id, fn, ln, age, cond);
    }

    private static void deletePatient() {
        String id = getString("Enter Patient ID to Delete: ");
        system.deletePatient(id);
    }

    private static void allocateBed() {
        String id = getString("Enter Patient ID: ");
        String bed = getString("Enter Bed Number (e.g., B01): ");
        system.allocateBed(id, bed);
    }

    private static void releaseBed() {
        String bed = getString("Enter Bed Number to Release: ");
        system.releaseBed(bed);
    }

    private static String getString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int getInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static PatientCategory getCategory() {
        while (true) {
            System.out.println("Select Category:");
            System.out.println("1. INPATIENT");
            System.out.println("2. OUTPATIENT");
            System.out.println("3. EMERGENCY");
            int choice = getInt("Enter choice: ");
            switch (choice) {
                case 1 -> { return PatientCategory.INPATIENT; }
                case 2 -> { return PatientCategory.OUTPATIENT; }
                case 3 -> { return PatientCategory.EMERGENCY; }
                default -> System.out.println("Invalid selection.");
            }
        }
    }
}