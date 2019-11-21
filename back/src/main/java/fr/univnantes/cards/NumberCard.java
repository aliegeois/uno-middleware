package fr.univnantes.cards;

public class NumberCard extends ACard {
	private static final long serialVersionUID = 1920263878730653339L;
	
	public final int value;
	
	public NumberCard(Color color, int value) {
		super(color);
		this.value = value;
	}
	
	@Override
	public boolean canBePlayedOn(ACard otherCard) {
		if(otherCard == null)
			return false;
		
		boolean playable = super.canBePlayedOn(otherCard);
		
		if(otherCard instanceof NumberCard)
			playable |= value == ((NumberCard) otherCard).value;
		
		return playable;
	}
	
	@Override
	public String toString() {
		return "(" + color.name() + ", " + value + ")";
	}
}