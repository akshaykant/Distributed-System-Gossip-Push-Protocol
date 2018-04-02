package uk.ac.ed.ds.kant.DistributedSystems;


import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;

public class NodeC implements Runnable{

	int id;
	boolean isVisited;
	boolean isParent;
	
	public NodeC(int id) {
		this.id = id;
		this.isVisited = false;
		this.isParent = false;
		
	}

	public NodeC(int id, boolean parent) {
		this.id = id;
		this.isVisited = false;
		this.isParent = parent;
		
	}
	public int getId() {
		return id;
	}
	
	public void run() {
		
		
	isVisited = true;
	System.out.println("Node "+ id + " isVisited: "+ isVisited);
	
	if(!isParent) {
	try
    {
		String fileName = "output.txt";
        File file = new File(System.getProperty("user.dir")+"//log//"+fileName);
        if (!file.exists()) 
        {
             file.createNewFile();
             
        }
    
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);

            synchronized(this){
            bw.write(id + "\r\n");        // write to file
          
        }
        bw.close();
       
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
	}
	
	}

	
}
