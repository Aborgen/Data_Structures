/*
 * SD2x Homework #8
 * This class represents the Data Tier in the three-tier architecture.
 * Implement the appropriate methods for this tier below.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataTier {
	
	private String fileName; // the name of the file to read
	
	public DataTier(String inputSource) {
		fileName = inputSource;
	}

	private class Library {
		private List<Book> allBooks;
		private boolean isSorted;
		public Library() {
			allBooks = new LinkedList<>();
			isSorted = false;
		}

		public void shelve(Book book) {
			if (isSorted) {
				isSorted = false;
			}

			allBooks.add(book);
		}

		public void sort() {
			if (isSorted) {
				return;
			}

			allBooks.sort(Comparator.naturalOrder());
			isSorted = true;
		}

		public List<Book> getAllBooks() {
			return allBooks;
		}
	}

	public List<Book> getAllBooks() {
		Library library = new Library();
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line = reader.readLine();
			while (line != null) {
				// Per instructions, we can assume that file is well formatted.
				String[] bookParts = line.split("\t");
				String title = bookParts[0];
				String author = bookParts[1];
				int year = Integer.parseInt(bookParts[2]);
				Book newBook = new Book(title, author, year);
				library.shelve(newBook);
				line = reader.readLine();
			}
		}
		catch(IOException exception) {
			return null;
		}

		library.sort();
		return library.getAllBooks();
	}
}
