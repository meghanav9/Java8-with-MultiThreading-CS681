
import java.nio.file.Path;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
	
	public class FIFO extends FileCache{ 
	
	public FIFO(int threshold, ReentrantLock lock) {
		super(threshold, lock);
	}

	@Override
	protected void replace(Path targetFile) {

		
		Date firstdate = null;
		for( Date d : Mapdate1.values())
		{
		    if( firstdate == null || d.before( firstdate))
		    	firstdate = d;
		}
		
		Object firstin = getKeyFromValue(Mapdate1, firstdate);
		cache.remove(firstin);
		Mapdate1.remove(firstin);
		Mapdate.remove(firstin);
		if(cache.size()<threshold){
			cacheFile(targetFile);
		}
		System.out.println("\nCache is full, Performaning FIFO replacement policy");
		System.out.println("\nAfter FIFO replacement policy: Content in HashMap ");
		for(Entry<Path, String> m:cache.entrySet()){  
			System.out.println("File name: "+ "'"+m.getKey()+"'"+" and Content: "+"'"+m.getValue()+"'");  
		}  

	}

}

