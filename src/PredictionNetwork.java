
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.Perceptron;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author HON3D
 */
public class PredictionNetwork {//extends Thread {

    public PredictionNetwork() {
        // create new perceptron network 
        NeuralNetwork neuralNetwork = new Perceptron(2,1);//Perceptron(2, 1);
        // create training set 
        DataSet trainingSet = new DataSet(2, 1);
        // add training data to training set (logical OR function) 
        trainingSet.addRow(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainingSet.addRow(new DataSetRow(new double[]{0, 1}, new double[]{1}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 0}, new double[]{1}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 1}, new double[]{1}));
        // learn the training set 
        neuralNetwork.learn(trainingSet);
        // save the trained network into file 
        neuralNetwork.save("or_perceptron.nnet");

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
    }
}