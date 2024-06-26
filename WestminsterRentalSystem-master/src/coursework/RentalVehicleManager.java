/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.io.IOException;


public interface RentalVehicleManager {
    
    public abstract boolean addVehicle(Vehicle vehicleToAdd);
    
    public abstract boolean deleteVehicle(String plateNumber);
    
    public abstract void printVehicles();
    
    public abstract boolean saveVehicleList() throws IOException;
    
    public abstract boolean checkAvailability(String plateNumber, Date start, Date end);
    
    public abstract boolean bookVehicle(String plateNumber, Date pickUpDate, Date dropOffDate);
    
    public abstract void launchGUI();
    
    public abstract boolean launchMenu();
}
