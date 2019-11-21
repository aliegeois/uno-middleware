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
		
		if(otherCard instanceof NumberCard) {
			return color == otherCard.color || value == ((NumberCard) otherCard).value;
		} else {
			return color == otherCard.color;
		}
	}
	
	@Override
	public String toString() {
		return "(" + color.name() + ", " + value + ")";
	}
}