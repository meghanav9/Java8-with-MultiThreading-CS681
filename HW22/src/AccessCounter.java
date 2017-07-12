import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccessCounter {
	protected HashMap<Path, Integer> Mapaccesscounter;
	private ReentrantReadWriteLock lock;  
	public AccessCounter(ReentrantReadWriteLock  lock){
		this.lock =lock;
		this.Mapaccesscounter = new HashMap<Path,Integer>();
	}
	public void increment(Path path){
		lock.writeLock().lock(); 
		 try{ 
				if(this.Mapaccesscounter.containsKey(path)){
		            int value = this.Mapaccesscounter.get(path) + 1;
		            this.Mapaccesscounter.put(path,value);  
		        }
		        else { 
		        	this.Mapaccesscounter.put(path,1);
		        }
				
		    }finally{
		    	System.out.println(
						Thread.currentThread().getName() + " called increment on " + path + "and its count is " + Mapaccesscounter.get(path) + " .");
		    	lock.writeLock().unlock();
		    }
	}
	public int getCount(Path path){
		lock.readLock().lock();
	    try{ 
			if(this.Mapaccesscounter.containsKey(path)){
	        	int value = this.Mapaccesscounter.get(path);
	        	return value;
	        }
	        else { 
	            return 0;
	        }
	     
	    }finally{
	    	System.out.println(
	    	Thread.currentThread().getName() + " called getCount on " + path + " and its count is " + Mapaccesscounter.get(path) + " .");
	    	lock.readLock().unlock();
	    }
	}
}
