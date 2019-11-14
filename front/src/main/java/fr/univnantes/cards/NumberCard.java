package fr.univnantes.cards;

public class NumberCard extends ACard {
    public final int value;

    public NumberCard(int id, Color color, int value) {
        super(id, color);
        this.value = value;
	}
	
    @Override
    public boolean canBePlayed(ACard pileCard) {
        if(pileCard instanceof NumberCard) {
            return color == pileCard.color || value == ((NumberCard) pileCard).value;
        } else {
            return color == pileCard.color;
        }
	}
	
	@Override
	public String toString() {
		return "(" + id + ", " + color.name() + ", " + value + ")";
	}
}