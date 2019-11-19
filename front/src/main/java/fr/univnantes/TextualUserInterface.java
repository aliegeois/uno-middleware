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
		System.out.print("Entrez votre nom : ");
		String name = console.readLine();
		ILocalClient _client = null;
		try {
			_client = new Client(name, this);
		} catch (RemoteException e) {
			// System.err.println(e.getMessage());
			e.printStackTrace();
		}
		client = _client;

		if(client != null) {
			client.setReady(true);
		}
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
			value += "(" + (i+1) + ")" + cardToText(cards.get(i));
			if(i != cards.size() - 1)
				value += " - ";
		}
		
		return value + " }";
	}

	// @Override
	// public void clientReady() {
	// 	System.out.println("Entrée dans le lobby");
	// 	boolean ready = false;
	// 	do {
	// 		System.out.println("Entrez \"ready\" quand vous êtes prêt");
	// 		String input = console.readLine();
	// 		if("ready".equals(input)) {
	// 			ready = true;
	// 		} else {
	// 			System.out.println("Incorrect");
	// 		}
	// 	} while(!ready);

	// 	client.setReady(true);
	// }

	@Override
	public void startGame(int nbPlayers, List<ACard> initialCards, ACard pileCard) {
		System.out.print("Debut de la partie, vous etes " + nbPlayers + " joueurs, voici vos cartes: ");
		for(int i = 0; i < initialCards.size(); i++) {
			System.out.print(cardToText(initialCards.get(i)));
			if(i != initialCards.size() - 1)
				System.out.print(" , ");
		}
		System.out.println();
		System.out.println("Carte du dessus du paquet: " + cardToText(pileCard));
	}

	@Override
	public void yourTurn() {
		System.out.println("A votre tour de jouer, liste de vos cartes: ");
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
		System.out.println(cardsToText(cards));
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
		if(cards.stream().filter(ncard -> ncard instanceof EffectCard && ((EffectCard)ncard).effect == Effect.PlusTwo).count() > 0){
			System.out.println("Votre tour va être passé.");
			System.out.println("Voulez-vous passer le tour du joueur suivant ?");
		}else{
			System.out.println("Votre tour est passe.");
		}

	}

	@Override
	public void getPlusTwoed(int nbCards) {
		if(cards.stream().filter(ncard -> ncard instanceof EffectCard && ((EffectCard)ncard).effect == Effect.PlusTwo).count() > 0){
			int cardNumber;
			System.out.println("Vous allez piochez "+ nbCards+ " cartes");
			do {
				System.out.println("Entrez le numero de la carte:");
				String input = console.readLine();
				try {
					cardNumber = Integer.parseInt(input);
					if(cardNumber < 1 || cardNumber > cards.size()|| !((cards.get(cardNumber) instanceof EffectCard) && ((EffectCard)cards.get(cardNumber)).effect == Effect.PlusTwo)) 
						throw new NumberFormatException();
				} catch(NumberFormatException e) {
					System.out.println("Nombre incorrect");
					cardNumber = -1;
				}
			} while(cardNumber!=-1);

			ACard cardToPlay = cards.get(cardNumber -1);
			client.playStandardCard(cardToPlay);
			this.cards.remove(cardNumber - 1);
			System.out.println(cardsToText(cards));

		} else {
			System.out.println("Vous piochez " + nbCards + " cartes.");
			System.out.println(cardsToText(cards));
		}
	}

	@Override
	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) {
		try {
			System.out.println(client.getName() + " joue : "+ cardToText(card));	
		} catch (RemoteException e) {}
	}

	public static void main(String[] args) {
		new TextualUserInterface();
	}
}