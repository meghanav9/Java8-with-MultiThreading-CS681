import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class RequestHandler implements Runnable{
	private static ReentrantLock lock = new ReentrantLock();  
	private AccessCounter ac;
	private ArrayList<Path> paths= new ArrayList<>();
	private Path path;
	private boolean done = false;
	Random rand = new Random();
	public RequestHandler(AccessCounter ac, Path path){
		this.ac = ac;
		this.path = path;
	}
	
	public void run() {
		ac.increment(this.path);
		ac.getCount(path);
		try {
			Thread.sleep(3000);
		}catch(InterruptedException exception){}
	}	
	public void setDone(){
		lock.lock();
		try{
			done = true;
		}finally{
			lock.unlock();
		}
	}
	public static void main(String[] args){			
		Random rand = new Random();
		ArrayList<Path> paths = new ArrayList<Path>();
		paths.add(Paths.get("src/file_root/a.html"));
		paths.add(Paths.get("src/file_root/b.txt"));
		
		AccessCounter accesscounter = new AccessCounter();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		Thread thread;
		RequestHandler requesthandler1 = new RequestHandler(accesscounter, paths.get(rand.nextInt(paths.size())));
		RequestHandler requesthandler2 = new RequestHandler(accesscounter, paths.get(rand.nextInt(paths.size())));
		
		
		for (int i = 0; i < 10; i++) {
			if (i < 5) {
				thread = new Thread(requesthandler1);
				thread.start();
				threads.add(thread);
			} else {
				thread = new Thread(requesthandler2);
				thread.start();
				threads.add(thread);
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
		requesthandler1.setDone();
		requesthandler2.setDone();	    	
		try {
				t.join();
			} catch (Exception e) {	}
		});
	}
}		   