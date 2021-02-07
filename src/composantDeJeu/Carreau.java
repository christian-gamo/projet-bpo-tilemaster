package composantDeJeu;

/** Type de donnée représentant un carreau de jeu */
public class Carreau {
    /** largeur du carreau (en unités) */
    private int largeur;
    /** hauteur du carreau (en unités) */
    private int hauteur;
    /** lettre attribuée au carreau et qui le représente */
    private char lettre;
    /** couleur du carreau */
    private Couleur COULEUR;
    /** indique si le carreau a été posé sur le mur ou non */
    private boolean utilisé = false;

    /**
     * Constructeur
     * 
     * @param l      la largeur du carreau (obligatoire)
     * @param h      la hauteur du carreau (obligatoire)
     * @param lettre la lettre attribuée et représentant le carreau
     *               (obligatoire)
     * @param c      couleur de la lettre (optionnel, on ne l'indique pas pour
     *               la pièce neutre)
     * @param piece  le carreau à copier (obligatoire)
     */
    public Carreau(int l, int h, char lettre, Couleur c) {
	this.largeur = l;
	this.hauteur = h;
	this.COULEUR = c;
	this.lettre = lettre;
	if (c == Couleur.ROUGE)
	    this.lettre = Character.toUpperCase(lettre);
    }

    public Carreau(int l, int h, char lettre) {
	this.largeur = l;
	this.hauteur = h;
	this.lettre = lettre;
	this.COULEUR = null;
    }

    public Carreau(Carreau piece) {
	assert (piece != null);
	this.largeur = piece.largeur;
	this.hauteur = piece.hauteur;
	this.lettre = piece.lettre;
	this.COULEUR = piece.COULEUR;
    }

    /** Modifie la lettre transmis au constructeur */
    public void setLettre(char c) { // pour l'algorithme de tri, même si c'est
				    // pas conseillé
	this.lettre = c;
    }

    /** Modifie le booléen d'utilisation du carreau */
    public void setUtilisé(boolean b) {
	this.utilisé = b;
    }

    /** Retourne le booléen qui indique si le carreau a été utilisé ou non */
    public boolean getUtilisé() {
	return this.utilisé;
    }

    /** Retourne la couleur du carreau */
    public Couleur getCouleur() {
	Couleur c = this.COULEUR;
	return c;
    }

    /** Retourne la hauteur du carreau */
    public int getHauteur() {
	return this.hauteur;
    }

    /** Retourne la largeur du carreau */
    public int getLargeur() {
	return this.largeur;
    }

    /** Retourne la lettre du carreau */
    public char getLettre() {
	return this.lettre;
    }

    /**
     * Indique si le carreau est de couleur rouge
     * 
     * @return vrai si le carreau est rouge
     */
    public boolean estRouge() {
	return this.getCouleur() == Couleur.ROUGE;
    }

    /**
     * Indique si le carreau est de couleur bleu
     * 
     * @return vrai si le carreau est bleu
     */
    public boolean estBleu() {
	return this.getCouleur() == Couleur.BLEU;
    }

    /**
     * Indique si le carreau a une largeur ou hauteur de 1
     * 
     * @return vrai si le carreau est de taille 1
     */
    public boolean estTaille1() {
	return this.getLargeur() == 1 || this.getHauteur() == 1;
    }

    /**
     * Indique si le carreau a une largeur ou hauteur de 2
     * 
     * @return vrai si le carreau est de taille 2
     */
    public boolean estTaille2() {
	return this.getLargeur() == 2 || this.getHauteur() == 2;
    }

    /**
     * Indique si le carreau a une largeur ou hauteur de 3
     * 
     * @return vrai si le carreau est de taille 3
     */
    public boolean estTaille3() {
	return this.getLargeur() == 3 || this.getHauteur() == 3;
    }

    /**
     * Indique si le carreau que l'on veut placer ne supprime pas un autre déja
     * placé sur le mur
     * 
     * @param m       le mur, zone de jeu
     * @param ligne   la ligne saisie par l'utilisateur
     * @param colonne la colonne saisie par l'utilisateur
     * @return vrai si le carreau ne supprime pas un autre carreau
     */
    public boolean estPlacable(Mur m, int ligne, int colonne) {
	int cpt = 0;

	// Si le carreau dépasse du mur il est forcément implacable
	if (depasseZone(m, ligne, colonne)) {
	    return false;
	}
	// Vérifie si la ligne où on veut placer
	// le carreau + sa hauteur < nombre de lignes existants du tab
	if (m.getTerrain().size() > ligne + this.getHauteur()) {

	    // Vérifie que pour chaque ligne, chaque case que
	    // le carreau remplacera est vide
	    for (int i = 0; i < this.getHauteur(); ++i) {
		for (int j = 0; j < this.getLargeur(); ++j) {
		    if (m.getTerrain().get(ligne + i)[colonne + j] 
	            == Mur.getDefaultCase())
			++cpt;
		}
	    }

	    if (cpt == this.getLargeur() * this.getHauteur())
		return true;
	    else {
		return false;
	    }
	}

	else // Sinon il ne vérifie que la première ligne
	{
	    for (int j = 0; j < this.getLargeur(); ++j) {
		if (m.getTerrain().get(ligne)[colonne + j] 
		== Mur.getDefaultCase())
		    ++cpt;
	    }

	    if (cpt == this.getLargeur())
		return true;
	    else
		return false;
	}
    }

    /**
     * Indique si le carreau dépasse du mur (possède une largeur de 5 unités)
     * 
     * @param colonne la colonne saisie par l'utilisateur
     * @return vrai si le carreau dépasse de la zone à carreler
     */
    public boolean depasseZone(Mur m, int ligne, int colonne) { // Figure 8
	return this.getLargeur() + colonne > Mur.getColonne()
		&& ligne < m.getTerrain().size();
    }

    /**
     * Indique si le carreau touche au moins un carreau déjà posé
     * 
     * @param m       le mur à carreler
     * @param ligne   la ligne saisie par l'utilisateur
     * @param colonne la colonne saisie par l'utilisateur
     * @return vrai si le carreau touche un carreau
     */
    public boolean toucheCarreau(Mur m, int ligne, int colonne) { // Figure 9
	boolean b = false;
	if (ligne != 0) { // on regarde en dessous de la pièce
	    for (int i = 0; i < this.getLargeur(); ++i) {
		if (m.getTerrain().get(ligne - 1)[colonne + i] 
	        != Mur.getDefaultCase())
		    b = true;
	    }
	}
	if (colonne != 0) { // on regarde à gauche de la pièce
	    if (m.getTerrain().get(ligne)[colonne - 1] != Mur.getDefaultCase())
		b = true;
	}

	// on regarde à droite de la pièce
	if (colonne + getLargeur() - 1 != Mur.getColonne() - 1) {
	    if (m.getTerrain().get(ligne)[colonne + getLargeur()] 
	    != Mur.getDefaultCase())
		b = true;
	}
	return b;
    }

    /**
     * Indique si la base du carreau repose sur le bas de la zone à carreler ou
     * sur des carreaux déjà posés
     * 
     * @param m       le mur à carreler
     * @param ligne   la ligne saisie par l'utilisateur
     * @param colonne la colonne saisie par l'utilisateur
     * @return vrai si le carreau est stable
     */
    public boolean baseStable(Mur m, int ligne, int colonne) { // Figure 10
	boolean b = false;
	if (ligne == 0)
	    b = true;
	else {
	    for (int i = 0; i < this.getLargeur(); ++i) {
		if (m.getTerrain().get(ligne - 1)[colonne + i] != Mur
			.getDefaultCase())
		    b = true;
		else
		    return false;
	    }
	}
	return b;
    }

    /**
     * Indique si un carreau touche un autre carreau avec la même taille
     * d'attribut (largeur ou hauteur) et renvoie si celui-ci le clone
     * 
     * @param m       le mur à carreler
     * @param ligne   la ligne saisie par l'utilisateur
     * @param colonne la colonne saisie par l'utilisateur
     * @param p       un tableau de Carreaux (pièces du jeu)
     * @return vrai si le carreau touche un carreau adjacent et le clone
     */
    public boolean aCarreauAdjacent(Mur m, int ligne, int colonne,
	    PiecesAJouer p) { // Figure 11
	boolean b = false;
	char stock = Mur.getDefaultCase();
	if (ligne != 0) { // on regarde en dessous de la pièce

	    int hauteurMax = 0; 
	    // stocke la + petite valeur entre 
	    //taille du terrain ou hauteur du carreau
	    
	    if (ligne + getHauteur() > m.getTerrain().size())
		hauteurMax = m.getTerrain().size() - ligne;
	    else
		hauteurMax = getHauteur();

	    for (int i = 0; i < this.getLargeur(); ++i) {
		// on stocke la lettre du carreau qui se trouve en bas
		stock = m.getTerrain().get(ligne - 1)[colonne + i]; 
		if (stock == 'x') {
		    if (m.getPieceNeutre().getLargeur() == this.getLargeur())
			b = this.cloneCarreau(m, ligne, colonne, stock,
				hauteurMax);
		    if (b)
			return b;
		} else {
		    for (int j = 0; j < PiecesAJouer.getNbCarreaux(); ++j) {
			if (stock == p.getPieces()[j].getLettre()) { 
			 // on cherche le carreau qui est lié à la lettre 
			 // (pièce neutre pas dans le tab)
			    
			    if(p.getPieces()[j].getLargeur()==this.getLargeur())
			    // on regarde la largeur du carreau
				b = this.cloneCarreau(m, ligne, colonne, stock,
					hauteurMax);
			}
			if (b)
			    return b;
		    }
		}
	    }
	}

	if (colonne != 0) { // on regarde à gauche de la pièce
	    if (m.getTerrain().get(ligne)[colonne - 1] 
		    != Mur.getDefaultCase()) {
		
		// stocke la + petite valeur entre 
		// taille du terrain ou hauteur du carreau
		int hauteurMax = 0;
		if (ligne + getHauteur() > m.getTerrain().size())
		    hauteurMax = m.getTerrain().size() - ligne;
		else
		    hauteurMax = getHauteur();

		for (int i = 0; i < hauteurMax; ++i) {
		    
		    stock = m.getTerrain().get(ligne + i)[colonne - 1];
		    // on stocke la lettre du carreau qui se trouve à gauche
		    
		    if (stock == 'x') {
			if (m.getPieceNeutre().getHauteur() 
			== this.getHauteur())
			    b = this.cloneCarreau(m, ligne, colonne, stock,
				    hauteurMax);
			if (b)
			    return b;
		    } else {
			for (int j = 0; j < PiecesAJouer.getNbCarreaux(); ++j) {
			    if (stock == p.getPieces()[j].getLettre())
			    // on cherche le carreau qui est lié à la lettre
				
				if (p.getPieces()[j].getHauteur() 
				== this.getHauteur())
				// on regarde la hauteur du carreau
				    
				    b = this.cloneCarreau(m, ligne, colonne,
					    stock, hauteurMax);
			    if (b)
				return b;
			}
		    }
		}
	    }
	}
	
	// on regarde à droite de la pièce
	if (colonne + getLargeur() - 1 != Mur.getColonne() - 1) { 
	    if (m.getTerrain().get(ligne)[colonne + getLargeur()] 
		    != Mur.getDefaultCase()) {
		
		// stocke la + petite valeur entre 
		// taille du terrain ou hauteur du carreau
		int hauteurMax = 0; 
		if (ligne + getHauteur() > m.getTerrain().size())
		    hauteurMax = m.getTerrain().size() - ligne;
		else
		    hauteurMax = getHauteur();
		for (int i = 0; i < hauteurMax; ++i) {
		    stock = m.getTerrain().get(ligne + i)
			    [colonne + getLargeur()];
		 // on stocke la lettre du carreau qui se trouve à droite
		    if (stock == 'x') {
			if (m.getPieceNeutre().getHauteur() 
				== this.getHauteur())
			    b = this.cloneCarreau(m, ligne, colonne, stock,
				    hauteurMax);
			if (b)
			    return b;
		    } else {
			for (int j = 0; j < PiecesAJouer.getNbCarreaux(); ++j) {
			    if (stock == p.getPieces()[j].getLettre()) 
			    // on cherche le carreau qui est lié à la lettre
				if (p.getPieces()[j].getHauteur() 
					== this.getHauteur()) 
				// on regarde la hauteur du carreau
				    
				    b = this.cloneCarreau(m, ligne, colonne,
					    stock, hauteurMax);
			    if (b)
				return b;
			}
		    }
		}
	    }
	}

	return b;
    }

    /**
     * Indique si le carreau est un clone d'un autre carreau déjà posé
     * 
     * @param m       le mur à carreler
     * @param ligne   la ligne saisie par l'utilisateur
     * @param colonne la colonne saisie par l'utilisateur
     * @param lettre  la lettre qui représente et est attribuée au carreau
     * @param hMax    la hauteur minimale entre le nbr de ligne du mur et la
     *                hauteur de la pièce + la ligne, on le désigne comme hMax
     * @return vrai si le carreau est un clone
     */
    public boolean cloneCarreau(Mur m, int ligne, int colonne, char lettre,
	    int hMax) {
	int cptLettreB = 0;
	int cptLettreG = 0;
	int cptLettreD = 0;
	if (ligne != 0) { // pour le bas
	    for (int i = 0; i < this.getLargeur(); ++i) {
		if (m.getTerrain().get(ligne - 1)[colonne + i] == lettre)
		    cptLettreB++;
		if (cptLettreB == this.getLargeur())
		    return true;
	    }
	}

	if (colonne != 0) { // on regarde à gauche de la pièce
	    for (int i = 0; i < hMax; ++i) {
		if (m.getTerrain().get(ligne + i)[colonne - 1] == lettre)
		    cptLettreG++;
		if (cptLettreG == this.getHauteur())
		    return true;
	    }
	}
	// on regarde à droite de la pièce
	if (colonne + getLargeur() - 1 != Mur.getColonne() - 1) {
	    for (int i = 0; i < hMax; ++i) {
		if (m.getTerrain().get(ligne + i)[colonne
			+ this.getLargeur()] == lettre)
		    cptLettreD++;
		if (cptLettreD == this.getHauteur())
		    return true;
	    }
	}

	return false;
    }

    /**
     * Indique si le placement du carreau est valide
     * 
     * @param m       le mur à carreler
     * @param ligne   la ligne saisie par l'utilisateur
     * @param colonne la colonne saisie par l'utilisateur
     * @param pions   un tableau de Carreaux (pièces du jeu)
     * @return vrai si le placement du carreau est valide
     */
    public boolean estValide(Mur m, int ligne, int colonne,
	    PiecesAJouer pions) {
	return  !(this.depasseZone(m, ligne, colonne))
		&& (this.estPlacable(m, ligne, colonne))
		&& (this.toucheCarreau(m, ligne, colonne))
		&& (this.baseStable(m, ligne, colonne))
		&& !(this.aCarreauAdjacent(m, ligne, colonne, pions));
    }

}