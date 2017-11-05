package hangman;

import java.util.Scanner;

public class Game {
	
	public static void displayGameState(int guesses, String answer, String attemptedGuesses, String correctLetters) {
		StringBuilder sb = new StringBuilder(attemptedGuesses);
		for (int i = 0; i < attemptedGuesses.length(); i++) {
			if (sb.charAt(0)=='-') {
				sb.deleteCharAt(0);
				sb.append('-');
			} else {
				break;
			}
		}
		System.out.println("the word so far: "+correctLetters);
		System.out.println("you have already guessed: ["+sb.toString()+"]");
		System.out.println("you have " + guesses + " guesses left");
	};
	
	public static String askToContinue(Scanner sc) {
        System.out.print("Continue? (y/n): ");        
        String validInput = "yn";
        while (true) {
        	String inputChecker = sc.next();
            if (!validInput.contains(inputChecker)) {
            	System.out.println();
    			System.out.print("please enter \"y\" or \"n\": ");
    			continue;
    		} else {
    			System.out.println();
    	        sc.nextLine();
    	        return inputChecker;
    		}
		}
    }
	
	public static String getUserInput(Scanner sc, String guessesString) {
		while (true) {
			System.out.print("Guess a new letter: ");
			String userInput = sc.next();
			System.out.println();
			StringBuilder sb = new StringBuilder(guessesString);
			if (!guessesString.contains(userInput)) {
				for (int i = 0; i < guessesString.length(); i++) {
					if (guessesString.charAt(i)=='-') {
						sb.deleteCharAt(0);
						sb.append(userInput);
						guessesString = sb.toString();
						break;
					}
				}
				return guessesString;
			} else {
				System.out.println("You already guessed that letter");
				System.out.println("you have already guessed: ["+guessesString+"]");
				sc.nextLine();
				continue;
			}
		}
	};
	
	public static String updateGameState(String guesses, int remaining, String correctGuess, String correctLetters) {
		StringBuilder sb= new StringBuilder(correctLetters);
		if (correctGuess.contains(String.valueOf(guesses.charAt(guesses.length()-1)))) {
			for (int k = 0; k < correctGuess.length(); k++) {
				if (correctGuess.charAt(k)==guesses.charAt(guesses.length()-1)) {
					sb.deleteCharAt(k);
					sb.insert(k, guesses.charAt(guesses.length()-1));
				}
			}
		}
		return sb.toString();
	};
	
	public static boolean gameIsOver(int guesses, String correctLetters, String correctGuess) {
		if (!correctLetters.contains("-")) {
			System.out.println("the word: "+correctGuess);
			System.out.println("holy moly, you win");
			return true;
		} else if (guesses==1) {
			System.out.println("the word was: "+correctGuess);
			System.out.println("na na na na boo boo, you lose");
			return true;
		} else {
			return false;
		}
	};

	public static String generateNewWord() {
		String[] wordChoices = new String[10];
		wordChoices[0] = "cannonball";
		wordChoices[1] = "lexicon";
		wordChoices[2] = "platform";
		wordChoices[3] = "computer";
		wordChoices[4] = "display";
		wordChoices[5] = "formula";
		wordChoices[6] = "sentence";
		wordChoices[7] = "potatoe";
		wordChoices[8] = "speakers";
		wordChoices[9] = "marathon";
		return wordChoices[(int) (Math.random() * 9) + 1];
	};
	
	public static String generateCorrectString(String correctGuess) {
		StringBuilder sb= new StringBuilder();
		for (int j = 0; j < correctGuess.length(); j++) {
			sb.append("-");
		}
		return sb.toString();
	};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String choice = "y";
		while (choice.equalsIgnoreCase("y")) {
			String correctGuess = generateNewWord();
			int totalGuesses = correctGuess.length()*3/2+2;
			StringBuilder sb= new StringBuilder();
			for (int i = 0; i < totalGuesses; i++) {
				sb.append("-");
			}
			String userGuessString = sb.toString();
			String correctLetters = generateCorrectString(correctGuess);
			for (int i = 0; i < totalGuesses; i++) {
				displayGameState(totalGuesses-i,correctGuess,userGuessString,correctLetters);
				userGuessString = getUserInput(sc,userGuessString);
				String lastUserGuess = String.valueOf(userGuessString);
				correctLetters = updateGameState(lastUserGuess,totalGuesses-i,correctGuess,correctLetters);
				if (gameIsOver(totalGuesses-i,correctLetters,correctGuess)) {
					break;
				}
			}
			choice = askToContinue(sc);
			continue;
		}
		sc.close();
		System.out.println("Bye for now, please come and play again.");
	}

}