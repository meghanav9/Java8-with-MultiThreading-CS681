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
		int sourceID = System.identityHashCode(source);
		int destID = System.identityHashCode(destination);
			if( sourceID < destID ){
				source.getLock().lock();
				destination.getLock().lock();
				if( source.getBalance() < amount ){
					try{
			            throw new Exception("Insufficient balance");
			      }catch(Exception ex){	}
				}
				else{
					source.withdraw(amount); //Nested locking
					destination.deposit(amount);//Nested locking
				}
				destination.getLock().unlock();
				source.getLock().unlock();
			}
			else if( sourceID > destID ){
				destination.getLock().lock();
				source.getLock().lock();
				if( destination.getBalance() < amount ){
					try{
			            throw new Exception("Insufficient balance");
					}catch(Exception ex){	}
					}
				else{
					destination.withdraw(amount); //Nested locking
					source.deposit(amount);//Nested locking
				}
				source.getLock().unlock();
				destination.getLock().unlock();
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
