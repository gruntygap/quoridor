package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Observable;

public class Model extends Observable {

	private int boardSize;
	
	private ArrayList<ArrayList<Space>> board;
	
	private Player playerOne;
	
	private Player playerTwo;
	
	private int turn;
	
	//private boolean gameOver;
	
	public Model(int size) {
		try {
		// Sets the board size and creates board and players
		setBoardSize(size);
		} catch(Exception e) {
			System.out.println("Object not created: " + e.getMessage());
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
						System.out.println("LOL GOTTEM");
						this.board.get(i).add(j, new Space(playerOne));
						playerOne.setPosition(new int[]{i, j});
					}
					// If we are in the bottom row, add player two in the space
					else if(i == (size-1)) {
						this.board.get(i).add(j, new Space(playerTwo));
						playerTwo.setPosition(new int[]{i, j});
					}
					// Add generic space if none of those things
					else {
						this.board.get(i).add(j, new Space());
					}
				}
				// Add generic space if not in top or bottom row
				else {
					this.board.get(i).add(j, new Space());
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
	
	public void placeWall() {
		
	}
	
	// If a valid move from player
	private boolean validMove(Player player) {
		int x = player.getPosition()[0];
		int y = player.getPosition()[1];
		// Queue of searching
		Queue<Space> q = new LinkedList<Space>();
		// ArrayList of all seen Spaces
		ArrayList<Space> seen = new ArrayList<Space>();
		ArrayList<Space> goalSpaces = new ArrayList<Space>();
		// Add the player location to the queue
		q.add(this.board.get(x).get(y));
		
		while(!q.isEmpty()) {
			
		}
		
		
	
		return false;
	}
	//TODO
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
