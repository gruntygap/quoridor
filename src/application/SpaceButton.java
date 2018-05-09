package application;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class SpaceButton extends Button {

	/**
	 * Instance variable that holds the card and its data
	 */
	private Space space;
	
	/**
	 * Instance variable that holds the image and its data
	 */
	private ImageView image;
	
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
