
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;

public class MainProgram {

	public static void main(String[] args) throws IOException {
		int threshold = 3;
		ReentrantLock lock = new ReentrantLock();  
		Path path1 = Paths.get("src/file_root/a.html");
		Path path2 = Paths.get("src/file_root/b.html");
		Path path3 = Paths.get("src/file_root/c.html");
		Path path4 = Paths.get("src/file_root/d.html");
		Path path5 = Paths.get("src/file_root/e.txt");
		Path path6 = Paths.get("src/file_root/f.txt");
		AccessCounter accesscounter = new AccessCounter(lock);
		ArrayList<Thread> threads = new ArrayList<Thread>();
		Thread thread;
		FileCache fifo = new FIFO(threshold, accesscounter,lock);
		FileCache lfu = new LFUFileCache(threshold, accesscounter,lock);
		FileCache lru = new LRUFileCache(threshold, accesscounter,lock);
		RequestHandler requesthandler1 = new RequestHandler(lfu, accesscounter, path1);
		RequestHandler requesthandler2 = new RequestHandler(lfu, accesscounter, path1);
		RequestHandler requesthandler3 = new RequestHandler(lfu, accesscounter, path2);
		RequestHandler requesthandler4 = new RequestHandler(lfu, accesscounter, path3);
		RequestHandler requesthandler5 = new RequestHandler(lfu, accesscounter, path4);
		RequestHandler requesthandler6 = new RequestHandler(lru, accesscounter, path1);
		RequestHandler requesthandler7 = new RequestHandler(lru, accesscounter, path2);
		RequestHandler requesthandler8 = new RequestHandler(lru, accesscounter, path3);
		RequestHandler requesthandler9 = new RequestHandler(lru, accesscounter, path4);
		RequestHandler requesthandler0 = new RequestHandler(fifo, accesscounter, path5);

		
		for (int i = 0; i < 10; i++) {
			switch (i % 10) {
			case 0: {
				thread = new Thread(requesthandler1);
				thread.start();
				threads.add(thread);
				break;
			}
			case 1: {
				thread = new Thread(requesthandler2);
				thread.start();
				threads.add(thread);
				break;
			}
			case 2: {
				thread = new Thread(requesthandler3);
				thread.start();
				threads.add(thread);
				break;
			}
			case 3: {
				thread = new Thread(requesthandler4);
				thread.start();
				threads.add(thread);
				break;
			}
			case 4: {
				thread = new Thread(requesthandler5);
				thread.start();
				threads.add(thread);
				break;
			}
			case 5: {
				thread = new Thread(requesthandler6);
				thread.start();
				threads.add(thread);
				break;
			}
			case 6: {
				thread = new Thread(requesthandler7);
				thread.start();
				threads.add(thread);
				break;
			}
			case 7: {
				thread = new Thread(requesthandler8);
				thread.start();
				threads.add(thread);
				break;
			}
			case 8: {
				thread = new Thread(requesthandler9);
				thread.start();
				threads.add(thread);
				break;
			}
			case 9: {
				thread = new Thread(requesthandler0);
				thread.start();
				threads.add(thread);
				break;
			}
		}
		try {
				Thread.sleep(500);
		} catch (InterruptedException e) {}
		    
		
		threads.forEach((Thread t) -> {
			lock.lock();
			try{
				t.interrupt();
			}finally{
			    lock.unlock();
		}   	
		try {
				t.join();
			} catch (Exception e) {	}
		});
	}
	}

}