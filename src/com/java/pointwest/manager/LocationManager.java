package com.java.pointwest.manager;

import java.util.Map;

import org.apache.log4j.Logger;

import com.java.pointwest.dao.LocationDAO;
import com.java.pointwest.exceptions.DBException;

public class LocationManager {

	static Logger myLogger = Logger.getLogger(LocationManager.class);
	
	public Map<String, String> getAllLocations() throws DBException{
		myLogger.info("START");
		LocationDAO locDao = new LocationDAO();
		
		Map<String, String> mapOfLocations = locDao.retrieveAllLocations();
		
		myLogger.info("Map of locations to return: " + mapOfLocations);
		myLogger.info("END");
		return mapOfLocations;
	}
}
