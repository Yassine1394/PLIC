import java.io.File;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import net.sf.javaml.featureselection.scoring.GainRatio;

public class TutorialDataLoader {

    public static void main(String[] args) throws Exception {
        Dataset data = FileHandler.loadDataset(new File("data/iris.data"), 4, ",");
//        System.out.println(data);
        
//        data = FileHandler.loadSparseDataset(new File("data/sparse.tsv"), 0, " ", ":");
//        System.out.println(data);
//        FileHandler.exportDataset(data, new File("output.txt"));
        
        GainRatio ga = new GainRatio();
        /* Apply the algorithm to the data set */
        ga.build(data);
        /* Print out the score of each attribute */
        for (int i = 0; i < ga.noAttributes(); i++)
            System.out.println(ga.score(i));

    }

}