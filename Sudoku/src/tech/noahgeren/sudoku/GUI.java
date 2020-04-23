package tech.noahgeren.sudoku;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame{

	private static final long serialVersionUID = 5984495259547829374L;
	
	private JTextField[][] cells = new JTextField[Sudoku.SIZE][Sudoku.SIZE];
	
	public GUI(Sudoku puzzle) {
		super("Sudoku");
		setSize(450, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel grid = new JPanel(new GridLayout(3, 3));
		grid.setPreferredSize(new Dimension(450, 450));
		final Font font = new Font("Arial", Font.BOLD, 32);
		final FocusListener focusListener =  new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				final String[] coords = e.getComponent().getName().split(",");
				final int x = Integer.parseInt(coords[0]), y = Integer.parseInt(coords[1]);
				try {
					Integer value = null;
					String input = cells[x][y].getText();
					if(!input.trim().isEmpty()) {
						value = Integer.parseInt(input);
					}
					puzzle.setCell(x, y, value);
				} catch(Exception ex) {
					cells[x][y].setText("");
				}
			}
		};
		for(int i = 0; i < Sudoku.SIZE; i++) {
			JPanel block = new JPanel(new GridLayout(3, 3));
			for(int j = 0; j < Sudoku.SIZE; j++) {
				final int x = (i % 3) * 3 + (j % 3), y = (int)(i / 3) * 3 + (j / 3);
				JTextField input = new JTextField(1);
				input.setHorizontalAlignment(JTextField.CENTER);
				input.setName(x + "," + y);
				input.addFocusListener(focusListener);
				input.setFont(font);
				block.add(input);
				cells[x][y] = input;
			}
			block.setBorder(new EmptyBorder(2, 2, 2, 2));
			grid.add(block);
		}
		JPanel buttons = new JPanel();
		final ActionListener buttonListener = e -> {
			switch(e.getActionCommand()) {
			case "check":
				JOptionPane.showMessageDialog(null, puzzle.checkIfSolved() ? "Great job!" : "Still needs some work.", "Check", JOptionPane.INFORMATION_MESSAGE);
				break;
			case "solve":
				try {
					AI.solve(puzzle);
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "This puzzle is unsolvable.", "Unsolvable", JOptionPane.ERROR_MESSAGE);
				}
				for(int x = 0; x < Sudoku.SIZE; x++) {
					for(int y = 0; y < Sudoku.SIZE; y++) {
						cells[x][y].setText(puzzle.getCell(x, y) + "");
					}
				}
				break;
			}
		};
		JButton check = new JButton("Check");
		check.setActionCommand("check");
		check.addActionListener(buttonListener);
		JButton solve = new JButton("Solve");
		solve.setActionCommand("solve");
		solve.addActionListener(buttonListener);
		buttons.add(check);
		buttons.add(solve);
		getContentPane().add(grid, BorderLayout.CENTER);
		getContentPane().add(buttons, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new GUI(new Sudoku());
		});
	}

}
