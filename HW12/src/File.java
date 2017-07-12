import java.util.concurrent.locks.ReentrantLock;
import java.util.Date;

public class File{ 
	private boolean changed = false;
	private boolean done = false;
	private ReentrantLock lock;
	private File afile;
	private int contentofFile=0;
	Date date = new Date();
	public File(){
		lock = new ReentrantLock();
		afile = this;
	}
	
	public void change(){
		lock.lock();
		try{
			contentofFile=contentofFile+1;
			System.out.println(Thread.currentThread().getName()+ " - New content is: " + contentofFile);
			changed = true;
		}
		finally{
			lock.unlock();
		}
	}
	
	public void save(){
		lock.lock();
		try{
			if(changed==false) return;
			if(changed==true){
				System.out.println(Thread.currentThread().getName()+ " - Saved at time: " + date + " & content: " + contentofFile);
				changed = false;
			}
		}
		finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args){
		File file = new File();
		Thread t1 = new Thread( file.new Editor() );
		t1.start();
		Thread t2 = new Thread( file.new AutoSaver());
			t2.start();
		try{
			Thread.sleep(500);
		}catch (InterruptedException e) {}
		file.new Editor().setDone();
		file.new AutoSaver().setDone();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {}
		System.out.println("threads terminated");
	}
	
	private class Editor implements Runnable{
		
		public void run(){
			while(true){
				lock.lock();
				try{
					if(done==true) {
						break;
					}
				}finally{
					lock.unlock();
				}	
				try{
					for(int i = 0; i < 4; i++){
						afile.change();
						afile.save();
						Thread.sleep(1000);
					}
				}catch(InterruptedException exception){}
				
			}
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
	
	private class AutoSaver implements Runnable{
		public void run(){
			while(true){
				lock.lock();
				try{
					if(done==true) {
						break;
					}
				}finally{
					lock.unlock();
				}
				try{
					for(int i = 0; i < 4; i++){
						afile.save();
						Thread.sleep(2000);
					}
				}catch(InterruptedException exception){}
				
			}
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
}
