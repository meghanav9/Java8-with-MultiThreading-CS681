import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;


public class AccountHandler implements Runnable {

	private ReentrantLock lock = new ReentrantLock();  
	private double amount;
	private boolean done = false;
	private Account ac;
	private Account source;
	private Account destination;
	private AccountHandler accounthandler;
	public AccountHandler(Account source,Account destination, double amount){
		this.source = source;
		this.destination = destination;
		this.amount = amount;
		accounthandler = this;
	}
	public void transfer( Account source,Account destination,double amount){
		Random random = new Random();
		
		
		while(true){
			if( source.getLock().tryLock() ){
				try{
					if( destination.getLock().tryLock() ){
						try{
							if( source.getBalance() < amount ){
								try{
						            throw new Exception("Insufficient balance");
						      	}catch(Exception ex){	}
							}	
							else{
								source.withdraw(amount);
								destination.deposit(amount);
							}
							return;
						}finally{
							destination.getLock().unlock();
						}
					}
				}finally{
					source.getLock().unlock();
				}
			}
			try {
				Thread.sleep(random.nextInt(500));
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void run() {
		try{
			accounthandler.transfer(source, destination, amount);
			Thread.sleep(500);
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
}	
