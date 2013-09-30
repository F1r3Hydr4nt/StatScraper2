
public class Team {

    public class VenueStats {

        int gamesPlayed, gamesWon, gamesDrawn, gamesLost, goalsFor, goalsAgainst, goalDifference, totalPoints;

        public VenueStats() {
            gamesPlayed = totalPoints = gamesWon = gamesDrawn = gamesLost = goalsFor = goalsAgainst = goalDifference = 0;
        }

        private String to_string() {
            return (gamesWon+" "+gamesDrawn+" "+gamesLost+" "+goalsFor+" "+goalsAgainst);
        }
    }
    String name;
    int matchGoals;
    VenueStats homePerformance, awayPerformance, overallPerformance;
    int tablePosition,gamesPlayed, goalDifference, totalPoints;

    public Team(String n) {
        name = n;
        homePerformance  = new VenueStats();
        awayPerformance  = new VenueStats();
        overallPerformance = new VenueStats();
        tablePosition=matchGoals=gamesPlayed=goalDifference=totalPoints=0;
    }
    void setStats(String data[]){
        //tablePosition, gamesplayed, Overall WDLFA, Home, Away, GD, 
        tablePosition = Integer.parseInt(data[0]);
        gamesPlayed = Integer.parseInt(data[1]);
        
        overallPerformance.gamesWon = Integer.parseInt(data[2]);
        overallPerformance.gamesDrawn = Integer.parseInt(data[3]);
        overallPerformance.gamesLost= Integer.parseInt(data[4]);
        overallPerformance.goalsFor= Integer.parseInt(data[5]);
        overallPerformance.goalsAgainst= Integer.parseInt(data[6]);
        
        homePerformance.gamesWon = Integer.parseInt(data[7]);
        homePerformance.gamesDrawn = Integer.parseInt(data[8]);
        homePerformance.gamesLost= Integer.parseInt(data[9]);
        homePerformance.goalsFor= Integer.parseInt(data[10]);
        homePerformance.goalsAgainst= Integer.parseInt(data[11]);
        
        awayPerformance.gamesWon = Integer.parseInt(data[12]);
        awayPerformance.gamesDrawn = Integer.parseInt(data[13]);
        awayPerformance.gamesLost= Integer.parseInt(data[14]);
        awayPerformance.goalsFor= Integer.parseInt(data[15]);
        awayPerformance.goalsAgainst= Integer.parseInt(data[16]);
        
        goalDifference = Integer.parseInt(data[17]);
        totalPoints = Integer.parseInt(data[18]);
        printStats();
    }
    void printStats(){
        System.out.println(tablePosition+" "+name+" "+gamesPlayed+" "+overallPerformance.to_string()+" "+homePerformance.to_string()+" "+awayPerformance.to_string()+" "+goalDifference+" "+totalPoints);
    }
}
