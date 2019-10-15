package fr.univnantes.cards;

public class NumberCard extends ColoredCard {
    public final int value;

    public NumberCard(int value, Color color) {
        super(color);
        this.value = value;
    }
}