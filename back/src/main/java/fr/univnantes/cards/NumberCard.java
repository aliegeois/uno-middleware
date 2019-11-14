package fr.univnantes.cards;

import org.json.JSONException;
import org.json.JSONObject;

public class NumberCard extends ACard {
    public final int value;

    public NumberCard(int value, Color color) {
        super(color);
        this.value = value;
	}
	
	@Override
	public String toJson() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("type", "number");
		obj.put("value", value);
		return obj.toString();
	}
}