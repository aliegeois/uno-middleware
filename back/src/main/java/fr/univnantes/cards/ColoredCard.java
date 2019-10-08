package fr.univnantes.cards;

public abstract class ColoredCard implements Cards{
    private Color couleur;

    public ColoredCard(Color couleur){
        this.couleur = couleur;
    }
}