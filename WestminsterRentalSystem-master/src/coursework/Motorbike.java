/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;


public class Motorbike extends Vehicle {
    
    public Motorbike(String plateNumber, String make) {
	super(plateNumber, make);
	colour = "black";
	this.type = "moped";
    }
    
    public Motorbike(String plateNumber, String make, String type) {
	super(plateNumber, make, type, "black");
    }
    
    public Motorbike(String plateNumber, String make, String type, String colour) {
	super(plateNumber, make, type, colour);
    }
    
    @Override
    public String toString() {
	return getVehicleType() + " of type " + this.getType() +  " with plate " + getPlateNumber() + ", make " + getMake() + ", and colour " + getColour() + ".";
    }

    @Override
    public String getVehicleType() {
	return "Motorbike";
    }
}
