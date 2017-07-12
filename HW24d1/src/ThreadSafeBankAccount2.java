
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeBankAccount2{
	private static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args){
		ArrayList<Thread> threads = new ArrayList<Thread>();
		Thread thread;
		Account account = new Account(888, 100);
		Account account1 = new Account(999, 0);
		
		AccountHandler requesthandler1 = new AccountHandler(account, account1, 10);
		for (int i = 0; i < 10; i++) {
				thread = new Thread(requesthandler1);
				thread.start();
				threads.add(thread);
		}
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {}
		
		threads.forEach((Thread t) -> {
			lock.lock();
			try{
				t.interrupt();
			}finally{
			    lock.unlock();
			}
			requesthandler1.setDone();
			try {
				t.join();
			} catch (Exception e) {	}
		});
	}
}	