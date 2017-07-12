public class CancellablePrimeNumberGenerator extends PrimeNumberGenerator{
	private volatile boolean done = false;
	private long n;
	
	public CancellablePrimeNumberGenerator(long from, long to) {
		super(from, to);
	}
	public void run(){
		// Stop generating prime numbers if done==true
		for (n = from; n <= to; n++) {
				if(done==true){
						System.out.println("Stopped generating prime numbers.");
						this.primes.clear();
						break;
				}
			if( isPrime(n) ){ this.primes.add(n); }
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {}
		}
	}
	public void setDone(){
			done = true;
	}
}

