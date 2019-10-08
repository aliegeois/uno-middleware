package fr.univnantes.cards;

public class NumberCard extends ColoredCards{

    public int valeur;

    public NumberCard(int valeur, Color couleur){
        super(couleur);
        this.valeur = valeur;
    }
}