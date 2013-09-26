
public class Team {

    public class VenueStats {

        int matchesPlayed, totalPoints, gamesWon, gamesDrawn, gamesLost, goalsFor, goalsAgainst, goalDifference;

        public VenueStats() {
            matchesPlayed = totalPoints = gamesWon = gamesDrawn = gamesLost = goalsFor = goalsAgainst = goalDifference = 0;
        }

        private String to_string() {
            return (matchesPlayed+" "+totalPoints+" "+gamesWon+" "+gamesDrawn+" "+gamesLost+" "+goalsFor+" "+goalsAgainst+" "+goalDifference);
        }
    }
    String name;
    int matchGoals;
    VenueStats home, away, overall;

    public Team(String n) {
        name = n;
        home  = new VenueStats();
        away  = new VenueStats();
        overall = new VenueStats();
    }
    void updateStats(boolean home, int h, int a){
        if(home){        
            updateHomeStats(h,a);
        }
        else{
            updateAwayStats(h,a);
        }
        updateOverallStats();
    }
    void updateHomeStats(int h, int a){
        home.matchesPlayed++;
        home.goalsFor+=h;
        home.goalsAgainst+=a;
        home.goalDifference+=h-a;
        if(h>a){//win
            home.gamesWon++;
            home.totalPoints+=3;
        }else if(h==a){//draw
            home.gamesDrawn++;
            home.totalPoints+=1;
        }else{//lose
            home.gamesLost++;
        }
    }
    void updateAwayStats(int h, int a){
        away.matchesPlayed++;
        away.goalsFor+=a;
        away.goalsAgainst+=h;
        away.goalDifference+=a-h;
        if(a>h){//win
            away.gamesWon++;
            away.totalPoints+=3;
        }else if(a==h){//draw
            away.gamesDrawn++;
            away.totalPoints+=1;
        }else{//lose
            away.gamesLost++;
        }
    }
    void updateOverallStats(){
        overall.matchesPlayed=home.matchesPlayed+away.matchesPlayed;
        overall.totalPoints=home.totalPoints+away.totalPoints;
        overall.gamesWon=home.gamesWon+away.gamesWon;
        overall.gamesDrawn=home.gamesDrawn+away.gamesDrawn;
        overall.gamesLost=home.gamesLost+away.gamesLost;
        overall.goalsFor=home.goalsFor+away.goalsFor;
        overall.goalsAgainst=home.goalsAgainst+away.goalsAgainst;
        overall.goalDifference=home.goalDifference+away.goalDifference;
    }
    void printStats(){
        System.out.println(name+" "+overall.to_string()+" Goals: "+matchGoals);
    }
}
