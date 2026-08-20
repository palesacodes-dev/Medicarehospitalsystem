/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicarehospitalsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HospitalSystem {
    private final List<Patient> patientList = new ArrayList<>();
    private final Bed[][] wardBeds = new Bed[4][5];

    public HospitalSystem() {
        initializeBeds();
    }

    private void initializeBeds() {
        int count = 1;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                String bedCode = String.format("B%02d", count++);
                wardBeds[r][c] = new Bed(bedCode);
            }
        }
    }

    public boolean registerPatient(Patient patient) {
        if (searchPatient(patient.getPatientId()) != null) {
            System.out.println("Error: Duplicate Patient ID " + patient.getPatientId());
            return false;
        }
        patientList.add(patient);
        System.out.println("Patient registered successfully!");
        return true;
    }

    public Patient searchPatient(String patientId) {
        for (Patient p : patientList) {
            if (p.getPatientId().equalsIgnoreCase(patientId)) {
                return p;
            }
        }
        return null;
    }

    public boolean updatePatientDetails(String patientId, String fname, String lname, int age, String condition) {
        Patient p = searchPatient(patientId);
        if (p != null) {
            p.setFirstName(fname);
            p.setLastName(lname);
            p.setAge(age);
            p.setMedicalCondition(condition);
            System.out.println("Patient details updated.");
            return true;
        }
        System.out.println("Patient not found.");
        return false;
    }

    public boolean deletePatient(String patientId) {
        Patient p = searchPatient(patientId);
        if (p != null) {
            if (p instanceof Inpatient inpatient && inpatient.getBedNumber() != null) {
                releaseBed(inpatient.getBedNumber());
            }
            patientList.remove(p);
            System.out.println("Patient deleted successfully.");
            return true;
        }
        System.out.println("Patient not found.");
        return false;
    }

    public void displayAllPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No registered patients found.");
            return;
        }
        for (Patient p : patientList) {
            p.displayDetails();
        }
    }

    private Bed findBed(String bedNumber) {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (wardBeds[r][c].getBedNumber().equalsIgnoreCase(bedNumber)) {
                    return wardBeds[r][c];
                }
            }
        }
        return null;
    }

    public boolean allocateBed(String patientId, String bedNumber) {
        Patient p = searchPatient(patientId);
        if (p == null) {
            System.out.println("Error: Patient not found.");
            return false;
        }
        if (!(p instanceof Inpatient inpatient)) {
            System.out.println("Error: Only Inpatients can be allocated a bed.");
            return false;
        }

        Bed bed = findBed(bedNumber);
        if (bed == null) {
            System.out.println("Error: Invalid bed number.");
            return false;
        }
        if (bed.isOccupied()) {
            System.out.println("Error: Bed " + bedNumber + " is already occupied.");
            return false;
        }

        bed.setOccupied(true);
        bed.setPatientId(patientId);
        inpatient.setBedNumber(bedNumber);
        System.out.println("Bed " + bedNumber + " successfully allocated to Patient " + patientId);
        return true;
    }

    public boolean releaseBed(String bedNumber) {
        Bed bed = findBed(bedNumber);
        if (bed == null || !bed.isOccupied()) {
            System.out.println("Error: Bed is invalid or not occupied.");
            return false;
        }

        String patientId = bed.getPatientId();
        bed.setOccupied(false);
        bed.setPatientId(null);

        Patient p = searchPatient(patientId);
        if (p instanceof Inpatient inpatient) {
            inpatient.setBedNumber(null);
        }

        System.out.println("Bed " + bedNumber + " released successfully.");
        return true;
    }

    public void displayWardLayout() {
        System.out.println("\nWARD LAYOUT");
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                Bed b = wardBeds[r][c];
                System.out.printf("%s %s\t", b.getBedNumber(), b.getStatusSymbol());
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getOccupiedBedCount() {
        int count = 0;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 5; c++) {
                if (wardBeds[r][c].isOccupied()) count++;
            }
        }
        return count;
    }

    public int getAvailableBedCount() {
        return 20 - getOccupiedBedCount();
    }

    public void displayWardReport() {
        System.out.println("\nWARD REPORT");
        System.out.println("Total Registered Patients: " + patientList.size());
        System.out.println("Total Occupied Beds: " + getOccupiedBedCount());
        System.out.println("Total Available Beds: " + getAvailableBedCount());
        double percentage = (getOccupiedBedCount() / 20.0) * 100;
        System.out.printf("Ward Occupancy: %.2f%%\n\n", percentage);
    }

    public void sortPatientsByLastName() {
        patientList.sort(Comparator.comparing(Patient::getLastName, String.CASE_INSENSITIVE_ORDER));
    }

    public void sortPatientsById() {
        patientList.sort(Comparator.comparing(Patient::getPatientId));
    }

    public List<Patient> getPatientList() { return patientList; }
}
