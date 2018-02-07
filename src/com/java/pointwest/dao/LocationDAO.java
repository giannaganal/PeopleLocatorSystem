package com.java.pointwest.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.util.DAOUtil;
import com.java.pointwest.util.LocationQueries;

public class LocationDAO {

	static Logger myLogger = Logger.getLogger(LocationDAO.class);
	
	public Map<String, String> retrieveAllLocations() throws DBException{
		myLogger.info("START");

		Map<String, String> locationList = null;
		Connection connection = DAOUtil.connect();
				
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			
			myLogger.info("statement to execute: " + statement);
			resultSet = statement.executeQuery(LocationQueries.GET_LOCATIONS);
			
			locationList = new HashMap<String, String>();
			myLogger.info("HashMap of locations created: " + locationList);
			
			while (resultSet.next()) {
				String bldgName = resultSet.getString("bldg_id");
				String officeLocation = resultSet.getString("bldg_address");
				myLogger.info("location found! " + bldgName + ", " + officeLocation);
				
				locationList.put(bldgName, officeLocation);
				myLogger.info("location adding to map...");
			}
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException e) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, statement);
		}
		myLogger.info("END");
		return locationList;
	}
}
