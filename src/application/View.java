package application;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


// implements EventHandler<ActionEvent>,Observer
public class View extends BorderPane implements EventHandler<ActionEvent>, Observer {
	
	// the Model object variable
	private Model model;
	
	// Instance variable for the button to reset the board
	private Button reset;
	
	// Instance variable for the ComboBox used to change the size
	private ComboBox<Integer> sizeSelect;
	
	// Instance variable for Label that displays player's turn and who won
	private Text feedback;
	
	// Instance variable used to scale text
	private ObjectProperty<Font> widthFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
	
	// Instance variable used to scale text
	private ObjectProperty<Font> heightFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
	
	// Instance variable that displays the playable board
	private GridPane gameBoard;
	
	private ArrayList<ArrayList<SpaceButton>> buttonGrid;
	
	public View() {
		
		// sets a nice neutral background color
		this.setStyle("-fx-background-color: B6D6DD;");
	    
		// a Model object used to communicate with the model
		try {
			model = new Model(5);
		} catch (Exception e) {
			alertMethod(e);
		}
		// Adds this class to a list of subscribers to Model
		model.addObserver(this);
		
		// Displays the gameBoard
		this.fillGameBoard();
		
		// Adds a BorderPane to organize the top part of the board
		BorderPane topBP = new BorderPane();
		topBP.setPadding(new Insets(0, 25, 0, 25));
		
		DropShadow ds = new DropShadow();
		ds.setOffsetY(10.0f);
		ds.setColor(Color.web("#183152"));
		
		
		// Creates a nice title for the game
		Text title = new Text("Quoridor!");
		title.setEffect(ds);
		title.setCache(true);
		title.setX(10.0f);
		title.setY(270.0f);
		title.setFill(Color.web("#F2797B"));
		// Scales the title with the width of the page
		title.fontProperty().bind(widthFontTracking);
		
		// Creates a nice feedback area for the game
		feedback = new Text(model.getFeedBack());
		feedback.setFill(Color.web("#375D81"));
		feedback.setEffect(ds);
		feedback.setCache(true);
		feedback.setX(10.0f);
		feedback.setY(270.0f);
		// Scales the feedback with the height of the page
		feedback.fontProperty().bind(heightFontTracking);
		
		// Creates a nice "Select a size: " area
		Text sizeText = new Text("Select a size: ");
		sizeText.setFill(Color.web("#375D81"));
		sizeText.setEffect(ds);
		sizeText.setCache(true);
		sizeText.setX(10.0f);
		sizeText.setY(270.0f);
		sizeText.setEffect(ds);
		// Scales the sizeLabel with the height of the page
		sizeText.fontProperty().bind(heightFontTracking);
		
		// Creates a button that resets the board to its original state
		reset = new Button("Reset");
		reset.setStyle("-fx-background-color: #C2C4C6;");
		// Tells us that this class handles button presses
	    reset.setOnAction((event) -> {
	    	try {
				model.resetGame();
			} catch (Exception e) {
				alertMethod(e);
			}
	    });
		
		// Creates a ComboBox to change sizes
		sizeSelect = new ComboBox<Integer>();
		// Adds sizes 5x5, 7x7, and 11x11
		sizeSelect.setItems(FXCollections.observableArrayList(5, 7, 11));
		// Sets the default size as 3 (in index 2) to display first
		sizeSelect.getSelectionModel().select(0);		
		
		sizeSelect.setStyle("-fx-background-color: #C2C4C6;");
		// Tells us that this class handles changes to the ComboBox
		sizeSelect.setOnAction((event) -> {
			try {
				model.setBoardSize(sizeSelect.getSelectionModel().getSelectedItem());
			} catch (Exception e) {
				alertMethod(e);
			}
		});
		
		// Creates a GridPane to hold data of right side of page
		GridPane topRightGP = new GridPane();
		
		// Adds the various data to the GridPane
		topRightGP.add(feedback, 1, 0);
		topRightGP.add(sizeText, 1, 1);
		topRightGP.add(sizeSelect, 2, 1);
		topRightGP.add(reset, 1, 2);
		
		VBox leftVBox = new VBox();
		leftVBox.getChildren().add(title);
		
		// Adds various data to the top BorderPane
		topBP.setLeft(leftVBox);
		topBP.setRight(topRightGP);
		
		// Adds top BorderPane to the default BorderPane
		this.setTop(topBP);
	
		// "Method" that scales text sizes with height of the page
		heightProperty().addListener(new ChangeListener<Number>()
	    {
	        @Override
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
	        {
	            heightFontTracking.set(Font.font(newWidth.doubleValue() / 24));
	        }
	    });
		
		// "Method" that scales text sizes with the width of the page
	    widthProperty().addListener(new ChangeListener<Number>()
	    {
	        @Override
	        public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
	        {
	            widthFontTracking.set(Font.font(newWidth.doubleValue() / 20));
	        }
	    });
		
	}
	
	public void fillGameBoard() {
		// Sets a scalar to size the game nicely
		int c = 12;
		if (model.getBoardSize() == 5) {
			c = 30;
		}else if (model.getBoardSize() == 7) {
			c = 24;
		}else if (model.getBoardSize() == 11) {
			c = 15;
		}
		
		buttonGrid = new ArrayList<ArrayList<SpaceButton>>();
		for(int i = 0; i < model.getBoardSize(); i++) {
			buttonGrid.add(new ArrayList<SpaceButton>());
		}
		gameBoard = new GridPane();
		gameBoard.setAlignment(Pos.CENTER);
		for (int i = 0; i < model.getBoardSize()*2 - 1; i ++) {
			for (int j = 0; j < model.getBoardSize()*2 - 1; j ++) {
				int x = j/2;
				int y = i/2;
				// Creates generic SpaceButton
				buttonGrid.get(x).add(y, new SpaceButton(x + ", " + y));
				SpaceButton b = buttonGrid.get(x).get(y);
				if (model.getBoard().get(x).get(y).getPlayerSpace() != null) {
					b.setText("Player");
				}
				b.setData(x, y);
				// Scales each button to the correct size.
				if (i % 2 == 1 && j % 2 == 1) { 
					// Creates the squares in between fence junctions that do absolutely nothing but look semi-okay
					SpaceButton sB = b;
					sB.setMinSize(c * 1, c * 1);
					sB.setMaxSize(c * 1, c * 1);
					sB.setStyle("-fx-background-color: #533226;-fx-border-color: #533226;");
				}else if (i % 2 == 1) {
					// Creates vertical areas for fences to be placed in
					b.setMinSize(c * 1, c * 3);
					b.setMaxSize(c * 1, c * 3);
					b.setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
					b.setOnAction((event) -> {
						System.out.println("Vertical Fence: " + b.getRow() + ", " + b.getColumn());
						//b.setText("Placed");
						//b.setStyle("-fx-background-color: #784122;-fx-border-color: #533226;");
						try {
							model.placeFence(x, y, "right");
						} catch (Exception e) {
								alertMethod(e);
						}
					});
				}else if (j %2 == 1) {
					// Creates horizontal areas for fences to be placed in
					b.setMinSize(c * 3, c * 1);
					b.setMaxSize(c * 3, c * 1);
					b.setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
					b.setOnAction((event) -> {
						System.out.println("Horizontal Fence: " + b.getRow() + ", " + b.getColumn());
						//b.setText("Placed");
						//b.setStyle("-fx-background-color: #784122;-fx-border-color: #533226;");
						try {
							model.placeFence(x, y, "bottom");
						} catch (Exception e) {
							alertMethod(e);
						}
					});
				}else {
					// Creates area for player to move into if valid
					b.setMinSize(c * 3, c * 3);
					b.setMaxSize(c * 3, c * 3);
					b.setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
					b.setOnAction((event) -> {
						System.out.println("Playable area: " + b.getRow() + ", " + b.getColumn());
						try {
							model.makeMove(x, y);
						} catch(Exception e) {
							alertMethod(e);
						}
					});
				}
				gameBoard.add(b, i, j);
			}
		}
		// Sets the outside border color of the gameBoar
		gameBoard.setStyle("-fx-border-width: 3px; -fx-border-color: #72048F; -fx-alignment: center;");
		gameBoard.setMaxSize(500, 500);
		this.setCenter(gameBoard);
	}
	
	// TODO
	public void updateGameBoard() {
		// Loop through the gridPane, updating all elements
		for (int i = 0; i < model.getBoardSize()*2 - 1; i ++) {
			for (int j = 0; j < model.getBoardSize()*2 - 1; j ++) {
				
			}
		}
	}
	
	public void alertMethod(Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Game Error");
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1.equals("reset")) {
			this.fillGameBoard();
			this.feedback.setText(model.getFeedBack());
		}else if (arg1.equals("updateBoard")) {
			this.feedback.setText(model.getFeedBack());
		}
	}

	@Override
	public void handle(ActionEvent event) {
		
	}
	
	
}
