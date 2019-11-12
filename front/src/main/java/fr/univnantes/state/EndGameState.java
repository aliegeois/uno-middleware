package fr.univnantes.state;

class EndGameState extends State {
	@Override
	void replay(Game game) {
		game.setState(new LobbyState());
	}

	@Override
	void quit(Game game) {
		System.exit(0);
	}
}