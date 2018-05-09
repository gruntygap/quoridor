package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Observable;

public class Model extends Observable {

	private int boardSize;
	
	private ArrayList<ArrayList<Space>> board;
	
	private Player playerOne;
	
	private Player playerTwo;
	
	private int turn;
	
	//private boolean gameOver;
	
	public Model(int size) throws Exception {
		try {
		// Sets the board size and creates board and players
		setBoardSize(size);
		} catch(Exception e) {
			throw new Exception("Object not created: " + e.getMessage());
		}
	}
	
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
	
	private void resetPlayers() {
		// Initialize/Reset Players
		this.playerOne = new Player("Player 1");
		this.playerTwo = new Player("Player 2");
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int size) throws Exception {
		if(size > 1 && size % 2 != 0) {
			resetPlayers();
			this.boardSize = size;
			createBoard(size);
			this.turn = 1;
		} else {
			throw new Exception("Size is an invalid number!");
		}
	}
	
	public ArrayList<ArrayList<Space>> getBoard(){
		return board;
	}
	
	private void changeTurn() {
		if(turn == 1) {
			turn = 2;
		} else if(turn == 2) {
			turn = 1;
		}
	}
	
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
	
	public void makeMove() {
		
	}
	
	public void movePlayer() {
		
	}
	
	public void placeWall(int x, int y, String key) throws Exception {
		// Temporary fence;
		Fence temp = null;
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
	}
	
	// If a valid move from player
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
		// Adds the space to the seen set
		seen.add(this.board.get(x).get(y));
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
				seen.add(this.board.get(tempX - 1).get(tempY));
			}
			// If there is no bottom fence placed
			if(!temp.getBottom().getPlaced()) {
				q.add(this.board.get(tempX + 1).get(tempY));
				seen.add(this.board.get(tempX + 1).get(tempY));
			}
			// If there is no left fence placed
			if(!temp.getLeft().getPlaced()) {
				q.add(this.board.get(tempX).get(tempY - 1));
				seen.add(this.board.get(tempX).get(tempY - 1));
			}
			// If there is no right fence placed
			if(!temp.getRight().getPlaced()) {
				q.add(this.board.get(tempX).get(tempY + 1));
				seen.add(this.board.get(tempX).get(tempY + 1));
			}
		}
		return valid;
	}
	
	private boolean isGameOver() {
		return false;
	}
	
	public Player getPlayerOne() {
		return playerOne;
	}
	
	public Player getPlayerTwo() {
		return playerTwo;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				s += "(" + i +", "+ j +") ||| " + this.board.get(i).get(j) + "\n";
			}
		}
		s += "PLAYER 1: " + playerOne.getPosition()[0] + " " + playerOne.getPosition()[1] + "\n";
		s += "PLAYER 2: " + playerTwo.getPosition()[0] + " " + playerTwo.getPosition()[1];
		return s;
	}
	
}
