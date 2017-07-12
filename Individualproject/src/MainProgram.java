
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainProgram{
    
	public static void main(String[] args) throws IOException, InterruptedException {
		Date date = new Date();
		FileSystem tmp = FileSystem.getFileSystem();
		tmp.setRoot(new Directory(null, "root", "own", date));
		Directory root=tmp.getRoot();
		tmp.setCurrent(tmp.getRoot());
	    Directory system = new Directory(tmp.getRoot(), "system","root", date);
	    Directory home = new Directory(tmp.getRoot(), "home","root", date);
	    File a = new File(system,"a.txt","system",date, 10);
	    File b = new File(system,"b.txt","system",date, 10);
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
        
        ExecutorService executor = Executors.newWorkStealingPool();
        FileCrawler fc1 = new FileCrawler(root);
        executor.execute(fc1);

        WebCrawler wc = new WebCrawler("http://www.google.com");
        executor.execute(wc);
        
        for(int i = 0;i<4;i++)
        {
        	FileIndexer fi = new FileIndexer();
        	executor.execute(fi);
        }
        Thread.sleep(8000);
        
        executor.shutdown();
	}
}	
