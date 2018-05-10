package application;
/**
 * Fence Model Class
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 05/10/18
 */

public class Fence {
	
	/** If a given fence spot has a fence, constricting travel
	 *  True if the fence is placed
	 */
	private boolean placed;
	
	/**
	 * The general constructor for the fence object
	 * Is not automatically placed
	 * These are the inside fences.
	 */
	public Fence() {
		this.placed = false;
	}
	
	/**
	 * The Specified constructor for the fence object
	 * Placed depending on the boolean value coming in.
	 * @param alreadyPlaced - If the fence is already placed, set the value to true;
	 */
	public Fence(boolean alreadyPlaced) {
		this.placed = alreadyPlaced;
	}
	
	/**
	 * Gets the placed value
	 * @return - the bool signifying if the fence is placed
	 */
	public boolean getPlaced() {
		return placed;
	}
	
	/**
	 * A method to place the fence
	 * @throws Exception - if the fence is already placed
	 */
	public void placeFence() throws Exception {
		if(this.placed) {
			throw new Exception("This fence has already been placed!");
		} else {
			this.placed = true;
		}
	}
	
	/**
	 * For the model, if the fence is invalid, then remove it
	 */
	public void removeFence() {
		this.placed = false;
	}
	
	/**
	 * toString method for testing model
	 */
	public String toString() {
		String s = "";
		if(this.placed) {
			s+= "Placed";
		} else {
			s+= "null";
		}
		return s;
	}
}
