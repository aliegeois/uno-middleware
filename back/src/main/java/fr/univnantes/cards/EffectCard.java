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
		
		boolean playable = super.canBePlayedOn(otherCard);
		
		if(otherCard instanceof EffectCard)
			playable |= effect == ((EffectCard)otherCard).effect;

		return playable;
	}
	
	@Override
	public String toString() {
		return "(" + color.name() + ", " + effect.name() + ")";
	}
}