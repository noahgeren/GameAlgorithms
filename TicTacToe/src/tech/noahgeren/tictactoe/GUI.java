package tech.noahgeren.tictactoe;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class GUI extends JFrame{

	private static final long serialVersionUID = 8948570634331258360L;
	
	private JButton[] buttons = new JButton[9];
	
	public GUI(TicTacToe game) {
		super("TicTacToe");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new GridLayout(3, 3, 10, 10));
		ActionListener listener = e -> {
			JButton btn = ((JButton) e.getSource());
			btn.setText(game.getTurn());
			btn.setEnabled(false);
			game.chooseCell(Integer.parseInt(e.getActionCommand()));
			String winner = game.getWinner();
			if(winner != null) {
				JOptionPane.showMessageDialog(null, winner + " won.", winner + " Won", JOptionPane.INFORMATION_MESSAGE);
				reset();
				game.reset();
			} 
			if(game.getTurn().equals("O")){
				int aiMove = AI.getComputerMove(game);
				System.out.println(AI.counter);
				buttons[aiMove].doClick();
			}
		};
		final Font font = new Font("Arial", Font.BOLD, 48);
		for(int i = 0; i < 9; i++) {
			JButton btn = new JButton();
			btn.setPreferredSize(new Dimension(100, 100));
			btn.setActionCommand(String.valueOf(i));
			btn.setFont(font);
			btn.addActionListener(listener);
			getContentPane().add(btn);
			buttons[i] = btn;
		}
		pack();
		setVisible(true);
	}
	
	private void reset() {
		for(JButton btn : buttons) {
			btn.setText("");
			btn.setEnabled(true);
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() ->  {
			new GUI(new TicTacToe());
		});
	}

}
