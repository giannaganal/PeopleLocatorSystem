package com.java.pointwest.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.beans.Seat;
import com.java.pointwest.beans.SeatPlan;
import com.java.pointwest.dao.EmployeeDAO;
import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.exceptions.ExpectedIntException;
import com.java.pointwest.manager.EmployeeManager;
import com.java.pointwest.manager.LocationManager;
import com.java.pointwest.manager.SeatPlanManager;
import com.java.pointwest.util.UIUtil;

public class SearchSeatPlanUI {
	
	public int displaySearchSeatPlanMenu(){
		UIUtil.displayHeader("View Seatplan");
		
		UIUtil.displayMenuHeader();
		UIUtil.displayMenuItem(1, "By Location - Floor Level");
		UIUtil.displayMenuItem(2, "By Quadrant");
		
		int userInput = 0;
		boolean wrongInput = true;
		do {
			try {
				userInput = UIUtil.getUserMenuItem(1, 2);
				wrongInput = false;
			} catch (ExpectedIntException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getDisplayErrorMessage());
			}	
		} while (wrongInput);
		
		return userInput;
	}
	
	public void searchSeatPlanByLocationAndFloor(){
		UIUtil.displayHeader("View Seatplan - By Location - Floor Level");

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter location: ");
		String location = sc.next();
		
		System.out.println("Enter Floor Level: ");
		boolean wrongInput = true;
		do {
			try {
				int floor = sc.nextInt();
				
				SeatPlanManager seatPlanManager = new SeatPlanManager();
				SeatPlan retrievedSP = seatPlanManager.searchSeatPlanByLocationAndFloor(location, floor);
				
				displaySeatPlanMap(retrievedSP);
				wrongInput = false;
			} catch (InputMismatchException parseErrorEx) {
				System.out.println((new ExpectedIntException()).getDisplayErrorMessage());

			} catch (NullPointerException nullEx) {
				System.out.println("No seat plan found.");
				wrongInput = false;
			} catch (DBException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getErrorMessage("searching seat plan by location and floor"));
			}	
		} while (wrongInput);
		
	}
	
	public void searchSeatPlanByQuadrant() {
		// TODO Auto-generated method stub
		UIUtil.displayHeader("View Seatplan - By Location - Floor Level");

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter location: ");
		String location = sc.next();
		
		System.out.println("Enter Floor Level: ");
		
		boolean wrongInput = true;
		do {
			try {
				int floor = sc.nextInt();
				
				System.out.println("Enter Quadrant: ");
				String quadrant = sc.next();

				SeatPlanManager seatPlanManager = new SeatPlanManager();
				SeatPlan retrievedSP = seatPlanManager.searchSeatPlanByQuadrant(location, floor, quadrant);

				displaySeatPlanMap(retrievedSP);
				wrongInput = false;
			} catch (InputMismatchException parseErrorEx) {
				System.out.println((new ExpectedIntException()).getDisplayErrorMessage());
				
			} catch (NullPointerException nullEx) {
				System.out.println("No seat plan found.");
				wrongInput = false;
			} catch (DBException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getErrorMessage("searching seat plan by quadrant"));
			}	
		} while (wrongInput);
	}

	private void displaySeatPlanMap(SeatPlan retrievedSP) {
		// TODO Auto-generated method stub
		UIUtil.displayHeader("View SeatPlan");

		String officeLocation = retrievedSP.getOfficeLocation();
		LocationManager locManager = new LocationManager();
		Map<String, String> listOfLocations;
		try {
			listOfLocations = locManager.getAllLocations();
			
			if (listOfLocations.containsKey(officeLocation)) {
				officeLocation = officeLocation + "[" + listOfLocations.get(officeLocation) + "]";
			}
			
			System.out.println("LOCATION: " + officeLocation +", FLOOR: " + retrievedSP.getFloor());
			UIUtil.displayHorizontalBar(80);
			
			List<Seat> seatList = retrievedSP.getSeats();
			String seatNameString = "";
			String seatEmployeeString = "";
			String seatLocalNumString = "";
			int seatCounter = 0;
			
			for (Seat s: seatList) {
				//prepare seatName row
				seatNameString = seatNameString + s.getSeatName() + "\t\t";
				
				//prepare seated employee name
				EmployeeManager empManager = new EmployeeManager();
				Employee e = null;
				try {
					e = empManager.searchEmployeeBySeat(s);
					
					if (e != null) {
						seatEmployeeString = seatEmployeeString + e.getLastName() + ", " + e.getFirstName() + "\t";
					} else {
						seatEmployeeString = seatEmployeeString + "         " + "\t\t";
					}
					
					//prepare localNumber row
					int localNum = s.getLocalNum();
					if (localNum == 0) {
						seatLocalNumString = seatLocalNumString + "         \t\t";
					} else {
						seatLocalNumString = seatLocalNumString + "loc. " + localNum + "\t\t";
					}
					
					if (seatCounter == 3) {
						System.out.println(seatNameString);
						System.out.println(seatEmployeeString);
						System.out.println(seatLocalNumString);
						System.out.println();
						
						//reset everything
						seatNameString = "";
						seatEmployeeString = "";
						seatLocalNumString = "";
						seatCounter = 0;
					} else {
						seatCounter++;
					}
				} catch (DBException e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.getErrorMessage("searching employee by seat"));
				}
			}
			System.out.println("-------------------------------end of seatplan----------------------------------");
		} catch (DBException e2) {
			// TODO Auto-generated catch block
			System.out.println(e2.getErrorMessage("getting all locations"));
		}
	}
	
	public int displayActionsPage() {
		// TODO Auto-generated method stub
		UIUtil.displayActionsHeader();
		UIUtil.displayMenuItem(1, "View SeatPlan again");
		UIUtil.displayMenuItem(2, "Home");
		
		int userInput = 0;
		boolean wrongInput = true;
		do {
			try {
				userInput = UIUtil.getUserMenuItem(1, 2);
				wrongInput = false;
			} catch (ExpectedIntException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getDisplayErrorMessage());
			}	
		} while (wrongInput);
		
		return userInput;
	}

	
}
