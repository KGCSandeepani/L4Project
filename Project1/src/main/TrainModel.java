package main;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.EM;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.gui.explorer.ClustererAssignmentsPlotInstances;
//import weka.gui.explorer.ClustererPanel;
//import weka.gui.visualize.PlotData2D;
import weka.gui.visualize.VisualizePanel;

public class TrainModel {
	private static EM model;
	
	public TrainModel() {
		train();
	}
	
	private static void train() {
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
	        
	        model = new EM();
	        model.setNumClusters(3);
	        model.buildClusterer(newData);
	        weka.core.SerializationHelper.write("D:\\Level 4 Semester 1 My\\Project\\Implementaion\\L4Project\\Project1\\EM.model", model);
	        System.out.println(model);	        	        
	       
	        // evaluate clusterer
	        
	        ClusterEvaluation eval = new ClusterEvaluation();
	        eval.setClusterer(model);
	        eval.evaluateClusterer(newData);
	        
	        ClustererAssignmentsPlotInstances plotInstances = new ClustererAssignmentsPlotInstances();
	        plotInstances.setClusterer(model);
	        plotInstances.setInstances(newData);
	        plotInstances.setClusterEvaluation(eval);
	        plotInstances.setUp();
	        
	        // setup visualization
	        // taken from: ClustererPanel.startClusterer()
	        //PlotData2D predData = new PlotData2D(newData);//ClustererPanel.setUpVisualizableInstances(newData, eval);
	        String name = (new SimpleDateFormat("HH:mm:ss - ")).format(new Date());
	        String cname = model.getClass().getName();
	        if (cname.startsWith("weka.clusterers."))
	          name += cname.substring("weka.clusterers.".length());
	        else
	          name += cname;

	        VisualizePanel vp = new VisualizePanel();
	        vp.setName(name + " (" + newData.relationName() + ")");
	        //predData.setPlotName(name + " (" + newData.relationName() + ")");
	        //vp.addPlot(predData);
	        vp.addPlot(plotInstances.getPlotData("plot name"));
	        
	        // display data
	        // taken from: ClustererPanel.visualizeClusterAssignments(VisualizePanel)
	        String plotName = vp.getName();
	        final javax.swing.JFrame jf = 
	          new javax.swing.JFrame("Weka Clusterer Visualize: " + plotName);
	        jf.setSize(500,400);
	        jf.getContentPane().setLayout(new BorderLayout());
	        jf.getContentPane().add(vp, BorderLayout.CENTER);
	        jf.addWindowListener(new java.awt.event.WindowAdapter() {
	          public void windowClosing(java.awt.event.WindowEvent e) {
	            jf.dispose();
	          }
	        });
	        jf.setVisible(true);
	        
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
  }
}
