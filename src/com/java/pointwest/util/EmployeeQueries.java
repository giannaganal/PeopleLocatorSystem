package com.java.pointwest.util;

public interface EmployeeQueries {
	String EMPLOYEE_LOGIN = "SELECT * FROM plsdb.employee "
			+ "WHERE employee.username = ? "
			//+ "WHERE employee.userame = ? "
			+ "AND employee.password = ?";
	
	String GET_ALL_EMPLOYEES = "SELECT * FROM plsdb.employee";
	
	String GET_EMPLOYEE_BY_FULL_NAME = "SELECT * FROM plsdb.employee "
			+ "WHERE employee.first_name like ? And employee.last_name like ? "
			+ "ORDER BY employee.emp_id ASC";
	
	String GET_EMPLOYEE_BY_FIRST_NAME = "SELECT * FROM plsdb.employee "
			+ "WHERE employee.first_name like ? "
			+ "ORDER BY employee.emp_id ASC";
	
	String GET_EMPLOYEE_BY_LAST_NAME = "SELECT * FROM plsdb.employee "
			+ "WHERE employee.last_name like ? "
			+ "ORDER BY employee.emp_id ASC";
	
	String GET_EMPLOYEE_BY_ID = "SELECT * FROM plsdb.employee "
			+ "WHERE employee.emp_id like ?"
			+ "ORDER BY employee.emp_id ASC";
	
	String GET_EMPLOYEE_BY_PROJECT_ALIAS = "SELECT employee.emp_id, employee.first_name, employee.last_name, "
			+ "employee.role, employee.shift, employee.username, employee_project.proj_alias "
			+ "FROM plsdb.employee "
			+ "JOIN employee_project on employee.emp_id = employee_project.employee_id "
			+ "WHERE employee_project.proj_alias not in ('BogusProject') "
			+ "And employee_project.proj_alias like ? "
			+ "ORDER BY employee.emp_id ASC";
	
	String GET_EMPLOYEE_BY_PROJECT_NAME = "SELECT employee.emp_id, employee.first_name, employee.last_name, "
			+ "employee.role, employee.shift, employee.username, employee_project.proj_alias "
			+ "FROM plsdb.employee "
			+ "JOIN employee_project on employee.emp_id = employee_project.employee_id "
			+ "JOIN project ON project.proj_alias = employee_project.proj_alias "
			+ "WHERE employee_project.proj_alias not in ('BogusProject') "
			+ "AND project.proj_name like ? "
			+ "ORDER BY employee.emp_id ASC";
	
	String GET_EMPLOYEE_WITH_SEATS = "SELECT employee_seat.emp_id, employee.first_name, employee.last_name, employee.username, "
			+ "employee.role, employee.shift, seat.bldg_id, seat.floor_number, "
			+ "seat.quadrant, seat.row_number, seat.column_number, seat.local_number "
			+ "FROM plsdb.employee_seat "
			+ "JOIN seat ON employee_seat.seat_id = seat.seat_id "
			+ "ORDER BY employee_seat.emp_id ASC";

	String GET_EMPLOYEE_BY_SEAT = "SELECT employee_seat.emp_id, employee.first_name, employee.last_name, employee.username " +
			"FROM plsdb.employee_seat " + 
			"JOIN seat ON employee_seat.seat_id = seat.seat_id " +
			"JOIN employee ON employee_seat.emp_id = employee.emp_id " + 
			"WHERE seat.bldg_id = ? " + 
			"AND seat.floor_number = ? " + 
			"AND seat.quadrant = ? " + 
			"AND seat.row_number = ? " + 
			"AND seat.column_number = ? " + 
			"ORDER BY employee_seat.emp_id ASC";
}
