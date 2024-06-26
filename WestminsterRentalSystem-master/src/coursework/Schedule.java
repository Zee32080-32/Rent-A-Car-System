/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;


public class Schedule {
    private Date pickUpDate;
    private Date dropOffDate;
    private Vehicle bookedVehicle;
    
    public Schedule(Vehicle booked, Date pickUp, Date dropOff) {
	this.bookedVehicle = booked;
	this.dropOffDate = dropOff;
	this.pickUpDate = pickUp;
    }
    
    public void setVehicle(Vehicle vehicleToBook) {
	this.bookedVehicle = vehicleToBook;
    }
    
    public Vehicle getVehicle() {
	return this.bookedVehicle;
    }
    
    public void setPickUpDate(Date newPickUp) {
	this.pickUpDate = newPickUp;
    }
    
    public Date getPickUpDate() {
	return this.pickUpDate;
    }
    
    public void setDropOffDate(Date newDropOff) {
	this.dropOffDate = newDropOff;
    }
    
    public Date getDropOffDate() {
	return this.dropOffDate;
    }
    

}