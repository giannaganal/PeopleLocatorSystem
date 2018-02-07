package com.java.pointwest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.java.pointwest.exceptions.DBException;

public class DAOUtil {
	
	static Logger myLogger = Logger.getLogger(DAOUtil.class);
	
	static final String CLASS_NAME = "com.mysql.jdbc.Driver";
	static final String DATABASE_NAME = "jdbc:mysql://172.26.83.193:3306/plsdb?autoReconnect=true&useSSL=false";
	static final String USERNAME = "newuser";
	static final String PASSWORD = "password123";
	
	public static Connection connect() throws DBException {
		myLogger.info("START");
		
		Connection conn = null;
		try {
			Class.forName(CLASS_NAME);
			
			conn = DriverManager.getConnection(DATABASE_NAME,USERNAME,PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// no class found
			myLogger.error("Class is not found. Please make sure you have the correct spelling"
					+ " of the driver or have the jar file installed.");
			myLogger.error(e);
			throw new DBException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			myLogger.error("SQL Exception error encountered.");
			myLogger.error("No connection established. Please check the IP address, database name or credentials.");
			myLogger.error(e);
			throw new DBException();
		}
		myLogger.info("Connection is: " + conn);
		myLogger.info("END");
		return conn;
	}
	
	public static void closeConnection(Connection c, ResultSet rs, Statement s) {
		myLogger.info("START");
		try {
			if (c != null) {
				c.close();
				myLogger.info("connection closed");
			}
			if (rs != null) {
				rs.close();
				myLogger.info("ResultSet closed");
			}
			if (s != null) {
				s.close();
				myLogger.info("statement closed");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		myLogger.info("END");
	}
}