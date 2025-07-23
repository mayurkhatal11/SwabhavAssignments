package Assignments;

import java.util.Scanner;
import java.util.Random;

public class WordGuesser {

	static final String[] WORD_LIST = { "apple", "banana", "computer", "programming", "java", "learn" };
	static final int MAX_LIVES = 6;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String wordToGuess = getRandomWord();
		char[] guessedWord = new char[wordToGuess.length()];
		boolean[] guessedLetters = new boolean[26];
		int lives = MAX_LIVES;

		for (int i = 0; i < guessedWord.length; i++) {
			guessedWord[i] = '_';
		}

		System.out.println("Welcome to WordGuesser Game!");

		while (true) {
			System.out.print("Word: ");
			printWord(guessedWord);
			System.out.println("Lives left: " + lives);
			System.out.print("Guess a letter: ");
			char guess = scanner.next().toLowerCase().charAt(0);

			if (!Character.isLetter(guess)) {
				System.out.println("Please enter a valid letter.");
				continue;
			}

			if (guessedLetters[guess - 'a']) {
				System.out.println("You've already guessed that letter.");
				continue;
			}

			guessedLetters[guess - 'a'] = true;

			if (wordToGuess.indexOf(guess) >= 0) {

				for (int i = 0; i < wordToGuess.length(); i++) {
					if (wordToGuess.charAt(i) == guess) {
						guessedWord[i] = guess;
					}
				}
				if (isWordGuessed(guessedWord)) {
					System.out.println("Congratulations! You guessed the word: " + wordToGuess);
					break;
				}
			} else {

				lives--;
				System.out.println("Wrong guess.");
				if (lives == 0) {
					System.out.println("Game Over! The word was: " + wordToGuess);
					break;
				}
			}
		}
	}

	private static String getRandomWord() {
		Random rand = new Random();
		return WORD_LIST[rand.nextInt(WORD_LIST.length)];
	}

	private static void printWord(char[] guessedWord) {
		for (char c : guessedWord) {
			System.out.print(c + " ");
		}
		System.out.println();
	}

	private static boolean isWordGuessed(char[] guessedWord) {
		for (char c : guessedWord) {
			if (c == '_') {
				return false;
			}
		}
		return true;
	}
}
