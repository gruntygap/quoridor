package application;

public class Space {

	private Fence top;
	private Fence bottom;
	private Fence left;
	private Fence right;
	private Player playerSpace;
	private int[] position;
	
	public Space(int x, int y) {
		top = null;
		bottom = null;
		left = null;
		right = null;
		playerSpace = null;
		position = new int[]{x,y};
	}
	
	public Space(Player player, int x, int y) {
		top = null;
		bottom = null;
		left = null;
		right = null;
		playerSpace = player;
		position = new int[]{x,y};
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
	
	public int[] getPosition() {
		return position;
	}

	public String toString() {
		String s = "";
		s += "Top: " + this.getTop() + " | Bottom: " + this.getBottom() + " | Left: " + this.getLeft() + " | Right: " + this.getRight() + " | PLAYER: " + this.getPlayerSpace();
		return s;
	}
}
