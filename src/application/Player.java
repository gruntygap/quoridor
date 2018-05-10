package application;
/**
 * Player Model Class
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 05/10/18
 */

public class Player {
	
	/** Gives the player a name */
	private String identifier;
	
	/** Stores the position of the player */
	private int[] position;
	
	/**
	 * The general constructor for the player
	 * @param name - The name for the identifier
	 */
	public Player(String name){
		this.identifier = name;
		this.position = null;
	}
	
	/**
	 * Sets the position of the player
	 * @param coords - With a array with 2 spots for x and y
	 */
	public void setPosition(int[] coords) {
		this.position = coords;
	}
	
	/**
	 * Gets the position of the player
	 * @return - An array with two values {x, y}
	 */
	public int[] getPosition() {
		return this.position;
	}
	
	/**
	 * gets the identifier for the player
	 * @return - The string of the player
	 */
	public String getIdentifier() {
		return this.identifier;
	}
}
