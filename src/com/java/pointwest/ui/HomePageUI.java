package com.java.pointwest.ui;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.exceptions.ExpectedIntException;
import com.java.pointwest.util.UIUtil;

public class HomePageUI {
	
	public int displayHomePage(Employee loggedInEmp){
		int userInput = 0;
		if (loggedInEmp == null) {
			System.out.println("Employee not found.");
		} else {
			//get employee information
			String firstName = loggedInEmp.getFirstName();
			String lastName = loggedInEmp.getLastName();
			String role = loggedInEmp.getRole();
			
			UIUtil.displayHeader("Home");
			System.out.println("Welcome " + firstName + " " + lastName + " [" + role + "]!");

			UIUtil.displayMenuHeader();
			UIUtil.displayMenuItem(1, "Search Employee");
			UIUtil.displayMenuItem(2, "View Seatplan");
			UIUtil.displayMenuItem(3, "Logout");

			boolean wrongInput = true;
			do {
				try {
					userInput = UIUtil.getUserMenuItem(1, 3);
					wrongInput = false;
				} catch (ExpectedIntException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getDisplayErrorMessage());
				}
			} while (wrongInput);
		}
		return userInput;
	}
	
	
}
