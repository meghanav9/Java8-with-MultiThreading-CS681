
import java.nio.file.Path;
import java.util.Date;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
	
	public class FIFO extends FileCache{ 
	
	public FIFO(int threshold, AccessCounter ac,ReentrantLock lock) {
		super(threshold, ac,lock);
	}

	@Override
	protected void replace(Path targetFile) {

		System.out.println("\nCache is full, Performaning FIFO replacement policy");
		
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
		counter.Mapaccesscounter.remove(firstin);
		if(cache.size()<threshold){
			cacheFile(targetFile);
		}
		System.out.println("\nAfter FIFO replacement policy: Content in HashMap ");
		for(Entry<Path, String> m:cache.entrySet()){  
			System.out.println("File name: "+ "'"+m.getKey()+"'"+" and Content: "+"'"+m.getValue()+"'");  
		}  
		for(Entry<Path, Integer> m1:counter.Mapaccesscounter.entrySet()){  
			System.out.println("Aceess: "+ "'"+m1.getKey()+"'"+" and Content: "+"'"+m1.getValue()+"'");  
		}  
	}

}

