import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
//import weka.core.converters.ConverterUtils;

import java.io.File;
 
public class CSV2Arff {
    /**
     * take 2 arguments
     * @param sourcepath
     * @param destpath
     * @throws Exception
     */
    public static void Convert(String sourcepath,String destpath) throws Exception
    {
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(sourcepath));
        Instances data = loader.getDataSet();

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(destpath));
        saver.setDestination(new File(destpath));
        saver.writeBatch();
         
        ConverterUtils.DataSink.write(destpath, data);
    }
	
    /**
    * takes 2 arguments:
    * - CSV input file
    * - ARFF output file
    */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
          System.out.println("\nUsage: CSV2Arff <input.csv> <output.arff>\n");
          System.exit(1);
        }

        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(args[0]));
        Instances data = loader.getDataSet();

        // save ARFF
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(new File(args[1]));
        saver.setDestination(new File(args[1]));
        saver.writeBatch();

        //Convert("data\\SRBCT.csv", "data\\SRBCT2.arff");
    }

}