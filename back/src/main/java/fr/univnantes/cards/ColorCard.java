package fr.univnantes.cards;

public abstract class ColorCard extends ACard {
    public final Color color;

    public ColorCard(Color color) {
		super();
        this.color = color;
    }
}