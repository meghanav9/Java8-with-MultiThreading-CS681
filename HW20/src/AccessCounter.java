import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccessCounter {
	private HashMap<java.nio.file.Path, Integer> accesscounter = new HashMap<>();
	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock(); 
	private static ReentrantLock lock = new ReentrantLock(); 
	public AccessCounter(){
		this.accesscounter = new HashMap<Path,Integer>();
		lock.lock();
		try{
			this.accesscounter.put(Paths.get("src/file_root/a.html"),10);
			this.accesscounter.put(Paths.get("src/file_root/c.html"),6);
			this.accesscounter.put(Paths.get("src/file_root/d.html"),12); 
		}finally{
			lock.unlock();
		}
	}
	public void increment(Path path){
		rwLock.writeLock().lock(); 
		 try{ 
				if(this.accesscounter.containsKey(path)){
		            int value = this.accesscounter.get(path) + 1;
		            this.accesscounter.put(path,value);  
		        }
		        else { 
		        	this.accesscounter.put(path,1);
		        }
				
		    }finally{
		    	System.out.println(
						Thread.currentThread().getName() + " called increment on " + path + "and its count is " + accesscounter.get(path) + " .");
		    	rwLock.writeLock().unlock();
		    }
	}
	public int getCount(Path path){
		rwLock.readLock().lock();
	    try{ 
			if(this.accesscounter.containsKey(path)){
	        	int value = this.accesscounter.get(path);
	        	return value;
	        }
	        else { 
	            return 0;
	        }
	     
	    }finally{
	    	System.out.println(
	    	Thread.currentThread().getName() + " called getCount on " + path + " and its count is " + accesscounter.get(path) + " .");
	    	rwLock.readLock().unlock();
	    }
	}
}
