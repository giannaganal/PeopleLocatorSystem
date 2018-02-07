package com.java.pointwest.util;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.java.pointwest.exceptions.ExpectedIntException;

public final class UIUtil {
	public static void displayHorizontalBar(int numberOfDashes) {
		for (int i = 0; i < numberOfDashes; i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	public static void displayHeader(String headerName) {
		displayHorizontalBar(40);
		System.out.println("### " + headerName.toUpperCase() + " ###");
		displayHorizontalBar(40);
	}

	public static void displayMenuHeader() {
		// TODO Auto-generated method stub
		System.out.println("MENU:");
	}
	
	public static void displayActionsHeader() {
		// TODO Auto-generated method stub
		System.out.println("ACTIONS: ");
	}
	
	public static void displayMenuItem(int optionNum, String optionItem) {
		System.out.println("[" + optionNum + "] " + optionItem);
	}
	
	// will get user choice
	// check for parse to Integer and check if within given range
	public static int getUserMenuItem(int floor, int ceiling) throws ExpectedIntException {
		Scanner sc = new Scanner(System.in);
		int userInput = 0;
		boolean isWithinRange = false;
		do {
			System.out.println("Please choose an item: ");
		
			try {
				userInput = sc.nextInt();
				//convertedInput = Integer.parseInt(userInput);
				
				if (userInput >= floor && userInput <= ceiling) {
					isWithinRange = true;
				} else {
					System.out.println("Please input a number within " + floor + " and " + ceiling + ".");
				}
			} catch (InputMismatchException parseErrorEx) {
				throw new ExpectedIntException();
			}
		} while(!isWithinRange);
		
		return userInput;
	}
}
