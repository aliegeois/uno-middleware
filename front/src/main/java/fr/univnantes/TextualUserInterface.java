package fr.univnantes;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;
import fr.univnantes.cards.Effect;
import fr.univnantes.cards.EffectCard;
import fr.univnantes.cards.NumberCard;

public class TextualUserInterface implements IUserInterface {
	private final Console console = System.console();
	private final ILocalClient client;
	private final List<ACard> cards = new ArrayList<>();

	TextualUserInterface() {
		System.out.println("Entrez votre nom:");
		String name = console.readLine();
		ILocalClient _client = null;
		try {
			_client = new Client(name, this);
		} catch (RemoteException e) {}
		client = _client;

		if (_client == null)
			return;
	}

	private static String cardToText(ACard card) {
		String value = "";
		switch(card.color) {
			case Red:
				value = ANSIColor.RED.toString();
				break;
			case Blue:
				value = ANSIColor.BLUE.toString();
				break;
			case Green:
				value = ANSIColor.GREEN.toString();
				break;
			case Yellow:
				value = ANSIColor.YELLOW.toString();
				break;
			case Wild:
				value = ANSIColor.WHITE.toString();
		}

		value += "[ ";

		if(card instanceof EffectCard) {
			switch(((EffectCard)card).effect) {
				case Skip:
				case Reverse:
				case Wild:
					value += ((EffectCard)card).effect.name();
					break;
				case PlusTwo:
					value += "+2";
					break;
				case PlusFour:
					value += "+4";
					break;
			}
		} else if(card instanceof NumberCard) {
			value += ((NumberCard)card).value;
		}

		return value + " ]" + ANSIColor.RESET;
	}

	private static String cardsToText(List<ACard> cards) {
		String value = "{ ";
		for(int i = 0; i < cards.size(); i++) {
			value += "(" + (i+1) + ") " + cardToText(cards.get(i));
			if(i != cards.size() - 1)
				value += " - ";
		}
		
		return value + " }";
	}

	@Override
	public void startGame(List<ACard> initialCards) {
		System.out.print("Début de la partie, voici vos cartes: ");
		for(int i = 0; i < initialCards.size(); i++) {
			System.out.print(cardToText(initialCards.get(i)));
			if(i != initialCards.size() - 1)
				System.out.print(" , ");
		}
		System.out.println();
	}

	@Override
	public void yourTurn() {
		// TODO Auto-generated method stub
		System.out.println("À votre tour de jouer, liste de vos cartes: ");
		System.out.println(cardsToText(cards));
		
		int cardNumber;
		do {
			System.out.print("Entrez le numéro de la carte que vous voulez jouer: ");
			String input = console.readLine();
			try {
				cardNumber = Integer.parseInt(input);
				if(cardNumber < 1 || cardNumber > cards.size())
					throw new NumberFormatException();
			} catch(NumberFormatException e) {
				System.out.println("Nombre incorrect");
				cardNumber = -1;
			}
		} while(cardNumber == -1);

		ACard cardToPlay = cards.get(cardNumber - 1);

		if(cardToPlay instanceof EffectCard) {
			if(((EffectCard)cardToPlay).effect == Effect.Wild || ((EffectCard)cardToPlay).effect == Effect.PlusFour) {
				Color color = null;
				do {
					System.out.print("Entrez la couleur que vous voulez appliquer à cette carte (rouge, bleu, vert ou jaune): ");
					String input = console.readLine();
					if("rouge".equalsIgnoreCase(input))
						color = Color.Red;
					if("bleu".equalsIgnoreCase(input))
						color = Color.Blue;
					if("vert".equalsIgnoreCase(input))
						color = Color.Green;
					if("jaune".equalsIgnoreCase(input))
						color = Color.Yellow;
					
					if(color == null) {
						System.out.println("Couleur incorrecte");
					}
				} while(color == null);
	
				cardToPlay.color = color;
	
				if(((EffectCard)cardToPlay).effect == Effect.Wild)
					client.playWildCard(cardToPlay, color);
				if(((EffectCard)cardToPlay).effect == Effect.PlusFour)
					client.playPlusFourCard(cardToPlay, color);
			}
		} else {
			client.playStandardCard(cardToPlay);
		}
		this.cards.remove(cardNumber - 1);
	}

	@Override
	public void draw(List<ACard> cards) {
		System.out.println("Vous piochez"+ cards.size() + " cartes.");
		this.cards.addAll(cards);
		System.out.println(cardsToText(cards));

	}

	@Override
	public void aboutToDrawFourCards() {
		System.out.println("Vous allez piochez 4 cartes, voulez-vous le contestez ?");

	}

	@Override
	public void winContest() {
		System.out.println("Vous avez gagnez le conteste.");

	}

	@Override
	public void loseContest(List<ACard> cards) {
		System.out.println("Dans le cul, Lulu !");
		this.cards.addAll(cards);
		System.out.println(cardsToText(cards));
	}

	@Override
	public void getContested() {
		System.out.println("Un joueur conteste votre +4.");

	}

	@Override
	public void getSkipped() {
		//TODO: faire le test sur la possession de carte skip
		System.out.println("Vous allez être skipped ?");
		System.out.println("Voulez-vous skip le joueur suivant ?");

	}

	@Override
	public void getPlusTwoed(int nbCards) {
		//TODO: faire le test sur la possession de carte +2
		System.out.println("Vous allez piochez "+ nbCards+ " cartes");
		System.out.println("Voulez-vous stack +2 ?");
	}

	@Override
	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) {
		System.out.println("..."+"joue: "+ cardToText(card));
	}

	public static void main(String[] args) {
		TextualUserInterface TUI = new TextualUserInterface();
		TUI.cards.add(new NumberCard(0, Color.Blue, 7));
		TUI.cards.add(new EffectCard(2, Color.Red, Effect.PlusTwo));
		TUI.cards.add(new EffectCard(1, Color.Green, Effect.Skip));
		TUI.cards.add(new EffectCard(1, Color.Wild, Effect.Wild));
		TUI.cards.add(new NumberCard(3, Color.Yellow, 7));
		TUI.yourTurn();
	}
}