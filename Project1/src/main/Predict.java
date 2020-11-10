package main;

import java.util.Arrays;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Predict {
	private SimpleKMeans model;
	
	  public Predict() {
		  train();
		  test();
	  }
	  
	  private void train() {
			try {
				DataSource src = new DataSource("trainDataset.csv");
		        Instances dt = src.getDataSet();		     
		        
		        String[] options = new String[2];
		        options[0] = "-R";                                    // "range"
		        options[1] = "1";                                     // first attribute
		        Remove remove = new Remove();                         // new instance of filter
		        remove.setOptions(options);                           // set options
		        remove.setInputFormat(dt);                          // inform filter about dataset **AFTER** setting options
		        Instances newData = Filter.useFilter(dt, remove);   // apply filter		        
		        
		        model = new SimpleKMeans();
		        model.setNumClusters(3);
		        model.buildClusterer(newData);
		        System.out.println(model);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
	  }
	  
	  private void test() {
			try {
				ClusterEvaluation eval = new ClusterEvaluation();
		        DataSource src1 = new DataSource("testData.csv");
		        Instances tdt = src1.getDataSet();
		        eval.setClusterer(model);
		        eval.evaluateClusterer(tdt);
		        System.out.println("=============================== evaluation ===============================");
		        System.out.println(eval.clusterResultsToString());
		        System.out.println("# of clusters: " + eval.getNumClusters());
		        System.out.println(Arrays.toString(eval.getClusterAssignments()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
}