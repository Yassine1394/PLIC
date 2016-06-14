import weka.attributeSelection.*;
import weka.core.*;
import java.io.FileWriter;
import weka.filters.Filter;
import weka.core.converters.CSVLoader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class CSV2SU {
	
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    	
    // number of features to be selected
    private static final int res_features = 100;
    // indices of selected features
    private static int[] f_index;
    // the value of all instances in data for a particular attribute
    private static double[] ds;
  
    /**
     * uses the filter
     */
    protected static void useFilter(Instances data) throws Exception {
  	  
        //System.out.println("\nDiscretize data.");    
        weka.filters.unsupervised.attribute.Discretize discretize = new weka.filters.unsupervised.attribute.Discretize();
        discretize.setFindNumBins(true);
        discretize.setInputFormat(data);
        Instances dis_data = Filter.useFilter(data, discretize);
        
        SymmetricalUncertAttributeEval eval = new SymmetricalUncertAttributeEval();
        Ranker search = new Ranker();
        search.setGenerateRanking(true);
        search.setNumToSelect(res_features);
        search.setThreshold(-1.7976931348623157E308);
        
        weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(dis_data);
        
        //System.out.println("\nUse SU rank and select features.");
        Instances newData = Filter.useFilter(dis_data, filter);
        
        int i, j;
        f_index = new int[res_features];
        for (i = 0; i < res_features; ++i)
			f_index[i] = dis_data.attribute(newData.attribute(i).name()).index();
        Arrays.sort(f_index);
        FileWriter fileWriter = null;
        
        try {
            fileWriter = new FileWriter("data\\SRBCT_f.csv");
            fileWriter.append("label");
            fileWriter.append(COMMA_DELIMITER);

            ds = data.attributeToDoubleArray(data.classIndex());
            for (i = 0; i < data.numInstances(); ++i) {
            	fileWriter.append(String.valueOf(ds[i]));
            	fileWriter.append(COMMA_DELIMITER);
            }
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (i = 0; i < res_features; ++i) {
                ds = data.attributeToDoubleArray(f_index[i]);

                fileWriter.append(String.valueOf(f_index[i] + 1));
                fileWriter.append(COMMA_DELIMITER);

            	for (j = 0; j < data.numInstances(); ++j) {
                	fileWriter.append(String.valueOf(ds[j]));
                	fileWriter.append(COMMA_DELIMITER);
                }
            	fileWriter.append(NEW_LINE_SEPARATOR);
            }

        } catch (Exception e) {
            //System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
            	fileWriter.flush();
            	fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        
    }

    /**
     * takes a dataset as first argument
     *
     * @param args        the commandline arguments
     * @throws Exception  if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // load data
        //System.out.println("\n0. Loading data");
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(args[0]));
        Instances data = loader.getDataSet();
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        useFilter(data);
    }
}