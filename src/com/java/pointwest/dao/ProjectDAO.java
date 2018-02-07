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
import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.util.DAOUtil;

public class ProjectDAO {

	static Logger myLogger = Logger.getLogger(ProjectDAO.class);
	
	//pass an Employee object and populate the list of projects there
	// OPTION: pass a Project object and populate there instead
	public void setProjectsForEmployee(Employee e) throws DBException {
		myLogger.info("START");

		int empIDReference = e.getEmployeeID();
		Connection connection = DAOUtil.connect();
		String querySearchProject = "SELECT employee_project.employee_id, employee_project.proj_alias, project.proj_name "
				+ "FROM plsdb.employee_project "
				+ "JOIN project ON project.proj_alias = employee_project.proj_alias "
				+ "WHERE employee_project.proj_alias not in ('BogusProject') "
				+ "AND employee_project.employee_id = ? "
				+ "ORDER BY employee_project.proj_alias ASC";
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query for searching project using employee ID");
			preparedStatement = connection.prepareStatement(querySearchProject);
			
			preparedStatement.setInt(1, empIDReference);
			myLogger.info("PreparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				String projAlias = resultSet.getString("proj_alias");
				myLogger.info("project found! " + projAlias);
				e.addProjects(projAlias);
				myLogger.info("Adding project to employee " + e);
			}
			/*
			// get first row for project information
			if (resultSet.next()) {
				int empID = resultSet.getInt("emp_id");
				String projAlias = resultSet.getString("proj_alias");
				String projName = resultSet.getString("proj_name");
				
				project = new Project(projName, projAlias);
				project.addTeamMember(empID);
			}
			
			// loop to get employees
			while(resultSet.next()) {
				project.addTeamMember(resultSet.getInt("emp_id"));
			}*/
		} catch (SQLException sqlEx) {
			myLogger.error("SQL Exception Error");
			myLogger.error(sqlEx);
		} catch (NullPointerException nullEx) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		//return project;
	}
	
	//retrieves list of string project aliases
	// OPTION: retrieve list of projects instead, but not populated with employees
	public List<String> getAllProjects() throws DBException {
		myLogger.info("START");

		List<String> projectList = new ArrayList<String>();
		myLogger.info("ArrayList of projects created " + projectList);
		Connection connection = DAOUtil.connect();
		String queryAllProjects = "SELECT * from plsdb.project where project.proj_alias not in ('BogusProject')";
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			myLogger.info("statement to execute: " + statement);
			resultSet = statement.executeQuery(queryAllProjects);
			
			while (resultSet.next()) {
				String projAlias = resultSet.getString("proj_alias");
				myLogger.info("project found! " + projAlias);
				/*String projName = resultSet.getString("proj_name");
				
				projectList = new Project(projName, projAlias);
				projectList.addTeamMember(empID);*/
				
				projectList.add(projAlias);
				myLogger.info("Adding project to arraylist " + projectList);
			}
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException nullEx) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, statement);
		}
		myLogger.info("END");
		return projectList;
	}
}
