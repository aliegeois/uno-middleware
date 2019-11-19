package fr.univnantes.cards;

import org.json.JSONException;
import org.json.JSONObject;

public class NumberCard extends ACard {
	private static final long serialVersionUID = 1920263878730653339L;

	public final int value;

    public NumberCard(int value, Color color) {
        super(color);
        this.value = value;
	}
	
	@Override
	public String toString() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("type", "number");
		obj.put("value", value);
		return obj.toString();
	}
}