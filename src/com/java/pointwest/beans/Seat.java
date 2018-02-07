package com.java.pointwest.beans;

public class Seat {
	private String location;
	private String floor;
	private String quadrant;
	private String rowNum;
	private String colNum;
	private int localNum;
	
	// constructor is for one time setters
	public Seat(String loc, String flr, String quad, String row, String col) {
		location = loc;
		floor = flr;
		quadrant = quad;
		rowNum = row;
		colNum = col;
		
	}
	
	public String getSeatName() {
		return location + floor + "F" + quadrant + rowNum + "-" + colNum;
	}

	public int getLocalNum() {
		return localNum;
	}

	public void setLocalNum(int localNum) {
		this.localNum = localNum;
	}

	public String getLocation() {
		return location;
	}

	public String getFloor() {
		return floor;
	}

	public String getQuadrant() {
		return quadrant;
	}

	public String getRowNum() {
		return rowNum;
	}

	public String getColNum() {
		return colNum;
	}
}