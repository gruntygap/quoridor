package application;

public class Space {

	private Fence top;
	private Fence bottom;
	private Fence left;
	private Fence right;
	private Player playerSpace;
	//private int[] position;
	
	public Space() {
		top = null;
		bottom = null;
		left = null;
		right = null;
		playerSpace = null;
	}
	
	public Space(Player player) {
		top = null;
		bottom = null;
		left = null;
		right = null;
		playerSpace = player;
	}

	public Fence getTop() {
		return top;
	}

	public void setTop(Fence top) {
		this.top = top;
	}

	public Fence getBottom() {
		return bottom;
	}

	public void setBottom(Fence bottom) {
		this.bottom = bottom;
	}

	public Fence getLeft() {
		return left;
	}

	public void setLeft(Fence left) {
		this.left = left;
	}

	public Fence getRight() {
		return right;
	}

	public void setRight(Fence right) {
		this.right = right;
	}

	public Player getPlayerSpace() {
		return playerSpace;
	}

	public void setPlayerSpace(Player playerSpace) {
		this.playerSpace = playerSpace;
	}
	
	public String toString() {
		String s = "";
		s += "Top: " + this.getTop() + " | Bottom: " + this.getBottom() + " | Left: " + this.getLeft() + " | Right: " + this.getRight();
		return s;
	}
}
