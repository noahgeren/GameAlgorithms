package tech.noahgeren.maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AI {
	
	// TODO: Improve performance and efficiency
	
	private static class Node implements Comparable<Node> {
		public Node parent;
		public int x, y;
		double h, g;
		Node(Node parent, int x, int y, double g, double h){
			this.parent = parent;
			this.x = x;
			this.y = y;
			this.g = g;
			this.h = h;
		}
		@Override
		public int compareTo(Node o) {
			return (int) ((g + h - o.g - o.h) * 100);
		}
		@Override
		public boolean equals(Object o) {
			if(o instanceof Node) {
				Node other = (Node) o;
				return x == other.x && y == other.y;
			}
			return false;
		}
	}
	
	public static int[][] findShortestPath(Maze maze) {
		final List<Node> open = new ArrayList<>();
		final List<Node> closed = new ArrayList<>();
		final List<Node> path = new ArrayList<>();
		Node current = new Node(null, maze.getStart()[0], maze.getStart()[1], 0, 0);
		closed.add(current);
		while(current.x != maze.getFinish()[0] || current.y != maze.getFinish()[1]) {
			addNeighborsToList(current, maze, open, closed);
			if(open.isEmpty()) return null;
			current = open.get(0);
			open.remove(0);
			closed.add(current);
		}
		path.add(0, current);
		while(current.x != maze.getStart()[0] || current.y != maze.getStart()[1]) {
			current = current.parent;
            path.add(0, current);
		}
		final int[][] pathCoords = new int[path.size()][2];
		for(int i = 0; i < path.size(); i++) {
			Node node = path.get(i);
			pathCoords[i] = new int[] {node.x, node.y};
		}
		return pathCoords;
	}

	private static void addNeighborsToList(Node current, Maze maze, List<Node> open, List<Node> closed) {
		for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
            	final Node node = new Node(current, 
            			current.x + x, current.y + y, 
            			current.g + 1, distance(maze, current, x, y));
            	if(!maze.isWall(node.x, node.y) && !open.contains(node) && !closed.contains(node)) {
            		if(x != 0 && y != 0) {
            			if(maze.isWall(node.x, current.y) && maze.isWall(current.x, node.y)) continue;
            			node.g += 0.4;
            		}
            		open.add(node);
            	}
            }
		}
		Collections.sort(open);
	}

	private static double distance(Maze maze, Node current, int x, int y) {
		return Math.hypot(current.x + x - maze.getFinish()[0], current.y + y - maze.getFinish()[1]);
	}

}
