package application;

import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


// implements EventHandler<ActionEvent>,Observer
public class View extends BorderPane implements EventHandler<ActionEvent>, Observer {
	
	private Model model;
	
	private Button reset;
	
	private ComboBox<Integer> sizeSelect;
	
	private Label feedback;
	
	public View() {
		
		model = new Model(5);
		model.addObserver( this);
		
		feedback = new Label("Quoridor!");
		
		
		// Creates a button that resets the board to its original state
		reset = new Button("Reset");
		// Tells us that this class handles button presses
	    reset.setOnAction(this);
		
		// Creates a ComboBox to change sizes
		sizeSelect = new ComboBox<Integer>();
		// Adds sizes 5x5, 7x7, and 11x11
		sizeSelect.setItems(FXCollections.observableArrayList(5, 7, 11));
		// Sets the default size as 3 (in index 2) to display first
		sizeSelect.getSelectionModel().select(1);
		// Tells us that this class handles changes to the ComboBox
		sizeSelect.setOnAction(this);
		
		GridPane top = new GridPane();
		
		top.add(feedback, 0, 0);
		top.add(new Label("Select a size: "), 0, 1);
		
		this.setTop(top);
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == sizeSelect) {
			model.setSize(sizeSelect.getSelectionModel().getSelectedItem());
		}
		
	}
	
	
}
