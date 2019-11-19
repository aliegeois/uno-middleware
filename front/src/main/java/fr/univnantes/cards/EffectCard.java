package fr.univnantes.cards;

import fr.univnantes.cards.Effect;

public class EffectCard extends ACard {
	private static final long serialVersionUID = -1973843047004144289L;
	
	public final Effect effect;

    public EffectCard(int id, Color color, Effect effect) {
        super(id, color);
        this.effect = effect;
	}

    @Override
    public boolean canBePlayed(ACard pileCard) {
        if(pileCard instanceof EffectCard) {
            return effect == ((EffectCard)pileCard).effect || color == ((EffectCard)pileCard).color;
        } else {
            return color == pileCard.color;
        }
	}
	
	@Override
	public String toString() {
		return "(" + id + ", " + color.name() + ", " + effect.name() + ")";
	}
}