package fr.univnantes.cards;

import fr.univnantes.cards.Effect;

public class EffectCard extends ColorCard {
    public final Effect effect;

    public EffectCard(int id, Color color, Effect effect) {
        super(id, color);
        this.effect = effect;
	}
	
	@Override
	public String toString() {
		return "(" + id + ", " + color.name() + ", " + effect.name() + ")";
	}
}