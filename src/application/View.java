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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
//	private ImageView playerOneImg;
//	
//	private ImageView playerTwoImg;

	public View() {
		
//		playerOneImg = new ImageView(new Image("/assets/SUPREMELEADERNKBK.png"));
//		playerOneImg.setPreserveRatio(true);
//		
//		playerTwoImg = new ImageView(new Image("/assets/gosnat.jpg"));
//		playerTwoImg.setPreserveRatio(true);
//
//		playerOneImg.setFitHeight(buttonGrid.get(0).get(0).getHeight());
//		playerTwoImg.setFitHeight(buttonGrid.get(0).get(0).getHeight());

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
		int c = 1;
		if (model.getBoardSize() == 5) {
			c = 30;
		}else if (model.getBoardSize() == 7) {
			c = 24;
		}else if (model.getBoardSize() == 11) {
			c = 15;
		}

		gameBoard = new GridPane();
		gameBoard.setAlignment(Pos.CENTER);

		// Make all Buttons
		buttonGrid = new ArrayList<ArrayList<SpaceButton>>();
		for(int i = 0; i < model.getBoardSize()*2 - 1; i++) {
			buttonGrid.add(new ArrayList<SpaceButton>());
			for(int j = 0; j < model.getBoardSize()*2 - 1; j++) {
				// For fence-posts
				if (i % 2 == 1 && j % 2 == 1) { 
					//					 buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "fence-post"));
					buttonGrid.get(i).add(new SpaceButton("", "fence-post"));
					buttonGrid.get(i).get(j).setMinSize(c * 1, c * 1);
					buttonGrid.get(i).get(j).setMaxSize(c * 1, c * 1);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: #533226;-fx-border-color: #533226;");
				}
				// for vert-fence (s)
				else if(j % 2 == 1){
					//					buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "vert-fence"));
					buttonGrid.get(i).add(new SpaceButton("", "vert-fence"));
					buttonGrid.get(i).get(j).setMinSize(c * 1, c * 3);
					buttonGrid.get(i).get(j).setMaxSize(c * 1, c * 3);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
				}
				// for horiz-fence (s)
				else if(i % 2 == 1){
					//					buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "horiz-fence"));
					buttonGrid.get(i).add(new SpaceButton("", "horiz-fence"));
					buttonGrid.get(i).get(j).setMinSize(c * 3, c * 1);
					buttonGrid.get(i).get(j).setMaxSize(c * 3, c * 1);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
				}
				// for space (s)
				else {
					//					buttonGrid.get(i).add(new SpaceButton(i + ", " + j, "space"));
					buttonGrid.get(i).add(new SpaceButton("", "space"));
					buttonGrid.get(i).get(j).setMinSize(c * 3, c * 3);
					buttonGrid.get(i).get(j).setMaxSize(c * 3, c * 3);
					buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
				}
				gameBoard.add(buttonGrid.get(i).get(j), j, i);
			}
		}

		// Add the gameboard to the GUI
		// Sets the outside border color of the gameBoar
		gameBoard.setStyle("-fx-border-width: 3px; -fx-border-color: #72048F; -fx-alignment: center;");
		gameBoard.setMaxSize(500, 500);
		this.setCenter(gameBoard);

		// Adding handlers to each board piece
		for(int i = 0; i < model.getBoardSize()*2 - 1; i++) {
			for(int j = 0; j < model.getBoardSize()*2 - 1; j++) {
				String type = buttonGrid.get(i).get(j).getType();
				SpaceButton sB = buttonGrid.get(i).get(j);
				int x = i/2;
				int y = j/2;
				if(type.equals("space")) {
					sB.setOnAction((event) -> {
						//						System.out.println("Playable area: " + sB.getRow() + ", " + sB.getColumn());
						try {
							model.makeMove(x, y);
						} catch(Exception e) {
							alertMethod(e);
						}
					});
				} else if(type.equals("vert-fence")) {
					sB.setOnAction((event) -> {
						//						System.out.println("Vertical Fence: " + sB.getRow() + ", " + sB.getColumn());
						try {
							model.placeFence(x, y, "right");
						} catch (Exception e) {
							alertMethod(e);
						}
					});
				} else if(type.equals("horiz-fence")) {
					sB.setOnAction((event) -> {
						//						System.out.println("Horizontal Fence: " + sB.getRow() + ", " + sB.getColumn());
						try {
							model.placeFence(x, y, "bottom");
						} catch (Exception e) {
							alertMethod(e);
						}
					});
				}
			}	
		}
		this.updateGameBoard();
	}

	// TODO
	public void updateGameBoard() {
		
		// Loop through the gridPane, updating all elements
		for (int i = 0; i < model.getBoardSize()*2 - 1; i ++) {
			for (int j = 0; j < model.getBoardSize()*2 - 1; j ++) {
				// TODO PLAYER CASE
				if (buttonGrid.get(i).get(j).getType().equals("space")) {
					if (model.getBoard().get(i/2).get(j/2).getPlayerSpace() != null) {
						int scalar = 1;
						if (model.getBoardSize() == 5) {
							scalar = 90;
						}else if (model.getBoardSize() == 7) {
							scalar = 72;
						}else {
							scalar = 45;
						}
						if(model.getBoard().get(i/2).get(j/2).getPlayerSpace().equals(model.getPlayerOne())) {
							Image playerOneImage = new Image("/assets/SUPREMELEADERNKBK.png", scalar, scalar, true, true);
							ImageView temp = new ImageView(playerOneImage);
							buttonGrid.get(i).get(j).setGraphic(temp);
						}else {
							
							Image playerTwoImage = new Image("/assets/gosnat.jpg", scalar, scalar, true, true);
							ImageView temp = new ImageView(playerTwoImage);
							buttonGrid.get(i).get(j).setGraphic(temp);						
						}

					} else {
						buttonGrid.get(i).get(j).setGraphic(null);
						buttonGrid.get(i).get(j).setStyle("-fx-background-color: Transparent;-fx-border-color: #8F1D04;");
					}
				}

				if (buttonGrid.get(i).get(j).getType().equals("horiz-fence")) {
					if(model.getBoard().get(i/2).get(j/2).getBottom().getPlaced()) {
						buttonGrid.get(i).get(j).setStyle("-fx-background-color: #784122;-fx-border-color: #533226;");
					}
				}
				if(buttonGrid.get(i).get(j).getType().equals("vert-fence")) {
					if(model.getBoard().get(i/2).get(j/2).getRight().getPlaced()) {
						buttonGrid.get(i).get(j).setStyle("-fx-background-color: #784122;-fx-border-color: #533226;");
					}
				}
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
			this.updateGameBoard();
			this.feedback.setText(model.getFeedBack());
		}
	}

	@Override
	public void handle(ActionEvent event) {

	}


}
