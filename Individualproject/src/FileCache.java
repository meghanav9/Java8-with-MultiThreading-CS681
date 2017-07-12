
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FileCache {
	protected ConcurrentHashMap<Path, String> cache= new ConcurrentHashMap<Path, String>();;
	private ReentrantLock lock ;
	protected ConcurrentHashMap<Path, Date> Mapdate= new ConcurrentHashMap<Path,Date>();;
	protected ConcurrentHashMap<Path, Date> Mapdate1= new ConcurrentHashMap<Path,Date>();;
	protected int threshold = 5;
	
	public FileCache(int threshold, ReentrantLock lock){
		this.lock = lock;
		this.threshold = threshold;
	}
	 public String fetch(Path targetFile){
		try{ 
			 lock.lock();
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
							System.out.println("cache is not full,So Inserted the new file '"+targetFile+"' and the file content '"+
									content+"'");
						}
						else{
							replace(targetFile);
						}
				}
				return cache.get(targetFile);
		}
		finally{ 
			lock.unlock();
			
		}
	 }
	
	 protected String cacheFile(Path targetFile){
		 String content = null;
		 try {
			content = new String(Files.readAllBytes(targetFile));
		 } catch (IOException e) {
		 }
		 content = "Content of file " +targetFile.toString();
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