package uk.ac.ed.ds.kant.DistributedSystems;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; 
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkB implements Runnable {
	
	ArrayList<String> nodeEdge;
	int numberOfNodes;
	int startNode;
	boolean[] nodes;
	int countVisitedNodes;
	Queue<Integer> queue;
	ExecutorService executorPool;
	
	public NetworkB(ArrayList<String> nodeEdge, int numberOfNodes, int startNode) {
		this.nodeEdge = nodeEdge;
		this.numberOfNodes = numberOfNodes;
		this.startNode = startNode;
	
	}

    public void init() {
    	//create the array with number of nodes to manage the gossip passed
    	nodes = new boolean[numberOfNodes];
    	//set the isVisited for all nodes as false
    	Arrays.fill(nodes, false);
    	//set the counter for visited nodes
    	countVisitedNodes = 0;
    	//Queue for sending gossip messages to the nodes;
    	queue = new LinkedList<Integer>();
    	//thread pool
    	executorPool = Executors.newFixedThreadPool(numberOfNodes);
    }
    
    public void communicate() {
    	
        	
    	//get the start node and add its edges to the queue and set it null in nodeEdge. Also check if no edges. 
    	//Mark the node visited, increase the counter and check for finish.
    	if(hasEdges(Integer.valueOf(startNode))) {
    		addToQueue(nodeEdge.get(Integer.valueOf(startNode)));
    		setNodeEdgeNull(Integer.valueOf(startNode));
    		executorPool.execute(new NodeC(Integer.valueOf(startNode), true));
    		markVisited(Integer.valueOf(startNode));
    		String fileName = "output.txt";
            File file = new File(System.getProperty("user.dir")+"//log//"+fileName);
            PrintWriter writer;
			try {
				writer = new PrintWriter(file);
				writer.print("");
	            writer.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
        
            try {
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
		        BufferedWriter bw = new BufferedWriter(fw);

		           
		        synchronized(this){
		            bw.write(startNode + "\r\n");        // write to file
		          
		        }
		        
		        bw.close();
			} catch (IOException e) {
				//  Auto-generated catch block
				e.printStackTrace();
			}
         
    		countVisitedNodes++;
    		if(isFinish()) {
    			finish();
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
            					// if not visited, call the node thread and mark it visited on the callback, increment the counter and repeat for other nodes in queue.
            					executorPool.execute(new NodeC(Integer.valueOf(presentNode)));
            					loop = true;  
            					markVisited(Integer.valueOf(presentNode));
            					countVisitedNodes++;
            					sleep();
            		    		if(isFinish()) {
            		    			finish();
            		    		}
            				}
            				else {
            					loop = true;          					
            				}
            			}
            			else
            			{
            				//System.out.println("This node hasTODO no edges to gossip");
            				loop = true;     
            			}
    				
            		}
            	
            		else {
            			finish();
            		}
            	}
    			
    		}
        	
    		
    		
    	}
    	else
    	{
    		System.out.println("Start node has no edges to gossip");
    	}
    	    	

    	
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
		//  Auto-generated method stub
		init();
		communicate();
		
	}
	   
    protected static void sleep() {
        try { 
        	int randVal = 900 + ((int) Math.random() * ((1100 - 900)));
        	Thread.sleep(randVal); 
        	} catch (Exception e) {}
    }
    
}
