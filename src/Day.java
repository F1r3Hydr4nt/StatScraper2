
import java.io.File;
import java.io.FileOutputStream;
import static java.lang.System.out;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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
                    matches.get(matchCounter).updateGoals(
                            Integer.parseInt(goals[0]),
                            Integer.parseInt(goals[1]));
                }
                matchCounter++;
            }
            // for matches add score
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(0);
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

    void getTableAtEndOfDay(List<Team> teams) {
        //get link to table
        link = link.replace("results", "table");
        out.println("Connecting to - " + link);
        out.println("Pos  P  W D L F A   W D L F A   W D L F A  GD  Pt");
        try {
            Document doc;
            doc = Jsoup.connect(link).timeout(Main.timeout).ignoreHttpErrors(true)
                    .followRedirects(true).get();
            // Find out all Seasons           
            Elements elements = doc.getAllElements();
            Elements teamStatsOdd = doc.getElementsByClass("c0");
            Elements teamStatsEven = doc.getElementsByClass("c1");
            Elements teamStats = new Elements();
            int numberOfStatistics = 19;
            for (int i = 0; i < teamStatsOdd.size(); i++) {
                teamStats.add(teamStatsOdd.get(i));
                if (i < teamStatsEven.size()) {
                    teamStats.add(teamStatsEven.get(i));
                }
            }
            for (Element stats : teamStats) {
                for (Team team : teams) {
                    if (stats.text().contains(team.name)) {
                        //out.println(stats.text());
                        String splitArray[], statArray[];
                        statArray = new String[19];
                        
                        splitArray = stats.text().split(" ");
                        for(int i=0;i<19;i++)statArray[i]="";
                        //out.print(splitArray[0] + " " + splitArray[1]+" ");
                        statArray[0]=splitArray[0];
                        int i=1;
                        //Work back down the array as it is split on spaces (i.e. "Man Utd" is more entries than "Arsenal")
                        for (int s = splitArray.length - numberOfStatistics + 1; s < splitArray.length; s++) {
                            //out.print(splitArray[s] + " ");
                            statArray[i]=splitArray[s];
                            i++;
                        }
                        //out.println();
                        team.setStats(statArray);
                    }
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Day.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}