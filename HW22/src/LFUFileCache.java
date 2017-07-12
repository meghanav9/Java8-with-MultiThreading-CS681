import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class LFUFileCache extends FileCache{

	public LFUFileCache(int threshold,AccessCounter ac, ReentrantReadWriteLock lock) {
		super(threshold, ac, lock);
	}

	@Override
	protected void replace(Path targetFile) {
		System.out.println("\nCache is full, Performaning LFU replacement policy");
		
		List<Map.Entry<Path, Integer>> list = new ArrayList<Map.Entry<Path, Integer>>(counter.Mapaccesscounter.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Path, Integer>>() {
			public int compare(Map.Entry<Path, Integer> o1, Map.Entry<Path, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}
		});
		Entry<Path, Integer> val = list.get(0);
		cache.remove(val.getKey());
		Mapdate.remove(val.getKey());
		Mapdate1.remove(val.getKey());
		counter.Mapaccesscounter.remove(val.getKey());
		
		if(cache.size()<threshold){
			cacheFile(targetFile);
		}
		System.out.println("\nAfter LFUFileCache replacement policy: Content in HashMap ");
		for(Entry<Path, String> m:cache.entrySet()){  
			System.out.println("File name: "+ "'"+m.getKey()+"'"+" and Content: "+"'"+m.getValue()+"'");  
		}  
	}

}
