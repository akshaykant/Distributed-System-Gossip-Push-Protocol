package uk.ac.ed.ds.kant.DistributedSystems;


import java.util.ArrayList; 
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GraphNetworkB implements Runnable{
	
	ArrayList<String> nodeEdge;
	int numberOfNodes;
	int startNode;
	boolean[] nodes;
	int countVisitedNodes;
	Queue<Integer> queue;
	ExecutorService executorPool;
	ArrayList<DataS> graphData;
	double randVal;
	ArrayList<String> tempNodeEdge;
	
	public GraphNetworkB(ArrayList<String> nodeEdge, int numberOfNodes, int startNode) {
		this.nodeEdge = nodeEdge;
		this.numberOfNodes = numberOfNodes;
		this.startNode = startNode;
	
	}

    @SuppressWarnings("unchecked")
	public void init() {
    	//create the array with number of nodes to manage the gossip passed
    	nodes = new boolean[numberOfNodes];
    	//set the isVisited for all nodes as false
    	Arrays.fill(nodes, false);
    	//set the counter for visited nodes
    	countVisitedNodes = 0;sleep();
    	//Queue for sending gossip messages to the nodes;
    	queue = new LinkedList<Integer>();
    	//thread pool
    	executorPool = Executors.newFixedThreadPool(numberOfNodes);
    	
    	graphData = new ArrayList<DataS>();
    	
    	tempNodeEdge = (ArrayList<String>) nodeEdge.clone();
    
    	
    }
    
    @SuppressWarnings("unchecked")
	public void communicate() {
    	
    	for(double i = 0.05; i <= 0.96; i = i + 0.05) {
    		
    		System.out.println("Waiting.. Probability "+ i);
    		
    		
    		//set the isVisited for all nodes as false
        	Arrays.fill(nodes, false);
    		
        	//set the counter for visited nodes
        	countVisitedNodes = 0;
        	
        	queue.clear();
        	
        	nodeEdge = (ArrayList<String>) tempNodeEdge.clone();
        	long startT = System.currentTimeMillis();
    		
    
        	
    	//get the start node and add its edges to the queue and set it null in nodeEdge. Also check if no edges. 
    	//Mark the node visited, increase the counter and check for finish.
    	if(hasEdges(Integer.valueOf(startNode))) {
    		
    		boolean startLoop = true;
    		while(startLoop)
    		{
    			randVal = 0.01 + (Math.random() * ((1 - 0.01)));
    			//System.out.println(randVal);
    			if(randVal >= i) {
    				startLoop=false;
    				
    				addToQueue(nodeEdge.get(Integer.valueOf(startNode)));
    				setNodeEdgeNull(Integer.valueOf(startNode));
    				executorPool.execute(new VisualNodeC(Integer.valueOf(startNode)));
    				markVisited(Integer.valueOf(startNode));  		
    				countVisitedNodes++;
    				sleep();
    			}
    			else
    			{
    				startLoop = true;
    			}
    			
    		}
    		
    		if(isFinish()) {
    			//return;
    		}
    		
    		else {
    			//dequeue. check if has edges, if yes, add it to the queue and make the edge null. check if dequeue node isVisited.
            	
            	boolean loop = true;
            	while(loop) {
            		loop = false;
            		if(!queue.isEmpty()) {
            			
            			int presentNode = queue.poll();
            			if(hasEdges(Integer.valueOf(presentNode))) {
            				addToQueue(nodeEdge.get(Integer.valueOf(presentNode)));
            				setNodeEdgeNull(Integer.valueOf(presentNode));
            				if(!isVisited(Integer.valueOf(presentNode))){
            					boolean nodeLoop = true;
            		    		while(nodeLoop)
            		    		{
            		    			randVal = 0.01 + (Math.random() * ((1 - 0.01)));
            		    			//System.out.println(randVal);
            		        		
            		    			if(randVal >= i) {
            		    				nodeLoop=false;
            		    				// if not visited, call the node thread and mark it visited on the callback, increment the counter and repeat for other nodes in queue.
            		    				executorPool.execute(new VisualNodeC(Integer.valueOf(presentNode)));
            							loop = true;  
            							markVisited(Integer.valueOf(presentNode));
            							countVisitedNodes++;
            							
            							if(isFinish()) {
            								loop = false; 
            							}
            		    			}
            		    			else
            		    			{
            		    				nodeLoop = true;
            		    			}
            		    			
            		    			sleep();
            		    		}
            				}
            				else {
            					loop = true;          					
            				}
            			}
            			else
            			{
            				//System.out.println("This node has no edges to gossip");
            				loop = true;     
            			}
    				
            		}
            	
            		else {
            			//return;
            		}
            	}
    			
    		}
    		long endtr =   System.currentTimeMillis();
    		
    		long endTf = (endtr - startT);
    		Long lf = null;
    		lf = new Long(endTf);
    		double endT2 =  lf.doubleValue();
    		//double endT = (double) endTf;
    		//double endT3 = (double) lf;
    		//System.out.println(endT2 + "" + i);
        	graphData.add(new DataS((endT2), i));        	
    		
    		
    	}
    	else
    	{
    		System.out.println("Start node has no edges to gossip");
    	}
    	    	
    	}

    	visualiseGraph();
    	
    }
    
    private void addToQueue(String edges) {
    	String[] eachEdge = null;
		eachEdge = edges.split(",");
		for(String i : eachEdge) {
			//add to queue
			queue.add(Integer.valueOf(i));
		}
    }
    
   
    private void setNodeEdgeNull(int node) {
    	nodeEdge.set(node, null);
    }
    
    private boolean hasEdges(int node) {
    	if(nodeEdge.get(node) == null) {
    		return false;
    	}
    	return true;
    }
    
    private boolean isVisited(int node) {
    	
		return nodes[node];
    	
    }
    private void markVisited(int node) {
    	if(!(isVisited(node))) {
    		nodes[node] = true;
    		//System.out.println(node);
    	}
    	
    } 
    
    private boolean isFinish() {
    	
    	if(numberOfNodes == countVisitedNodes) {
    		return true;   	}
    
    return false;
    }
    
    private void finish() {
    	
    	
    	executorPool.shutdown();
    	
    }

	public void run() {
		
		init();
		communicate();
		
	}
	
	private void visualiseGraph() {
		  SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			      ScatterPlot plot = new ScatterPlot("Scatter Chart prob-time| Distributed Systems", graphData);
			      plot.setSize(800, 400);
			      plot.setLocationRelativeTo(null);
			      plot.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			      plot.setVisible(true);
			    }
		});
		finish();
		
	}
	   
    protected static void sleep() {
        try { 
        	int randVal = 900 + ((int) Math.random() * ((1100 - 900)));
        	Thread.sleep(randVal); 
        	} catch (Exception e) {}
    }
    
}