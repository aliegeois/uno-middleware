package fr.univnantes.cards;

import java.io.Serializable;

public abstract class ACard implements Serializable {
	private static final long serialVersionUID = -6320387025355652230L;
	
	public Color color;

	public ACard(Color color) {
		this.color = color;
	}

	public abstract boolean canBePlayedOn(ACard otherCard);
}