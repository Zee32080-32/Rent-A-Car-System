/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class WestminsterRentalVehicleManager implements RentalVehicleManager {
    private final ArrayList<Vehicle> vehiclesToRent;
    private final ArrayList<Schedule> rentalSchedules;
    private final HashSet<String> plateNumbers;
    private final int lotSize = 50;

    public WestminsterRentalVehicleManager() {
	vehiclesToRent = new ArrayList<>();
	rentalSchedules = new ArrayList<>();
	plateNumbers = new HashSet<>();
    }
    
    public ArrayList<Vehicle> getVehiclesToRent() {
	return this.vehiclesToRent;
    }
    
    public ArrayList<Schedule> getRentalSchedules() {
	return this.rentalSchedules;
    }
    
    public HashSet<String> getPlateNumbers() {
	return this.plateNumbers;
    }
    
    @Override
    public boolean addVehicle(Vehicle vehicleToAdd) {
	if (plateNumbers.contains(vehicleToAdd.getPlateNumber()) || vehiclesToRent.size() == 50) {
	    return false;
	}
	vehiclesToRent.add(vehicleToAdd);
	plateNumbers.add(vehicleToAdd.getPlateNumber());
	return true;
    }

    @Override
    public boolean deleteVehicle(String plateNumber) {
	if (plateNumbers.contains(plateNumber) == false) {
	    return false;
	}
	Vehicle toDelete = null;
	for (Vehicle v: vehiclesToRent) {
	    if (v.getPlateNumber().equals(plateNumber)) {
		toDelete = v;
	    }
	}
	vehiclesToRent.remove(toDelete);
	return true;
    }

    @Override
    public void printVehicles() {
	System.out.println();
	if (this.vehiclesToRent.isEmpty()) {
	    System.out.println("There are no vehicles in the rental parking lot. Please add one.");
	} else {
	    System.out.println("VEHICLE LIST:");
	    ArrayList<Vehicle> sortedVehicles = this.vehiclesToRent;
	    Collections.sort(sortedVehicles);
	    for (Vehicle v: sortedVehicles) {
		System.out.println(v);
	} 
	}
    }

    @Override
    public boolean saveVehicleList() throws IOException {
	if (this.vehiclesToRent.isEmpty()) {
	    return false;
	}
	try (FileWriter writer = new FileWriter("vehicles.txt")) {
	    for (Vehicle v : this.vehiclesToRent) {
		writer.write(v.getVehicleType() + "," + v.getType() + "," + v.getMake() + "," + v.getPlateNumber() + "," + v.getColour() + "\n");
	    }
	    return true;
	}
    }

    @Override
    public boolean checkAvailability(String plateNumber, Date start, Date end) {
	if (start.greaterThan(end)) {
	    return false;
	}
	for (Schedule s: rentalSchedules) {
	    if (s.getVehicle().getPlateNumber().equals(plateNumber)) {
		if (Date.overlap(s.getPickUpDate(), s.getDropOffDate(), start, end)) {
		    return false;
		}
	    }
	}
	return true;
    }

    @Override
    public boolean bookVehicle(String plateNumber, Date pickUp, Date dropOff) {
	boolean booked = false;
	for (Vehicle v: vehiclesToRent) {
	    if (v.getPlateNumber().equals(plateNumber)) {
		rentalSchedules.add(new Schedule(v, pickUp, dropOff));
		booked = true;
	    }
	}
	return booked;
    }

    @Override
    public void launchGUI() {
	RentalGUI test = new RentalGUI(this.vehiclesToRent);
	test.setSize(1000, 500);
	test.setVisible(true);
	test.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
    }

    @Override
    public boolean launchMenu() {
	boolean exit = false;
	
	System.out.println("To Add a new Vehicle press 1");
	System.out.println("To Delete a Vehicle press 2");
	System.out.println("To Print the list of Vehicles press 3");
	System.out.println("To Save the list of Vehicles press 4");
	System.out.println("To launch the Customer GUI press 5");
	System.out.println("To exit the console system press 6");
	
	Scanner s = new Scanner(System.in);
	try {
	    int choice = s.nextInt();
	
	    switch(choice) {
		case 1:
		    if (this.vehiclesToRent.size() == this.lotSize) {
			System.out.println("Sorry, the rental parking lot is full. Please delete a vehicle from the system.");
			break;
		    }
		    System.out.println("Press 1 to add a Car");
		    System.out.println("Press 2 to add a Motorbike");

		    int vehicleType = s.nextInt();
		    s.nextLine();
		    if (vehicleType != 1 && vehicleType != 2) {
			System.out.println();
			System.out.println("Invalid vehicle type. Cancelling operation.");
			System.out.println();
			break;
		    }

		    System.out.println("Enter the plate number of the vehicle:");
		    String plateNumber = s.nextLine();

		    System.out.println("Enter the colour of the vehicle:");
		    String colour = s.nextLine();

		    System.out.println("Enter the make of the vehicle:");
		    String make = s.nextLine();

		    switch(vehicleType) {
			case 1:
			    Map<Integer, String> carTypes = new HashMap<>();
			    carTypes.put(1, "SUV");
			    carTypes.put(2, "Sedan");
			    carTypes.put(3, "Minivan");
			    System.out.println("Enter 1 for SUV, 2 for Sedan, or 3 for Minivan.");
			    try {
				int carType = s.nextInt();
				s.nextLine();
				if (carTypes.containsKey(carType)) {
				    Car c = new Car(plateNumber, make, carTypes.get(carType), colour);
				    boolean added = addVehicle(c);
				    if (added) {
					System.out.println("Car successfully added.");
				    } else {
					System.out.println("Vehicle is already in parking lot. Cancelling operation.");
				    }
				} else {
				    System.out.println();
				    System.out.println("Invalid Car Type. Cancelling operation.");
				    System.out.println();
				} 
			    } catch (InputMismatchException e) {
				System.out.println("Invalid Input. Cancelling operation.");
			    }
			    break;
			case 2:
			    Map<Integer, String> bikeTypes = new HashMap<>();
			    bikeTypes.put(1, "Moped");
			    bikeTypes.put(2, "Dirt Bike");
			    bikeTypes.put(3, "Cruiser");
			    System.out.println("Enter 1 for Moped, 2 for Dirt Bike, or 3 for Cruiser.");
			    try {
				int bikeType = s.nextInt();
				s.nextLine();
				if (bikeTypes.containsKey(bikeType)) {
				    Motorbike m = new Motorbike(plateNumber, make, bikeTypes.get(bikeType), colour);
				    boolean added = addVehicle(m);
				    if (added) {
					System.out.println("Motorbike successfully added.");
				    } else {
					System.out.println("Vehicle is already in parking lot. Cancelling operation.");
				    }
				} else {
				    System.out.println();
				    System.out.println("Invalid Motorbike Type. Cancelling operation.");
				    System.out.println();
				} 
			    } catch (InputMismatchException e) {
				System.out.println("Invalid Input. Cancelling operation.");
			    }
			    break;	
		    }
		    System.out.println();
		    break;

		case 2:
		    s.nextLine();
		    System.out.println("Enter the plate number of the vehicle");
		    String plateToDelete = s.nextLine();
		    boolean deleted = deleteVehicle(plateToDelete);
		    if (deleted) {
			System.out.println("Vehicle with plate " + plateToDelete + " successfully deleted from system.");
		    } else {
			System.out.println("Vehicle with plate " + plateToDelete + " does not exist in system.");
		    }
		    System.out.println();
		    break;

		case 3:
		    printVehicles();
		    System.out.println();
		    break;

		case 4:
		    System.out.println("Saving...");
		    try {
			boolean saved = saveVehicleList();
			if (saved) {
			    System.out.println("Vehicle list saved.");
			} else {
			    System.out.println("Rental parking lot is empty. Nothing to save.");
			}
		    } catch (IOException ex) {
			System.out.println("Error saving Vehicle list. Try Again.");
		    }
			System.out.println();
		    break;

		case 5:
		    launchGUI();
		    break;

		case 6:
		    exit = true;
		    break;
	    }
	} catch (InputMismatchException e) {
	    System.out.println();
	    System.out.println("Please enter valid input.");
	    System.out.println();
	}
	return exit;
    }
    
    private class RentalGUI extends JFrame {

	private JComboBox picker;
	private JTextField value;
	private JButton filterBtn;
	private VehicleTableModel tableModel;
	private JTable table;
	private TableRowSorter<TableModel> rowSorter;

	private JTextField startDay;
	private JTextField startMonth;
	private JTextField startYr;
	private JTextField endDay;
	private JTextField endMonth;
	private JTextField endYr;
	private JButton check;


	public RentalGUI(ArrayList<Vehicle> list){
	    setLayout(new BorderLayout(5, 10));
	    this.add(createFilterPanel(), BorderLayout.WEST);
	    this.add(createTablePanel(list), BorderLayout.CENTER);
	    this.add(createAvailabilityPanel(), BorderLayout.EAST);
	}
	
	/* 
	creates the panel on the left side, for list filtering
	*/
	public JPanel createFilterPanel() {
	    JPanel filter = new JPanel();
	    filter.setBackground(Color.BLUE);
	    filter.setLayout(new GridLayout(8, 2, 0, 10));
	    JLabel title = new JLabel("Filter Vehicle List");
	    title.setForeground(Color.WHITE);
	    JLabel placeholder = new JLabel("");
	    filter.add(title);
	    filter.add(placeholder);

	    JLabel param = new JLabel("Pick Filter Parameter");
	    param.setForeground(Color.WHITE);
	    this.picker = new JComboBox(new String[]{"Select", "Type", "Make", "Colour"});
	    JLabel placeholder2 = new JLabel("");
	    JLabel valueLbl = new JLabel("Enter Value:");
	    valueLbl.setForeground(Color.WHITE);
	    this.value = new JTextField();

	    this.filterBtn = new JButton("Filter Vehicles");
	    FilterHandler filterHandle = new FilterHandler();
	    this.filterBtn.addActionListener(filterHandle);

	    filter.add(param);
	    filter.add(this.picker);
	    filter.add(placeholder2);
	    filter.add(valueLbl);
	    filter.add(this.value);
	    filter.add(this.filterBtn);

	    return filter;
	}

	/* 
	creates the panel in the middle, representing the vehicle list table
	*/
	public JPanel createTablePanel(ArrayList<Vehicle> list) {
	    JPanel tbl = new JPanel();
	    tbl.setBackground(Color.BLUE);
	    tbl.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
	    JLabel title = new JLabel("Vehicle List");
	    title.setForeground(Color.WHITE);
	    tbl.add(title);

	    this.tableModel = new VehicleTableModel(list);
	    this.table = new JTable(this.tableModel);
	    this.rowSorter = new TableRowSorter<>(this.table.getModel());
	    this.table.setRowSorter(this.rowSorter);

	    table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer(){
		Color originalColor = null;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		    Component renderer;
		    renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		    if (value == "Car") {
			renderer.setBackground(Color.GREEN);
		    } else {
			renderer.setBackground(Color.CYAN);
		    }
		    return renderer;
		}
	    });
	    JScrollPane scrollPane = new JScrollPane(table);
	    tbl.add(scrollPane);

	    return tbl;
	}
	
	/* 
	creates the panel on the right side, for checking availability and booking
	*/
	public JPanel createAvailabilityPanel() {
	    JPanel avail = new JPanel();
	    avail.setBackground(Color.BLUE);
	    avail.setLayout(new GridLayout(8, 2, 0, 10));
	    JLabel title = new JLabel("Check Availability");
	    title.setForeground(Color.WHITE);
	    avail.add(title);
	    avail.add(new JLabel());

	    this.startDay = new JTextField(2);
	    JLabel startDayLbl = new JLabel("Start day: (dd)");
	    startDayLbl.setForeground(Color.WHITE);

	    this.startMonth = new JTextField(2);
	    JLabel startMonthLbl = new JLabel("Start month: (mm)");
	    startMonthLbl.setForeground(Color.WHITE);

	    this.startYr = new JTextField(4);
	    JLabel startYrLbl = new JLabel("Start year: (yyyy)");
	    startYrLbl.setForeground(Color.WHITE);

	    this.endDay = new JTextField(2);
	    JLabel endDayLbl = new JLabel("End day: (dd)");
	    endDayLbl.setForeground(Color.WHITE);

	    this.endMonth = new JTextField(2);
	    JLabel endMonthLbl = new JLabel("End month: (mm)");
	    endMonthLbl.setForeground(Color.WHITE);

	    this.endYr = new JTextField(4);
	    JLabel endYrLbl = new JLabel("End year: (yyyy)");
	    endYrLbl.setForeground(Color.WHITE);

	    this.check = new JButton("Check Availability");
	    RentalHandler rentalHandle = new RentalHandler();
	    this.check.addActionListener(rentalHandle);

	    avail.add(startDayLbl);
	    avail.add(this.startDay);
	    avail.add(startMonthLbl);
	    avail.add(this.startMonth);
	    avail.add(startYrLbl);
	    avail.add(this.startYr);
	    avail.add(endDayLbl);
	    avail.add(this.endDay);
	    avail.add(endMonthLbl);
	    avail.add(this.endMonth);
	    avail.add(endYrLbl);
	    avail.add(this.endYr);
	    avail.add(check);

	    return avail;
	}

	private class FilterHandler implements ActionListener {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (picker.getSelectedItem() == "Type") {
		    rowSorter.setRowFilter(RowFilter.regexFilter(value.getText(), 0));
		} else if (picker.getSelectedItem() == "Make") {
		    rowSorter.setRowFilter(RowFilter.regexFilter(value.getText(), 2));
		} else if (picker.getSelectedItem() == "Colour") {
		    rowSorter.setRowFilter(RowFilter.regexFilter(value.getText(), 4));
		}
	    }

	}

	private class RentalHandler implements ActionListener {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		try {
		    int startD = Integer.parseInt(startDay.getText());
		    int startM = Integer.parseInt(startMonth.getText());
		    int startY = Integer.parseInt(startYr.getText());
		    int endD = Integer.parseInt(endDay.getText());
		    int endM = Integer.parseInt(endMonth.getText());
		    int endY = Integer.parseInt(endYr.getText());
		    if (Date.couldBeValid(startD, startM, startY) && Date.couldBeValid(endD, endM, endY)) {
			int row = table.getSelectedRow();
			if (row == -1) {
			    JOptionPane.showMessageDialog(null, "Please select a vehicle");
			    return;
			}
			String value = table.getModel().getValueAt(row, 1).toString();
			Date start = new Date(startD, startM, startY);
			Date end = new Date(endD, endM, endY);
			boolean available = checkAvailability(value, start, end);
			if (available) {
			    String message = "Vehicle with plate " + value + " is available between " + start.getDate() + " and " + end.getDate() + ". Would you like to book it?";
			    int book = JOptionPane.showConfirmDialog(null, message, "Book Vehicle", 0);
			    if (book == 0) {
				boolean booked = bookVehicle(value, start, end);
				if (booked) {
				    JOptionPane.showMessageDialog(null, "Wonderful! Vehicle with plate " + value + " has been booked between " + start.getDate() + " and " + end.getDate() + ".");
				} else {
				    JOptionPane.showMessageDialog(null, "Unable to book vehicle with plate " + value + " between " + start.getDate() + " and " + end.getDate() + ".");
				}
			    }	
			} else {
			    JOptionPane.showMessageDialog(null, "Sorry, vehicle with plate " + value + " is not available between " + start.getDate() + " and " + end.getDate() + ".");
			}

		    } else {
			JOptionPane.showMessageDialog(null, "At least one date is invalid. Make sure your dates are both valid.");
		    }
		} catch (NumberFormatException nfe) {
		    JOptionPane.showMessageDialog(null, "Not all your numbers are valid. Please enter valid numbers.");
		}
	    }
	}
    }
}
