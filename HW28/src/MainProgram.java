import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainProgram{

	public static void main(String[] args) {

		Observable observable = new Observable();
		ArrayList<Thread> threads = new ArrayList<Thread>();
		 Thread t1 = new Thread(()->{observable.addObserver( (Observable o, Object obj)->{System.out.println(obj);} );});
		 threads.add(t1);
		 t1.start();
		 Thread t2 = new Thread(()-> {observable.addObserver((Observable o, Object obj)->{System.out.println(obj);});});
		 threads.add(t2);
		 t2.start();
		 Thread t3 = new Thread(()-> {observable.addObserver((Observable o, Object obj)->{System.out.println(obj);});});
		 threads.add(t3);
		 t3.start();

		threads.forEach((Thread t) -> {
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		observable.setChanged();
		observable.notifyObserver("Hello World!");
	}
}	

	