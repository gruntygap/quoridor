package application;
/**
 * Space Model Class
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 05/10/18
 */

/**
 * A Class that defines what a Space does/holds
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 5/10/18
 */
public class Space {

	// Instance variable that holds the fence to the top of this Space
	private Fence top;
	
	// Instance variable that holds the fence to the bottom of this Space
	private Fence bottom;
	
	// Instance variable that holds the fence to the left of this Space
	private Fence left;
	
	// Instance variable that holds the fence to the right of this Space
	private Fence right;
	
	// Instance variable that creates the player in this Space
	private Player playerSpace;
	
	// Keeps track of the position of this Space in a 2 spaced array
	private int[] position;
	
	/*
	 * Constructor for Spaces that take in the coordinates of the Space
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public Space(int x, int y) {
		top = null;
		bottom = null;
		left = null;
		right = null;
		playerSpace = null;
		position = new int[]{x,y};
	}
	
	/**
	 * Constructor that 
	 * @param player the current player in the Space
	 * @param x the x coordinate of the Space
	 * @param y the y coordinate o the Space
	 */
	public Space(Player player, int x, int y) {
		top = null;
		bottom = null;
		left = null;
		right = null;
		playerSpace = player;
		position = new int[]{x,y};
	}

	// Returns the fence on the top
	public Fence getTop() {
		return top;
	}
	
	// Sets the top fence
	public void setTop(Fence top) {
		this.top = top;
	}

	// Returns the bottom fence
	public Fence getBottom() {
		return bottom;
	}

	// Sets the bottom fence
	public void setBottom(Fence bottom) {
		this.bottom = bottom;
	}

	// Returns the left fence
	public Fence getLeft() {
		return left;
	}

	// Sets the left fence
	public void setLeft(Fence left) {
		this.left = left;
	}

	// Returns the right fence
	public Fence getRight() {
		return right;
	}

	// Sets the right fence
	public void setRight(Fence right) {
		this.right = right;
	}

	// Returns the playerSpace
	public Player getPlayerSpace() {
		return playerSpace;
	}

	// Sets the playerSpace
	public void setPlayerSpace(Player playerSpace) {
		this.playerSpace = playerSpace;
	}
	
	// Returns the position of the Space
	public int[] getPosition() {
		return position;
	}

	// toString used for testing purposes
	public String toString() {
		String s = "";
		s += "Top: " + this.getTop() + " | Bottom: " + this.getBottom() + " | Left: " + this.getLeft() + " | Right: " + this.getRight() + " | PLAYER: " + this.getPlayerSpace();
		return s;
	}
}
