package uk.ac.ed.ds.kant.DistributedSystems;

public class VisualNodeC implements Runnable{
	
	int id;
	boolean isVisited;

	
	public VisualNodeC(int id) {
		this.id = id; 
		this.isVisited = false;
		
		
	}

	public int getId() {
		return id;
	}
	
	public void run() {
		
		
	isVisited = true;
	System.out.println("Node"+ id + "isVisited: "+ isVisited);
	
	
	}

	
	
}
