/*
 * SD2x Homework #8
 * This class represents the Presentation Tier in the three-tier architecture.
 * Implement the appropriate methods for this tier below. 
 * Also implement the start method as described in the assignment description.
 */

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Scanner;

public class PresentationTier {
	
	private LogicTier logicTier; // link to the Logic Tier
	private Scanner console;
	
	public PresentationTier(LogicTier logicTier) {
		this.logicTier = logicTier;
		this.console = new Scanner(System.in);
	}

	private class FeatureCodes {
		static final short EXIT = 0, BOOK_BY_YEAR = 1, BOOK_BY_AUTHOR = 2;
	}

	public void showBookTitlesByAuthor() {
		System.out.println("Okay.");
		System.out.println("Enter an author");
		System.out.print(": ");
		String author = console.next();
		List<String> bookTitles = logicTier.findBookTitlesByAuthor(author);
		if (bookTitles.isEmpty()) {
			System.out.format("%s has not written any books!\n", author);
			return;
		}

		System.out.println("Titles");
		for (String title : bookTitles) {
			System.out.println("\t" + title);
		}
	}

	public void showNumberOfBooksInYear() {
		System.out.println("Alright.");
		System.out.println("Enter a year");
		System.out.print(": ");
		int year = console.nextInt();
		int bookCount = logicTier.findNumberOfBooksInYear(year);
		if (bookCount == 1) {
			System.out.format("There was %d book printed in %d.\n", bookCount, year);
			return;
		}

		System.out.format("There were %d books printed in %d.\n", bookCount, year);
	}

	public void start() {
		System.out.println("Hello.");
		System.out.println("Which feature would you like to use today?");
		menu: while (true) {
			int featureCode = introMenu();
			switch (featureCode) {
				case FeatureCodes.BOOK_BY_AUTHOR:
					showBookTitlesByAuthor();
					break menu;
				case FeatureCodes.BOOK_BY_YEAR:
					showNumberOfBooksInYear();
					break menu;
				case FeatureCodes.EXIT:
					exitChoice();
				default:
					System.out.println("Sorry, couldn't quite make out what you just said");
			}
		}

		System.out.println("Well, that's that.");
		outroMenu();
	}

	private int extractCode() {
		System.out.print(": ");
		int code = console.nextInt();
		return code;
	}

	private int introMenu() {
		System.out.println("\t\t0:\tExit");
		System.out.println("\t\t1:\tGet number of books published in a year");
		System.out.println("\t\t2:\tGet all books written by an author");
		int featureCode = extractCode();
		return featureCode;

	}

	private void outroMenu() {
		System.out.println("\t\t0:\tExit");
		System.out.println("\t\t1:\tGive it another try");
		int code = extractCode();
		switch (code) {
			case 0:
				exitChoice();
			case 1:
				start();
			default:
				System.out.println("I'm gonna go ahead and assume you meant exit.");
				exitChoice();
		}
	}

	private void exitChoice() {
		System.out.print("Ta Ta.");
		System.exit(0);
	}
}
