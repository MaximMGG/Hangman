package edu.hangman;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetWord {

    private String guessedWord;


    public GetWord() {
        guessedWord = initializeWord();
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    private String initializeWord() {
        try {
            return getRandomWord(wordsPreparate(getElements()));
        } catch (IOException e) {
            e.printStackTrace();
            return "Ups";
        }
    }

    /**
     * Takes elements from site
     * @return
     * @throws IOException
     */

    private Elements getElements() throws IOException {
        return Jsoup.connect("https://ahaslides.com/blog/random-english-words/").get()
                                        .select("div[class=container-xs]")
                                        .select("p")
                                        .select("strong");
    }

    /**
     * Transform Elements to List<String> and cut words
     * @param elements
     * @return
     */

    private List<String> wordsPreparate(Elements elements) {
        List<String> words = new ArrayList<>();
        for (Element element : elements) {
            String word = element.text();
            Matcher matcher = Pattern.compile("^[a-z]+").matcher(word);
            if (matcher.find()) {
                String a = matcher.group();
                if (a.length() < 10) {
                    words.add(a);
                }
            }
        }
        System.out.println(words);
        return words;
    }
    private String getRandomWord(List<String> words) {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}
