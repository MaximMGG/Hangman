package edu.hungman.maventest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayGame {
    
    private Scanner scan;
    private String guesedWord;
    private String[] guessedWordFull;
    private String[] guessedWordEmpty;
    private List<String> currentLetters;
    private int mistakesCounter;
    private String finalWord;

    public PlayGame() throws InterruptedException {
            firstStart();
    }

    private void firstStart() throws InterruptedException {
        System.out.println("Greetings in HANGMAN!");
        awesomePrinting("Do you want to start a new game? \n[yes/no]\n");
        scan = new Scanner(System.in);
        String answer = scan.nextLine();
        while (!answer.equalsIgnoreCase("yes")) {
            if (answer.equalsIgnoreCase("no")) {
                return;
            }
            awesomePrinting("Please, write your answer[yes/no]");
            answer = scan.nextLine();
        }
        prepareForANewGame();
        startPlaying();
    }
    private String getGuessedWord() throws InterruptedException {
        awesomePrinting("How you fill about your level in guessing words?\n");
        awesomePrinting("I am new in this game: 1\n");
        awesomePrinting("I am good in this game: 2\n");
        awesomePrinting("I am profesional in this game: 3\n");
        int count = 1;
        while (count != 0) {
        String answer = scan.nextLine();
            switch(answer) {
                case "1" -> {count--; return new GetWord(WordsLevel.EASY).getGuessedWord();}
                case "2" -> {count--; return new GetWord(WordsLevel.MEDEUM).getGuessedWord();}
                case "3" -> {count--; return new GetWord(WordsLevel.HARD).getGuessedWord();}
                default -> awesomePrinting("This level do not exist, try agane\n");
            }
        }
        return "ups";
    }


    private void startPlaying() throws InterruptedException {
        
        preparingTheWord();
        while (currentLetters.size() != guesedWord.length()) {
            printMistakes();
            awesomePrinting(" <> guessed word is: " + printGuessedWord() + " <>");
            awesomePrinting(" -- Please write the letter --> ");
            String writeLetter = scan.nextLine();
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
        awesomePrinting(finalWord + "\n");
        printMistakes();
        awesomePrinting(" <> Guessed word was --> " + printLoseGuessedWord() + " <> \n");
        waiting();
        awesomePrinting("Do you want play agane? [yes/no]\n");
        String answer = scan.nextLine();
        while (!answer.equalsIgnoreCase("yes")) {
            if (answer.equalsIgnoreCase("no")) {
                awesomePrinting("Thank you for playing HANGMAN, BYE");
                waiting();
                return;
            }
            awesomePrinting("Please, write your answer[yes/no]");
            answer = scan.nextLine();
        }
        prepareForANewGame();
        startPlaying();
    }

    private void prepareForANewGame() throws InterruptedException {
        guesedWord = getGuessedWord();
        guessedWordFull = guesedWord.split("");
        guessedWordEmpty = new String[guessedWordFull.length];
        Arrays.fill(guessedWordEmpty, "*");
        currentLetters = new ArrayList<>();
        mistakesCounter = 0;
    }

    private String printGuessedWord() {
        StringBuilder build = new StringBuilder();
        for(String s : guessedWordEmpty) {
            build.append(s + " ");
        }
        return build.toString();
    }

    private String printLoseGuessedWord() {
        StringBuilder build = new StringBuilder();
        for(String s : guesedWord.split("")) {
            build.append(s + " ");
        }
        return build.toString();
    }

    private void showCorrentGuessedWord(String letter) throws InterruptedException {
        letter = checkLetter(letter);
        boolean anyGues = false;
        if (!currentLetters.contains(letter)) {
            for (int i = 0; i < guessedWordFull.length; i++) {
               if (letter.equals(guessedWordFull[i])) {
                    guessedWordEmpty[i] = letter;
                    currentLetters.add(letter);
                    anyGues = true;
                }
            }
        } else {
            awesomePrinting(" This letter is exist in guessed word, try agane!");
            anyGues = true;
            System.out.println();
            return;
        }
        if (!anyGues) {
            mistakesCounter++;
            if (mistakesCounter == 6) {
                return;
            }
            awesomePrinting("Unfortunately not, try agane\n");
        } else {
            if (currentLetters.size() == guesedWord.length()) {
            } else {
                    awesomePrinting("Good job, let's continious");
                    System.out.println();
            }
        }
    }

    private String checkLetter(String letter) throws InterruptedException {
        if (letter.isBlank()) {
            awesomePrinting("You did't wrote a letter, please try agane\n");
            letter = scan.nextLine();
            letter = checkLetter(letter);
        }
        letter = letter.toLowerCase();
        if (letter.length() > 1) {
            awesomePrinting("You wrote incorrect letter, length gretter then one, please, write agane\n");
            letter = scan.nextLine();
            letter = checkLetter(letter);
        }
        Matcher match = Pattern.compile("[A-z]").matcher(letter);
        if (!match.find()) {
            awesomePrinting("You wrote symbol or number, please write letter\n");
            letter = scan.nextLine();
            letter = checkLetter(letter);
        }
        return letter;
    }

    private void printMistakes() throws InterruptedException {
        switch(mistakesCounter) {
            case 0 -> awesomePrinting(Mistakes.ZEROMISTAKE);
            case 1 -> awesomePrinting(Mistakes.FIRSTMISTAKE);
            case 2 -> awesomePrinting(Mistakes.SECONDMISTAKE);
            case 3 -> awesomePrinting(Mistakes.THERDMISTAKE);
            case 4 -> awesomePrinting(Mistakes.FOURSMISTAKE);
            case 5 -> awesomePrinting(Mistakes.FIVESMISTAKE);
            case 6 -> awesomePrinting(Mistakes.SIXESMISTAKE);
        }
    }

    private final String FINALMESSAGEWIN = "Congrats, you are did awesome job!";
    private final String FINALMESSAGELOSE = "Sory, but you are lose!";


    private void preparingTheWord() throws InterruptedException {
        awesomePrinting("Awesome, lets prepare the word");
        System.out.println();
        awesomePrinting("Prepearing ");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(" .");
        }
        System.out.println();
    }
    private void waiting() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void awesomePrinting(String str) throws InterruptedException {
        for(String a : str.split("")) {
            Thread.sleep(30);
            System.out.print(a);
        }
    }
}

