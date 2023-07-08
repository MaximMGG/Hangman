package edu.hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        String answer = scan.nextLine(); 
        while (!answer.equalsIgnoreCase("yes")) {
            if (answer.equalsIgnoreCase("no")) {
                return;
            }
            awesomePrinting("Please, write your answer[yes/no]");
            answer = scan.nextLine();
        }
        awesomePrinting("How you fill about your level in guessing words?\n");
        awesomePrinting("I am new in this game: 1");
        awesomePrinting("I am good in this game: 2");
        awesomePrinting("I am profesional in this game: 3");
        answer = scan.nextLine();
        int count = 1;
        while (count != 0) {
            switch(answer) {
                case "1" -> {count--; guesedWord = new GetWord(WordsLevel.EASY).getGuessedWord();}
                case "2" -> {count--; guesedWord = new GetWord(WordsLevel.MEDEUM).getGuessedWord();}
                case "3" -> {count--; guesedWord = new GetWord(WordsLevel.HARD).getGuessedWord();}
                default -> awesomePrinting("This level do not exist, try agane");
            }
        }
        guessedWordFull = guesedWord.split("");
        guessedWordEmpty = new String[guessedWordFull.length];
        Arrays.fill(guessedWordEmpty, "*");
        scan = new Scanner(System.in);
        currentLetters = new ArrayList<>();
        waiting();
        startPlaying();
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
        awesomePrinting(finalWord);
        awesomePrinting(" <> Guessed word was --> " + printGuessedWord() + " <> \n");
        waiting();
        awesomePrinting("Do you want play agane?\n");
        String answer = scan.nextLine();
        while (!answer.equalsIgnoreCase("yes")) {
            if (answer.equalsIgnoreCase("no")) {
                return;
            }
            awesomePrinting("Please, write your answer[yes/no]");
            answer = scan.nextLine();
        }
        startPlaying();
    }

    private String printGuessedWord() {
        StringBuilder build = new StringBuilder();
        for(String s : guessedWordEmpty) {
            build.append(s + " ");
        }
        return build.toString();
    }

    private void showCorrentGuessedWord(String letter) throws InterruptedException {
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
            System.out.println();
        }
        if (!anyGues) {
            mistakesCounter++;
            awesomePrinting("Unfortunately not, try agane\n");
        } else {
            waiting();
            if (currentLetters.size() == guesedWord.length()) {

            } else {
                    awesomePrinting("Good job, let's continious");
                    System.out.println();
            }
        }
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
    private final String FINALMESSAGELOSE = "Sory, but you are lose(";


    private void preparingTheWord() throws InterruptedException {
        awesomePrinting("Awesome, lets prepare the word");
        System.out.println();
        awesomePrinting("Prepearing ");
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(" .");
        }
        waiting();
        System.out.println();
    }
    private void waiting() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void awesomePrinting(String str) throws InterruptedException {
        for(String a : str.split("")) {
            Thread.sleep(50);
            System.out.print(a);
        }
    }
}
