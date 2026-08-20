/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package medicarehospitalsystem;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BedTest {
    private HospitalSystem manager;

    @Before
    public void setUp() {
        manager = new HospitalSystem();
    }

    @Test
    public void testRegisterPatient() {
        Patient p = new Patient("P001", "John", "Doe", 30, "Male", "Flu", PatientCategory.OUTPATIENT);
        assertTrue(manager.registerPatient(p));
        assertNotNull(manager.searchPatient("P001"));
    }

    @Test
    public void testSearchPatient() {
        Patient p = new Patient("P002", "Jane", "Smith", 25, "Female", "Fever", PatientCategory.OUTPATIENT);
        manager.registerPatient(p);
        assertNotNull(manager.searchPatient("P002"));
    }

    @Test
    public void testUpdatePatientDetails() {
        Patient p = new Patient("P003", "Alice", "Brown", 40, "Female", "Headache", PatientCategory.OUTPATIENT);
        manager.registerPatient(p);
        assertTrue(manager.updatePatientDetails("P003", "Alice", "Green", 41, "Migraine"));
        assertEquals("Green", manager.searchPatient("P003").getLastName());
    }

    @Test
    public void testDeletePatient() {
        Patient p = new Patient("P004", "Bob", "White", 50, "Male", "Fracture", PatientCategory.OUTPATIENT);
        manager.registerPatient(p);
        assertTrue(manager.deletePatient("P004"));
        assertNull(manager.searchPatient("P004"));
    }

    @Test
    public void testAllocateBed() {
        Inpatient inp = new Inpatient("P005", "Charlie", "Black", 60, "Male", "Cardiac", "W1", null);
        manager.registerPatient(inp);
        assertTrue(manager.allocateBed("P005", "B01"));
        assertEquals("B01", inp.getBedNumber());
    }

    @Test
    public void testReleaseBed() {
        Inpatient inp = new Inpatient("P006", "David", "Grey", 35, "Male", "Asthma", "W1", null);
        manager.registerPatient(inp);
        manager.allocateBed("P006", "B02");
        assertTrue(manager.releaseBed("B02"));
        assertNull(inp.getBedNumber());
    }

    @Test
    public void testPreventDuplicatePatientIDs() {
        Patient p1 = new Patient("P007", "Eva", "Adams", 28, "Female", "Cold", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P007", "Eva", "Baker", 30, "Female", "Cough", PatientCategory.OUTPATIENT);
        assertTrue(manager.registerPatient(p1));
        assertFalse(manager.registerPatient(p2));
    }

    @Test
    public void testPreventAllocatingOccupiedBed() {
        Inpatient p1 = new Inpatient("P008", "Grace", "Hopper", 45, "Female", "Surgery", "W1", null);
        Inpatient p2 = new Inpatient("P009", "Alan", "Turing", 42, "Male", "Observation", "W1", null);
        manager.registerPatient(p1);
        manager.registerPatient(p2);

        assertTrue(manager.allocateBed("P008", "B05"));
        assertFalse(manager.allocateBed("P009", "B05"));
    }

    @Test
    public void testPreventBedAllocationWhenAllBedsOccupied() {
        for (int i = 1; i <= 20; i++) {
            String id = "P10" + i;
            Inpatient patient = new Inpatient(id, "Test", "Patient" + i, 30, "Other", "None", "W1", null);
            manager.registerPatient(patient);
            manager.allocateBed(id, String.format("B%02d", i));
        }

        Inpatient extra = new Inpatient("P999", "Extra", "Patient", 20, "Male", "Checkup", "W1", null);
        manager.registerPatient(extra);
        assertFalse(manager.allocateBed("P999", "B01"));
    }

    @Test
    public void testSortPatientsBySurname() {
        Patient p1 = new Patient("P010", "Zoe", "Zebra", 22, "Female", "Flu", PatientCategory.OUTPATIENT);
        Patient p2 = new Patient("P011", "Adam", "Apple", 23, "Male", "Cold", PatientCategory.OUTPATIENT);
        manager.registerPatient(p1);
        manager.registerPatient(p2);

        manager.sortPatientsByLastName();
        assertEquals("Apple", manager.getPatientList().get(0).getLastName());
    }
}