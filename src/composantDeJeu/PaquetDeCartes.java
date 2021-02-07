package composantDeJeu;

import java.util.*;

/** Type de donnée représentant le paquet de cartes */
public class PaquetDeCartes {
    /** Le nombre de cartes maximal */
    private static final int MAX_CARTES = 33;
    /** Le nombre de cartes indiquant une taille */
    private static final int NB_CARTES_TAILLE = 5;
    /** Le nombre de cartes indiquant une couleur */
    private static final int NB_CARTES_COULEUR = 9;
    /** Tableau dynamique contenant le paquet de cartes */
    private ArrayList<Carte> paquet;

    /**
     * Constructeur Crée un paquet de cartes contenant des cartes de couleur et
     * des cartes de taille 
     * Si aucune paramètre n'est rentré un paquet vide sera créé
     * 
     * @param nbCouleur Le nombre de cartes indiquant une couleur (optionnel)
     * @param nbTaille  Le nombre de cartes indiquant une taille (optionnel)
     */
    public PaquetDeCartes(int nbCouleur, int nbTaille) {
	this.paquet = new ArrayList<>();
	// Ajout des 18 cartes Couleur
	for (ContenuCarte c : ContenuCarte.values()) {
	    if (c == ContenuCarte.ROUGE || c == ContenuCarte.BLEU) {
		for (int i = 0; i < nbCouleur; ++i) {
		    paquet.add(new Carte(c));
		}
	    }
	}
	// Ajout des 15 cartes Taille
	for (int i = 0; i < nbTaille; ++i) {
	    this.paquet.add(new Carte(ContenuCarte.TAILLE1));
	    this.paquet.add(new Carte(ContenuCarte.TAILLE2));
	    this.paquet.add(new Carte(ContenuCarte.TAILLE3));
	}
    }

    public PaquetDeCartes() { // Pour le paquet de tas écarté
	this.paquet = new ArrayList<>();
    }

    /** Retourne une copie du paquet de cartes */
    public ArrayList<Carte> getPaquet() {
	return new ArrayList<Carte>(paquet);
    }

    /** Retourne le nombre maximum de cartes par paquet */
    public static int getMaxCartes() {
	return MAX_CARTES;
    }

    /** Retourne le nombre max de cartes Taille dans un paquet */
    public static int getNbCartesTaille() {
	return NB_CARTES_TAILLE;
    }

    /** Retourne le nombre max de cartes Couleur dans un paquet */
    public static int getNbCartesCouleur() {
	return NB_CARTES_COULEUR;
    }

    /** Mélange le paquet de cartes */
    public void mélanger() {
	Collections.shuffle(this.paquet);
    }

    /**
     * Pioche une carte
     * 
     * @return sommet la carte au sommet du paquet
     */
    public Carte piocher() {
	// carte au sommet
	Carte sommet = this.paquet.get(this.paquet.size() - 1);
	this.retirer();
	return sommet;
    }

    /** Retire une carte */
    public void retirer() {
	this.paquet.remove(this.paquet.size() - 1);
    }

    /** Ajoute une carte dans le paquet */
    public void ajouter(Carte c) {
	this.paquet.add(c);
    }

    /**
     * Indique si le paquet n'a plus de cartes
     * 
     * @return vrai si le paquet n'a plus de cartes
     */
    public boolean plusDeCartes() {
	return this.paquet.size() == 0;
    }

}
