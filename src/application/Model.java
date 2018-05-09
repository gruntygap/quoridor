package application;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

	private int boardSize;
	
	private ArrayList<ArrayList<Space>> board;
	
	private Player playerOne;
	
	private Player playerTwo;
	
	//private boolean gameOver;
	
	public Model(int size) {
		try {
		// Sets the board size
		setBoardSize(size);
		// Initialize Players
		this.playerOne = new Player("Player 1");
		this.playerTwo = new Player("Player 2");
		// Creates the board
		createBoard(size);
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
				if(j == (j / 2) + 1) {
					// If we are in the top row, add player one in the space
					if(i == 0) {
						this.board.get(i).add(j, new Space(playerOne));
					}
					// If we are in the bottom row, add player two in the space
					else if(i == (size-1)) {
						this.board.get(i).add(j, new Space(playerTwo));
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

	public int getBoardSize() {
		return boardSize;
	}

	private void setBoardSize(int size) throws Exception {
		if(size > 1 && size % 2 != 0) {
			this.boardSize = size;
		} else {
			throw new Exception("Size is an invalid number!");
		}
	}
	
	public ArrayList<ArrayList<Space>> getBoard(){
		return board;
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				s += "(" + i +", "+ j +") ||| " + this.board.get(i).get(j) + "\n";
			}
		}
		return s;
	}
	
}
