package com.java.pointwest.beans;
import java.util.ArrayList;
import java.util.List;

public class Employee {
	private int employeeID;
	private String firstName;
	private String lastName;
	private String password;
	private String username;
	private String role;
	private String shift;
	private List<String> projects;
	private List<Seat> seatsLoc;
	
	//constructor for one time setters
	public Employee(int empID, String fName, String lName, String uName) {
		employeeID = empID;
		firstName = fName;
		lastName = lName;
		username = uName;
		projects = new ArrayList<String>();
		seatsLoc = new ArrayList<Seat>();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public List<String> getProjects() {
		return projects;
	}
	
	public int getNumberOfProjects() {
		return projects.size();
	}

	public void addProjects(String projectAlias) {
		this.projects.add(projectAlias);
	}

	public List<Seat> getSeats() {
		return seatsLoc;
	}
	
	public int getNumberOfSeats() {
		return seatsLoc.size();
	}

	public void setSeatLoc(Seat seatLoc) {
		seatsLoc.add(seatLoc);
	}
	
	public boolean doesEmployeeHaveSeat() {
		return seatsLoc.size() > 0;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}
}