package fr.univnantes.cards;

public abstract class ACard {
	final int id;

	public ACard(int id) {
		this.id = id;
	}

	public abstract boolean canBePlayed(ACard pileCard);
}