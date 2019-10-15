package fr.univnantes.cards;

import fr.univnantes.cards.Effect;

public class EffectCard extends ColoredCard {
    public final Effect effect;

    public EffectCard(Effect effect, Color color) {
        super(color);
        this.effect = effect;
    }
}