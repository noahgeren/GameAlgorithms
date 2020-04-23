package tech.noahgeren.sudoku;

import java.util.stream.IntStream;

public class Sudoku {
	
	private Integer[][] cells;
	
	public Sudoku() {
		reset();
	}
	
	public void reset() {
		cells = new Integer[9][9];
	}
	
	public boolean checkIfSolved() {
		for(int n = 0; n < 9; n++) {
			boolean[][] recorded = new boolean[3][9 + 1];
			final int xStart = (n % 3) * 3, yStart = (int)(n / 3) * 3;
			for(int i = 0; i < 9; i++) {
				if(cells[i][n] == null || cells[n][i] == null || 
						cells[xStart + (i / 3)][yStart + (i % 3)] == null || 
						recorded[0][cells[i][n]] || recorded[1][cells[n][i]] || 
						recorded[2][cells[xStart + (i / 3)][yStart + (i % 3)]]) return false;
				recorded[0][cells[i][n]] = true;
				recorded[1][cells[n][i]] = true;
				recorded[2][cells[xStart + (i / 3)][yStart + (i % 3)]] = true;
			}
		}
		return true;
	}
	
	public int[] getAvailableValues(int x, int y){
		boolean[] recorded = new boolean[9 + 1];
		final int xStart = (int)(x / 3) * 3, yStart = (int)(y / 3) * 3;
		for(int i = 0; i < 9; i++) {
			if(cells[x][i] != null) 
				recorded[cells[x][i]] = true;
			if(cells[i][y] != null)
				recorded[cells[i][y]] = true;
			if(cells[xStart + (i / 3)][yStart + (i % 3)] != null) 
				recorded[cells[xStart + (i / 3)][yStart + (i % 3)]] = true;
		}
		return IntStream.range(1, 9 + 1)
				.filter(i -> !recorded[i])
				.toArray();
	}
	
	public void setCell(int x, int y, Integer value) {
		if(value != null && (value < 1 || value > 9)) 
			throw new RuntimeException("Value should be between 1 and " + 9 + " inclusively.");
		cells[x][y] = value;
	}
	
	public Integer getCell(int x, int y) {
		return cells[x][y];
	}

}
