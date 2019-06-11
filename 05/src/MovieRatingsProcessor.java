/*
 * SD2x Homework #5
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */

import java.util.*;


public class MovieRatingsProcessor {

	public static List<String> getAlphabeticalMovies(TreeMap<String, PriorityQueue<Integer>> movieRatings) {
		if (movieRatings == null || movieRatings.isEmpty()) {
			return new LinkedList<String>();
		}

		List<String> sortedTitles = new LinkedList<>();
		for (String title : movieRatings.keySet()) {
			if (title == null || title.isEmpty()) {
				continue;
			}

			sortedTitles.add(title);
		}

		sortedTitles.sort(String::compareTo);
		return sortedTitles;
	}

	public static List<String> getAlphabeticalMoviesAboveRating(TreeMap<String, PriorityQueue<Integer>> movieRatings, int rating) {
		if (movieRatings == null || movieRatings.isEmpty()) {
			return new LinkedList<String>();
		}
		// We'll remove entries if they are not greater than the cutoff rating.
		movieRatings.entrySet().removeIf(e -> !(e.getValue().poll() > rating));
		List<String> sortedTitles = getAlphabeticalMovies(movieRatings);
		return sortedTitles;
	}
	
	public static TreeMap<String, Integer> removeAllRatingsBelow(TreeMap<String, PriorityQueue<Integer>> movieRatings, int rating) {
		if (movieRatings == null || movieRatings.isEmpty()) {
			return new TreeMap<String, Integer>();
		}

		TreeMap<String, Integer> numberOfReviewsRemoved = new TreeMap<>();
		// We use an iterator, as that will allow us to safely remove entries from within our iteration of the queue.
		Iterator<Map.Entry<String, PriorityQueue<Integer>>> queueIter = movieRatings.entrySet().iterator();
		while (queueIter.hasNext()) {
			Map.Entry<String, PriorityQueue<Integer>> review = queueIter.next();
			if (!reviewChecker(review)) {
				continue;
			}

			String movieTitle = review.getKey();
			PriorityQueue<Integer> scores = review.getValue();
			int numberRemoved = 0;
			Iterator<Integer> scoreIter = scores.iterator();
			while (scoreIter.hasNext()) {
				int nextScore = scoreIter.next();
				if (nextScore >= rating) {
					break;
				}

				numberRemoved++;
				scoreIter.remove();
			}
			// If no scores have been removed, we do not need to keep track of the movie.
			if (numberRemoved == 0) {
				continue;
			}
			// If all scores have been removed, remove the movie entry from within the input queue as a side effect.
			if (scores.isEmpty()) {
				queueIter.remove();
			}
			// This is here, since lambdas require outside variables to be "effectively final."
			int finalRemoved = numberRemoved;
			numberOfReviewsRemoved.computeIfPresent(movieTitle, (k, v) -> v += finalRemoved);
			numberOfReviewsRemoved.putIfAbsent(movieTitle, finalRemoved);
		}

		return numberOfReviewsRemoved;
	}

	private static boolean reviewChecker(Map.Entry<String, PriorityQueue<Integer>> entrySet) {
		if (entrySet == null) {
			return false;
		}

		String movieTitle = entrySet.getKey();
		if (movieTitle == null || movieTitle.isEmpty()) {
			return false;
		}

		PriorityQueue<Integer> scores = entrySet.getValue();
		if (scores == null || scores.isEmpty()) {
			return false;
		}

		return true;
	}
}
