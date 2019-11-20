package fr.univnantes.cards;

import java.io.Serializable;

public abstract class ACard implements Serializable {
	private static final long serialVersionUID = -6320387025355652230L;

	private static int lastId = 0;
	public final int id;
    public final Color color;

	public ACard(Color color) {
		this.id = lastId++;
        this.color = color;
	}

	/*public String toString() throws JSONException {
		throw new UnsupportedOperationException("Method must be implemented");
	}*/
}