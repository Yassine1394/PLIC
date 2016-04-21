import java.io.File;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.tools.weka.WekaAttributeSelection;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ASSearch;
import weka.attributeSelection.GainRatioAttributeEval;
import weka.attributeSelection.Ranker;

public class UseWeka {
    /**
     * Shows the basic steps to create use a feature scoring algorithm.
     * 
     * @author Thomas Abeel
     * 
     */
    public static void main(String[] args) throws Exception {
    	/* Load the iris data set */
    	Dataset data = FileHandler.loadDataset(new File("data/iris.data"), 4, ",");
    	/*Create a Weka AS Evaluation algorithm */
    	ASEvaluation eval = new GainRatioAttributeEval();
    	/* Create a Weka's AS Search algorithm */
    	ASSearch search = new Ranker();
    	/* Wrap WEKAs' Algorithms in bridge */
    	WekaAttributeSelection wekaattrsel = new WekaAttributeSelection(eval,search);
    	/* Apply the algorithm to the data set */
    	wekaattrsel.build(data);
    	/* Print out the score and rank  of each attribute */
    	for (int i = 0; i < wekaattrsel.noAttributes(); i++) 
    	    System.out.println("Attribute  " +  i +  "  Ranks  " + wekaattrsel.rank(i) + " and Scores " + wekaattrsel.score(i) );
    }

}