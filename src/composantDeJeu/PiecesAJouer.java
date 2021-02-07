package composantDeJeu;

/** Type de données représentant les pièces à jouer pour une partie */
public class PiecesAJouer {
    /** Représente le nombre total de carreaux dans une partie */
    private static final int NB_CARREAUX = 18;
    /** Tableau statique de carreaux */
    private static Carreau[] pieces;
    /** nombre de carreleurs */
    private final static int NB_JOUEURS = 2;

    /**
     * Constructeur Initialise le tableau statique à la taille NB_CARREAUX
     */
    public PiecesAJouer() {
	pieces = new Carreau[getNbCarreaux()];
	int cpt = 0;
	for (Couleur c : Couleur.values()) {
	    pieces[0 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(3, 3, 'i', c);
	    pieces[1 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(3, 2, 'h', c);
	    pieces[2 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(2, 3, 'g', c);
	    pieces[3 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(3, 1, 'f', c);
	    pieces[4 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(1, 3, 'e', c);
	    pieces[5 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(2, 2, 'd', c);
	    pieces[6 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(2, 1, 'c', c);
	    pieces[7 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(1, 2, 'b', c);
	    pieces[8 + cpt * PiecesAJouer.NB_CARREAUX
		    / NB_JOUEURS] = new Carreau(1, 1, 'a', c);
	    ++cpt;
	}
	this.trier();
    }

    /** Retourne le tableau statique de carreaux */
    public Carreau[] getPieces() {
	Carreau[] copie = pieces.clone();
	return copie;
    }

    /** Retourne le nombre de carreaux jouables dans une partie */
    public static int getNbCarreaux() {
	return NB_CARREAUX;
    }

    /** Modifie l'attribut pieces avec le tableau de Carreau trier */
    public static void setPieces(Carreau[] trier) {
	Carreau[] copie = trier;
	pieces = copie;
    }

    /**
     * Indique s'il y a encore des carreaux non utilisés
     * 
     * @return vrai si tous les carreaux ont été posés
     */
    public boolean plusDeCarreau() {
	for (int i = 0; i < PiecesAJouer.getNbCarreaux(); ++i) {
	    if (this.getPieces()[i].getUtilisé() == false)
		return false;
	}
	return true;
    }

    /**
     * Renvoie tous les carreaux non utilisées et qui respecte la carte pioche
     * 
     * @param pioche la carte piochée
     * @return une chaîne de caractères représentant les carreaux disponibles et
     *         désignées par la carte pioche
     */
    public String toString(Carte pioche) {
	String s = "";
	int maxH = 0; // Hauteur maximale des pièces non utilisées
	for (int i = 0; i < PiecesAJouer.getNbCarreaux(); i++) {
	    if (!this.getPieces()[i].getUtilisé()
		    && pioche.conditionCarte(this.getPieces()[i])) {
		if (maxH < this.getPieces()[i].getHauteur())
		    maxH = this.getPieces()[i].getHauteur();
	    }
	}

	while (maxH != 0) {
	    for (int i = 0; i < PiecesAJouer.getNbCarreaux(); i++) {
		if (!this.getPieces()[i].getUtilisé()) {
		 // condition de la carte
		    if (pioche.conditionCarte(this.getPieces()[i])) { 
			if (this.getPieces()[i].getHauteur() >= maxH) {
			    for (int l = 0; l < this.getPieces()[i]
				    .getLargeur(); ++l) {
				s += this.getPieces()[i].getLettre();
			    }
			} else {
			    for (int l = 0; l < this.getPieces()[i]
				    .getLargeur(); ++l) {
				s += " ";
			    }
			}
			s += " ";
		    }
		}
	    }
	    s += System.lineSeparator();
	    --maxH;
	}
	return s;
    }

    /**
     * Trie les pièces à jouer, par ordre croissante et les minuscules avant les
     * majuscules
     */

    public void trier() {
	Carreau[] piecesCopie = this.getPieces();

	char letter = 'a';
	Carreau stock = null;
	for (int k = 0; k < NB_JOUEURS; ++k) {
	    for (int j = 0; j < NB_CARREAUX / NB_JOUEURS; ++letter, ++j) {
		for (int i = 0; i < NB_CARREAUX; ++i) {
		    if (piecesCopie[i].getLettre() == letter) {
			stock = piecesCopie[j + k * NB_CARREAUX / NB_JOUEURS];
			
			piecesCopie[j + k * NB_CARREAUX/ NB_JOUEURS] 
			= piecesCopie[i];
			
			piecesCopie[i] = stock;
		    }
		}
	    }
	    letter = 'A';
	}

	PiecesAJouer.setPieces(piecesCopie);
    }
}
