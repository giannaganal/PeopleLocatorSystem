package com.java.pointwest.util;

public interface SeatQueries {
	String GET_ALL_SEATS = "SELECT * from plsdb.seat";
	
	String GET_SEATS_BY_LOCATION_AND_FLOOR = "SELECT * from plsdb.seat "
			+ "WHERE seat.bldg_id = ? "
			+ "AND seat.floor_number = ? "
			+ "ORDER BY seat.quadrant ASC";
	
	String GET_SEATS_BY_QUADRANT = "SELECT * from plsdb.seat "
			+ "WHERE seat.bldg_id = ? "
			+ "AND seat.floor_number = ? "
			+ "AND seat.quadrant = ? "
			+ "ORDER BY seat.quadrant ASC";
	
	String GET_EMPLOYEE_SEATS = "SELECT employee_seat.emp_id, employee_seat.seat_id, seat.bldg_id, seat.floor_number, "
			+ "seat.quadrant, seat.row_number, seat.column_number, seat.local_number\r\n" + 
			"FROM plsdb.employee_seat\r\n" + 
			"JOIN seat ON employee_seat.seat_id = seat.seat_id\r\n" + 
			"WHERE employee_seat.emp_id = ?\r\n" + 
			"ORDER BY employee_seat.emp_id ASC";
}
