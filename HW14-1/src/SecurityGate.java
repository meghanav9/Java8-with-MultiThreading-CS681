import java.util.concurrent.locks.ReentrantLock;
public class SecurityGate {
	private static ReentrantLock lock = new ReentrantLock();
	private int counter = 0;
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
	public void enter(){
		lock.lock();
		try{
			counter++;
		}finally{	
			System.out.println(Thread.currentThread().getName()+" enter and value: "+ counter);
			lock.unlock();
		}
	}
	public void exit(){
		lock.lock();
		try{
			counter--;
		}finally{	
			System.out.println(Thread.currentThread().getName()+" exit and value: "+ counter);
			lock.unlock();
		}
	}
	public int getCount(){
		return counter;
	}
		
	public static void main(String[] args) {
	SecurityGate tmp = SecurityGate.getInstance();
	Thread t1 = new Thread( tmp.new Guest());t1.start();
	Thread t2 = new Thread( tmp.new Guest());t2.start();
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
				for(int i=0; i<5; i++){
					gate.enter();
					gate.exit();
					gate.getCount();
					
				}
		}
	}
}