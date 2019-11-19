package fr.univnantes.state;

import java.util.List;

import fr.univnantes.IRemoteClient;
import fr.univnantes.cards.ACard;

class WaitingState extends State {
	@Override
	void yourTurn(Game game) {
		game.setState(new PlayState());
	}

	@Override
	void draw(Game game, List<ACard> cards) {
		game.cards.addAll(cards);
		// Rester dans le même état
	}

	@Override
	void aboutToDrawFourCards(Game game) {
		game.setState(new WillGetContestedState());
	}

	@Override
	void getContested(Game game) {
		game.setState(new WillGetContestedState());
	}

	@Override
	void getSkipped(Game game) {
		game.setState(new CounterSkipState());
	}

	@Override
	void getPlusTwoed(Game game, int quantity) {
		game.setState(new CounterPlusTwoState());
	}

	@Override
	void cardPlayedBySomeoneElse(Game game, IRemoteClient client, ACard card) {
		
	}
}