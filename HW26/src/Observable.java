import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class Observable {
	private ReentrantLock lockObs =  new ReentrantLock();
	private ArrayList<Observer> observers= new ArrayList<Observer>();
	private boolean flag = false;
	public Observable() {}

	public void addObserver(Observer o){
		lockObs.lock();
		observers.add(o);
		lockObs.unlock();
	}
	public Object getObserver(){
		return observers;
	}
	public void deleteObserver(Observer o){
		observers.remove(o);
	}
	protected void setChanged(){
		lockObs.lock();
		flag = true;
		lockObs.unlock();
	}
	protected void clearChanged(){
		lockObs.lock();
		flag = false;
		lockObs.unlock();
	}
	public boolean hasChanged(){
		if(flag == true){	
			return true;
		}
		else{
			return false;
		}
	}
	public void notifyObserver(Object obj){
		Object[] arrLocal;
		lockObs.lock();
		try{
			if (hasChanged()== false){
				lockObs.unlock();
				return; 
			}
			arrLocal = observers.toArray(); // observers copied to arrLocal
			clearChanged();
		} finally{
			lockObs.unlock();
		}
		for (int i = 0; i <= arrLocal.length-1; i++){
			((Observer)arrLocal[i]).update(this, obj); // OPEN CALL
		}	
	}
}

