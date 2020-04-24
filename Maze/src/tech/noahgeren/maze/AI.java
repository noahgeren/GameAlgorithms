package tech.noahgeren.maze;

import java.util.HashSet;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Stack;

public class AI {
		
	private static class Node implements Comparable<Node> {
		final public Node parent;
		final public int x, y;
		final double h, g;
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
		@Override
		public int hashCode() {
			return Objects.hash(x, y);
		}
	}
	
	public static int[][] findApproxShortestPath(Maze maze) {
		final PriorityQueue<Node> open = new PriorityQueue<>(100);
		final HashSet<Node> closed = new HashSet<>(100);
		Node current = new Node(null, maze.getStart()[0], maze.getStart()[1], 0, 0);
		while(current.x != maze.getFinish()[0] || current.y != maze.getFinish()[1]) {
			closed.add(current);
			addNeighborsToList(current, maze, open, closed);
			if(open.isEmpty()) return null;
			current = open.poll();
		}
		final Stack<Node> path = new Stack<Node>();
		while(current.x != maze.getStart()[0] || current.y != maze.getStart()[1]) {
			current = current.parent;
            path.push(current);
		}
		path.pop();
		final int[][] pathCoords = new int[path.size()][2];
		for(int i = 0; !path.isEmpty(); i++) {
			Node node = path.pop();
			pathCoords[i] = new int[] {node.x, node.y};
		}
		return pathCoords;
	}

	private static void addNeighborsToList(Node current, Maze maze, PriorityQueue<Node> open, HashSet<Node> closed) {
		for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
            	final Node node = new Node(current, 
            			current.x + x, current.y + y, 
            			current.g + ((x == 0 || y == 0) ? 1 : 1.414), distance(maze, current, x, y));
            	if(!maze.isWall(node.x, node.y) && !open.contains(node) && !closed.contains(node)) {
            		if(x != 0 && y != 0 && 
            				maze.isWall(node.x, current.y) && maze.isWall(current.x, node.y)) continue;
            		open.offer(node);
            	}
            }
		}
	}

	private static double distance(Maze maze, Node current, int x, int y) {
		return Math.hypot(current.x + x - maze.getFinish()[0], current.y + y - maze.getFinish()[1]);
	}

}
