
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class League extends Thread {

    String name, link, parentDir;
    List<Season> seasons;

    public League(String n, String l) {
        name = n;
        link = l;
        seasons = new ArrayList<>();
        out.println("League: " + name + " - " + link);
    }

    // This is the entry point for the second thread.
    @Override
    public void run() {
        out.println("Connecting to - " + link);
        Document doc;
        try {
            doc = Jsoup.connect(link).timeout(Main.timeout).ignoreHttpErrors(true)
                    .followRedirects(true).get();
            // Find out all Seasons
            Elements seasonLinks = doc.select("option"); // direct a after h3
            String seasonYear;
            String seasonLink;
            for (int l = 0; l < seasonLinks.size(); l++) {
                seasonYear = seasonLinks.get(l).text();
                if (!seasonYear.contains("Decimal")
                        && !seasonYear.contains("Fraction")
                        && !seasonYear.contains("American")
                        && !seasonYear.contains("Binary")
                        && !seasonYear.contains("Split")) {
                    seasonLink = link + "/" + seasonYear + "/results";
                    seasons.add(new Season(seasonYear, seasonLink));
                }
            }
            collectSeasons();
        } catch (IOException e) {
        }
    }

    private void collectSeasons() {
        if (Main.isDebug) {
            seasons.get(1).start();
            try {
                seasons.get(1).join();
            } catch (InterruptedException e) {
            }
        } else {
            for (Season season : seasons) {
                season.start();
            }
            for (Season season : seasons) {
                try {
                    season.join();
                } catch (InterruptedException e) {
                    out.println("THREAD ENDED ALREADY");
                }
            }
        }
    }
}
