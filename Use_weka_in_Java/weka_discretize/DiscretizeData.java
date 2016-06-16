import java.io.*;
import java.util.Vector;

import weka.core.*;
import weka.core.converters.CSVLoader;
import weka.filters.Filter;
 
/**
 * Shows how to generate compatible train/test sets using the Discretize
 * filter.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class DiscretizeData {
	
	//Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    //class label of samples
    private static double[] ds;
    
	/**
     * loads the given ARFF file and sets the class attribute as the last
     * attribute.
     *
     * @param filename    the file to load
     * @throws Exception  if somethings goes wrong
     */
    protected static Instances load(String filename) throws Exception {
        Instances       result;
        BufferedReader  reader;

        reader = new BufferedReader(new FileReader(filename));
        result = new Instances(reader);
        result.setClassIndex(result.numAttributes() - 1);
        reader.close();

        return result;
    }
    
    /**
     * Renames all the labels of nominal attributes to numbers, they way they
	 * appear, e.g., attribute a1 has the labels "what", "so" and "ever" are renamed
	 * to "0", "1" and "2".
     */
    protected static void renameAttribute(Instances data) throws Exception {
    	// determine attributes to modify
        Integer[] indices = null;

        Vector<Integer> v = new Vector<Integer>();
        for (int i = 0; i < data.numAttributes() - 1; i++) {
            if (data.attribute(i).isNominal()) {
                v.add(new Integer(i));
            }
        }
        indices = v.toArray(new Integer[v.size()]);

        // rename labels of all nominal attributes
        for (Integer indice : indices) {
            int attInd = indice.intValue();
            Attribute att = data.attribute(attInd);
            for (int n = 0; n < att.numValues(); n++) {
                data.renameAttributeValue(att, att.value(n), "" + n);
            }
        }
    }
    
    /**
     * EqualWidthDiscretize use cross-entropy find best bin number
     */
    protected static void EqualWidthDiscretize(Instances inputTrain, Instances inputTest, String outputTrainFile, String outputTestFile) throws Exception {
    	
    	weka.filters.unsupervised.attribute.Discretize discretize = new weka.filters.unsupervised.attribute.Discretize();
    	//discretize.setUseBinNumbers(true);
    	discretize.setFindNumBins(true);
        discretize.setInputFormat(inputTrain);
        
        // apply filter
        inputTrain = Filter.useFilter(inputTrain, discretize);
        inputTest  = Filter.useFilter(inputTest,  discretize);
        
        renameAttribute(inputTrain);
        renameAttribute(inputTest);
        
        save(inputTrain, outputTrainFile);
        save(inputTest, outputTestFile);
    	
    }
    
    /**
     * default Bin number = 10
     */
    protected static void EqualFrequencyDiscretize(Instances inputTrain, Instances inputTest, String outputTrainFile, String outputTestFile) throws Exception {
        
    	weka.filters.unsupervised.attribute.Discretize discretize = new weka.filters.unsupervised.attribute.Discretize();
    	discretize.setUseEqualFrequency(true);
        discretize.setInputFormat(inputTrain);
         
        // apply filter
        inputTrain = Filter.useFilter(inputTrain, discretize);
        inputTest  = Filter.useFilter(inputTest,  discretize);
        
        renameAttribute(inputTrain);
        renameAttribute(inputTest);
        
        System.out.println(inputTrain.numAttributes());
        save(inputTrain, outputTrainFile);
        save(inputTest, outputTestFile);
    }
    
    /**
     * default Use Usama M. Fayyad and Keki B. Irani's MDL criterion
     */
    protected static void MDLDiscretize(Instances inputTrain, Instances inputTest, String outputTrainFile, String outputTestFile) throws Exception {
        
        weka.filters.supervised.attribute.Discretize discretize = new weka.filters.supervised.attribute.Discretize();
        discretize.setInputFormat(inputTrain);
           
        // apply filter
        inputTrain = Filter.useFilter(inputTrain, discretize);
        inputTest  = Filter.useFilter(inputTest,  discretize);
        
        renameAttribute(inputTrain);
        renameAttribute(inputTest);
        
        save(inputTrain, outputTrainFile);
        save(inputTest, outputTestFile);
    }
 
    /**
     * saves the data to the specified file
     *
     * @param data        the data to save to a file
     * @param filename    the file to save the data to
     * @throws Exception  if something goes wrong
     */
    protected static void save(Instances data, String filename) throws Exception {
    	FileWriter fileWriter = null;
        int i,j;
        try {
            fileWriter = new FileWriter(filename);
            fileWriter.append("ID");
            fileWriter.append(COMMA_DELIMITER);
            
            // print the class label index of data
            // System.out.println(data.classIndex());
            ds = data.attributeToDoubleArray(data.classIndex());
            for (i = 0; i < data.numInstances(); ++i) {
            	fileWriter.append(String.valueOf(ds[i]));
            	fileWriter.append(COMMA_DELIMITER);
            }
            fileWriter.append(NEW_LINE_SEPARATOR);

            for (i = 0; i < data.numAttributes()-1; ++i) {
                ds = data.attributeToDoubleArray(i);

                fileWriter.append(String.valueOf(i));
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
     * Takes 3 arguments:
     *
     * input train file
     * input test file
     * method id
     * output train file
     * output test file
     *
     * @param args        the commandline arguments
     * @throws Exception  if something goes wrong
     */
    public static void main(String[] args) throws Exception {

    	if (args.length < 3)
    		throw new Exception("Needs at least input file, output file and discretize method!");
    	// load CSV
        CSVLoader loader = new CSVLoader();
        
        loader.setSource(new File(args[0]));
        Instances trainData = loader.getDataSet();
        if (trainData.classIndex() == -1)
        	trainData.setClassIndex(trainData.numAttributes() - 1);
        
        loader.setSource(new File(args[1]));
        Instances testData = loader.getDataSet();
        if (testData.classIndex() == -1)
        	testData.setClassIndex(testData.numAttributes() - 1);
        
        switch(Integer.parseInt(args[2])){
	        case 0: EqualWidthDiscretize(trainData, testData, args[0], args[1]); break;
	        case 1: EqualWidthDiscretize(trainData, testData, args[0], args[1]); break;
	        case 2: EqualWidthDiscretize(trainData, testData, args[0], args[1]); break;
	        case 4: break;
	        default: throw new Exception("Discretize argument should be 0, 1 or 2!");
        }

   }
}
