package application;
/**
 * Main Model Class
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 05/14/18
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Observable;

public class Model extends Observable {

	/** Size of the board */
	private int boardSize;
	
	/** A 2D array of Space(s) which represents the 'board' */
	private ArrayList<ArrayList<Space>> board;
	
	/** Player One of the game */
	private Player playerOne;
	
	/** Player Two of the game */
	private Player playerTwo;
	
	/** A number representing whose turn it is */
	private int turn;
	
	/** A boolean that stores if the game is over */
	private boolean gameOver;
	
	/**
	 * The general constructor for the Model
	 * @param size - The n value that is for the n * n board
	 * ^ must be an odd number greater than 1
	 * @throws Exception - If the board size is invalid, do not create
	 */
	public Model(int size) throws Exception {
		try {
		// Sets the board size and creates board and players
		setBoardSize(size);
		} catch(Exception e) {
			throw new Exception("Board not created: " + e.getMessage());
		}
	}
	
	/**
	 * Creates the board, and fences (from the method call at the bottom).
	 * @param size - Creates the board with a size of size.
	 */
	private void createBoard(int size) {
		// Initializes the 2D arrayList
		this.board = new ArrayList<ArrayList<Space>>();
		
		for(int i = 0; i < size; i++) {
			this.board.add(i, new ArrayList<Space>());
			for(int j = 0; j < size; j++) {
				// If the added space is along the center column
				if(j == ((this.boardSize - 1) / 2)) {
					// If we are in the top row, add player one in the space
					if(i == 0) {
						this.board.get(i).add(j, new Space(playerOne, i, j));
						playerOne.setPosition(new int[]{i, j});
					}
					// If we are in the bottom row, add player two in the space
					else if(i == (size-1)) {
						this.board.get(i).add(j, new Space(playerTwo, i, j));
						playerTwo.setPosition(new int[]{i, j});
					}
					// Add generic space if none of those things
					else {
						this.board.get(i).add(j, new Space(i, j));
					}
				}
				// Add generic space if not in top or bottom row
				else {
					this.board.get(i).add(j, new Space(i, j));
				}
			}
		}
		addDefaultFences();
	}
	
	/**
	 * Adds the fences onto the board.
	 * Adds placed outer fences as well as unplaced inner fences.
	 */
	private void addDefaultFences() {
		// Creates the outer ring of placed fences
		// These fences are shared with no other spaces
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				// If we are looping through the top row, set the top fence as placed
				if(i == 0) {
					this.board.get(i).get(j).setTop(new Fence(true));
				}
				// If we are looping through the bottom row, set the bottom fence as placed
				if(i == (this.boardSize - 1)) {
					this.board.get(i).get(j).setBottom(new Fence(true));
				}
				// If we are looping through the left column, set the left fence as placed
				if(j == 0) {
					this.board.get(i).get(j).setLeft(new Fence(true));
				}
				// If we are looping through the right column, set the right fence as placed
				if(j == (this.boardSize - 1)) {
					this.board.get(i).get(j).setRight(new Fence(true));
				}
			}
		}
		// Creates the inner fences
		// Each of these fences are shared with two adjacent spaces
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				if(j < boardSize - 1) {
					this.board.get(i).get(j).setRight(new Fence());
				}
				if(j > 0) {
					this.board.get(i).get(j).setLeft(this.board.get(i).get(j-1).getRight());
				}
				if(i < boardSize - 1) {
					this.board.get(i).get(j).setBottom(new Fence());
				}
				if(i > 0) {
					this.board.get(i).get(j).setTop(this.board.get(i-1).get(j).getBottom());
				}
			}
		}
	}
	
	/**
	 *  Creates the players
	 *  Also used to reset the players
	 */
	private void createPlayers() {
		// Initialize/Reset Players
		this.playerOne = new Player("Player 1");
		this.playerTwo = new Player("Player 2");
	}

	/**
	 * Gets the board size
	 * @return boardSize - which is the n in the n * n board.
	 */
	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * Sets the board Size, as well as creating everything.
	 * @param size - The size to set the board to.
	 * @throws Exception - If the size is not odd and greater than one.
	 */
	public void setBoardSize(int size) throws Exception {
		if(size > 1 && size % 2 != 0) {
			// Creates/Resets players
			createPlayers();
			// Sets the size variable with the size
			this.boardSize = size;
			// Creates the board and fences
			createBoard(size);
			// Sets the initial turn
			this.turn = 1;
			// sets observers as changed.
			this.setChanged();
			// Notifies the Observers
			this.notifyObservers("reset");
		} else {
			throw new Exception("Size is an invalid number!");
		}
	}
	
	/**
	 * Gets the board
	 * @return - return the ArrayList<ArrayList<Space>>
	 */
	private ArrayList<ArrayList<Space>> getBoard(){
		return board;
	}
	
	/**
	 * Change the turn from 1 to 2 and 2 to 1
	 */
	private void changeTurn() {
		if(turn == 1) {
			turn = 2;
		} else if(turn == 2) {
			turn = 1;
		}
	}
	
	/**
	 * Gets feedback for the current game
	 * @return - A string that tells the status of the game
	 */
	public String getFeedBack() {
		if(isGameOver()) {
			return "Player " + turn + " Won!";
		}
		else if(turn == 1) {
			return "It is " + playerOne.getIdentifier() + "'s turn"; 
		}
		else if(turn == 2) {
			return "It is " + playerTwo.getIdentifier() + "'s turn";
		} else {
			return "";
		}
	}
	
	/**
	 * Makes a move for the current player
	 * @param x - The x coordinate to move to 
	 * @param y - The y coordinate to move to
	 * @throws Exception -
	 * 	If the game is over
	 * 	If the space is invalid
	 */
	public void makeMove(int x, int y) throws Exception {
		if(this.gameOver) {
			throw new Exception("Game is Over");
		}
		Player pointer = null;
		if(this.turn == 1) {
			pointer = playerOne;
		} else if(this.turn == 2) {
			pointer = playerTwo;
		}
		if(validPlayerMove(pointer, x, y)) {
			// get the current space location of the player
			Space old = this.board.get(pointer.getPosition()[0]).get(pointer.getPosition()[1]);
			// Set the intended space as the space holding the player
			this.board.get(x).get(y).setPlayerSpace(pointer);
			pointer.setPosition(new int[] {x,y});
			// Set the old playerSpace to holding null
			old.setPlayerSpace(null);
		} else {
			throw new Exception("That is not a valid space to move to");
		}
		// Player has now moved, it is the other players turn
		if(!isGameOver()) {
			changeTurn();
		}
		this.setChanged();
		this.notifyObservers("updateBoard");
	}
	
	/**
	 * If the move is valid for the current player
	 * @param currentPlayer - The player that is currently making a turn
	 * @param x - The x move in question
	 * @param y - The y move in question
	 * @return - a boolean stating if the move is valid or not.
	 */
	private boolean validPlayerMove(Player currentPlayer, int x, int y) {
		boolean valid = false;
		// Get location of the currentPlayer
		int playerX = currentPlayer.getPosition()[0];
		int playerY = currentPlayer.getPosition()[1];
		
		// Get location of otherPlayer
		int otherPlayerX;
		int otherPlayerY;
		if(currentPlayer.equals(playerOne)) {
			otherPlayerX = playerTwo.getPosition()[0];
			otherPlayerY = playerTwo.getPosition()[1];
		} else {
			otherPlayerX = playerOne.getPosition()[0];
			otherPlayerY = playerOne.getPosition()[1];
		}
		// Create an arrayList of spaces which are valid for current player to move to.
		ArrayList<Space> validSpaces = new ArrayList<Space>();
		
		// Tests if the space of the player doesnt have a top placed fence
		if(!this.board.get(playerX).get(playerY).getTop().getPlaced())
			validSpaces.add(this.board.get(playerX - 1).get(playerY));
		
		// Tests if the space of the player doesnt have a bottom placed fence
		if(!this.board.get(playerX).get(playerY).getBottom().getPlaced())
			validSpaces.add(this.board.get(playerX + 1).get(playerY));
		
		// Tests if the space of the player doesnt have a left placed fence
		if(!this.board.get(playerX).get(playerY).getLeft().getPlaced())
			validSpaces.add(this.board.get(playerX).get(playerY - 1));
		
		// Tests if the space of the player doesnt have a right placed fence
		if(!this.board.get(playerX).get(playerY).getRight().getPlaced())
			validSpaces.add(this.board.get(playerX).get(playerY + 1));
		
		// If any of the valid spaces contain the space of the other player
		if(validSpaces.contains(this.board.get(otherPlayerX).get(otherPlayerY))) {
			// Cannot move to the other player, as the other player lives there
			// Remove that space from valid Spaces.
			validSpaces.remove(this.board.get(otherPlayerX).get(otherPlayerY));
			
			// The Spaces that may be valid
			if(!this.board.get(otherPlayerX).get(otherPlayerY).getTop().getPlaced())
				validSpaces.add(this.board.get(otherPlayerX - 1).get(otherPlayerY));
			
			if(!this.board.get(otherPlayerX).get(otherPlayerY).getBottom().getPlaced())
				validSpaces.add(this.board.get(otherPlayerX + 1).get(otherPlayerY));
			
			if(!this.board.get(otherPlayerX).get(otherPlayerY).getLeft().getPlaced())
				validSpaces.add(this.board.get(otherPlayerX).get(otherPlayerY - 1));
			
			if(!this.board.get(otherPlayerX).get(otherPlayerY).getRight().getPlaced())
				validSpaces.add(this.board.get(otherPlayerX).get(otherPlayerY + 1));
			
			// Remove the space the player is coming from
			validSpaces.remove(this.board.get(playerX).get(playerY));
		}
		// If the validSpaces algorithm finds the space player wants to go to
		// Then it is true
		if(validSpaces.contains(this.board.get(x).get(y))) {
			valid = true;
		}
		return valid;
	}
	
	/**
	 * Places a fence move
	 * @param x - The x coord for the space that holds the fence
	 * @param y - The y coord for the space that holds the fence
	 * @param key - A string that says what fence one is trying to place
	 * ^ key can be == "top" || "bottom" || "right" || "left"
	 * @throws Exception - If the fence key is given and invalid
	 */
	public void placeFence(int x, int y, String key) throws Exception {
		if(this.gameOver) {
			throw new Exception("Game is Over");
		}
		// Temporary fence;
		Fence temp = null;
		// Gets the temp fence based on they key word
		if(key.equals("top")) {
			temp = this.board.get(x).get(y).getTop();
		} else if(key.equals("bottom")) {
			temp = this.board.get(x).get(y).getBottom();
		} else if(key.equals("left")) {
			temp = this.board.get(x).get(y).getLeft();
		} else if(key.equals("right")) {
			temp = this.board.get(x).get(y).getRight();
		} else {
			throw new Exception("There are no fences called: " + key);
		}
		
		if(temp.getPlaced()) {
			throw new Exception("There is already a fence there!");
		}
		
		// Place the fence so we can make sure its valid for both players
		temp.placeFence();
		// Test validity for both players
		// If either makes it an invalid move, then the fence is removed, and exception thrown
		if(!(validFencePlacement(playerOne) && validFencePlacement(playerTwo))) {
			temp.removeFence();
			throw new Exception("You are not allowed to block a path to a player goal!");
		}
		// If the player sets a valid fence, then change the turn 
		changeTurn();
		this.setChanged();
		this.notifyObservers("updateBoard");
	}
	
	/**
	 * If there is a path from player to playerGoal
	 * @param player - The player in question
	 * @return - boolean that says if the fence placement is valid
	 */
	private boolean validFencePlacement(Player player) {
		// By default the fence placement is not valid
		boolean valid = false;
		int x = player.getPosition()[0];
		int y = player.getPosition()[1];
		// Queue of searching
		Queue<Space> q = new LinkedList<Space>();
		// A set of all seen Spaces
		Set<Space> seen = new HashSet<Space>();
		// An ArrayList of spaces that we want to reach
		ArrayList<Space> goalSpaces = new ArrayList<Space>();
		
		// Add the player location space to the queue
		q.add(this.board.get(x).get(y));

		// Creates the seen spaces depending on the player
		// For player two its goal is to get to row 0
		int row = 0;
		// For player one its goal is to get to row 1
		if(player.equals(playerOne)) {
			row = this.boardSize - 1;
		}
		for(int i = 0; i < this.boardSize; i++) {
			goalSpaces.add(this.board.get(row).get(i));
		}
		
		while(!q.isEmpty()) {
			Space temp = q.remove();
			int tempX = temp.getPosition()[0];
			int tempY = temp.getPosition()[1];
			// If temp has been seen already, continue
			if(seen.contains(temp)) {
				continue;
			}
			// If goal spaces contains temp, then player can make it to its positions.
			if(goalSpaces.contains(temp)) {
				valid = true;
				break;
			}
			
			// If there is no top fence placed
			if(!temp.getTop().getPlaced()) {
				q.add(this.board.get(tempX - 1).get(tempY));
				//seen.add(this.board.get(tempX - 1).get(tempY));
			}
			// If there is no bottom fence placed
			if(!temp.getBottom().getPlaced()) {
				q.add(this.board.get(tempX + 1).get(tempY));
				//seen.add(this.board.get(tempX + 1).get(tempY));
			}
			// If there is no left fence placed
			if(!temp.getLeft().getPlaced()) {
				q.add(this.board.get(tempX).get(tempY - 1));
				//seen.add(this.board.get(tempX).get(tempY - 1));
			}
			// If there is no right fence placed
			if(!temp.getRight().getPlaced()) {
				q.add(this.board.get(tempX).get(tempY + 1));
				//seen.add(this.board.get(tempX).get(tempY + 1));
			}
			// Adds the space to the seen set
			seen.add(temp);
		}
		return valid;
	}
	
	/**
	 * Tests if the game is over
	 * @return - if the game is over boolean
	 */
	private boolean isGameOver() {
		Set<Space> playerOneGoal = new HashSet<Space>();
		Set<Space> playerTwoGoal = new HashSet<Space>();
		for(int i = 0; i < this.boardSize; i++) {
			playerOneGoal.add(this.board.get(this.boardSize - 1).get(i));
			playerTwoGoal.add(this.board.get(0).get(i));
		}
		
		if(playerOneGoal.contains(this.board.get(this.getPlayerOne().getPosition()[0]).get(this.getPlayerOne().getPosition()[1])) ||
				playerTwoGoal.contains(this.board.get(this.getPlayerTwo().getPosition()[0]).get(this.getPlayerTwo().getPosition()[1]))) {
			this.gameOver = true;
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the firstPlayer
	 * @return - Player which is playerOne
	 */
	public Player getPlayerOne() {
		return playerOne;
	}
	
	/**
	 * Gets the secondPlayer
	 * @return - Player which is playerTwo
	 */
	public Player getPlayerTwo() {
		return playerTwo;
	}
	
	/**
	 * Resets the game
	 * @throws Exception - If the board size is invalid (SHOULD NEVER HAPPEN)
	 */
	public void resetGame() throws Exception {
		this.turn = 1;
		this.gameOver = false;
		this.setBoardSize(this.boardSize);
	}
	
	/**
	 * Gets the player within the space (x, y)
	 * @param x the X coordinate of the space
	 * @param y the Y coordinate of the space
	 * @return The player that is in the playerSpace of the Space.
	 */
	public Player getPlayerForSpace(int x, int y) {
		return getBoard().get(x).get(y).getPlayerSpace();
	}
	
	/**
	 * Gets the placed boolean value for the listed fence based on key
	 * @param x - The X coord of the space that have the fence
	 * @param y - The Y coord of the space that have the fence
	 * @param key - The key for the fence, must be "top" || "bottom" ||
	 * "left" || "right"
	 * @return - The boolean value of if that fence is placed or not
	 * @throws Exception - if there is no "key" fence, throw exception
	 */
	public boolean getPlacedForFence(int x, int y, String key) throws Exception {
		if(key.equals("top")) {
			return this.board.get(x).get(y).getTop().getPlaced();
		} else if(key.equals("bottom")) {
			return this.board.get(x).get(y).getBottom().getPlaced();
		} else if(key.equals("left")) {
			return this.board.get(x).get(y).getLeft().getPlaced();
		} else if(key.equals("right")) {
			return this.board.get(x).get(y).getRight().getPlaced();
		} else {
			throw new Exception("There are no fences called: " + key);
		}
	}
	/**
	 * A toString method for testing the model w/o the GUI
	 */
	public String toString() {
		String s = "";
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				s += "(" + i +", "+ j +") ||| " + this.board.get(i).get(j) + "\n";
			}
		}
		s += "PLAYER 1: " + playerOne.getPosition()[0] + " " + playerOne.getPosition()[1] + "\n";
		s += "PLAYER 2: " + playerTwo.getPosition()[0] + " " + playerTwo.getPosition()[1] + "\n";
		s += "FEEDBACK: " + this.getFeedBack() + "\n";
		s += "TURN: " + this.turn;
		return s;
	}
	
}
