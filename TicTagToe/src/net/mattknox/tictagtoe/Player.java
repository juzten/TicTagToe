package net.mattknox.tictagtoe;

public class Player {
	private String name;

	public Player() {
		name = "Guest";
	}

	public Player(String nameArg) {
		name = nameArg;
	}

	public String getName() {
		return name;
	}

}
