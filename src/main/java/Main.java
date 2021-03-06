

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Parse a URL into its text and persist the word counts.
 *
 *
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];

        print("\n\nConnecting to DB.");

        JPAUtil util = new JPAUtil();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WordCountService");
        EntityManager em = emf.createEntityManager();
        WordCountService service = new WordCountService(em);


        print("Fetching %s...", url);
        Page page;
        em.getTransaction().begin();

        page = service.createPage(url);
        List<Word> wordList = PageWordCount.count(page);

        // Link the words to the page and persist them.
        wordList.forEach(w -> {
            w.setPageId(page.getId());
            em.persist(w);
        });
        em.flush();
        em.getTransaction().commit();
        System.out.println("Persisted " + page);

        print("\n\nPage in DB");
        util.checkData("select * from Page");
        print("\n\nWords in DB");
        util.checkData("select * from Word");

        print("\n\nClosing database after writing information for  %s...", url);
        em.close();
        emf.close();
        util.shutdown();
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}