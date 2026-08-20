/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package medicarehospitalsystem;

public class Bed {
    private String bedNumber;
    private boolean isOccupied;
    private String patientId;

    public Bed(String bedNumber) {
        this.bedNumber = bedNumber;
        this.isOccupied = false;
        this.patientId = null;
    }

    public String getBedNumber() { return bedNumber; }
    public boolean isOccupied() { return isOccupied; }
    public void setOccupied(boolean occupied) { isOccupied = occupied; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getStatusSymbol() {
        return isOccupied ? "[O]" : "[A]";
    }
}
