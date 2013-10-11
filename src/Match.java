
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void updateGoals(int h, int a) {
        home.matchGoals=h;
        away.matchGoals=a;
        out.println(home.name+" "+home.matchGoals+" - "+away.matchGoals+" "+away.name);
    }

    void writeDataToFile() {
        File f = new File("trainingData.txt");
    try{
    if(f.exists()==false){
            System.out.println("We had to make a new file.");
            f.createNewFile();
    }
    PrintWriter out = new PrintWriter(new FileWriter(f,true));
    if(home.gamesPlayed>Main.minMatchesPlayed&&away.gamesPlayed>Main.minMatchesPlayed){
    //out.append(home.name+" "+home.homeToNeuralInput()+" "+away.name+" "+away.awayToNeuralInput()+" result "+home.matchGoals+" "+away.matchGoals);
    out.append(home.homeToNeuralInput()+" "+away.awayToNeuralInput()+" "+home.matchGoals+" "+away.matchGoals);
    out.println();
    }
    out.close();
    }catch(IOException e){
        System.out.println("COULD NOT WRITE TO FILE!!");
    }
}

}
