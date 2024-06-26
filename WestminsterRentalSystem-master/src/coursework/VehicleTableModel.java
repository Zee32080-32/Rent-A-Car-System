/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;

import java.util.*;
import javax.swing.table.AbstractTableModel;


public class VehicleTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Vehicle Type", "Plate Number", "Make", "Subtype", "Colour"};
    private final ArrayList<Vehicle> vehicles;
    
    public VehicleTableModel(ArrayList<Vehicle> list) {
	vehicles = list;
    }

    @Override
    public int getRowCount() {
	return vehicles.size();
    }

    @Override
    public int getColumnCount() {
	return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
	Object value = null;
	switch (columnIndex) {
	    case 0:
		value = vehicles.get(rowIndex).getVehicleType();
		break;
	    case 1:
		value = vehicles.get(rowIndex).getPlateNumber();
		break;
	    case 2:
		value = vehicles.get(rowIndex).getMake();
		break;
	    case 3:
		value = vehicles.get(rowIndex).getType();
		break;
	    case 4:
		value = vehicles.get(rowIndex).getColour();
		break;
	    default:
		break;
	}
	return value;
    }
    
    @Override
    public String getColumnName(int col) {
	return columnNames[col];
    }
    
}
