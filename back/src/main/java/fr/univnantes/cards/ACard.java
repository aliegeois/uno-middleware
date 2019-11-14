package fr.univnantes.cards;

import org.json.JSONException;

public abstract class ACard {
	private static int lastId = 0;
	public final int id;
    public final Color color;

	public ACard(Color color) {
		this.id = lastId++;
        this.color = color;
	}

	public String toJson() throws JSONException {
		throw new UnsupportedOperationException("Method must be implemented");
	}
}