import java.util.concurrent.locks.ReentrantLock;


public class FileCrawler implements Runnable{
	private Directory dir; //root dir of a given drive (tree structure)
	private FileQueue queue = FileQueue.getInstance();
	private boolean done;
	public ReentrantLock lock;
	public FileCrawler(Directory dir) {

		this.dir = dir;
		this.done = false;
		this.lock = new ReentrantLock();
	}
	public void setDone() {
		lock.lock();
		try {
			done = true;
		} finally {
			lock.unlock();
		}
	}
	public void run(){
	crawl(this.dir);
	}
	private void crawl(Directory dir){
	// Crawl a given tree structure
	// Put files to a queue. Ignore directories and links.
		for (int i = 0; i < dir.getChildren().size(); i++) {
			try {
				lock.lock();
				if (done) {
					System.out.println("Thread(FileCrawler) "
							+ Thread.currentThread().getId() + " stoped by main thread");
					return;
				}
				lock.unlock();
				FSElement fse = dir.getChildren().get(i);
				if (fse instanceof File) {
					System.out.println("Thread: "
							+ Thread.currentThread().getId() + " crawl the file "
							+ fse.getName());
					queue.put((File) fse);

				}
				if (fse instanceof Directory) {
					System.out.println("Thread: "
							+ Thread.currentThread().getId() + " crawl the directory "
							+ fse.getName());
					queue.put((Directory) fse);
					crawl((Directory) fse);
					
				}
				if(fse instanceof Link){
					System.out.println("Thread: "
							+ Thread.currentThread().getId() + " crawl the Link "
							+ fse.getName());
					queue.put((Link) fse);
				}
			} catch (InterruptedException e) {
				System.out.println("Thread(FileCrawler) " + Thread.currentThread().getId()
						+ " is being stopped by InterruptedException");
				continue;
			}
		}

	}

}
