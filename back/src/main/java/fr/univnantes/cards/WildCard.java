package fr.univnantes.cards;

enum WildEffect {ChangeColor, Plus4};

public class WildCard implements Cards{

    private WildEffect effect;

    public WildCard(Effect effect){
        this.effect = effect;
    }
}