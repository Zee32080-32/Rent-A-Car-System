/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;


public abstract class Vehicle implements Comparable<Vehicle> {
    protected String plateNumber;
    protected String make;
    protected String colour;
    protected String type;
    
    public Vehicle(String plateNumber, String make) {
        this.plateNumber = plateNumber;
	this.make = make;
    }
    
    public Vehicle(String plateNumber, String make, String type) {
	this.plateNumber = plateNumber;
	this.make = make;
	this.type = type;
    }
    
    public Vehicle(String plateNumber, String make, String type, String colour) {
        this.plateNumber = plateNumber;
	this.make = make;
	this.type = type;
	this.colour = colour;
    }
    
    public void setPlateNumber(String plateNumber) {
	this.plateNumber = plateNumber;
    }
    
    public String getPlateNumber() {
	return this.plateNumber;
    }
    
    public void setMake(String make) {
	this.make = make;
    }
    
    public String getMake() {
	return this.make;
    }
    
    public void setColour(String colour) {
	this.colour = colour;
    }
    
    public String getColour() {
	return this.colour;
    }
    
    public String getType() {
	return this.type;
    }
    
    public void setType(String type) {
	this.type = type;
    }
    
    public abstract String getVehicleType();
    
    @Override
    public String toString() {
	return getVehicleType() + " with plate " + getPlateNumber() + ", make " + getMake() + ", and colour " + getColour() + ".";
    }

    @Override
    public int compareTo(Vehicle v) {
	return this.getMake().compareTo(v.getMake());
    }
    
    
}
