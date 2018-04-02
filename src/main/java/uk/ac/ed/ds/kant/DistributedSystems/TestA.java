package uk.ac.ed.ds.kant.DistributedSystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestA {

	public static void main(String [] argv) {

    	//TODO get file input from command line
		
    			//storing file name
    			//String fileName = "barbell_graph";
    			//String fileName = "complete_graph";
    			//String fileName = "star_graph";
    			//String choice = "q3";
				//String fileName = argv[1];
    			//String startNode = "2";
				String choice = argv[0];
				String fileName = argv[1];
    			String startNode = argv[2];
    			//System.out.println(argv[0]);
    			//System.out.println(argv[1]);
    			
    			File graphFile = new File(System.getProperty("user.dir")+"//"+fileName);
    			Scanner input = null;
    			
    			 
    			
    			try {
    				input = new Scanner(graphFile);
    				input.useDelimiter("\\n|:");
    				
    				
    				
    				ArrayList<String> nodes = new ArrayList<String>();
    				ArrayList<String> nodeEdge = new ArrayList<String>();
    			 		    	
    				//count number of nodes
    				int numberOfNodes = 0;
    				
    			   				
    				while(input.hasNext()){
    					nodes.add(input.next());
    					numberOfNodes++;
        				
    					if(input.hasNext()) {
    						nodeEdge.add(input.next());
    					}
    					
    				}
    				
    				if(choice.equals("q1")) {
    				// Pass the nodeEdge to the network thread
    				ExecutorService executor = Executors.newSingleThreadExecutor();
    				executor.submit(new NetworkB(nodeEdge, numberOfNodes, Integer.valueOf(startNode)));
    				executor.shutdown();
    				}
    				
    				if(choice.equals("q2")) {
    				ExecutorService executorVisual = Executors.newSingleThreadExecutor();
    				executorVisual.submit(new VisualNetworkB(nodes, nodeEdge, numberOfNodes, Integer.valueOf(startNode)));
    				executorVisual.shutdown();
    				}
    				
    				if(choice.equals("q3")) {
        				ExecutorService executorVisual = Executors.newSingleThreadExecutor();
        				executorVisual.submit(new GraphNetworkB(nodeEdge, numberOfNodes, Integer.valueOf(startNode)));
        				executorVisual.shutdown();
        			}
    			
    			} catch (FileNotFoundException e) {
    				e.printStackTrace();
    			}
    			
    	    } 		    
    		
    		
	
}
