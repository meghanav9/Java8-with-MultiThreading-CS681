import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class Singleton implements Runnable{
	private static ReentrantLock lock = new ReentrantLock();
	private Singleton(){};
	private static Singleton instance = null;
	public static Singleton getInstance(){
		lock.lock();
		try{
			if(instance==null)
				instance = new Singleton();
		}finally{	
			lock.unlock();
		}
		return instance;
	}
	@Override
	public void run() {
		try{	
				Date now = new Date();
				System.out.println(Thread.currentThread().getName()+" - On "+now + " " + "instance created " + instance);
				Thread.sleep(1000);
		}
		catch(InterruptedException ex){}
	}
		
	public static void main(String[] args) {
		Singleton tmp = Singleton.getInstance();
		Thread thread = new Thread(tmp);
		Thread thread2= new Thread(tmp);
		thread.start();
		thread2.start();
		try {
			thread.join();
			thread2.join();
		} catch (InterruptedException e1) {}
		System.out.println("Singleton.getInstancce() is " + Singleton.getInstance());      
	}
}
