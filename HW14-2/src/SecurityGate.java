import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
public class SecurityGate {
	private static ReentrantLock lock = new ReentrantLock();
	private SecurityGate(){};
	private static SecurityGate instance = null;
	public static SecurityGate getInstance(){
		lock.lock();
		try{
			if(instance==null)
				instance = new SecurityGate();
		}finally{	
			lock.unlock();
		}
		return instance;
	}
	AtomicInteger counter = new AtomicInteger(0);
	public void enter(){
		counter.updateAndGet((int i)->++i);
		System.out.println(Thread.currentThread().getName()+" - After enter() counter =" + counter);
	}
	public void exit(){
		counter.updateAndGet((int i)->--i);
		System.out.println(Thread.currentThread().getName()+ " - After exit() counter =" + counter);
	}
	public AtomicInteger getCount(){
		return counter;
	}
		
	public static void main(String[] args) {
	SecurityGate tmp = SecurityGate.getInstance();
	Thread t1 = new Thread( tmp.new Guest());t1.start();
	Thread t2 = new Thread( tmp.new Guest());t2.start();
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {}	
	try {
			t1.join();
			t2.join();
		} catch (InterruptedException e1) {}
	}
	
public 	class Guest implements Runnable{
	private SecurityGate gate;
	public Guest(){
		gate = SecurityGate.getInstance();
	}
	public void run(){
		try{
			for(int i=0; i<5; i++){
				gate.enter();
				gate.exit();
				Thread.sleep(1000);
			}
		}catch(InterruptedException ex){}	
	}
}
}