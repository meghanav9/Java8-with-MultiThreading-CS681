

import java.util.Date;

public class File extends FSElement {	
	File(Directory parent, String name, String owner,Date created, int size) {
		super(parent, name, owner, created);
		this.size=size;
	}

	@Override
	public boolean isLeaf() {
		return true;
	}
	public String toString() {

		StringBuffer stringbuffer = new StringBuffer();

		stringbuffer.append("File: " + this.name + "  ");
		stringbuffer.append("Parent: " + this.parent.name + "  ");
		stringbuffer.append("Owner: " + this.owner + "  ");
		stringbuffer.append("Created: " + this.created + "\r\n");
		return stringbuffer.toString();
	}

	@Override
	public FSElement getTarget() {
		return this;
	}

	public int getSize(){
		return this.size;
	}

}
