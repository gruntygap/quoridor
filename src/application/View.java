package application;
/**
 * View for Quoridor
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 5/14/18
 */
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

// Constructor for View (and Controller) 
public class View extends BorderPane implements Observer {

	// the Model object variable which the view uses to tell the model where input occurred
	private Model model;

	// Instance variable for the button to reset the gameBoard
	private Button reset;

	// Instance variable for the ComboBox used to change the size of the game board
	private ComboBox<Integer> sizeSelect;

	// Instance variable for Label that displays player's turn and if a player won it displays who won
	private Text feedback;

	// Instance variable used to scale text
	private ObjectProperty<Font> widthFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

	// Instance variable used to scale text
	private ObjectProperty<Font> heightFontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

	// Instance variable that displays the playable board using a GridPane hodling SpaceButtons
	private GridPane gameBoard;

	// Instance variable that tracks where SpaceButtons are and what happens if they are clicked
	private ArrayList<ArrayList<SpaceButton>> buttonGrid;

	/**
	 * General Contructor for the view
	 */
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

		// Adds a DropShaodow effect to the text
		DropShadow ds = new DropShadow();
		ds.setOffsetY(10.0f);
		ds.setColor(Color.web("#183152"));


		// Creates a nice title for the game which is displayed in upper left corner
		Text title = new Text("Quoridor!");
		title.setEffect(ds);
		title.setCache(true);
		title.setX(10.0f);
		title.setY(270.0f);
		title.setFill(Color.web("#F2797B"));
		// Scales the title with the width of the page
		title.fontProperty().bind(widthFontTracking);

		// Creates a nice feedback area for the game which is displayed in upper right corner
		feedback = new Text(model.getFeedBack());
		feedback.setFill(Color.web("#375D81"));
		feedback.setEffect(ds);
		feedback.setCache(true);
		feedback.setX(10.0f);
		feedback.setY(270.0f);
		// Scales the feedback with the height of the page
		feedback.fontProperty().bind(heightFontTracking);

		// Creates a nice "Select a size: " area under feedback area
		Text sizeText = new Text("Select a size: ");
		sizeText.setFill(Color.web("#375D81"));
		sizeText.setEffect(ds);
		sizeText.setCache(true);
		sizeText.setX(10.0f);
		sizeText.setY(270.0f);
		sizeText.setEffect(ds);
		// Scales the sizeLabel with the height of the page
		sizeText.fontProperty().bind(heightFontTracking);

		// Creates a button that resets the board to its original state under select a size
		reset = new Button("Reset");
		// Changes color of button
		reset.setStyle("-fx-background-color: #C2C4C6;");
		// Calls model.resetGame() to reset the board when clicked
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
		// changes color of combobox
		sizeSelect.setStyle("-fx-background-color: #C2C4C6;");
		// updates the size of the board when changed
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

		// Creates a VBox to hold title and possibly timer on upper left of borderpane
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

	/*
	 * A method that creates the default viewable gameBoard for Quoridor
	 */
	public void fillGameBoard() {
		// Sets a scalar to size the game to fit within the default window size
		// and fullscreen.
		// If size is 5 we have a scalar of 30, if 7 then scalar of 24, if 11 then 15
		int c = 1;
		if (model.getBoardSize() == 5) {
			c = 30;
		}else if (model.getBoardSize() == 7) {
			c = 24;
		}else if (model.getBoardSize() == 11) {
			c = 15;
		}

		// Creates new GridPane and centers it
		gameBoard = new GridPane();
		gameBoard.setAlignment(Pos.CENTER);

		// Make all Buttons which will eventually either be a vertical fence,
		// a horizontal fence, a fence post (which is invald) or a space where
		// a player can reside.
		buttonGrid = new ArrayList<ArrayList<SpaceButton>>();
		for(int i = 0; i < model.getBoardSize()*2 - 1; i++) {
			// Creates 2D ArrayList of SpaceButtons
			buttonGrid.add(new ArrayList<SpaceButton>());
			for(int j = 0; j < model.getBoardSize()*2 - 1; j++) {
				// Creates fence-posts which have a default dark brown color and don't do anything/
				// can't be interacted with.
				if (i % 2 == 1 && j % 2 == 1) { 
					//buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "fence-post"));
					buttonGrid.get(i).add(new SpaceButton("", "fence-post"));
					buttonGrid.get(i).get(j).setMinSize(c * 1, c * 1);
					buttonGrid.get(i).get(j).setMaxSize(c * 1, c * 1);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: #533226;-fx-border-color: #533226;");
				}
				// Creates vertical fence areas which are colored like all other buttons until
				// clicked and then the updateGameBoard() method changes their color to brown and 
				// sets them to placed using the model if valid
				else if(j % 2 == 1){
					//buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "vert-fence"));
					buttonGrid.get(i).add(new SpaceButton("", "vert-fence"));
					buttonGrid.get(i).get(j).setMinSize(c * 1, c * 3);
					buttonGrid.get(i).get(j).setMaxSize(c * 1, c * 3);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
				}
				// Creates horizontal fence areas which are colored like all other buttons until
				// clicked and then the updateGameBoard() method changes their color to brown and
				// sets them to placed using the model if valid
				else if(i % 2 == 1){
					//buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "horiz-fence"));
					buttonGrid.get(i).add(new SpaceButton("", "horiz-fence"));
					buttonGrid.get(i).get(j).setMinSize(c * 3, c * 1);
					buttonGrid.get(i).get(j).setMaxSize(c * 3, c * 1);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
				}
				// Creates spaces which players can move into, which are colored the default color until placed
				// which then the assigned player image moves into that button
				else {
					//buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "space"));
					buttonGrid.get(i).add(new SpaceButton("", "space"));
					buttonGrid.get(i).get(j).setMinSize(c * 3, c * 3);
					buttonGrid.get(i).get(j).setMaxSize(c * 3, c * 3);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
				}
				// Adds SpaceButtons to the gameBoard
				gameBoard.add(buttonGrid.get(i).get(j), j, i);
			}
		}

		// Add the gameBoard to the GUI
		// Sets the outside border color of the gameBoard
		// and centers the gameBaord
		gameBoard.setStyle("-fx-border-width: 20px; -fx-border-color: #72048F; -fx-alignment: center;");
		gameBoard.setMaxSize(500, 500);
		this.setCenter(gameBoard);

		// Adding handlers to each board piece
		for(int i = 0; i < model.getBoardSize()*2 - 1; i++) {
			for(int j = 0; j < model.getBoardSize()*2 - 1; j++) {
				// Type determines if it is a vertical fence, horizontal fence, fence post, or playable space
				String type = buttonGrid.get(i).get(j).getType();
				SpaceButton sB = buttonGrid.get(i).get(j);
				// Since we used a size*2 - 1 GridPane to stop enough buttons for fences and playable spaces
				// we need to divide the row and column values by 2 to get points that can be used to interact
				// with the model
				int x = i/2;
				int y = j/2;
				if(type.equals("space")) {
					// This is a playable space SpaceButton
					sB.setOnAction((event) -> {
						//System.out.println("Playable area: " + sB.getRow() + ", " + sB.getColumn());
						// Try to make a move, but if it is not possible display an error as to why it cannot be completed
						try {
							model.makeMove(x, y);
						} catch(Exception e) {
							alertMethod(e);
						}
					});
				} else if(type.equals("vert-fence")) {
					// This is a vertical fence SpaceButton
					sB.setOnAction((event) -> {
						//System.out.println("Vertical Fence: " + sB.getRow() + ", " + sB.getColumn());
						// Try to place the vertical fence, but if it is not possible display an error as to why it cannot be completed
						try {
							model.placeFence(x, y, "right");
						} catch (Exception e) {
							alertMethod(e);
						}
					});
				} else if(type.equals("horiz-fence")) {
					// This is a horizontal fence SpaceButton
					sB.setOnAction((event) -> {
						//System.out.println("Horizontal Fence: " + sB.getRow() + ", " + sB.getColumn());
						// Try to place the horizontal fence, but if it is not possible display an error as to why it cannot be completed
						try {
							model.placeFence(x, y, "bottom");
						} catch (Exception e) {
							alertMethod(e);
						}
					});
				}
			}	
		}
		// Calls updatGameBoard() method to place the players in their default starting positions
		this.updateGameBoard();
	}

	/**
	 * Method that updates the gameBoard whenever a valid fence is played, or a player makes a valid move
	 */
	public void updateGameBoard() {
		// Loop through the GridPane, updating all elements
		for (int i = 0; i < model.getBoardSize()*2 - 1; i ++) {
			for (int j = 0; j < model.getBoardSize()*2 - 1; j ++) {
				if (buttonGrid.get(i).get(j).getType().equals("space")) {
					if (model.getPlayerForSpace(i/2, j/2) != null) {
						// If a player doesn't exist in this space, then...
						// scalar is used to scale the player images to the correct sizes depending on size of the board
						int scalar = 1;
						if (model.getBoardSize() == 5) {
							scalar = 90;
						}else if (model.getBoardSize() == 7) {
							scalar = 72;
						}else {
							scalar = 45;
						}
						if(model.getPlayerForSpace(i/2, j/2).equals(model.getPlayerOne())) {
							// If the player is player one then set the corresponding SpaceButton to the image below
							Image playerOneImage = new Image("/assets/SUPREMELEADERNKBK.png", scalar, scalar, true, true);
							ImageView temp = new ImageView(playerOneImage);
							buttonGrid.get(i).get(j).setGraphic(temp);
						}else {
							// Else the player is player two so set the corresponding SpaceButton to the image below
							Image playerTwoImage = new Image("/assets/gosnat.jpg", scalar, scalar, true, true);
							ImageView temp = new ImageView(playerTwoImage);
							buttonGrid.get(i).get(j).setGraphic(temp);						
						}
					} else {
						// Changes the SpaceButtons where a player use to be to the default coloring
						buttonGrid.get(i).get(j).setGraphic(null);
						buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
					}
				}
				// If the SpaceButton in buttonGrid is a horizontal fence then set the fence below of the space to placed
				// and set the color of that fence to brown signifying a placed fence.
				if (buttonGrid.get(i).get(j).getType().equals("horiz-fence")) {
					try {
						if(model.getPlacedForFence(i/2, j/2, "bottom")) {
							buttonGrid.get(i).get(j).setStyle("-fx-background-color: #784122;-fx-border-color: #533226;");
						}
					} catch (Exception e) {
						alertMethod(e);
					}
				}
				// If the SpaceButton in the buttonGrid is a vertical fence then set the fence to the right of the space to placed
				// and set the color of that fence to brown signifying a placed fence.
				if(buttonGrid.get(i).get(j).getType().equals("vert-fence")) {
					try {
						if(model.getPlacedForFence(i/2, j/2, "right")) {
							buttonGrid.get(i).get(j).setStyle("-fx-background-color: #784122;-fx-border-color: #533226;");
						}
					} catch (Exception e) {
						alertMethod(e);
					}
				}
			}
		}
	}

	/**
	 * Alert method used to display Alerts when an error is thrown
	 * @param e the received Exception
	 */
	public void alertMethod(Exception e) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Game Error");
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}

	/** 
	 * update method that updates the board and the feedback area
	 * either when the size is set/reset is clicked or when a valid 
	 * fence is placed or a valid move is made
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1.equals("reset")) {
			this.fillGameBoard();
			this.feedback.setText(model.getFeedBack());
		}else if (arg1.equals("updateBoard")) {
			this.updateGameBoard();
			this.feedback.setText(model.getFeedBack());
		}
	}

}
