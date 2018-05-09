package application;

public class Player {
	
	private String identifier;
	private int[] position;
	
	public Player(String name){
		this.identifier = name;
		this.position = null;
	}
	
	public void setPosition(int[] coords) {
		this.position = coords;
	}
	
	public int[] getPosition() {
		return this.position;
	}
	
	public String getIdentifier() {
		return this.identifier;
	}
}
