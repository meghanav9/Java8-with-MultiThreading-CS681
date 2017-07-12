
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class Directory extends FSElement{
	private int size;
	private ArrayList<FSElement> children;
	int size_arr[] = new int[5];
	FileSystem filesystem;
	FSElement childrenn;
	Directory(Directory parent,String name, String owner,Date created) {
		super(parent , name, owner, created);
		this.children = new ArrayList<FSElement>();
		this.size = 0;
	}
	public void appendChild(FSElement fse){
		Date date = new Date();
		this.children.add(fse);
		this.lastModified = date;
	}
	public void addChild(FSElement child, int index){
		children.add(child);
	}
	public ArrayList<FSElement> getChildren(){
		return this.children;
	}	
	@Override
	public boolean isLeaf() {
		return false;
	}
	public void showAllElements() {
		Iterator<FSElement> itr =this.getChildren().iterator();
		while(itr.hasNext()){
			FSElement elem = itr.next();
			if(elem.isLink()){
				System.out.println("Parent:"+ getName() +"\t"+"	Link:\t	"+ elem.getName());								
			}
			else if(!elem.isFile()){
				System.out.println("Parent:"+ getName() +"\t"+"	Directory:	"+ elem.getName());								
				if(!elem.isFile()){
					((Directory) elem).showAllElements();
				}
			}
			else{
				System.out.println("Parent:"+ getName() +"\t"+"	File:	"+"\t"+ elem.getName());					
			}
		}
	}
	
	@Override
	public int getsize(){
		Iterator<FSElement> itr =this.getChildren().iterator();	
		while(itr.hasNext()){
			FSElement elem = itr.next();
			if(elem.isLink()){
				this.size = this.size + elem.getsize();
			}
			else if(!elem.isFile()){								
				if(!elem.isFile()){
					this.size = this.size + elem.getsize();
					((Directory) elem).getsize();
				}
			}
			else{
				this.size = this.size + elem.getsize();
			}
		}	
		return this.size;
	}
	public String toString() {
		StringBuffer stringbuffer = new StringBuffer();
		stringbuffer.append("Directory: " + this.name + "  ");
		String parent = "null";
		if (this.parent != null)
		{
			parent = this.parent.name;
		}
		stringbuffer.append("Parent: " + parent + "  ");
		stringbuffer.append("Owner: " + this.owner + "  ");
		stringbuffer.append("Created: " + this.created + "\r\n");
		return stringbuffer.toString();
	}
	@Override
	public FSElement getTarget() {
		return this;
	}

}