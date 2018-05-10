package application;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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
		return data[0];
	}
	
	public int getRow() {
		return data[1];
	}
}
