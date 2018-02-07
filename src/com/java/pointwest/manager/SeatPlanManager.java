package com.java.pointwest.manager;

import org.apache.log4j.Logger;

import com.java.pointwest.beans.SeatPlan;
import com.java.pointwest.dao.SeatDAO;
import com.java.pointwest.exceptions.DBException;

public class SeatPlanManager {

	static Logger myLogger = Logger.getLogger(SeatPlanManager.class);
	
	public SeatPlan searchSeatPlanByLocationAndFloor(String location, int floor) throws DBException {
		myLogger.info("START");
		SeatDAO seatDAO = new SeatDAO();
		
		SeatPlan sp = seatDAO.searchSeatPlanByLocationFloorLevel(location, floor); 
		myLogger.info("SeatPlan to return: " + sp);
		myLogger.info("END");
		return sp;
	}
	
	public SeatPlan searchSeatPlanByQuadrant(String location, int floor, String quadrant) throws DBException {
		myLogger.info("START");
		SeatDAO seatDAO = new SeatDAO();
		
		SeatPlan sp = seatDAO.searchSeatsByQuadrant(location, floor, quadrant);
		myLogger.info("SeatPlan to return: " + sp);
		myLogger.info("END");
		return sp;
	}
}
