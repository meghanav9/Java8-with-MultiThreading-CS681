import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	protected int AcctNum;
	protected double balance;
	protected ReentrantLock lock;
	private Condition sufficientFundsCondition, belowUpperLimitFundsCondition;
	public Account(int AcctNum,double balance){
		this.AcctNum = AcctNum;
		this.balance = balance;
		lock = new ReentrantLock();
		sufficientFundsCondition = lock.newCondition();
		belowUpperLimitFundsCondition = lock.newCondition();
	}
	public int getAcctNum() {
		return AcctNum;
	}
	public double getBalance() {
		return balance;
	}
	
	public void withdraw(double amount) {
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() +
					" (w): Account number: " + getAcctNum() +
					"  & current balance: " +  balance );
			while(balance <= 0){
				System.out.println(Thread.currentThread().getId() + 
						" (w): Account number: " + getAcctNum() +
						" & await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): Account number: " + getAcctNum() +
					" & new balance: "+  balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public void deposit(double amount) {
		lock.lock();
		try{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (d): Account number: " + getAcctNum() +
					" & current balance: " + balance);
			while(balance >= 300){
				System.out.println(Thread.currentThread().getId() + 
						" (d): Account number: " + getAcctNum() +
						" & await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().getId() + 
					" (d): Account number: " + getAcctNum() +
					" & new balance: " + balance);
			sufficientFundsCondition.signalAll();
		}
		catch (InterruptedException exception){
			exception.printStackTrace();
		}
		finally{
			lock.unlock();
			System.out.println("Lock released");
		}
	}

	public ReentrantLock getLock() {
		return lock;
	}

}
