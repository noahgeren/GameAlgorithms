package tech.noahgeren.tictactoe;

public class AI {
	
	// TODO: Make this class not static
	
	public static int counter = 0;
	
	public static int getComputerMove(TicTacToe game) {
		counter = 0;
		int max = Integer.MIN_VALUE;
		int index = -1;
		for(int i = 0; i < game.getCells().length; i++) {
			if(game.getCells()[i] == null) {
				game.chooseCell(i);
				int value = minimaxWithPruning(game, false, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
				game.removeCell(i);
				if(value > max) {
					max = value;
					index =  i;
				}
			}
		}
		return index;
	}
	
	private static int minimaxWithPruning(TicTacToe game, boolean isMax, int alpha, int beta, int depth) {
		counter++;
		if(!game.isPlaying()) {
			switch(game.getWinner()) {
			case "X":
				return -100 - depth;
			case "0":
				return 100 - depth;
			default:
				return -depth;
			}
		}
		int minMax = (isMax) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		for(int i = 0; i < game.getCells().length; i++) {
			if(game.getCells()[i] == null) {
				game.chooseCell(i);
				int value = minimaxWithPruning(game, !isMax, alpha, beta, depth++);
				game.removeCell(i);
				if(isMax) {
					minMax = Math.max(value, minMax);
					alpha = Math.max(minMax, alpha);
				}else {
					minMax = Math.min(value, minMax);
					beta = Math.min(minMax, beta);
				}
				if(alpha >= beta) {
					break;
				}
			}
		}
		return minMax;
	}

}
