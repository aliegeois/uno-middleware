package fr.univnantes.card;

public abstract class ACard {
	private final String name;
	private final Color color;

	public ACard(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}
}