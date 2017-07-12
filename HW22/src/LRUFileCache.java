
import java.nio.file.Path;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUFileCache extends FileCache{

	public LRUFileCache(int threshold,AccessCounter ac, ReentrantReadWriteLock lock) {
		super(threshold,ac,lock);
	}

	@Override
	protected void replace(Path targetFile) {
		
		System.out.println("\nCache is full, Performaning LRUFileCache replacement policy");
		
		Date firstdate = null;
		for( Date d : Mapdate.values())
		{
		    if( firstdate == null || d.before( firstdate))
		    	firstdate = d;
		}
		Object firstin = getKeyFromValue(Mapdate, firstdate);
		
		cache.remove(firstin);
		Mapdate.remove(firstin);
		Mapdate1.remove(firstin);
    	counter.Mapaccesscounter.remove(firstin);
		if(cache.size()<threshold){
			cacheFile(targetFile);
		}
		
		System.out.println("\nAfter LRUFileCache replacement policy: Content in HashMap ");
		for(Entry<Path, String> m:cache.entrySet()){  
			System.out.println("File name: "+ "'"+m.getKey()+"'"+" and Content: "+"'"+m.getValue()+"'");  
		}  
	}

}
