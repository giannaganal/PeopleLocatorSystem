package com.java.pointwest.beans;

import java.util.ArrayList;
import java.util.List;

public class SeatPlan {
	private String officeLocation;
	private String floor;
	private String quadrant;
	private List<Seat> seats;

	// constructor for one time setters
	public SeatPlan(String location, String flr, String quad) {
		seats = new ArrayList<Seat>();
		
		officeLocation = location;
		floor = flr;
		quadrant = quad;
	}

	public List<Seat> getSeats() {
		return seats;
	}
	
	/*public Iterator<Seat> getSeats){
		return seats.iterator();
	}*/

	public void addSeats(Seat seat) {
		seats.add(seat);
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public String getFloor() {
		return floor;
	}

	public String getQuadrant() {
		return quadrant;
	}
	
	
}