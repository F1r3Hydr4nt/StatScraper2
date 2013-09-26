
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Season extends Thread {

    String year, link;
    List<Month> months;
    List<Match> matches;
    List<Team> teams;

    public Season(String y, String l) {
        year = y;
        link = l;
        months = new ArrayList<>();
        teams = new ArrayList<>();
        //out.println("Season: " + year + " - " + link);
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
            Elements monthLinks = doc.select("a[href]"); // direct a after h3
            String monthName, monthLink;
            for (int l = 0; l < monthLinks.size(); l++) {
                monthName = monthLinks.get(l).text();
                monthLink = monthLinks.get(l).attr("abs:href");
                if ((monthName.contains("Jan") || monthName.contains("Feb")
                        || monthName.contains("Mar")
                        || monthName.contains("Apr")
                        || monthName.contains("May")
                        || monthName.contains("Jun")
                        || monthName.contains("Jul")
                        || monthName.contains("Aug")
                        || monthName.contains("Sep")
                        || monthName.contains("Oct")
                        || monthName.contains("Nov") || monthName
                        .contains("Dec")) && monthLink.contains("results")) {
                    months.add(new Month(monthName, monthLink));
                }
            }
            
            if (!months.isEmpty()) {
                if (Main.isDebug) {
                    months.get(0).getData(teams);
                } else {
                    for (int m = 0; m < months.size(); m++) {
                        months.get(m).getData(teams);
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}
