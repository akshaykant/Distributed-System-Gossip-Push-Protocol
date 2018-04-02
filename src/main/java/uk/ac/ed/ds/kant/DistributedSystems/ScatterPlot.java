package uk.ac.ed.ds.kant.DistributedSystems;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ScatterPlot extends JFrame{
	
	private static final long serialVersionUID = 6294689542092367723L;
	ArrayList<DataS> data;

	  public ScatterPlot(String title, ArrayList<DataS> graphData) {
	    super(title);
	    this.data = graphData;

	    // Create dataset
	    XYDataset dataset = createDataset();

	    // Create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(
	        "Probability Time Chart", 
	        "probability p", "time(in milliseconds)", dataset);

	    
	    //Changes background color
	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(255,228,196));
	    
	   
	    // Create Panel
	    ChartPanel panel = new ChartPanel(chart);
	    setContentPane(panel);
	  }

	  private XYDataset createDataset() {
	    XYSeriesCollection dataset = new XYSeriesCollection();

	   
	    XYSeries series1 = new XYSeries("probabilty_time");
	    for(DataS i : data) {
	    	 series1.add(i.getProb(), i.getTime());
	    }
	   
	  

	    dataset.addSeries(series1);
	    
	 

	    return dataset;
	  }
	  
}