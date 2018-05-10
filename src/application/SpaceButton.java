package application;

import javafx.scene.control.Button;

public class SpaceButton extends Button {
	
	private int [] data;
	
	private String type;
	
	public SpaceButton(String string, String type) {
		this.setText(string);
		this.type = type;
		data = new int [2];
	}

	public void setData(int i, int j) {
		data = new int[]{i, j};
	}

	public int getColumn() {
		return data[1];
	}
	
	public int getRow() {
		return data[0];
	}
	
	public String getType() {
		return type;
	}
}
