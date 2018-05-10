package application;

/**
 * A Class that defines what a SpaceButton 
 * can and cannot do / hold
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 5/10/18
 */

import javafx.scene.control.Button;

// SpaceButton extends Button since it is a Button with
// extra functionality
public class SpaceButton extends Button {
	
	// Instance variable that holds what type of SpaceButton it is
	private String type;
	
	// Sets the text of the SpaceButton (for testing purposes)
	// and sets the type of the SpaceButton
	public SpaceButton(String string, String type) {
		this.setText(string);
		this.type = type;
	}
	
	/**
	 * @return the type of the SpaceButton
	 */
	public String getType() {
		return type;
	}
}
