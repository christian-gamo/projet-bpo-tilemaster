package composantDeJeu;

import java.util.*;

/** Type de donnée représentant le mur où se déroulera le jeu */
public class Mur {
    /** Nombre de colonnes */
    private static final int COLONNE = 5;
    /** Case par défaut (espace) */
    private static final char DEFAULT_CASE = ' ';
    /** Nombre de positions possibles pour la pièce neutre */
    private static final int nbCasNeutre = 4;
    /** Tableau dynamique contenant tableau de caractères représentant le mur */
    private ArrayList<char[]> terrain;
    /** Carreau neutre */
    private Carreau pieceNeutre;

    /**
     * Constructeur Initialise un mur avec une ligne vide et place le carreau
     * neutre
     * 
     */
    public Mur() {
	terrain = new ArrayList<>();
	char[] ligne = new char[getColonne()];
	Arrays.fill(ligne, getDefaultCase());
	terrain.add(ligne);
	placerCaroNeutre();
    }

    /** Retourne le tableau dynamique terrain */
    public ArrayList<char[]> getTerrain() {
	return new ArrayList<char[]>(terrain);
    }

    /** Retourne le carreau neutre */
    public Carreau getPieceNeutre() {
	assert (pieceNeutre != null);
	Carreau copieNeutre = new Carreau(pieceNeutre);
	return copieNeutre;
    }

    /** Retourne la constante de la case par défaut (espace) */
    public static char getDefaultCase() {
	return DEFAULT_CASE;
    }

    /** Retourne la constante nombre de colonnes du mur */
    public static int getColonne() {
	return COLONNE;
    }

    /** Place aléatoirement le carreau neutre */
    public void placerCaroNeutre() {
	int casMax = nbCasNeutre;
	Random r = new Random();
	int casAléatoire = r.nextInt(casMax);
	Carreau neutreHori = new Carreau(3, 1, 'x');
	Carreau neutreVerti = new Carreau(1, 3, 'x');
	switch (casAléatoire) {
	case 0:
	    placerCarreau(neutreHori, 0, 0);
	    pieceNeutre = neutreHori;
	    break;
	case 1:
	    placerCarreau(neutreVerti, 0, 4);
	    pieceNeutre = neutreVerti;
	    break;
	case 2:
	    placerCarreau(neutreVerti, 0, 0);
	    pieceNeutre = neutreVerti;
	    break;
	case 3:
	    placerCarreau(neutreHori, 0, 2);
	    pieceNeutre = neutreHori;
	    break;
	}
    }

    /**
     * Place un carreau valide sur le mur
     * 
     * @param c       le carreau que l'on veut placer
     * @param lettre  la lettre correspondant au carreau
     * @param ligne   la ligne où l'on veut placer le carreau
     * @param colonne la colonne où l'on veut placer le carreau
     */
    public void placerCarreau(Carreau c, int ligne, int colonne) {
	assert (!c.getUtilisé());
	assert (!c.depasseZone(this, ligne, colonne)); // Condition 3
	if (c.getLettre() != 'x') // Ne le fais pas pour le carreau neutre
	    assert (c.toucheCarreau(this, ligne, colonne)); // Condition 4
	assert (c.baseStable(this, ligne, colonne)); // Condition 5
	assert (c.estPlacable(this, ligne, colonne));
	// Ne supprime pas un carreau déja placé
	if (this.terrain.size() <= ligne + c.getHauteur()) {
	    int j = ligne + 1 + c.getHauteur();
	    for (int i = this.terrain.size(); i < j; ++i) {
		char[] nvLigne = new char[getColonne()];
		Arrays.fill(nvLigne, getDefaultCase());
		this.terrain.add(nvLigne);
	    }
	}

	for (int i = 0; i < c.getHauteur(); ++i) {
	    for (int j = 0; j < c.getLargeur(); ++j) {
		this.terrain.get(ligne + i)[colonne + j] = c.getLettre();
	    }
	}
	c.setUtilisé(true);

    }

    /**
     * Permet d'afficher le mur
     */
    public String toString() {
	String result = "";
	char c;
	final int dizaines = 10;
	for (int i = terrain.size() - 1; i >= 0; --i) {
	    if (i + 1 < dizaines)
	    // en dessous de 10, on rajoute un espace pour l'affichage
		result += getDefaultCase();
	    result = result + (i + 1) + getDefaultCase();
	    for (int j = 0; j < getColonne(); ++j) {
		c = terrain.get(i)[j];
		result = result + c + getDefaultCase();
	    }
	    result += System.lineSeparator();
	}

	result = result + getDefaultCase() + getDefaultCase()
		+ getDefaultCase();
	for (int i = 1; i <= getColonne(); ++i) {
	    result = result + i + getDefaultCase();
	}
	return result;
    }

}