package fr.univnantes.cards;

import org.json.JSONException;

public abstract class ACard {
	private static int lastId = 0;
	private final int id;

	public ACard() {
		this.id = lastId++;
	}

	public int getId() {
		return id;
	}

	public String toJson() throws JSONException {
		throw new UnsupportedOperationException("Method must be implemented");
	}
}