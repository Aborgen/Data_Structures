import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
 * SD2x Homework #3
 * Implement the methods below according to the specification in the assignment description.
 * Please be sure not to change the method signatures!
 */
public class Analyzer {

    /*
     * Implement this method in Part 1
     */
    public static List<Sentence> readFile(String filename) {
        if (filename == null) {
            return new LinkedList<Sentence>();
        }

        LinkedList<Sentence> file = new LinkedList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            while (line != null) {
                // Lines must begin with a number in the range of [-2, 2], inclusive,
                // followed by a single space and any number of non-linebreak characters.
                if (!line.matches("^(-?[1-2]|0) {1}.+")) {
                    line = reader.readLine();
                    continue;
                }

                // Split the line into two strings: the number in the range of [-2, 2] and the rest.
                String[] splitLine = line.split(" ", 2);
                int score = Integer.parseInt(splitLine[0]);
                String text = splitLine[1];
                Sentence nextSentence = new Sentence(score, text);
                file.add(nextSentence);
                line = reader.readLine();
            }
        }
        catch(IOException exception) {
            return new LinkedList<Sentence>();
        }

        return file;
    }

    /*
     * Implement this method in Part 2
     */
    public static Set<Word> allWords(List<Sentence> sentences) {
        if (sentences == null || sentences.isEmpty()) {
            return new HashSet<Word>();
        }

        Map<String, Word> wordOccurance = getWordFrequencyAndScore(sentences);
        Set<Word> wordSet = new HashSet<>();
        wordOccurance.forEach((k, v) -> {
            if (k != null) {
                wordSet.add(v);
            }
        });

        return wordSet;
    }

    private static Map<String, Word> getWordFrequencyAndScore(List<Sentence> sentences) {
        HashMap<String, Word> wordOccurance = new HashMap<>();
        for (Sentence sentence : sentences) {
            if (sentence == null) {
                continue;
            }

            int score = sentence.getScore();
            String[] wordArray = sentence.getText().split(" ");
            for (String word : wordArray) {
                if (!isValidWord(word)) {
                    continue;
                }

                word = word.toLowerCase(Locale.ENGLISH);
                // Update total score by sentence's overall score.
                wordOccurance.computeIfPresent(word, (k, v) -> {
                    v.increaseTotal(score);
                    return v;
                });
                // Add a new key-value pair.
                wordOccurance.computeIfAbsent(word, k -> {
                    Word newWord = new Word(k);
                    newWord.increaseTotal(score);
                    return newWord;
                });
            }
        }

        return wordOccurance;
    }

    // A word must begin with a letter.
    private static boolean isValidWord(String word) {
        return !word.isEmpty() && !word.equals("") && word.matches("^[a-zA-Z]+");
    }

    /*
     * Implement this method in Part 3
     */
    public static Map<String, Double> calculateScores(Set<Word> words) {
        if (words == null || words.isEmpty()) {
            return new HashMap<String, Double>();
        }

        HashMap<String, Double> wordScores = new HashMap<>();
        for (Word word : words) {
            if (word == null) {
                continue;
            }

            Double score = word.calculateScore();
            String text = word.getText();
            wordScores.putIfAbsent(text, score);
        }

        return wordScores;
    }

    /*
     * Implement this method in Part 4
     */
    public static double calculateSentenceScore(Map<String, Double> wordScores, String sentence) {
        Double sentenceScore = 0.0;
        if (wordScores == null || sentence == null || wordScores.isEmpty() || sentence.isEmpty()) {
            return sentenceScore;
        }

        String[] wordList = sentence.split(" ");
        int parsedWords = 0;
        for (String word : wordList) {
            char firstLetter = word.charAt(0);
            if (!isValidWord(word) || !Character.isLetter(firstLetter)) {
                continue;
            }

            word = word.toLowerCase(Locale.ENGLISH);
            for (Map.Entry<String, Double> entry : wordScores.entrySet())
            {
                String key = entry.getKey();
                Double value = entry.getValue();
                if (word.equals(key)) {
                    sentenceScore += value;
                }
            }

            parsedWords++;
        }

        if (parsedWords == 0) {
            return sentenceScore;
        }

        return sentenceScore / parsedWords;
    }

    /*
     * This method is here to help you run your program. Y
     * You may modify it as needed.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify the name of the input file");
            System.exit(0);
        }
        String filename = args[0];
        System.out.print("Please enter a sentence: ");
        Scanner in = new Scanner(System.in);
        String sentence = in.nextLine();
        in.close();
        List<Sentence> sentences = Analyzer.readFile(filename);
        Set<Word> words = Analyzer.allWords(sentences);
        Map<String, Double> wordScores = Analyzer.calculateScores(words);
        double score = Analyzer.calculateSentenceScore(wordScores, sentence);
        System.out.println("The sentiment score is " + score);
    }
}
