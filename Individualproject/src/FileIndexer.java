import java.nio.file.Paths;
import java.util.concurrent.locks.ReentrantLock;


public class FileIndexer implements Runnable{
	private FileQueue queue = FileQueue.getInstance();
	private boolean done;
	private static ReentrantLock lock= new ReentrantLock();
	private static FileCache cache = new FIFO(5, lock);
	private FSElement file;
	public FileIndexer() {
		this.done = false;
	}
	public void run() {
		while (true) {
			lock.lock();
			if (done) {
				System.out.println("Thread(FileIndexer) "
						+ Thread.currentThread().getId() + " stopped by main thread");
				break;
			}
			lock.unlock();
			file = queue.get();
			if (file == null) {
				System.out.println("Thread(FileIndexer) "
						+ Thread.currentThread().getId()  + " is being stopped by InterruptedException.");
				continue;
			} else {
				indexFile(file);
			}
		}
	}
	public void setDone() {
		lock.lock();
		try {
			done = true;
		} finally {
			lock.unlock();
		}
	}
	
	public void indexFile(FSElement file){
		// Index a given file.
		System.out.println("Thread " + Thread.currentThread().getId()
				+ " details " + file.getName() + " " + file.getParent());
		
		cache.fetch(Paths.get(file.getName()));
	}
	
	

}
