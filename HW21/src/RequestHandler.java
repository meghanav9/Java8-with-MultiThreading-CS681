
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantLock;

public class RequestHandler implements Runnable{
	private static ReentrantLock lock = new ReentrantLock();  
	private AccessCounter ac;
	private FileCache filecache;
	private Path path;
	public RequestHandler(FileCache filecache, AccessCounter ac, Path path){
		this.filecache = filecache;
		this.ac = ac;
		this.path = path;
	}
	
	public void run() {
		filecache.fetch(path);
		ac.increment(path);
		ac.getCount(path);
	}
}