package application;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


// implements EventHandler<ActionEvent>,Observer
public class View extends BorderPane {
	
	private Button reset;
	
	private ComboBox<Integer> sizeSelect;
	
	private Label feedback;
	
	public View() {
		
		//model = new model(size)
		//model.addObserver(this);
		
		
		// Creates a button that resets the board to its original state
		reset = new Button("Reset");
		// Tells us that this class handles button presses
	//  reset.setOnAction(this);
		
		// Creates a ComboBox to change sizes
		sizeSelect = new ComboBox<Integer>();
		// Adds sizes 5x5, 7x7, and 11x11
		sizeSelect.setItems(FXCollections.observableArrayList(5, 7, 11));
		// Sets the default size as 3 (in index 2) to display first
		sizeSelect.getSelectionModel().select(1);
		// Tells us that this class handles changes to the ComboBox
	//	sizeSelect.setOnAction(this);
		
	}
	
	
}
