
 
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Future implements Pizza{
	private RealPizza realPizza = null;
	private ReentrantLock lock;
	private Condition ready;
	private boolean isReady;
	class CasherTimeoutException extends TimeoutException {
		   public CasherTimeoutException(String msg){
		      super(msg);
		   }
	}
	public Future(){
		lock = new ReentrantLock();
		ready = lock.newCondition();
	}
	public boolean isReady(){
		lock.lock();
		try{
			return isReady;
		}
		finally{
			lock.unlock();
		}
	}
	public void setRealPizza( RealPizza real ){
		lock.lock();
		try{
			if( realPizza != null ){return; }
			realPizza = real;
			isReady = true;
			ready.signalAll();
		}
		finally{
			lock.unlock();
		}
	}
	public String getPizza(long timeout)throws CasherTimeoutException, InterruptedException{
		String pizzaData = null;
		lock.lock();
		try{
			if( realPizza == null ){
				if (!ready.await(timeout, TimeUnit.MILLISECONDS)) {
					throw new CasherTimeoutException("Pizza is not Ready...");
				}
			}
			pizzaData = realPizza.getPizza();
		}
		finally{
			lock.unlock();
		}
		return pizzaData;
	}
	
	public String getPizza() {
		try {
			return getPizza(10000);
		} catch (CasherTimeoutException | InterruptedException e) {
			return e.getMessage();
		}
	}
}	
