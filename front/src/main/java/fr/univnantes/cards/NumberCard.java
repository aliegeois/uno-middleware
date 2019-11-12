package fr.univnantes.cards;

public class NumberCard extends ColorCard {
    public final int value;

    public NumberCard(int id, Color color, int value) {
        super(id, color);
        this.value = value;
    }
}