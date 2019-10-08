package fr.univnantes.cards;

enum Effect {Skip,Plus2,Reverse};

public class EffectCard extends ColoredCard {
    private Effetc effect;

    public EffectCard(Effect effect, Color color) {
        super(color);
        this.effect = effect;
    }
}