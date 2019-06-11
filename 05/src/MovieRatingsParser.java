/*
 * SD2x Homework #5
 * Implement the method below according to the specification in the assignment description.
 * Please be sure not to change the method signature!
 */

import java.util.List;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class MovieRatingsParser {

	public static TreeMap<String, PriorityQueue<Integer>> parseMovieRatings(List<UserMovieRating> allUsersRatings) {
		if (allUsersRatings == null || allUsersRatings.isEmpty()) {
			return new TreeMap<String, PriorityQueue<Integer>>();
		}

		TreeMap<String, PriorityQueue<Integer>> ratingsTree = new TreeMap<>();
		for (UserMovieRating review : allUsersRatings) {
			if (review == null) {
				continue;
			}

			String movieTitle = review.getMovie();
			if (movieTitle == null || movieTitle.isEmpty()) {
				continue;
			}

			int score = review.getUserRating();
			if (score < 0) {
				continue;
			}

			movieTitle = movieTitle.toLowerCase(Locale.ENGLISH);
			ratingsTree.computeIfPresent(movieTitle, (k, v) -> {
				PriorityQueue<Integer> nextRatings = new PriorityQueue<>(v);
				nextRatings.add(score);
				return nextRatings;
			});
			ratingsTree.computeIfAbsent(movieTitle, k -> {
				PriorityQueue<Integer> nextRatings = new PriorityQueue<>();
				nextRatings.add(score);
				return nextRatings;
			});
		}
		
		return ratingsTree;
	}
}
