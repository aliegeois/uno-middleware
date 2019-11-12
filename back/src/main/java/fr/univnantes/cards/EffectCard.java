package fr.univnantes.cards;

import org.json.JSONException;
import org.json.JSONObject;

import fr.univnantes.cards.Effect;

public class EffectCard extends ColorCard {
    public final Effect effect;

    public EffectCard(Effect effect, Color color) {
        super(color);
        this.effect = effect;
	}
	
	@Override
	public String toJson() throws JSONException {
		JSONObject obj = new JSONObject();
		obj.put("type", "effect");
		switch(effect) {
			case Skip:
				obj.put("effect", "skip");
			case PlusTwo:
				obj.put("effect", "plusTwo");
			case Reverse:
				obj.put("effect", "reverse");
			case Wild:
				obj.put("effect", "wild");
			case PlusFour:
				obj.put("effect", "plusFour");
		}
		return obj.toString();
	}
}