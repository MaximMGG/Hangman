package edu.hungman.maventest;

public class App 
{
    public static void main( String[] args ) {
        try {
            PlayGame play = new PlayGame();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
}
