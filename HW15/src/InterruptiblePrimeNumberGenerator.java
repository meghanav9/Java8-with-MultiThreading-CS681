import java.util.concurrent.locks.ReentrantLock;

public class InterruptiblePrimeNumberGenerator extends PrimeNumberGenerator{
	private ReentrantLock lock;
	public InterruptiblePrimeNumberGenerator(long from, long to) {
		super(from, to);
		lock = new ReentrantLock();
	}
			
	public void run(){
		// Stop generating prime numbers if Thread.interrupted()==true
		for (long n = from; n <= to; n++) {
			lock.lock();
			try{
				if( isPrime(n) ){ this.primes.add(n); }
				if(Thread.interrupted()==true){
					System.out.println("Stopped generating prime numbers.");
					this.primes.clear();
					break;
				}
			}finally{
				lock.unlock();
			}
		}
	}	
}

