package fr.univnantes.client.state;

class InitialState implements State {
	@Override
	public void joinLobby(Game game, String name) {
		game.setState(new LobbyState());
	}
}