package fr.univnantes;

import java.io.Console;
import java.rmi.RemoteException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;
import fr.univnantes.cards.Effect;
import fr.univnantes.cards.EffectCard;
import fr.univnantes.cards.NumberCard;

public class TextualUserInterface implements IUserInterface {
	private static Predicate<? super ACard> isSkip() { return card -> card instanceof EffectCard && ((EffectCard)card).effect == Effect.Skip; }
	private static Predicate<? super ACard> isPlusTwo() { return card -> card instanceof EffectCard && ((EffectCard)card).effect == Effect.PlusTwo; }

	private static final Console console = System.console();
	private ILocalClient client;
	private String name;
	// private final List<ACard> cards = new ArrayList<>();

	TextualUserInterface() {
		System.out.print("Entrez votre nom : ");
		name = console.readLine();
		try {
			client = new Client(name, this);
		} catch(RemoteException e) {
			e.printStackTrace();
		}

		System.out.println("Vous etes dans le lobby");
		boolean ready = false;
		do {
			System.out.println("Entrez \"ready\" quand vous etes prets");
			String input = console.readLine();
			if("ready".equalsIgnoreCase(input)) {
				ready = true;
			} else {
				System.out.println("Entree incorrecte");
			}
		} while(!ready);

		client.setReady(ready);
	}

	@Override
	public void startGame(List<String> players, List<ACard> initialCards, ACard pileCard) {
		System.out.println("Debut de la partie, vous etes " + players.size() + " joueurs : " + players.stream().reduce((acc, pl) -> acc + ", " + pl).get());
	}

	@Override
	public void yourTurn() {
		System.out.println("A votre tour de jouer, liste de vos cartes :");
		System.out.println(cardsToText(client.getCards(), false));
		System.out.println("Carte du dessus du paquet : " + cardToText(client.getTopCard()));
		
		List<ACard> pCards = playableCards(client.getCards(), client.getTopCard());
		if(pCards.size() == 0) {
			System.out.println("Vous n'avez aucune carte jouable");
			return;
		}
		// System.out.println("Liste de vos cartes jouables :");
		// System.out.println(cardsToText(pCards, true));
		
		ACard cardToPlay = chooseACard(pCards);

		if(cardToPlay instanceof EffectCard) {
			switch(((EffectCard)cardToPlay).effect) {
				case PlusFour:
					System.out.println("Vous jouez un +4, le prochain joueur pioche 4 cartes et cette carte devient " + cardToPlay.color.name().toLowerCase());
					break;
				case PlusTwo:
					System.out.println("Vous jouez un +2, le prochain joueur pioche 2 cartes");
					break;
				case Reverse:
					System.out.println("Vous jouez un changement de sens, le prochain joueur devient le precedent");
					break;
				case Skip:
					System.out.println("Vous jouez une interdiction, le prochain joueur ne joue pas");
					break;
				case Wild:
					System.out.println("Vous jouez un choix de couleur, cette carte devient " + cardToPlay.color.name().toLowerCase());
					break;
			}
		}

		client.playCard(cardToPlay);

		/*if(cardToPlay instanceof EffectCard && ((EffectCard)cardToPlay).effect == Effect.PlusFour) {
			client.playPlusFourCard(cardToPlay);
		} else {
			client.playStandardCard(cardToPlay);
		}*/
	}

	@Override
	public void draw(List<ACard> cards) {
		System.out.println("Vous piochez " + cards.size() + " cartes : " + cardsToText(cards, false));
	}

	@Override
	public void aboutToDrawFourCards() {
		int answer = 0;
		do {
			System.out.println("Vous allez piochez 4 cartes, voulez-vous le contestez ? (oui/non)");
			String input = console.readLine();
			if("oui".equalsIgnoreCase(input))
				answer = 1;
			else if("non".equalsIgnoreCase(input))
				answer = -1;
			else {
				System.out.println("Reponse incorrecte");
				answer = 0;
			}
		} while(answer == 0);

		if(answer == 1) {
			System.out.println("Vous contestez");
			client.contest();
		} else {
			System.out.println("Vous ne contestez pas");
			client.doNotContest();
		}
	}

	@Override
	public void winContest() {
		System.out.println("Vous avez gagnez le conteste, le joueur precedent pioche 6 cartes");
	}

	@Override
	public void loseContest(List<ACard> cards) {
		System.out.print("Vous perdez le conteste, vous piochez 4 cartes : " + cardsToText(cards, false));
	}

	@Override
	public void getContested() {
		System.out.println("Un joueur conteste votre +4");
	}

	@Override
	public void getSkipped() {
		List<ACard> playableCards = client.getCards().stream().filter(isSkip()).collect(Collectors.toList());

		if(playableCards.size() == 0) {
			System.out.println("Votre tour est passe");
		} else {
			System.out.println("Vous pouvez jouer un skip pour contrer le precedent");// , choisissez le skip à utiliser : " + cardsToText(playableCards, false));
			ACard cardToPlay = chooseACard(playableCards);
			client.counterSkip(cardToPlay);
		}
	}

	@Override
	public void getPlusTwoed(int nbCardsStacked) {
		List<ACard> playableCards = client.getCards().stream().filter(isPlusTwo()).collect(Collectors.toList());

		if(playableCards.size() != 0) {
			System.out.println("Vous pouvez jouer un +2 pour ne rien piocher et faire piocher 2 cartes de plus au joueur suivant");
			ACard cardToPlay = chooseACard(playableCards);
			client.counterPlusTwo(cardToPlay);
		}
	}

	@Override
	public void cardPlayedBySomeoneElse(String otherPlayer, ACard card) {
		System.out.println(otherPlayer + " joue : " + cardToText(card));
	}

	private ACard chooseACard(List<ACard> cards) {
		System.out.println("Cartes selectionnables : " + cardsToText(cards, true));

		ACard choosenCard;
		do {
			System.out.print("Entrez le numero de la carte que vous voulez choisir : ");
			String input = console.readLine();

			int cardNumber;
			try {
				cardNumber = Integer.parseInt(input);
				if(cardNumber < 1 || cardNumber > cards.size())
					throw new NumberFormatException();
				
				choosenCard = cards.get(cardNumber - 1);
			} catch(NumberFormatException e) {
				System.out.println("Nombre incorrect");
				choosenCard = null;
			}
		} while(choosenCard == null);

		if(choosenCard instanceof EffectCard && (((EffectCard)choosenCard).effect == Effect.Wild || ((EffectCard)choosenCard).effect == Effect.PlusFour)) {
			Color color;
			do {
				System.out.print("Entrez la couleur que vous voulez appliquer à cette carte (rouge, bleu, vert ou jaune) : ");
				String input = console.readLine();

				if("rouge".equalsIgnoreCase(input))
					color = Color.Red;
				else if("bleu".equalsIgnoreCase(input))
					color = Color.Blue;
				else if("vert".equalsIgnoreCase(input))
					color = Color.Green;
				else if("jaune".equalsIgnoreCase(input))
					color = Color.Yellow;
				else
					color = null;
				
				if(color == null)
					System.out.println("Couleur incorrecte");
			} while(color == null);

			choosenCard.color = color;
		}

		System.out.println("Vous avez choisi " + cardToText(choosenCard));

		return choosenCard;
	}

	/**
	 * 
	 * @param cards Liste des cartes à filtrer
	 * @param topCard Carte au dessus du paquet
	 * @return Liste des cartes qui peuvent être posées
	 */
	private static List<ACard> playableCards(List<ACard> cards, ACard topCard) {
		return cards.stream().filter(card -> card.canBePlayedOn(topCard)).collect(Collectors.toList());
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

	private static String cardsToText(List<ACard> cards, boolean showNumbers) {
		String value = "{ ";
		for(int i = 0; i < cards.size(); i++) {
			if(showNumbers)
				value += "(" + (i+1) + ")";
			value += cardToText(cards.get(i));
			if(i != cards.size() - 1)
				value += " - ";
		}
		
		return value + " }";
	}

	public static void main(String[] args) {
		new TextualUserInterface();
	}
}