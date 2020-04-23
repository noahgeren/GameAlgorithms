package tech.noahgeren.tictactoe;

public class TicTacToe {
	
	private final static int[][] wins = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
										 {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
										 {0, 4, 8}, {2, 4, 6}};
		
	private String[] cells = new String[9];
	
	private boolean xTurn = true;
	
	public void chooseCell(int x, int y) {
		chooseCell(y * 3 + x);
	}
	
	public void chooseCell(int index) {
		if(cells[index] == null) {
			cells[index] = xTurn ? "X" : "O";
		}
		xTurn ^= true;
	}
	
	public void removeCell(int index) {
		cells[index] = null;
		xTurn ^= true;
	}
	
	public void reset() {
		cells = new String[9];
	}
	
	public boolean isPlaying() {
		if(getWinner() == null) {
			for(String cell : cells) {
				if(cell == null) return true;
			}
		}
		return false;
	}
	
	public String getWinner() {
		for(int[] win : wins) {
			if(cells[win[0]] != null && 
					cells[win[0]].equals(cells[win[1]]) &&
					cells[win[1]].equals(cells[win[2]])) {
				return cells[win[0]];
			}
		}
		for(String cell : cells) {
			if(cell == null) return null;
		}
		return "Nobody";
	}
	
	public String getTurn() {
		return xTurn ? "X" : "O";
	}
	
	public String[] getCells() {
		return cells;
	}
	
}
