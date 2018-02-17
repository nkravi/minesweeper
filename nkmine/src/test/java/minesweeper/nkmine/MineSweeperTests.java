package minesweeper.nkmine;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MineSweeperTests {
	
	MineSweeper ms;
	
	
	@Before
	public void init() {
		ms = new MineSweeper(10,20);
	}
	
	@Test
	public void successGame() {
		BoardCoin[][] bc = ms.getBoard(); 
		for(int row=0; row< bc.length;row++) {
			for(int col=0; col< bc[row].length; col++) {
				if(!bc[row][col].hasBomb && !bc[row][col].isOpen) {
					ms.open(row, col);
				}
			}
		}
		assertEquals(true,ms.isGameEnded());
		assertEquals(true,ms.isWin());
	}
	
	
	@Test
	public void failureGame() {
		BoardCoin[][] bc = ms.getBoard(); 
		for(int row=0; row< bc.length;row++) {
			for(int col=0; col< bc[row].length; col++) {
				if(bc[row][col].hasBomb) {
					ms.open(row, col);
					break;
				}
			}
		}
		assertEquals(true,ms.isGameEnded());
		assertEquals(false,ms.isWin());
	}
}
