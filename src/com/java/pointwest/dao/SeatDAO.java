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
import com.java.pointwest.beans.SeatPlan;
import com.java.pointwest.exceptions.DBException;
import com.java.pointwest.util.DAOUtil;
import com.java.pointwest.util.SeatQueries;

public class SeatDAO {

	static Logger myLogger = Logger.getLogger(SeatDAO.class);
	
	// retrieves list of Seats objects
	// all seats
	public List<Seat> getAllSeats() throws DBException {
		myLogger.info("START");

		List<Seat> seatList = null;
		Connection connection = DAOUtil.connect();
		
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get all seats");
			statement = connection.createStatement();
			
			myLogger.info("statement to execute: " + statement);
			resultSet = statement.executeQuery(SeatQueries.GET_ALL_SEATS);
			
			seatList = new ArrayList<Seat>();
			myLogger.info("ArrayList of Seat created: " + seatList);
			
			while (resultSet.next()) {
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");

				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				myLogger.info("Seat created! - " + s.getSeatName());
				
				seatList.add(s);
				myLogger.info("Adding seat to arraylist...");
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
		return seatList;
	}
	
	// retrieves list of Seats objects
	// searches by office location
	public SeatPlan searchSeatPlanByLocationFloorLevel(String location, int floor) throws DBException {
		myLogger.info("START");

		SeatPlan seatList = null;
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get seats by location and floor");
			preparedStatement = connection.prepareStatement(SeatQueries.GET_SEATS_BY_LOCATION_AND_FLOOR);
			
			preparedStatement.setString(1, location);
			preparedStatement.setInt(2, floor);
			
			myLogger.info("preparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();
			
			//separately get first row for setting seat plan info
			if (resultSet.next()) {
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");
				
				seatList = new SeatPlan(building, floorNum, quadrant);
				myLogger.info("SeatPlan created! - " + seatList);
				
				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				myLogger.info("Seat created! - " + s.getSeatName());
				
				seatList.addSeats(s);
				myLogger.info("Adding seat to seatplan...");
			}
			populateSeatPlan(resultSet, seatList);
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException nullEx) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return seatList;
	}
	
	//retrieves seats by quadrant
	public SeatPlan searchSeatsByQuadrant(String locationSearch, int floorSearch, String quadrantSearch) throws DBException {
		myLogger.info("START");

		SeatPlan seatList = null;
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get seats by location, floor and quadrant");
			preparedStatement = connection.prepareStatement(SeatQueries.GET_SEATS_BY_QUADRANT);
			
			preparedStatement.setString(1, locationSearch);
			preparedStatement.setInt(2, floorSearch);
			preparedStatement.setString(3, quadrantSearch);
			
			myLogger.info("preparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			//separately get first row for setting seat plan info
			if (resultSet.next()) {
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");
				
				seatList = new SeatPlan(building, floorNum, quadrant);
				myLogger.info("SeatPlan created! - " + seatList);
				
				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				myLogger.info("Seat created! - " + s.getSeatName());
				
				seatList.addSeats(s);
				myLogger.info("Adding seat to seatplan...");
			}
			populateSeatPlan(resultSet, seatList);
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException nullEx) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return seatList;
	}
	
	// retrieves list of seats for selected employee
	public List<Seat> searchSeatByEmployee(Employee e) throws DBException {
		myLogger.info("START");

		List<Seat> seatList = null;
		int empIDForReference = e.getEmployeeID();
		Connection connection = DAOUtil.connect();
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get seats by employee");
			preparedStatement = connection.prepareStatement(SeatQueries.GET_EMPLOYEE_SEATS);
			
			preparedStatement.setInt(1, empIDForReference);
			
			myLogger.info("preparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			seatList = new ArrayList<Seat>();
			myLogger.info("Arraylist of Seats created");
			while (resultSet.next()) {
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");

				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				myLogger.info("Seat created! - " + s.getSeatName());
				
				seatList.add(s);
				myLogger.info("Adding seat to arraylist...");
			}
		} catch (SQLException sqlEx) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException nullEx) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
		return seatList;
	}

	// gets employee object and creates seat
	// and sets it for employee
	public void setSeatForEmployee(Employee e) throws DBException {
		myLogger.info("START");

		int empIDForReference = e.getEmployeeID();
		Connection connection = DAOUtil.connect();

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			myLogger.info("Query get seats by employee");
			preparedStatement = connection.prepareStatement(SeatQueries.GET_EMPLOYEE_SEATS);
			
			preparedStatement.setInt(1, empIDForReference);
			
			myLogger.info("preparedStatement to execute: " + preparedStatement);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");

				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				myLogger.info("Seat created! - " + s.getSeatName());
				
				e.setSeatLoc(s);
				myLogger.info("Adding seat to employee...");
			}
		} catch (SQLException sqlEx) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		} catch (NullPointerException nullEx) {
			myLogger.error("Query was not executed. An object is null.");
		} finally {
			DAOUtil.closeConnection(connection, resultSet, preparedStatement);
		}
		myLogger.info("END");
	}

	
	private void populateSeatPlan(ResultSet resultSet, SeatPlan sp) {
		myLogger.info("START");
		try{
			//loop through to get other seats
			while (resultSet.next()) {
				String building = resultSet.getString("bldg_id");
				String floorNum = resultSet.getString("floor_number");
				String quadrant = resultSet.getString("quadrant");
				String rowNum = resultSet.getString("row_number");
				String colNum = resultSet.getString("column_number");
				int localNum = resultSet.getInt("local_number");
				
				Seat s = new Seat(building, floorNum, quadrant, rowNum, colNum);
				s.setLocalNum(localNum);
				myLogger.info("Seat created! - " + s.getSeatName());
				
				sp.addSeats(s);
				myLogger.info("Adding seat to seatplan...");
			}
		} catch (SQLException e) {
			myLogger.error("SQL Exception Error");
			myLogger.error(e);
		}
		myLogger.info("END");
	}
}
