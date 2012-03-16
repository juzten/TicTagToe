package net.mattknox.tictagtoe;

public class GameManager {
	Player player1;
	Player player2;
	GameState game;
	int playerTurn;
	
	public GameManager() {
		startGame();
	}
	
	public void startGame() {
		player1 = new Player("X");
		player2 = new Player("O");
		game = new GameState();
		playerTurn = 1;
	}
	
	public String getMove(int locationArg) {
		return game.getMoveString(locationArg);
	}
	
	public void submitMove(int locationArg) {
		game.setMove(locationArg, playerTurn);
		toggleTurn();
	}
	
	public void toggleTurn() {
		if (playerTurn == 1) playerTurn = 2;
		else if (playerTurn == 2) playerTurn = 1;
	}
	
	public int getCurrentPlayer() {
		return playerTurn;
	}
	
	public int getWinner() {
		return game.getWinner();
	}
	
	public String getPlayerName(int whichPlayer) {
		if (whichPlayer == 1) return player1.getName();
		if (whichPlayer == 2) return player2.getName();
		else return "Player ID not recognized.";
	}
}
