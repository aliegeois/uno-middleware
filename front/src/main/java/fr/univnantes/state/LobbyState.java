package fr.univnantes.state;

class LobbyState extends State {
	@Override
	void leaveLobby(StateContext context) {
		context.setState(new InitialState());
	}

	@Override
	void startGame(StateContext context) {
		context.setState(new WaitingState());
	}
}