
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class MainProgram{
    
	public static void main(String[] args) throws IOException {
		Date date = new Date();
		FileSystem tmp = FileSystem.getFileSystem();
		tmp.setRoot(new Directory(null, "root", "own", date));
		Directory root=tmp.getRoot();
		tmp.setCurrent(tmp.getRoot());
	    Directory system = new Directory(tmp.getRoot(), "system","root", date);
	    Directory home = new Directory(tmp.getRoot(), "home","root", date);
	    File a = new File(system,"a","system",date, 10);
	    File b = new File(system,"b","system",date, 10);
	    File c = new File(system,"c","system", date,10);
	    Directory pictures = new Directory(home, "pictures","home", date);
	    Link x = new Link(home, "x", "home", date);
	    
	    File d = new File(home,"d","home",date, 10);
	    Link y = new Link(pictures,"y","picture", date);
	    File e = new File(pictures,"e","pictures",date, 60);
	    File f = new File(pictures,"f","pictures", date, 10);
        
	    root.appendChild(system);
	    root.appendChild(home);
        system.appendChild(a);
        system.appendChild(b);
        system.appendChild(c);
        
        home.appendChild(pictures);
        home.appendChild(x);
        home.appendChild(d);
        
        pictures.appendChild(y);
        pictures.appendChild(e);
        pictures.appendChild(f);
        
        x.linkto(system);
        y.linkto(e);
        
        ArrayList<Thread> threads= new  ArrayList<Thread>();
        FileQueue fq = FileQueue.getInstance();
        ArrayList<FileCrawler> fcList=new ArrayList<FileCrawler>();
        FileCrawler fc1 = new FileCrawler(root);
        fcList.add(fc1);
        threads.add(new Thread(fc1));
        FileCrawler fc2 =new FileCrawler(home);
        fcList.add(fc2);
        threads.add(new Thread(fc2));		

        ArrayList<FileIndexer> fiList=new ArrayList<FileIndexer>();
        for(int i = 0;i<4;i++)
        {
        	FileIndexer fi = new FileIndexer();
        	fiList.add(fi);
        	threads.add(new Thread(fi));		
        }
        for (Thread t: threads) {
              t.start();
        }
        try{
        	Thread.sleep(200);
        }catch(InterruptedException error){	}

        fcList.forEach((FileCrawler fc)->{fc.setDone();});
        fiList.forEach((FileIndexer fi)->{fi.setDone();});
        System.out.println("Interrupt threads");
        threads.forEach((Thread t)->{
        	t.interrupt();			
        });
	}
}	

