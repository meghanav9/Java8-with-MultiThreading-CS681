import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccessCounter {
	private ConcurrentHashMap<Path, AtomicInteger> accesscounter ;
	private ReentrantLock lock = new ReentrantLock();  
	public AccessCounter(){
		this.accesscounter = new ConcurrentHashMap<Path,AtomicInteger>();
		lock.lock();
		try{
			this.accesscounter.put(Paths.get("src/file_root/a.html"), new AtomicInteger(10));
			this.accesscounter.put(Paths.get("src/file_root/c.html"), new AtomicInteger(6));
			this.accesscounter.put(Paths.get("src/file_root/d.html"), new AtomicInteger(12)); 
		}finally{
		        lock.unlock();
		}
	}
	public void increment(Path path){
		 lock.lock(); 
		 try{ 
				if(this.accesscounter.containsKey(path)){
					this.accesscounter.get(path).incrementAndGet();
		        }
		        else { 
		        	 this.accesscounter.putIfAbsent(path, new AtomicInteger(1));
		        }
				
		    }finally{
		    	System.out.println(
						Thread.currentThread().getName() + " called increment on " + path + "and its count is " + accesscounter.get(path) + " .");
		        lock.unlock();
		    }
	}
	public AtomicInteger getCount(Path path){
	    lock.lock();
	    try{ 
			if(this.accesscounter.containsKey(path)){
	        	return this.accesscounter.get(path);
	        }
	        else { 
	            return new AtomicInteger(0);
	        }
	     
	    }finally{
	    	System.out.println(
	    	Thread.currentThread().getName() + " called getCount on " + path + " and its count is " + accesscounter.get(path) + " .");
	        lock.unlock();
	    }
	}
}
