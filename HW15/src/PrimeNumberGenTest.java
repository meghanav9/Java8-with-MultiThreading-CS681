import java.util.concurrent.locks.ReentrantLock;

public class PrimeNumberGenTest {
	
	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		long startTime = System.currentTimeMillis();
		PrimeNumberGenerator gen = new PrimeNumberGenerator(1L, 1000L);
		gen.run();
		long time = System.currentTimeMillis() - startTime;
		gen.getPrimes().forEach( (Long prime)->System.out.print(prime + ", ") );		
		System.out.println("\n" + gen.getPrimes().size() + " prime numbers are found.");
        System.out.println( time/1000f + " seconds, " + time + " milliseconds ");

        PrimeNumberGenerator gen1 = new PrimeNumberGenerator(1L, 5000L);
		PrimeNumberGenerator gen2 = new PrimeNumberGenerator(5001L, 10000L);
		Thread t1 = new Thread(gen1);
		t1.start();
		Thread t2 = new Thread(gen2);
		t2.start();
		try {
			t1.join(); t2.join();
		} catch (InterruptedException e) {}
		gen1.getPrimes().forEach( (Long prime)->System.out.print(prime + ", ") );
		System.out.println("\n" + gen1.getPrimes().size() + " prime numbers are found.");
		gen2.getPrimes().forEach( (Long prime)->System.out.print(prime + ", ") );
		System.out.println("\n" + gen2.getPrimes().size() + " prime numbers are found.");
		System.out.println(gen1.getPrimes().size()+gen2.getPrimes().size() + " prime numbers are found in total.");
        
        InterruptiblePrimeNumberGenerator interruptibleGen = new InterruptiblePrimeNumberGenerator(1L, 1000000L);
		t2 = new Thread(interruptibleGen);
		t2.start();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {}
		
		lock.lock();
		try{
			t2.interrupt();
		}finally{
			lock.unlock();
		}
		
		try {
			t2.join();
		} catch (InterruptedException e) {}
		System.out.println(interruptibleGen.getPrimes().size() + " prime numbers are found.");        
        
	}

}
