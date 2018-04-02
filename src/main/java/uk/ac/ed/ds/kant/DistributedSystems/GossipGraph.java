package uk.ac.ed.ds.kant.DistributedSystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

public class GossipGraph {

	public static void main(String [] argv) {
		
		// get file input from command line
		//storing file name
		String fileName = "barbell_graph";

		File graphFile = new File(System.getProperty("user.dir")+"//"+fileName);
		Scanner input = null;
		try {
			input = new Scanner(graphFile);
			input.useDelimiter("\\n|:");
			
			
			//graph
			System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
	        Graph graph = new  SingleGraph("test 1");
	        //graph.display();
	 /*       graph.addAttribute("ui.antialias");
	        graph.addAttribute("stylesheet", "graph {padding : 50px;}"
	                + "node {size: 100px; fill-mode: plain;}"
	                + "node.red {fill-color: red;}"
	                + "node.blue {fill-color: blue;}");*/
	        
	        graph.addAttribute("ui.stylesheet", styleSheet);
	        graph.setAutoCreate(true);
	        graph.setStrict(false);
	        
			ArrayList<String> nodeEdge = new ArrayList<String>();
		 		    
			while(input.hasNext()) {
				graph.addNode(input.next());
				if(input.hasNext()) {
					nodeEdge.add(input.next());
				}
			}
			
			for(String nodeE : nodeEdge) {
				String[] eachEdge = null;
				eachEdge = nodeE.split(",");
				for(String i : eachEdge) {
					String par = nodeEdge.indexOf(nodeE) + "";
					System.out.println(par.concat(i) + " " + par  + " " + i);
			
					
					graph.addEdge(par.concat(i), par, i);
				}
			}
			
			
			 for (Node node : graph) {
		            node.addAttribute("ui.label", node.getId());
		        }
			 
			 graph.display();
			 
			 explore(graph.getNode("1"));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
    } 
	
	 public static void explore(Node source) {
		 
		 
		 
	        Iterator<? extends Node> k = source.getBreadthFirstIterator();

	    	//set time for the start node and print
		 	source.setAttribute("start_timer", System.currentTimeMillis());
		 	//source.setAttribute("time", (System.currentTimeMillis() - System.currentTimeMillis()));
		 	//System.out.println("Node :" + source.getId() + " time: "+ source.getAttribute("time") );
		 	
	        while (k.hasNext()) {
	            Node next = k.next();
	            next.setAttribute("ui.class", "marked");
	            //set time for each node and print
	            next.setAttribute("time", (System.currentTimeMillis() - Long.parseLong(String.valueOf(source.getAttribute("start_timer")))));
			 	System.out.println("Node :" + next.getId() + " time: "+ next.getAttribute("time") );
	            sleep();
	        }
	    }
	    
	    protected static void sleep() {
	        try { Thread.sleep(1000); } catch (Exception e) {}
	    }
	    
	
	  protected static String styleSheet =
	            "node {" +
	            "	fill-color: red;" +
	            "}" +
	            "node.marked {" +
	            "	fill-color: green;" +
	            "}";
	
}
