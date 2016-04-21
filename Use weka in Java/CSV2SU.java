import weka.attributeSelection.*;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.core.converters.CSVLoader;
import java.io.File;
/**
 * performs attribute selection using CfsSubsetEval and GreedyStepwise
 * (backwards) and trains J48 with that. Needs 3.5.5 or higher to compile.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class CSV2SU {

  	/**
  	 * print double array ds
  	 * @param ds
  	 */
  	public static void printArray(double[] ds) {
        System.out.println(ds.length);
    //		for (Double element : ds){
    //		    System.out.printf("%s ", element);
    //		    System.out.println();
    //		}
  	}
  	
    /**
     * uses the filter
     */
    protected static void useFilter(Instances data) throws Exception {
  	  
        System.out.println("\nDiscretize data.");    
        weka.filters.unsupervised.attribute.Discretize discretize = new weka.filters.unsupervised.attribute.Discretize();
        discretize.setFindNumBins(true);
        discretize.setInputFormat(data);
        Instances dis_data = Filter.useFilter(data, discretize);
        
        SymmetricalUncertAttributeEval eval = new SymmetricalUncertAttributeEval();
        Ranker search = new Ranker();
        search.setGenerateRanking(true);
        search.setNumToSelect(100);
        search.setThreshold(-1.7976931348623157E308);
        
        weka.filters.supervised.attribute.AttributeSelection filter = new weka.filters.supervised.attribute.AttributeSelection();
        filter.setEvaluator(eval);
        filter.setSearch(search);
        filter.setInputFormat(dis_data);
        
        System.out.println("\nUse SU rank and select features.");
        Instances newData = Filter.useFilter(dis_data, filter);

        System.out.println(newData.numAttributes());

        //System.out.println(newData.relationName());
        //System.out.println(data.classAttribute());
        for (int i = 0; i < 100; ++i) {
        //System.out.println(newData.attribute(i).index());
        	  printArray(newData.attributeToDoubleArray(i));
        }
        System.out.println(newData.numInstances());
        System.out.println("Finished!");
    }

    /**
     * takes a dataset as first argument
     *
     * @param args        the commandline arguments
     * @throws Exception  if something goes wrong
     */
    public static void main(String[] args) throws Exception {
        // load data
        System.out.println("\n0. Loading data");
        // load CSV
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File(args[0]));
        Instances data = loader.getDataSet();
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);

        useFilter(data);
    }
}