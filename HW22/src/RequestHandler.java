
import java.nio.file.Path;

public class RequestHandler implements Runnable{ 
	private AccessCounter ac;
	private FileCache filecache;
	private Path path;
	public RequestHandler(FileCache filecache, AccessCounter ac, Path path){
		this.ac = ac;
		this.filecache = filecache;
		this.path = path;
	}
	
	public void run() {
		filecache.fetch(path);
		ac.increment(path);
		ac.getCount(path);
	}	
}