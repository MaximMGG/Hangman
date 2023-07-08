package edu.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PlayGame {
    
    private Scanner scan;
    private String guesedWord;
    private String[] guessedWordBefore;
    private String[] guessedWordAfter;
    private List<String> currentLetters;
    private int mistakesCounter;
    private String finalWord;

    public PlayGame(String guesedWord) {
            this.guesedWord = guesedWord;
            guessedWordBefore = guesedWord.split("");
            guessedWordAfter = new String[guessedWordBefore.length];
            Arrays.fill(guessedWordAfter, "*");
            scan = new Scanner(System.in);
            currentLetters = new ArrayList<>();
            startPlaying();
    }

    private void startPlaying() {
        System.out.println("Do you want to start new game? + \n[yes/no]");
        String answer = scan.nextLine();
        if (answer.equals("no")){
            return;
        }
        while (currentLetters.size() != guesedWord.length()) {
            printMistakes();
            System.out.printf("Please write the letter");
            String writeLetter = scan.nextLine();
            System.out.printf("\r\r\r\r\r\r\r");
            printMistakes();
            showCorrentGuessedWord(writeLetter);
            if (mistakesCounter == 6) {
                finalWord = FINALMESSAGELOSE;
                break;
            }
        }
        if (finalWord != null) {
        } else {
            finalWord = FINALMESSAGEWIN;
        }
        System.out.println(finalWord);

    }

    private void showCorrentGuessedWord(String letter) {
        boolean anyGues = false;
        if (!currentLetters.contains(letter)) {
            for (int i = 0; i < guessedWordBefore.length; i++) {
               if (letter.equals(guessedWordBefore[i])) {
                    guessedWordAfter[i] = letter;
                    currentLetters.add(letter);
                    anyGues = true;
                }
            }
        } else {
            System.out.println("\r \r \r This letter is exist in guessed word, try agane!");
        }
        StringBuilder build = new StringBuilder();
        for (String let : guessedWordAfter) {
            build.append(let + " ");
        }
        System.out.printf("\r" + "  ---->> " + build.toString());
        if (!anyGues) {
            mistakesCounter++;
        }
    }

    private void printMistakes() {
        switch(mistakesCounter) {
            case 0 -> System.out.print(Mistakes.ZEROMISTAKE);
            case 1 -> System.out.print(Mistakes.FIRSTMISTAKE);
            case 2 -> System.out.print(Mistakes.SECONDMISTAKE);
            case 3 -> System.out.print(Mistakes.THERDMISTAKE);
            case 4 -> System.out.print(Mistakes.FOURSMISTAKE);
            case 5 -> System.out.print(Mistakes.FIVESMISTAKE);
            case 6 -> System.out.print(Mistakes.SIXESMISTAKE);
        }
    }

    private final String FINALMESSAGEWIN = "Congrats, you are did awesome job!";
    private final String FINALMESSAGELOSE = "Sory, but you are lose(";
}
