package fr.univnantes.state;

class InitialState extends State {
	@Override
	void joinLobby(Game game, String name) {
		game.setState(new LobbyState());
	}
}