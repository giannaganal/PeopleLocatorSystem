package com.java.pointwest.ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.beans.Seat;
import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.exceptions.ExpectedIntException;
import com.java.pointwest.manager.EmployeeManager;
import com.java.pointwest.util.UIUtil;

public class SearchEmployeeUI {
	
	public int displaySearchEmployeeMenu() {
		UIUtil.displayHeader("Search");

		UIUtil.displayMenuHeader();
		UIUtil.displayMenuItem(1, "By Employee ID");
		UIUtil.displayMenuItem(2, "By Name");
		UIUtil.displayMenuItem(3, "By Project");
		
		int userInput = 0;
		boolean wrongInput = true;
		do {
			try {
				userInput = UIUtil.getUserMenuItem(1, 3);
				wrongInput = false;
			} catch (ExpectedIntException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getDisplayErrorMessage());
			}
		} while(wrongInput);
		return userInput;
	}
	
	public void searchEmpByIDPage() {
		UIUtil.displayHeader("Search - By Employee ID");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Employee ID: ");
		
		List<Employee> retrievedEmployees = null;
		boolean wrongInput = true;
		do {
			try {
				int empIDToSearch = sc.nextInt();

				EmployeeManager empManager = new EmployeeManager();
				retrievedEmployees = empManager.searchEmployeesByID(empIDToSearch);
				
				displayFoundEmployeesList(retrievedEmployees);
				wrongInput = false;
			} catch (InputMismatchException e) {
				// TODO Auto-generated catch block
				System.out.println((new ExpectedIntException()).getDisplayErrorMessage());
			} catch (DBException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getErrorMessage("Searching employees by employee ID"));
				break;
			}
		} while (wrongInput);
	}
	
	public void searchEmpByNamePage(){
		UIUtil.displayHeader("Search - By Name");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Name: ");
		String userInput = sc.nextLine();
		String[] nameSplit = userInput.split(" ");
		
		List<Employee> retrievedEmployees = null;
		
		EmployeeManager empManager = new EmployeeManager();
		try {
			retrievedEmployees = empManager.searchEmployeesByName(nameSplit);
			
			displayFoundEmployeesList(retrievedEmployees);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorMessage("Searching employees by name"));
		}
	}

	//can accept Alias and Name
	public void searchEmpByProjectPage(){
		UIUtil.displayHeader("Search - By Project");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Project Name/Alias: ");
		String userInput = sc.nextLine();
		
		List<Employee> retrievedEmployees = null;
		
		EmployeeManager empManager = new EmployeeManager();
		try {
			retrievedEmployees = empManager.searchEmployeesByProject(userInput);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorMessage("Searching employees by project"));
		}
		
		displayFoundEmployeesList(retrievedEmployees);
	}
	
	private void displayFoundEmployeesList(List<Employee> retrievedEmployees) {
		// TODO Auto-generated method stub
		UIUtil.displayHeader("Search Result - (" + retrievedEmployees.size() + ")");
		
		UIUtil.displayHorizontalBar(80);
		System.out.println("EMPLOYEE ID| FIRSTNAME | LASTNAME | SEAT | LOCAL | SHIFT | PROJECTS(S)");
		UIUtil.displayHorizontalBar(80);
		
		int empCounter = 0;
		for(Employee emp: retrievedEmployees) {
			//set values for output
			empCounter++;
			int empID = emp.getEmployeeID();
			String fName = emp.getFirstName();
			String lName = emp.getLastName();
			String shift = emp.getShift();
			List<Seat> seats = emp.getSeats();
			List<String> listOfProjects = emp.getProjects();
			
			//set projects string
			String projects = "";
			for(int i = 0; i < emp.getNumberOfProjects(); i++) {
				projects = projects + listOfProjects.get(i) + ", ";
			}
			
			// put duplicate employee rows for multiple seats
			for (int i = 0; i < emp.getNumberOfSeats(); i++) {
				Seat seat = seats.get(i);

				// set local number
				String localNumberDisplay = "";
				int localNum = seat.getLocalNum();
				if (seat.getLocalNum() == 0) {
					localNumberDisplay = "NONE";
				} else {
					localNumberDisplay = Integer.toString(localNum);
				}
				System.out.print("[" + empCounter + "] " + empID + " | " + fName + " | " + lName + " | "
						+ seat.getSeatName() + " | " + localNumberDisplay + " | " + shift + " | " + projects);
				System.out.println();
			}
		}
		System.out.println("-------------------------------end of result------------------------------------");
	}

	public int displayActionsPage(){
		UIUtil.displayActionsHeader();
		UIUtil.displayMenuItem(1, "Search Again");
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
