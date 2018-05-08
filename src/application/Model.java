package application;

import java.util.Observable;

public class Model extends Observable {

	private int size;
	
	public Model(int size) {
		
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
}
