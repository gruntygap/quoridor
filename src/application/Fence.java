package application;

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
	
	public boolean getPlaced() {
		return placed;
	}
	
	public void placeFence() throws Exception {
		if(this.placed) {
			throw new Exception("This fence has already been placed!");
		} else {
			this.placed = true;
		}
	}
	
	public void removeFence() {
		this.placed = false;
	}
	
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
