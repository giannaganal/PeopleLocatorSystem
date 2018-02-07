package com.java.pointwest.ui;

import java.util.Scanner;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.manager.EmployeeManager;
import com.java.pointwest.util.UIUtil;

public class EmployeeLoginUI {
	
	// returns Employee object with ff details:
	// emp_id, first name, last name, username, role
	// returns null if employee does not exist
	public Employee displayLoginPage() {
		UIUtil.displayHeader("Login");
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Username: ");
		String uName = sc.next();
		
		System.out.println("Password: ");
		String pWord = sc.next();
				
		EmployeeManager empManager = new EmployeeManager();
		Employee loggedInEmp = null;
		try {
			loggedInEmp = empManager.employeeLogin(uName, pWord);
		} catch (DBException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getErrorMessage("employee log in"));
		}
		
		return loggedInEmp;
	}
	
	public void displayLogoutPage() {
		System.out.println("Logging out...");
		System.out.println("Thank you!");
	}
	
}
