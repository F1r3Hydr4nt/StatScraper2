
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.System.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Match {

    Team home, away;
    private String file;

    public Match(Team h, Team a) {
        home = h;
        away = a;
    }

    public void update(int h, int a) {
        home.matchGoals=h;
        away.matchGoals=a;
        home.printStats();
        away.printStats();
        home.updateStats(true,h,a);
        away.updateStats(false,h,a);
        out.println(home.name + " " + home.matchGoals + " vs "
                + away.matchGoals + " " + away.name);
       
    }

}
