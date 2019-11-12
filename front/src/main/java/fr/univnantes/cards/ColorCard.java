package fr.univnantes.cards;

public abstract class ColorCard extends ACard {
    public final Color color;

    public ColorCard(int id, Color color) {
		super(id);
        this.color = color;
    }
}