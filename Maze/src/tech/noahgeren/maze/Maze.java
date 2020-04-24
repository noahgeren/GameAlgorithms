package tech.noahgeren.maze;

public class Maze {
	
	private boolean[][] walls;
	
	private int[] start;
	private int[] finish;
	
	public Maze() {
		reset();
	}
	
	public void setStart(int x, int y) {
		start = new int[] {x, y};
	}
	
	public void setFinish(int x, int y) {
		finish = new int[] {x, y};
	}
	
	public int[] getStart() {
		return start;
	}
	
	public int[] getFinish() {
		return finish;
	}
	
	public boolean isWall(int x, int y) {
		if(x < 0 || y < 0 || x >= walls.length || y >= walls[0].length) return true;
		return walls[x][y];
	}
	
	public void setWall(int x, int y, boolean value) {
		walls[x][y] = value;
	}
	
	public void reset() {
		walls = new boolean[100][100];
		start = null;
		finish = null;
	}

}
