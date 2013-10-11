
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.TransferFunctionType;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HON3D
 */
public class PredictionNetwork {//extends Thread {

    //public PredictionNetwork() throws FileNotFoundException {
     public static void main(String[] args) throws IOException {
    // create new perceptron network 
        //NeuralNetwork neuralNetwork = new Perceptron(26,2);//Perceptron(2, 1);
         
         // create multi layer perceptron
MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, 26, 14, 2);

        Scanner scan = new Scanner(new File("trainingData.txt"));
        ArrayList<String> lineList = new ArrayList<String>();
        while (scan.hasNextLine()){
                lineList.add(scan.nextLine());
        }
        scan.close();
        // create training set 
        DataSet trainingSet = new DataSet(26,2);
        for(String s:lineList){
            String split[]=s.split(" ");
            double[] input = new double[26];
            double[] output =  new double[2];
           /* //skip team name
for (int i = 1; i<14; i++) {
    input[i-1]=Double.valueOf(split[i]);
}
    //skip team name
for (int i = 15; i<27; i++) {
    input[i-1]=Double.valueOf(split[i]);
}
output[0]=Double.valueOf(split[28]);
output[1]=Double.valueOf(split[29]);
*/
            for(int i=0;i<26;i++){
    input[i]=Double.valueOf(split[i]);
            }
output[0]=Double.valueOf(split[26]);
output[1]=Double.valueOf(split[27]);
            trainingSet.addRow(new DataSetRow(input, output));
        }
// learn the training set
myMlPerceptron.learn(trainingSet);

// test perceptron
System.out.println("Testing trained neural network");
testNeuralNetwork(myMlPerceptron, trainingSet);

// save trained neural network
myMlPerceptron.save("myMlPerceptron.nnet");

// load saved neural network
NeuralNetwork loadedMlPerceptron = NeuralNetwork.load("myMlPerceptron.nnet");

// test loaded neural network
System.out.println("Testing loaded neural network");
testNeuralNetwork(loadedMlPerceptron, trainingSet);
        // learn the training set 
        //neuralNetwork.learn(trainingSet);
        // save the trained network into file 
        //neuralNetwork.save("or_perceptron.nnet");
/*
        // load the saved network 
        neuralNetwork = NeuralNetwork.load("or_perceptron.nnet");
        // set network input 
        neuralNetwork.setInput(1, 1);
        // calculate network 
        neuralNetwork.calculate();
        // get network output 
        double[] networkOutput = neuralNetwork.getOutput();
        for (double d : networkOutput) {
            System.out.print(d + " ");
        }
        */
    }
     public static void testNeuralNetwork(NeuralNetwork nnet, DataSet tset) {

for(DataSetRow dataRow : tset.getRows()) {

nnet.setInput(dataRow.getInput());
nnet.calculate();
double[ ] networkOutput = nnet.getOutput();
System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
System.out.println(" Output: " + Arrays.toString(networkOutput) );

}

}
}
