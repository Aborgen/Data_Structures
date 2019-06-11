/*
 * SD2x Homework #8
 * This class represents the Logic Tier in the three-tier architecture.
 * Implement the appropriate methods for this tier below.
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogicTier {
	
	private DataTier dataTier; // link to the Data Tier
	private List<Book> allBooks;
	
	public LogicTier(DataTier dataTier) {
		this.dataTier = dataTier;
		allBooks = dataTier.getAllBooks();
	}

	public List<String> findBookTitlesByAuthor(String author) {
		if (author == null || author.isEmpty()) {
			return new LinkedList<>();
		}

		List<String> bookTitles = new LinkedList<>();
		Pattern p = Pattern.compile(author);
		for (Book book : allBooks) {
			String bookAuthor = book.getAuthor();
			Matcher matcher = p.matcher(bookAuthor);
			if (matcher.find()) {
				String bookTitle = book.getTitle();
				bookTitles.add(bookTitle);
			}
		}

		return bookTitles;
	}

	public int findNumberOfBooksInYear(int year) {
		if (year <= 0) {
			return 0;
		}

		int bookCount = 0;
		for (Book book : allBooks) {
			int publicationYear = book.getPublicationYear();
			if (publicationYear < year) {
				continue;
			}
			// Since allBooks is sorted by year ascending, break to prevent unnecessary iteration.
			if (publicationYear > year) {
				break;
			}

			if (publicationYear == year) {
				bookCount++;
			}
		}

		return bookCount;
	}
}
