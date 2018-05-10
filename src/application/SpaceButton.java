package application;

import javafx.scene.control.Button;

public class SpaceButton extends Button {
	
	private int [] data;
	
	public SpaceButton(String string) {
		this.setText(string);
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
}
