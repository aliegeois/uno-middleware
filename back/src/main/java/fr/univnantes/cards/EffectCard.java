package fr.univnantes.cards;

public class EffectCard extends ACard {
	private static final long serialVersionUID = -1973843047004144289L;

    public final Effect effect;

    public EffectCard(Effect effect, Color color) {
        super(color);
        this.effect = effect;
	}
	
	/*@Override
	public String toString() throws JSONException {
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
	}*/
}