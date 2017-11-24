
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * program to count the frequency of words from a URL (page).
 */
public class PageWordCount {
    private static final Logger log = LoggerFactory.getLogger(PageWordCount.class);

    public static List<Word> count(Page page) throws IOException {

        HashMap<String, Word> wordCount = new HashMap<>();

        Validate.isTrue(page.getUrl().length() >= 1, "usage: supply url to fetch");
        String url = page.getUrl();
        info("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();

        Document.OutputSettings os = new Document.OutputSettings();
        os.prettyPrint(false);
        os.escapeMode(Entities.EscapeMode.extended);
        os.charset("ascii");

        Cleaner cleaner = new Cleaner(Whitelist.none());
        Document cleanDoc = cleaner.clean(doc).outputSettings(os);
        String[] wordList = cleanDoc.text().split("\\W");
        for (String key : wordList) {
            key = key.toLowerCase();
            if (key.isEmpty()) {
                continue;
            }
            if (wordCount.containsKey(key)) {
                Word w = wordCount.get(key);
                w.setWordCount(w.getWordCount() + 1L);
            } else {
                wordCount.put(key, new Word(key, 1l));
            }
        }

        info("=== raw counts ===");
        info("%s",wordCount.entrySet());

        ArrayList<Word> wordsList = new ArrayList<>(wordCount.size());
        wordsList.addAll(wordCount.values());

        // A comparator that compares two map entries based on their value
        Comparator<Word> wordsComparator = Comparator.comparingLong(Word::getWordCount);

        wordsList.sort(wordsComparator);

        info("=== frequency ===");
        wordsList.forEach(word -> info(" w: |%s| f: %d ", word.getWord(), word.getWordCount()));

        return wordsList;
    }

    private static void info(String msg, Object... args) {
        log.info(String.format("count: "+msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}
