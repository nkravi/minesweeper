package minesweeper.nkmine;


import java.util.Random;

class BoardCoin{
	boolean hasBomb;
	boolean isOpen;
	int nearByBombs;
}

public class MineSweeper {

	private BoardCoin[][] board;
	private int bombs;
	private int bombLoopCount;
	private boolean gameEnded;
	private int totalOpenCells;
	
	public MineSweeper(int boardSize,int bombLoopCount) {
		this.board = new BoardCoin[boardSize][boardSize];
		this.bombLoopCount = bombLoopCount;
		this.bombs = 0;
		this.gameEnded = false;
		this.totalOpenCells = 0;
		initializeBoard();
	}
	
	public BoardCoin[][] getBoard(){
		return board;
	}
	
	public int getTotalCellsToBeOpen() {
		return this.totalOpenCells;
	}
	
	private void placeBombs() {
		Random rand = new Random();
		while(this.bombLoopCount >0 ) {
			int col = rand.nextInt(board.length-1);
			int row = rand.nextInt(board.length-1);
			if(!board[row][col].hasBomb) {
				board[row][col].hasBomb = true;
				this.bombs++;
			}
			bombLoopCount--;
		}
	}
	
	
	private void openCells(int row,int col) {
		if(this.isOutOfBounds(row, col)) {
			return;
		}
		if(board[row][col].isOpen || 
				board[row][col].hasBomb) {
			return;
		}
		if(board[row][col].nearByBombs >0) {
			board[row][col].isOpen = true;
			this.totalOpenCells++;
			return;
		}
		board[row][col].isOpen = true;
		this.totalOpenCells++;
		//left
		openCells(row,col-1);
		//right
		openCells(row,col+1);
		//top
		openCells(row+1,col);
		//bottom
		openCells(row-1,col);
		//top left
		openCells(row+1,col-1);
		//top right
		openCells(row+1,col+1);
		//bottom right
		openCells(row-1,col+1);
		//bottom left
		openCells(row-1,col-1);
		
	}
	
	public boolean isGameEnded() {
		return this.gameEnded;
	}
	
	public void open(int row,int col) {
		if(this.isOutOfBounds(row, col) || board[row][col].isOpen || this.gameEnded) {
			return;
		}
		//click on bomb
		if(board[row][col].hasBomb) {
			this.gameEnded = true;
			return;
		}
		openCells(row,col);
		if(!board[row][col].isOpen) {
			board[row][col].isOpen = true;
			this.totalOpenCells++;
		}
		if(isWin()) {
			this.gameEnded = true;
		}
		
	}
	
	public boolean isWin() {
		return bombs + totalOpenCells == board.length*board.length;
	}
	
	public void printScore() {
		if(this.gameEnded) {
			System.out.println("is win: "+ isWin());
			System.out.println("cells to be opened "+ 
					(board.length* board.length - bombs - this.totalOpenCells));
		}else {
			System.out.println("Game has not ended");
		}
	}
	
	private boolean isOutOfBounds(int row, int col) {
		return row<0 || row >= board.length || col <0 || col >= board.length;
	}
	
	private int getNearByBombs(int row,int col) {
		int totalBombs = 0;
		//left
		if(!isOutOfBounds(row,col-1) && board[row][col-1].hasBomb) {
			totalBombs++;
		}
		//right
		if(!isOutOfBounds(row,col+1) && board[row][col+1].hasBomb) {
			totalBombs++;
		}
		//top
		if(!isOutOfBounds(row+1,col) && board[row+1][col].hasBomb) {
			totalBombs++;
		}
		//bottom
		if(!isOutOfBounds(row-1,col) && board[row-1][col].hasBomb) {
			totalBombs++;
		}
		//top left
		if(!isOutOfBounds(row+1,col-1) && board[row+1][col-1].hasBomb) {
			totalBombs++;
		}
		//top right
		if(!isOutOfBounds(row+1,col+1) && board[row+1][col+1].hasBomb) {
			totalBombs++;
		}
		//bottom left
		if(!isOutOfBounds(row-1,col-1) && board[row-1][col-1].hasBomb) {
			totalBombs++;
		}
		//bottom right
		if(!isOutOfBounds(row-1,col+1) && board[row-1][col+1].hasBomb) {
			totalBombs++;
		}
		return totalBombs;
	}
	
	private void updateNearByBombCount() {
		for(int row=0;row<board.length;row++) {
			for(int col=0; col<board[row].length; col++) {
				board[row][col].nearByBombs = getNearByBombs(row,col);
			}
		}
	}
	
	public void printBoardState() {
		for(int row=0;row<board.length;row++) {
			for(int col=0; col<board[row].length; col++) {
				if(board[row][col].isOpen) {
					System.out.print(board[row][col].nearByBombs);
				}else {
					System.out.print('-');
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	
	public void printBoard() {
		for(int row=0;row<board.length;row++) {
			for(int col=0; col<board[row].length; col++) {
				if(board[row][col].hasBomb) {
					System.out.print('B');
				}else {
					System.out.print(board[row][col].nearByBombs);
				}
				System.out.print("\t");
			}
			System.out.println();
		}
	}
	
	private void initializeBoard() {
		for(int row=0; row < board.length; row++) {
			for(int col=0; col< board[row].length; col++) {
				board[row][col] = new BoardCoin();
			}
		}
		placeBombs();
		updateNearByBombCount();
	}
	
	
}
