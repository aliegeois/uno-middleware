package fr.univnantes.cards;

public abstract class ACard {
	public final int id;
	public Color color;

	public ACard(int id, Color color) {
		this.id = id;
		this.color = color;
	}

	public abstract boolean canBePlayed(ACard pileCard);
}