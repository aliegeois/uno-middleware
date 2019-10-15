package fr.univnantes.cards;

public abstract class ColoredCard implements ICard {
    public final Color color;

    public ColoredCard(Color color) {
        this.color = color;
    }
}