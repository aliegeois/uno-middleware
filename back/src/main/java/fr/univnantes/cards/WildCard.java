package fr.univnantes.cards;

public class WildCard implements Card {
    private Effect effect;

    public WildCard(Effect effect) {
        this.effect = effect;
    }
}