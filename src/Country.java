
import java.util.List;

import static java.lang.System.*;

public class Country extends Thread {

    String name;
    List<League> leagues;

    public Country(String n, List<League> l) {
        name = n;
        leagues = l;
        out.println("Country: " + name + " #Leagues: " + leagues.size());
                        System.err.println("");
    }

    @Override
    public void start() {
        int debugNo = 0;
        if (Main.isDebug) {
            if (!leagues.isEmpty()) {
                leagues.get(debugNo).start();
                try {
                    leagues.get(debugNo).join();
                } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                        System.exit(0);
                }
            }
        } else {
            for (League league : leagues) {
            }
            for (League league : leagues) {
                try {
                    league.start();
                    league.join();
                } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                        System.exit(0);
                }
            }
        }
    }
}
