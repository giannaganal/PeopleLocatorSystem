package com.java.pointwest.main;

import org.apache.log4j.Logger;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.dao.EmployeeDAO;
import com.java.pointwest.exceptions.ExpectedIntException;
import com.java.pointwest.ui.EmployeeLoginUI;
import com.java.pointwest.ui.HomePageUI;
import com.java.pointwest.ui.SearchEmployeeUI;
import com.java.pointwest.ui.SearchSeatPlanUI;

public class Main {

	static Logger myLogger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) throws ExpectedIntException {
		// TODO Auto-generated method stub
		//Employee e = new Employee(123, "firstname", "lastname", "firstname.lastname");
		//e.setRole("employee");
		
		int chosenItem = 0;
		boolean exitFlag = false;
		EmployeeLoginUI loginPage = new EmployeeLoginUI();
		HomePageUI homePage = new HomePageUI();
		
		//perform login first
		Employee loggedInEmp = loginPage.displayLoginPage();
		do {
			// display home page and get selected item
			chosenItem = homePage.displayHomePage(loggedInEmp);
			myLogger.info("User chose " + chosenItem + " in main menu.");
			
			switch(chosenItem) {
			case 1:
				searchEmployee();
				break;
			case 2:
				searchSeatPlan();
				break;
			case 3:
				exitFlag = true;
				break;
			default:
				exitFlag = true;
			}
		} while (!exitFlag);
		loginPage.displayLogoutPage();
	}

	private static void searchSeatPlan() throws ExpectedIntException {
		// TODO Auto-generated method stub
		SearchSeatPlanUI searchSP = new SearchSeatPlanUI();
		
		boolean pageExit = false;
		int chosenItem;
		do {
			chosenItem = searchSP.displaySearchSeatPlanMenu();
			switch(chosenItem) {
			case 1:
				searchSP.searchSeatPlanByLocationAndFloor();
				break;
			case 2:
				searchSP.searchSeatPlanByQuadrant();
				break;
			}
			
			// choose which menu to go back to
			chosenItem = searchSP.displayActionsPage();
			switch (chosenItem) {
			case 1:
				// proceed
				break;
			case 2:
				pageExit = true;
				break;
			}
		} while (!pageExit);
	}

	private static void searchEmployee() throws ExpectedIntException {
		// TODO Auto-generated method stub
		SearchEmployeeUI searchEmpPage = new SearchEmployeeUI();
		
		boolean pageExit = false;
		int chosenItem;
		do {
			// choose what kind of search will be performed
			chosenItem = searchEmpPage.displaySearchEmployeeMenu();
			switch(chosenItem) {
			case 1:
				searchEmpPage.searchEmpByIDPage();
				break;
			case 2:
				searchEmpPage.searchEmpByNamePage();
				break;
			case 3:
				searchEmpPage.searchEmpByProjectPage();
				break;
			}
			
			// choose which menu to go back to
			chosenItem = searchEmpPage.displayActionsPage();
			switch(chosenItem) {
			case 1:
				//proceed
				break;
			case 2:
				pageExit = true;
				break;
			}
		} while(!pageExit);
	}
}