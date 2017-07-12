import java.util.concurrent.locks.ReentrantLock;

public class Casher{
	private Future future = new Future();
	public Pizza order(){
		System.out.println("An order is made.");		
		Thread t = new Thread(()->{
				RealPizza realPizza = new RealPizza();
				future.setRealPizza( realPizza );
		});
		t.start();
		return future;
	}

	public static void main(String[] args){
		Casher casher = new Casher();
		ReentrantLock lock = new ReentrantLock();
		System.out.println("Ordering pizzas at a casher counter.");
		Pizza p1 = casher.order();
		Pizza p2 = casher.order();
		Pizza p3 = casher.order();
		Thread thread = new Thread(() -> {
			try {
				((Future) p3).getPizza(1000);
			} catch (Exception e) {
				System.out.println("Let's see if pizza 3 is ready : \n"+ e.getMessage());
			}
		});
		thread.start();
		
		try {
			thread.join();
		} catch (InterruptedException e1) {
		}
		
		
		try {
			Thread.sleep(2000);
		} catch (Exception e1) {
		}
		
		while(true){
			lock.lock();
			try{
				System.out.println("Let's see if pizzas are ready to pick up...");
				if( casher.future.isReady() ){
						System.out.println(p1.getPizza());
						System.out.println( p2.getPizza());
						break;
				}	
		
				Thread.sleep(2000);
				}
				catch(InterruptedException e){}

			finally{
			lock.unlock();
			}
			System.out.println("Doing something, reading newspapers, magazines, etc., " +
					"until pizzas are ready to pick up...");
		}
		try {
			thread.join();
		} catch (InterruptedException e1) {
		}
		

	}
}
