package fr.univnantes.cards;

import org.json.JSONObject;

public class JCard {
	private JCard(){}
	
	/**
	 * Prend en paramètre une chaîne de caractères en json et crée la carte correspondante
	 * @param card La carte en json
	 * @return La carte générée
	 * @throws Exception
	 */
	public static ACard parse(String card) throws Exception {
		JSONObject obj = new JSONObject(card);
		String type = obj.getString("type");
		switch(type) {
			case "effect":
				return new EffectCard(obj.getInt("id"), parseColor(obj.getString("color")), parseEffect(obj.getString("effect")));
			case "number":
				return new NumberCard(obj.getInt("id"), parseColor(obj.getString("color")), obj.getInt("value"));
			default:
				throw new Exception("Cannot resolve type \"" + type + "\"");
		}
	}

	private static Color parseColor(String color) throws Exception {
		switch(color) {
			case "red":
				return Color.Red;
			case "blue":
				return Color.Blue;
			case "green":
				return Color.Green;
			case "yellow":
				return Color.Yellow;
			default:
				throw new Exception("Cannot resolve color \"" + color + "\"");
		}
	}

	private static Effect parseEffect(String effect) throws Exception {
		switch(effect) {
			case "skip":
				return Effect.Skip;
			case "plusTwo":
				return Effect.PlusTwo;
			case "reverse":
				return Effect.Reverse;
			case "wild":
				return Effect.Wild;
			case "plusFour":
				return Effect.PlusFour;
			default:
				throw new Exception("Cannot resolve effect \"" + effect + "\"");
		}
	}
}