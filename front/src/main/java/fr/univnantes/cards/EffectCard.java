package fr.univnantes.cards;

import fr.univnantes.cards.Effect;

public class EffectCard extends ACard {
	private static final long serialVersionUID = -1973843047004144289L;
	
	public final Effect effect;
	
	public EffectCard(Color color, Effect effect) {
		super(color);
		this.effect = effect;
	}
	
	@Override
	public boolean canBePlayedOn(ACard otherCard) {
		if(otherCard == null)
			return false;
		
		if(otherCard instanceof EffectCard) {
			return effect == ((EffectCard)otherCard).effect || color == ((EffectCard)otherCard).color;
		} else {
			return color == otherCard.color;
		}
	}
	
	@Override
	public String toString() {
		return "(" + color.name() + ", " + effect.name() + ")";
	}
}