package com.java.pointwest.manager;

import java.util.List;

import org.apache.log4j.Logger;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.beans.Seat;
import com.java.pointwest.dao.EmployeeDAO;
import com.java.pointwest.dao.ProjectDAO;
import com.java.pointwest.dao.SeatDAO;
import com.java.pointwest.exceptions.DBException;

public class EmployeeManager {

	static Logger myLogger = Logger.getLogger(EmployeeManager.class);
	
	public List<Employee> getAllEmployees() throws DBException{
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();
		
		// gets simple employee info
		myLogger.info("Getting all employees...");
		List<Employee> empList = empDAO.getAllEmployees();
		
		setProjectsAndSeats(empList);

		myLogger.info("END");
		return empList;
	}

	public Employee employeeLogin(String username, String password) throws DBException{
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();

		Employee loggedInEmp = empDAO.employeeLogin(username, password); 
		myLogger.info("logged in employee is: " + loggedInEmp);
		myLogger.info("END");
		return loggedInEmp;
	}
	
	// retrieves Employees with Seats only, no projects
	/*public List<Employee> getSeatedEmployees(){
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();
		
		List<Employee> empList = empDAO.getSeatedEmployees(); 
		myLogger.info("END");
		return empList;
	}*/

	public List<Employee> searchEmployeesByProject(String project) throws DBException {
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();

		myLogger.info("Searching employees per project...");
		List<Employee> empList = empDAO.searchEmployeeByProject(project);

		if (empList.size() > 0) {
			setProjectsAndSeats(empList);
		} else {
			empList = null;
		}
		
		myLogger.info("List of employees to return: " + empList);
		myLogger.info("END");
		return empList;
	}
	
	public List<Employee> searchEmployeesByID(int empID) throws DBException{
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();
		
		myLogger.info("Searching employees by ID...");
		List<Employee> empList = empDAO.searchEmployeeByID(empID);
		
		if (empList.size() > 0) {
			setProjectsAndSeats(empList);
		} else {
			empList = null;
		}
		
		myLogger.info("List of employees to return: " + empList);
		myLogger.info("END");
		return empList;
	}
	
	public List<Employee> searchEmployeesByName(String[] names) throws DBException{
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();
		List<Employee> empList = null;
		
		//gets simple employee info
		if (names.length == 1) {
			empList = empDAO.searchEmployeeByName(names[0]);
		} else {
			empList = empDAO.searchEmployeeByName(names[0], names[1]);
		}
		
		if (empList != null) {
			setProjectsAndSeats(empList);
		}		
		myLogger.info("List of employees to return: " + empList);
		myLogger.info("END");
		return empList;
	}

	public Employee searchEmployeeBySeat(Seat s) throws DBException {
		myLogger.info("START");
		EmployeeDAO empDAO = new EmployeeDAO();
		
		Employee emp = empDAO.searchEmployeeBySeat(s.getLocation(), s.getFloor(), s.getQuadrant(), s.getRowNum(), s.getColNum());
		myLogger.info("employee with seat " + s.getSeatName() + " is " + emp);
		
		myLogger.info("END");
		return emp;
	}
	
	private void setProjectsAndSeats(List<Employee> empList) throws DBException {
		ProjectDAO projDAO = new ProjectDAO();
		for (Employee emp : empList) {
			myLogger.info("Setting projects for employee " + emp.getUsername());
			projDAO.setProjectsForEmployee(emp);
		}

		// sets seats
		SeatDAO seatDAO = new SeatDAO();
		for (Employee emp : empList) {
			myLogger.info("Setting seats for employee " + emp.getUsername());
			seatDAO.setSeatForEmployee(emp);
		}
	}
}
