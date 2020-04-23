package tech.noahgeren.sudoku;

public class AI {

	public static void solve(Sudoku puzzle) {
		if(!solveCell(puzzle, 0, 0))
			throw new RuntimeException("This puzzle is unsolvable.");
	}
	
	private static boolean solveCell(Sudoku puzzle, int x, int y) {
		if(x >= 9 || y >= 9) return true;
		if(puzzle.getCell(x, y) != null)
			return solveCell(puzzle, (x + 1) % 9, y + (x / 8));
		for(int value : puzzle.getAvailableValues(x, y)) {
			puzzle.setCell(x, y, value);
			if(solveCell(puzzle, (x + 1) % 9, y + (x / 8))) return true;
			puzzle.setCell(x, y, null);
		}
		return false;
	}
	
	
	
}
