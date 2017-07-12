
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class FileCache {
	protected HashMap<Path, String> cache= new HashMap<Path, String>();
	private ReentrantReadWriteLock lock ;
	protected Map<Path, Date> Mapdate;
	protected Map<Path, Date> Mapdate1;
	protected int threshold;
	protected AccessCounter counter;
	
	public  FileCache(int threshold, AccessCounter ac, ReentrantReadWriteLock lock){
		this.lock = lock;
		this.threshold = threshold;
		this.counter=ac;
//		cache = new HashMap<Path, String>();
		this.Mapdate = new HashMap<Path,Date>();
		this.Mapdate1 = new HashMap<Path,Date>();
	}
	 public String fetch(Path targetFile){
		try{ 
			lock.writeLock().lock();
			 String content = null;
				 if(cache.containsKey(targetFile)){
					 content = cache.get(targetFile);
					 Date date1 = new Date();
					 Mapdate.replace(targetFile,date1);
						System.out.println("\nTargetFile '"+targetFile+ "' found in the cache and its content is "
									+ " ' " + content + " ' ");
						
				 }
				 else{
						if(cache.size() < threshold){
							content = cacheFile(targetFile);
							System.out.println("cache is not full,So Inserted the new file '"+targetFile+"'and the file content '"+
									content+"'"+ " on '" + Mapdate.get(targetFile)+ "'");
						}
						else{
							replace(targetFile);
						}
				}
				 lock.readLock().lock();
				 lock.writeLock().unlock();
				return cache.get(targetFile);
		}
		finally{ 
			lock.readLock().unlock();
			
		}
	 }
	
	 protected String cacheFile(Path targetFile){
		 String content = null;
		 try {
			content = new String(Files.readAllBytes(targetFile));
		 } catch (IOException e) {
			e.printStackTrace();
		 }
		 
		 if(content==null){
			 return "";
		 }	
		 cache.put(targetFile, content);
		 Mapdate1.put(targetFile, new Date());
		 Mapdate.put(targetFile, new Date());
		return content;
	}
	protected abstract void replace(Path targetFile);
	
	
	public static Object getKeyFromValue(Map hm, Object value) {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }
}