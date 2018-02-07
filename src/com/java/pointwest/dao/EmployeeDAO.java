package com.java.pointwest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.java.pointwest.beans.Employee;
import com.java.pointwest.beans.Seat;
import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.util.DAOUtil;
import com.java.pointwest.util.EmployeeQueries;

public class EmployeeDAO {

	static Logger myLogger = Logger.getLogger(EmployeeDAO.class);
	
	public Employee employeeLogin(String username, String password) throws DBException {
		myLogger.info("START");

		Employee emp = null;
		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DAOUtil.connect();
			myLogger.info("Query getting employee from username and password");
			preparedStatement = connection.prepareStatement(EmployeeQueries.EMPLOYEE_LOGIN);
			
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int empID = resultSet.getInt("emp_id");
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				String role = resultSet.getString("role");
				
				emp = new Employee(empID, fName ,lName, username);
				emp.setRole(role);
				myLogger.info("Created new employee!");
				myLogger.info("Employee ID: " + emp.getEmployeeID());
				myLogger.info("Employee First Name: " + emp.getFirstName());
				myLogger.info("Employee Last Name: " + emp.getLastName());
				myLogger.info("Employee Role: " + emp.getRole());
			} else {
				myLogger.info("Employee object null.");
			}
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return emp;
	}
	
	public List<Employee> getAllEmployees() throws DBException{
		myLogger.info("START");
		
		List<Employee> empList = new ArrayList<Employee>();
		myLogger.info("ArrayList of Employees created: " + empList);
		Connection connection = null;
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = DAOUtil.connect();
			statement = connection.createStatement();
			myLogger.info("Query getting all employees");
			myLogger.info("Statement to execute: " + statement);
			resultSet = statement.executeQuery(EmployeeQueries.GET_ALL_EMPLOYEES);
			
			populateEmployees(resultSet, empList);
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, statement);
		}
		myLogger.info("END");
		return empList;
	}
	
	public List<Employee> searchEmployeeByName(String fNameSearch, String lNameSearch) throws DBException {
		myLogger.info("START");

		List<Employee> empList = new ArrayList<Employee>();
		myLogger.info("ArrayList of Employees created: " + empList);
		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DAOUtil.connect();
			myLogger.info("Query using full name");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_FULL_NAME);
			
			preparedStatement.setString(1, "%" + fNameSearch + "%");
			preparedStatement.setString(2, "%" + lNameSearch + "%");
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			populateEmployees(resultSet, empList);
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return empList;
	}
	
	public List<Employee> searchEmployeeByName(String name) throws DBException{
		myLogger.info("START");

		List<Employee> empList = new ArrayList<Employee>();
		myLogger.info("ArrayList of Employees created: " + empList);
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			// query through first name
			myLogger.info("Query using first name");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_FIRST_NAME);
			
			preparedStatement.setString(1, "%" + name + "%");
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			populateEmployees(resultSet, empList);
			preparedStatement.close();
			resultSet.close();
			
			//query through last name
			myLogger.info("Query using last name");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_LAST_NAME);
			
			preparedStatement.setString(1, "%" + name + "%");
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			populateEmployees(resultSet, empList);
			
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return empList;
	}
	
	public List<Employee> searchEmployeeByID(int empID) throws DBException{
		myLogger.info("START");

		List<Employee> empList = new ArrayList<Employee>();
		myLogger.info("ArrayList of Employees created: " + empList);
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get employees by employee ID");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_ID);
			
			preparedStatement.setString(1, "%" + empID + "%");
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			populateEmployees(resultSet, empList);
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return empList;
	}
	
	public List<Employee> searchEmployeeByProject(String projName) throws DBException{
		myLogger.info("START");

		List<Employee> empList = new ArrayList<Employee>();
		myLogger.info("ArrayList of Employees created: " + empList);
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query using project alias");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_PROJECT_ALIAS);
			
			preparedStatement.setString(1, "%" + projName + "%");
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			populateEmployees(resultSet, empList);
					
			//reset PS and RS then query through project name
			preparedStatement.close();
			resultSet.close();

			myLogger.info("Query using project name");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_PROJECT_NAME);

			preparedStatement.setString(1, "%" + projName + "%");
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			populateEmployees(resultSet, empList);
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return empList;
	}
	
	// creates employee objects
	// creates seat objects and set it to the employee
	public List<Employee> getSeatedEmployees() throws DBException{
		myLogger.info("START");

		List<Employee> empList = null;
		Connection connection = DAOUtil.connect();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			//resultSet = statement.executeQuery(querySeatedEmployees);
			resultSet = statement.executeQuery(EmployeeQueries.GET_EMPLOYEE_WITH_SEATS);
			
			int previousEmpID = 0;
			empList = new ArrayList<Employee>();
			Employee createdEmployee = null;
			while(resultSet.next()) {
				int empID = resultSet.getInt("emp_id");
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				String username = resultSet.getString("username");
				String role = resultSet.getString("role");
				String shift = resultSet.getString("shift");
				
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");
				
				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				if (empID == previousEmpID) {
					createdEmployee.setSeatLoc(s);
				} else {
					previousEmpID = empID;
					createdEmployee = new Employee(empID, fName ,lName, username);
					createdEmployee.setRole(role);
					createdEmployee.setShift(shift);
					createdEmployee.setSeatLoc(s);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, statement);
		}
		myLogger.info("END");
		return empList;
	}
	
	private void populateEmployees(ResultSet resultSet, List<Employee> eList) {
		myLogger.info("START");
		try {
			while(resultSet.next()) {
				int empID = resultSet.getInt("emp_id");
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				String username = resultSet.getString("username");
				String role = resultSet.getString("role");
				String shift = resultSet.getString("shift");
				
				Employee emp = new Employee(empID, fName ,lName, username);
				emp.setRole(role);
				emp.setShift(shift);
				myLogger.info("Created new employee!");
				myLogger.info("Employee ID: " + emp.getEmployeeID());
				myLogger.info("Employee First Name: " + emp.getFirstName());
				myLogger.info("Employee Last Name: " + emp.getLastName());
				myLogger.info("Employee Role: " + emp.getRole());
				myLogger.info("Employee Role: " + emp.getShift());
				
				eList.add(emp);
				myLogger.info("Employee added to list " + eList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myLogger.info("END");
	}

	
	// returns one employee object
	public Employee searchEmployeeBySeat(String location, String floor, String quadrant, String row, String col) throws DBException {
		// TODO Auto-generated method stub
		myLogger.info("START");

		Employee emp = null;
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get employee using seat location");
			preparedStatement = connection.prepareStatement(EmployeeQueries.GET_EMPLOYEE_BY_SEAT);
			
			preparedStatement.setString(1, location);
			preparedStatement.setString(2, floor);
			preparedStatement.setString(3, quadrant);
			preparedStatement.setString(4, row);
			preparedStatement.setString(5, col);
			
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			// assume one row returned
			// because no two employees can occupy the same seat
			if(resultSet.next()) {
				int empID = resultSet.getInt("emp_id");
				String fName = resultSet.getString("first_name");
				String lName = resultSet.getString("last_name");
				String username = resultSet.getString("username");
				
				emp = new Employee(empID, fName ,lName, username);
				myLogger.info("Created new employee!");
				myLogger.info("Employee ID: " + emp.getEmployeeID());
				myLogger.info("Employee First Name: " + emp.getFirstName());
				myLogger.info("Employee Last Name: " + emp.getLastName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return emp;
	}
}
