import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Link extends FSElement{
	private int size=0;
	private FSElement target;
	private ArrayList<FSElement> refer;
	
	Link(Directory parent, String name, String owner, Date date) {
		super(parent, name, owner, date);
		this.refer = new ArrayList<FSElement>();
		this.size = 0;
	}
	public void linkto(FSElement fs){
		this.target = fs;
		this.refer.add(fs);
	}
	@Override
	public boolean isLeaf() {
		return true;
	}
	public ArrayList<FSElement> getChildren(){
		return this.refer;
	}
	public FSElement getTarget()
	{
		return this.target.getTarget();
	}
	public int getTargetSize(){
		
		Iterator<FSElement> itr =this.getChildren().iterator();	
		while(itr.hasNext()){
			FSElement elem = itr.next();
			if(elem.isLink()){
				size = size + elem.getsize();
				((Link) elem).getTargetSize();
			}
			else if(!elem.isFile()){								
				if(!elem.isFile()){
					size = size + elem.getsize();
					((Directory) elem).getsize();
				}
			}
			else{
				size = size + elem.getsize();
			}
		}
		return size;
	}
	
	public int getDiskUtil(){
		return 0;
	}
	public String toString() {

		StringBuffer stringbuffer = new StringBuffer();

		stringbuffer.append("Link: " + this.name + "  ");
		stringbuffer.append("Parent: " + this.parent.name + "  ");
		stringbuffer.append("Target: " + this.target.name + "  ");
		stringbuffer.append("Owner: " + this.owner + "  ");
		stringbuffer.append("Created: " + this.created + "\r\n");
		return stringbuffer.toString();
	}

}
