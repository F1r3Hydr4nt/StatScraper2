
import java.io.File;
import java.io.FileOutputStream;
import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Day {

    List<Match> matches;
    String date, link;

    public Day(String d, String l) {
        date = d;
        link = l;
        out.println("MatchDay: " + date + " " + link);
        matches = new ArrayList<>();
    }

    public void getMatches(List<Team> teams) {
        out.println("Connecting to - " + link);

        Document doc;
        try {
            doc = Jsoup.connect(link).timeout(Main.timeout).ignoreHttpErrors(true)
                    .followRedirects(true).get();
            // Find out all Seasons
            Elements scores = doc.select("td.c1");
            Elements teamNames = doc.select("td.team"); // direct a after h3
            String homeName, awayName;
            Team homeTeam, awayTeam;
            for (int m = 0; m < teamNames.size(); m += 2) {
                homeTeam = awayTeam = null;
                homeName = teamNames.get(m).text();
                awayName = teamNames.get(m + 1).text();
                // out.println(homeName);
                // out.println("VS");
                // out.println(awayName);
                // check if teams already in League
                for (int t = 0; t < teams.size(); t++) {
                    if (teams.get(t).name.contains(homeName)) {
                        homeTeam = teams.get(t);
                    }
                    if (teams.get(t).name.contains(awayName)) {
                        awayTeam = teams.get(t);
                    }
                }
                if (homeTeam == null) {
                    // out.println("Creating... "+homeName);
                    homeTeam = new Team(homeName);
                    teams.add(homeTeam);
                }
                if (awayTeam == null) {
                    // out.println("Creating... "+awayName);
                    awayTeam = new Team(awayName);
                    teams.add(awayTeam);
                }
                matches.add(new Match(homeTeam, awayTeam));
            }
            int matchCounter = 0;
            for (int s = 0; s < matches.size() * 2; s += 2) {
                String[] goals = scores.get(s).text().split("-");
                if (isInteger(goals[0]) && isInteger(goals[1])) {
                    matches.get(matchCounter).update(
                            Integer.parseInt(goals[0]),
                            Integer.parseInt(goals[1]));
                }
                matchCounter++;
            }
            // for matches add score
        } catch (IOException e) {
        }
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}