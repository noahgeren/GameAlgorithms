package tech.noahgeren.maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GUI extends JFrame{

	private static final long serialVersionUID = -7975540226304781143L;
	
	private GUIState state = GUIState.DRAWING;
	
	public GUI(Maze maze) {
		super("Maze Solver");
		setSize(900, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel grid = new JPanel(new GridLayout(100, 100));
		grid.setPreferredSize(new Dimension(900, 900));
		JPanel[][] cells = new JPanel[100][100];
		final MouseListener mouseListener = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				final String[] coords = e.getComponent().getName().split(",");
				final int x = Integer.parseInt(coords[0]), y = Integer.parseInt(coords[1]);
				if(state != GUIState.SOLVE) {
					switch(e.getModifiersEx()) {
						case MouseEvent.BUTTON1_DOWN_MASK:
							switch(state) {
							case DRAWING:
								e.getComponent().setBackground(Color.BLACK);
								maze.setWall(x, y, true);
								break;
							case START:
								e.getComponent().setBackground(Color.BLUE);
								state = GUIState.END;
								maze.setStart(x, y);
								break;
							case END:
								e.getComponent().setBackground(Color.GREEN);
								state = GUIState.SOLVE;
								maze.setFinish(x, y);
								int[][] path = AI.findApproxShortestPath(maze);
								if(path != null) {
									for(int[] coord : path) {
										cells[coord[0]][coord[1]].setBackground(Color.RED);
									}
								} else {
									JOptionPane.showMessageDialog(null, "Maze is unsolvable.", "Unsolvable", JOptionPane.ERROR_MESSAGE);
								}
								break;
							default:
								break;
							}
							break;
						case MouseEvent.BUTTON3_DOWN_MASK:
							if(state == GUIState.DRAWING) {
								e.getComponent().setBackground(Color.WHITE);
								maze.setWall(x, y, false);
							}
							break;
					}
				}
			}	
			
			@Override
			public void mouseEntered(MouseEvent e) {
				final String[] coords = e.getComponent().getName().split(",");
				final int x = Integer.parseInt(coords[0]), y = Integer.parseInt(coords[1]);
				if(state == GUIState.DRAWING) {
					switch(e.getModifiersEx()) {
						case MouseEvent.BUTTON1_DOWN_MASK:
							e.getComponent().setBackground(Color.BLACK);
							maze.setWall(x, y, true);
							break;
						case MouseEvent.BUTTON3_DOWN_MASK:
							e.getComponent().setBackground(Color.WHITE);
							maze.setWall(x, y, false);
							break;
					}
				}
			}
		};
		for(int y = 0; y < 100; y++) {
			for(int x = 0; x < 100; x++) {
				final JPanel cell = new JPanel();
				cell.setBackground(Color.WHITE);
				cell.setName(x + "," + y);
				cell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				cell.addMouseListener(mouseListener);
				grid.add(cell);
				cells[x][y] = cell;
			}
		}
		JPanel buttons = new JPanel();
		JButton start = new JButton("Start");
		start.addActionListener(e -> {
			if(start.getText().equals("Start")) {
				start.setText("Reset");
				JOptionPane.showMessageDialog(null, "Pick where to start and finish.\nChoose carefully, you only have one click per endpoint.", "Choose Endpoints", JOptionPane.PLAIN_MESSAGE);
				state = GUIState.START;
			}else {
				start.setText("Start");
				maze.reset();
				for(int x = 0; x < 100; x++) {
					for(int y = 0; y < 100; y++) {
						cells[x][y].setBackground(Color.WHITE);
					}
				}
				state = GUIState.DRAWING;
			}
			
		});
		buttons.add(start);
		getContentPane().add(grid, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new GUI(new Maze());
		});
	}
	
	private enum GUIState {
		DRAWING, START, END, SOLVE
	}

}
