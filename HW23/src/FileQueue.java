import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class FileQueue {
	private ReentrantLock lock= new ReentrantLock();
	private ArrayList<FSElement> queue = new ArrayList<FSElement>();
	private Condition belowtheLimit = lock.newCondition(), 
			abovetheLimit= lock.newCondition();
	private int threshold = 5;	
	private static FileQueue instance;
	private FileQueue(){};
	public static FileQueue getInstance(){
		if(instance==null) instance = new FileQueue();
		return instance;
		
	}

	public void put(FSElement file) throws InterruptedException {
		lock.lock();
		try {
			if (queue.size() >= threshold) {
				belowtheLimit.await();
			}
			queue.add(file);
			abovetheLimit.signal();
		} finally {
			lock.unlock();
		}

	}

	public FSElement get() {
		FSElement file = null;
		lock.lock();
		try {
			while (queue.size() <= 0) {
				abovetheLimit.await();
			}
			if (queue.size() > 0) {
				file = (FSElement) queue.get(0);
				queue.remove(0);
			}
			belowtheLimit.signal();
			return file;
		} catch (InterruptedException e) {
			return null;
		} finally {
			lock.unlock();
		}
	}
}
