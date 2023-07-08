package edu.hungman.maventest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class GetWord {

    private String guessedWord;
    private final String url = "https://www.ef.com/wwen/english-resources/english-vocabulary/top-3000-words/";
    private WordsLevel level;
    private List<String> words;


    public GetWord(WordsLevel level) {
        this.level = level;

        guessedWord = initializeWord();
    }

    public String getGuessedWord() {
        return guessedWord;
    }

    private String initializeWord() {
    
        try {
            if (level == WordsLevel.EASY) {
                return getRandomWord(wordsPreparation(getElements(url), 0, 4));
            } else if (level == WordsLevel.MEDEUM) {
                return getRandomWord(wordsPreparation(getElements(url), 4, 7));
            } else {
                return getRandomWord(wordsPreparation(getElements(url), 7, 15));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Ups";
        }
    }




    private Elements getElements(String url) throws IOException {
        return Jsoup.connect(url).get()
                                .select("div[class=field-item even]")
                                .select("p");
                               
    }

    /**
     * Transform Elements to List<String> and cut words
     * @param elements
     * @return
     */

    private List<String> wordsPreparation(Elements elements, int min, int max) {
        words = new ArrayList<>();
        for (Element element : elements) {
            for(Node child : element.childNodes()) {
                String word = "";
                if (child instanceof TextNode) {
                    word = ((TextNode) child).text();
                }
                Matcher matcher = Pattern.compile("^[a-z]+").matcher(word);
                if (matcher.find()) {
                    String a = matcher.group();
                    if (a.length() >= min && a.length() < max) {
                    words.add(a);
                    }
                 }
            }
        }
        return words;
    }


    private String getRandomWord(List<String> words) {
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }
}
