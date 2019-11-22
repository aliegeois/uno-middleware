package fr.univnantes.card;

import java.io.Serializable;
import java.util.List;

public abstract class ACard implements Serializable {
	private static final long serialVersionUID = -6320387025355652230L;
	
	private static int nextId = 0;

	public final int id = nextId++;
	public Color color;

	public ACard(Color color) {
		this.color = color;
	}

	public boolean canBePlayedOn(ACard otherCard) {
		return color == Color.Wild || otherCard.color == Color.Wild || color == otherCard.color;
	}

	public static String asText(List<ACard> cards, boolean showNumbers) {
		String text = "{ ";
		
		for(int i = 0; i < cards.size(); i++) {
			if(showNumbers)
				text += "(" + (i+1) + ")";
			text += cards.get(i);
			if(i != cards.size() - 1)
				text += " - ";
		}
		
		return text + " }";
	}
}