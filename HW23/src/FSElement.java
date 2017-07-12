
import java.util.Date;

public abstract class FSElement {
		protected String name;
		protected String owner;
		protected Date created = new Date();
		protected Date lastModified = new Date();
		protected int size;
		protected Directory parent = null;

		FSElement(Directory parent, String name, String owner, Date created){
			this.parent = parent;
			this.name = name;
			this.owner = owner;
			this.created = created;
			this.lastModified = created;			
		}
		public void setParent(Directory parent) {
			this.parent = parent;
		}
		public Directory getParent(){
			return this.parent;	
		}
		public boolean isFile(){			
			return this instanceof File;
		}
		public boolean isLink(){
			return this instanceof Link;
		}
		public abstract boolean isLeaf();
		
		public int getsize(){
			return this.size;
		}
		public String getowner(){
			return this.owner;
		}
		public void setowner(String owner){
			this.owner = owner;
		}
		public void setlastModified(Date lastModified){
			this.lastModified = lastModified;
		}
		
		public Date getlastModified(){
			return this.lastModified;
		}
		public String getName(){
			return this.name;
		}
		public void setName(String name){
			this.name = name;
		}	
		public Date getcreated(){
			return this.created;
		}
		public abstract  FSElement getTarget() ;
		public int getDiskUtil(){
			return this.size;
		}
}
