package application;
/**
 * Test Cases
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 05/10/18
 */

// UPDATE: This file used to have 5 tests in it
// However, to protect access to getBoard method, many of them became useless.
// Their purpose had been served.
// RIP 3 tests.

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MainTests {

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
		
		assertTrue(model.getPlayerForSpace(0, 2).equals(model.getPlayerOne()));
		
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
			model.placeFence(0, 2, "bottom"); // change Player 2 turn
			model.placeFence(0, 3, "bottom"); // change Player 1 turn
			model.placeFence(0, 4, "bottom"); // change Player 2 turn
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("You are not allowed to block a path to a player goal!"));
		}
		System.out.println(model);
		
		
	}
}
