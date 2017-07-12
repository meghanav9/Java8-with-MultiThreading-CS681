import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Observable {
	private ReentrantLock lockObs =  new ReentrantLock();
	private CopyOnWriteArrayList<Observer> observers= new CopyOnWriteArrayList<Observer>();
	private boolean flag = false;
	public Observable() {}

	public void addObserver(Observer o){
		observers.add(o);
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
		lockObs.lock();
		try{
			if (hasChanged()== false){
				lockObs.unlock();
				return; 
			}
			clearChanged();
		} finally{
			lockObs.unlock();
		}
		Iterator<Observer> it = observers.iterator();
		while( it.hasNext() ){
			 it.next().update(this, obj);
		}
	}
	
}

