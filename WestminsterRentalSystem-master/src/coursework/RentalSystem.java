/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;


public class RentalSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        WestminsterRentalVehicleManager sys = new WestminsterRentalVehicleManager();
        boolean exit = false;
        
        while (!exit) {
            exit = sys.launchMenu();
        }
    }
    
}
