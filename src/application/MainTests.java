package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTests {
	
	@Test
	void verifyPointers() {
		Model model = null;
		try {
			model = new Model(5);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
//		System.out.println(model);
		
		assertTrue(model.getBoard().get(0).get(0).getRight().equals(model.getBoard().get(0).get(1).getLeft()));
		assertTrue(model.getBoard().get(0).get(0).getBottom().equals(model.getBoard().get(1).get(0).getTop()));
	}
	
	@Test
	void verifyPointerConnection() {
//		System.out.println("=============================================================================================================================================================");
//		System.out.println("");
		Model model = null;
		try {
			model = new Model(5);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
//		System.out.println(model);
		
//		System.out.println(model.getBoard().get(0).get(0).getRight().getPlaced() + " -> " + model.getBoard().get(0).get(1).getLeft().getPlaced());
		try {
			model.getBoard().get(0).get(0).getRight().placeFence();
		} catch(Exception e) {
			System.out.println("" + e.getMessage());
		}
//		System.out.println(model.getBoard().get(0).get(0).getRight().getPlaced() + " -> " + model.getBoard().get(0).get(1).getLeft().getPlaced());
	}
	
	@Test
	void verifyPlayerLocation() {
//		System.out.println("=============================================================================================================================================================");
//		System.out.println("");
		Model model = null;
		try {
			model = new Model(5);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		assertTrue(model.getBoard().get(0).get(2).getPlayerSpace().equals(model.getPlayerOne()));
		
	}
	
	@Test
	void testPathfinding() {
		Model model = null;
		try {
			model = new Model(5);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			model.placeFence(0, 0, "bottom"); // change Player 2 turn
			model.placeFence(0, 1, "bottom"); // change Player 1 turn
			model.placeFence(0, 2, "bottom"); //
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(model);
		
		
	}
}
