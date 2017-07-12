

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FileSystem {
	private static FileSystem instance = new FileSystem();
	private Directory root;
	private Directory current;
	private Comparator<FSElement> comparator;
	private FileSystem(){}
	public static FileSystem getFileSystem() {
		return instance;
	}	
	public ArrayList<FSElement> sort(Directory dir, Comparator<FSElement> comparator){
		this.current = dir;
		this.comparator = comparator;
		Collections.sort(getChildren(), this.comparator);
		for(FSElement fse : getChildren())
			System.out.println(fse.getName());
		return null;
	}
	public void setRoot(Directory root){
		this.root = root;
	}
	public Directory getRoot(){
		return this.root;
	}
	public void setCurrent(Directory current){
		this.current = current;
	}
	public Directory getCurrent(){
		return this.current;
	}
	public ArrayList<FSElement> getChildren() {
		return this.current.getChildren();
	}
	
	
	public void showAllElements(){
		System.out.println("Parent:"+ root.getParent() +"\t"+"	Directory:	"+ root.getName());								
		this.root.showAllElements();
	}

}
