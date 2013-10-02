
import java.io.File;
import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Month {

    List<Day> days;
    String name, link;

    public Month(String n, String l) {
        name = n;
        link = l;
        out.println("Month: " + name + " - " + link);
        days = new ArrayList<>();
        // TODO Auto-generated constructor stub
    }

    public void getData(List<Team> teams) {
        out.println("Connecting to - " + link);

        Document doc;
        try {
            doc = Jsoup.connect(link).timeout(Main.timeout).ignoreHttpErrors(true)
                    .followRedirects(true).get();
            // Find out all Seasons
            Elements monthLinks = doc.select("a[href]"); // direct a after h3
            String date, dateLink, dateMatches;
            for (int l = 0; l < monthLinks.size(); l++) {
                date = monthLinks.get(l).text();
                dateLink = monthLinks.get(l).attr("abs:href");
                dateMatches = monthLinks.get(l).attr("title");
                if (dateMatches.contains("match")) {
                    days.add(new Day(date, dateLink));
                }
            }

            // create a new file
            File dir = new File(name);
            dir.mkdir();
            if (!days.isEmpty()) {
                if (Main.isDebug) {
                    days.get(0).getMatches(teams);
                    days.get(0).getTableAtEndOfDay(teams);
                } else {
                    for(Day d:days){
                        d.getMatches(teams);
                        d.getTableAtEndOfDay(teams);
                    }
                }
            }
        } catch (IOException e) {
                        System.err.println(e.getMessage());
                        System.exit(0);
        }
    }

}
