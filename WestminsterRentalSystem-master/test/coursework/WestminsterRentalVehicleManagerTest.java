/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class WestminsterRentalVehicleManagerTest {
    WestminsterRentalVehicleManager rvm;
    
    public WestminsterRentalVehicleManagerTest() {
    }
    
    @Before
    public void setUp() {
	rvm = new WestminsterRentalVehicleManager();
    }
    
    @After
    public void tearDown() {
	rvm = null;
    }

    /**
     * Test of addVehicle method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testAddVehicle() {
	System.out.print("Testing WestminsterRentalVehicleManager.addVehicle()...");
	Car c1 = new Car("plate1", "Honda", "sedan", "black");
	boolean added = rvm.addVehicle(c1);
	assertTrue(added);
	assertEquals(1, rvm.getVehiclesToRent().size());
	assertEquals(c1.getPlateNumber(), rvm.getVehiclesToRent().get(0).getPlateNumber());
	
	Motorbike m1 = new Motorbike("plate2", "Suzuki", "cruiser", "red");
	added = rvm.addVehicle(m1);
	assertTrue(added);
	assertEquals(2, rvm.getVehiclesToRent().size());
	assertEquals(m1.getPlateNumber(), rvm.getVehiclesToRent().get(1).getPlateNumber());
	
	Car c2 = new Car("plate2", "Ferrari", "sedan", "white");
	added = rvm.addVehicle(c2);
	assertFalse(added);
	assertEquals(2, rvm.getVehiclesToRent().size());
	
	for (int i = 3; i < 51; i++) {
	    added = rvm.addVehicle(new Car("plate" + Integer.toString(i), "Toyota", "SUV", "blue"));
	    assertTrue(added);
	}
	
	Motorbike m2 = new Motorbike("plate70", "Suzuki", "dirt bike", "green");
	added = rvm.addVehicle(m2);
	assertFalse(added);
	assertEquals(50, rvm.getVehiclesToRent().size());
	
	System.out.print("PASSED");
	System.out.println();
    }

    /**
     * Test of deleteVehicle method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testDeleteVehicle() {
	System.out.print("Testing WestminsterRentalVehicleManager.deleteVehicle()...");
	Car c1 = new Car("plate1", "Honda", "sedan", "black");
	rvm.addVehicle(c1);
	boolean test = rvm.deleteVehicle("plate2");
	assertFalse(test);
	test = rvm.deleteVehicle("plate1");
	assertTrue(test);
	System.out.print("PASSED");
	System.out.println();
    }

    /**
     * Test of printVehicles method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testPrintVehicles() {
	System.out.print("Testing WestminsterRentalVehicleManager.printVehicles()...");
	PrintStream psOrig = System.out;
	OutputStream os = new ByteArrayOutputStream();
	PrintStream ps = new PrintStream(os);
	System.setOut(ps);
	Car c1 = new Car("TEST", "Mercedes", "SUV", "silver");
	rvm.addVehicle(c1);
	rvm.printVehicles();
	assertEquals("\nVEHICLE LIST:\nCar of type SUV with plate TEST, make Mercedes, and colour silver." + System.getProperty("line.separator"), os.toString());
	os = new ByteArrayOutputStream();
	ps = new PrintStream(os);
	System.setOut(ps);
	rvm.deleteVehicle("TEST");
	rvm.printVehicles();
	assertEquals("\nThere are no vehicles in the rental parking lot. Please add one." + System.getProperty("line.separator"), os.toString());
	System.setOut(psOrig);
	System.out.print("PASSED");
	System.out.println();
    }

    /**
     * Test of saveVehicleList method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testSaveVehicleList() throws Exception {
	System.out.print("Testing WestminsterRentalVehicleManager.saveVehicleList()...");
	boolean saved = rvm.saveVehicleList();
	assertFalse(saved);
	Car c1 = new Car("TEST1", "Mercedes", "SUV", "silver");
	Motorbike m1 = new Motorbike("TEST2", "BMW", "Moped", "black");
	rvm.addVehicle(c1);
	rvm.addVehicle(m1);
	saved = rvm.saveVehicleList();
	assertTrue(saved);
	File file = new File("vehicles.txt");
	try {
	    FileReader r = new FileReader(file);
	    char[] vChars = new char[(int) file.length()];
	    r.read(vChars);
	    
	    String fileContent = new String(vChars);
	    String[] vehicles = fileContent.split("\n");
	    String[] vehicleAttrs;
	    for (int i = 0; i < vehicles.length; i++) {
		vehicleAttrs = vehicles[i].split(",");
		assertEquals(rvm.getVehiclesToRent().get(i).getVehicleType(), vehicleAttrs[0]);
		assertEquals(rvm.getVehiclesToRent().get(i).getType(), vehicleAttrs[1]);
		assertEquals(rvm.getVehiclesToRent().get(i).getMake(), vehicleAttrs[2]);
		assertEquals(rvm.getVehiclesToRent().get(i).getPlateNumber(), vehicleAttrs[3]);
		assertEquals(rvm.getVehiclesToRent().get(i).getColour(), vehicleAttrs[4]);
	    }
	} catch (IOException e) {
	}
	
	System.out.print("PASSED");
	System.out.println();
    }

    /**
     * Test of checkAvailability method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testCheckAvailability() {
	System.out.print("Testing WestminsterRentalVehicleManager.checkAvailability()...");
	Car c1 = new Car("TEST", "Mercedes", "SUV", "silver");
	rvm.addVehicle(c1);
	Date start = new Date(13, 1, 2020);
	Date end = new Date(14, 2, 2020);
	boolean avail = rvm.checkAvailability("TEST", start, end);
	assertTrue(avail);
	rvm.getRentalSchedules().add(new Schedule(c1, new Date(7, 1, 2020), new Date(20, 1, 2020)));
	avail = rvm.checkAvailability("TEST", start, end);
	assertFalse(avail);
	System.out.print("PASSED");
	System.out.println();
    }

    /**
     * Test of bookVehicle method, of class WestminsterRentalVehicleManager.
     */
    @Test
    public void testBookVehicle() {
	System.out.print("Testing WestminsterRentalVehicleManager.bookVehicle()...");
	Car c1 = new Car("TEST", "Mercedes", "SUV", "silver");
	rvm.addVehicle(c1);
	Date pickUpDate = new Date(13, 1, 2020);
	Date dropOffDate = new Date(22, 2, 2020);
	boolean booked = rvm.bookVehicle(c1.getPlateNumber(), pickUpDate, dropOffDate);
	assertTrue(booked);
	assertEquals(1, rvm.getRentalSchedules().size());
	assertEquals(c1, rvm.getRentalSchedules().get(0).getVehicle());
	assertEquals(pickUpDate, rvm.getRentalSchedules().get(0).getPickUpDate());
	assertEquals(dropOffDate, rvm.getRentalSchedules().get(0).getDropOffDate());
	System.out.print("PASSED");
	System.out.println();
    }
}
