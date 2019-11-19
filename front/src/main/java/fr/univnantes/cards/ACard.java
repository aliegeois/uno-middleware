package fr.univnantes.cards;

import java.io.Serializable;

public abstract class ACard implements Serializable {
	private static final long serialVersionUID = -6320387025355652230L;
	
	public final int id;
	public Color color;

	public ACard(int id, Color color) {
		this.id = id;
		this.color = color;
	}

	public abstract boolean canBePlayed(ACard pileCard);
}