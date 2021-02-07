package jeu;

import java.util.*;
import composantDeJeu.Carreau;
import composantDeJeu.Carte;
import composantDeJeu.Mur;
import composantDeJeu.PaquetDeCartes;
import composantDeJeu.PiecesAJouer;

public class Appli {

    /** bool�en contr�lant la boucle de jeu */
    private static boolean boucleDeJeu = false;
    /** points gagn�es par niveaux compl�t�s */
    public final static int PTS_NIVEAU = 5;
    /** nombre de carreleurs */
    public final static int NB_JOUEURS = 2;
    /** saisie � entrer pour arr�ter la partie */
    public final static String STOP = "stop";
    /** saisie � entrer pour �carter une carte */
    public final static String NEXT = "next";

    public static void main(String[] args) {
	PaquetDeCartes p = new PaquetDeCartes
       (PaquetDeCartes.getNbCartesCouleur(),PaquetDeCartes.getNbCartesTaille());
	
	PaquetDeCartes tasEcarte = new PaquetDeCartes();
	p.m�langer();
	PiecesAJouer pion = new PiecesAJouer();

	Mur m = new Mur();

	Scanner saisie = new Scanner(System.in);

	while (!boucleDeJeu) {
	    String zone = m.toString();
	    /* Etape 1 : Affichage de la zone � carreler */
	    System.out.println(zone);

	    /* Etape 2 : Tirage de carte */

	    Carte piocher = p.piocher();
	    piocher.toString();
	    System.out.println(piocher);

	    /* Etape 3 : Affichage des carreaux disponibles + convenables */
	    String pions = pion.toString(piocher);
	    System.out.println(pions);

	    if (pions.isEmpty()) {
		tasEcarte.ajouter(piocher);
		System.out.println
		("La carte � " + piocher + " � a �t� �cart�. "
		+ "Aucun carreau ne satisfait cette condition.");
	    }

	    /* Etape 4 : Attendre la saisie du choix du carreleur */
	    if (!pions.isEmpty()) {
		boolean b = false;
		// indique la validit� de la commande saisie par l'utilisateur
		while (!b) {
		    try {
			b = saisie(m, saisie, pion, piocher, tasEcarte);
		    } catch (Exception e) {
			System.err.println(e.getMessage());
		    }
		}
	    }

	    if (!boucleDeJeu)
		boucleDeJeu = finDeJeu(p, pion);
	    
	    /* Fin Etape 4 */
	}
	
	/* Fin while du jeu */
	String resultat = "";
	resultat = finDePartie(m, pion, tasEcarte);
	System.out.println(resultat);
	saisie.close();
    }

    /**
     * Lance la saisie utilisateur et son traitement
     * 
     * @param m         tableau de cha�ne de caract�res saisies par
     *                  l'utilisateur
     * @param scan      entr�e de l'utilisateur
     * @param pions     le tableau statique des carreaux de la partie
     * @param piocher   la carte pioch�e
     * @param tasEcarte le paquet de cartes �cart�es
     * @return vrai si la commande est valide
     * @throws Exception si la commande est incorrecte
     */
    public static boolean saisie(Mur m, Scanner scan, PiecesAJouer pions,
	    Carte piocher, PaquetDeCartes tasEcarte) throws Exception {

	String saisie = scan.nextLine();
	boolean b = false;

	if ((saisie.trim().length() == 0) || saisie.isEmpty())
	 // V�rifie si l'utilisateur a entr� des espaces ou un saut de ligne						
	    return b;

	if (saisie.equals(STOP)) {
	    boucleDeJeu = true;
	    b = true;
	} else if (saisie.equals(NEXT)) {
	    tasEcarte.ajouter(piocher);
	    b = true;
	    
	    
	} else {
	    Scanner sc = new Scanner(saisie);
	    char lettre = sc.next().charAt(0);

	    if (!sc.hasNextInt()) {
		sc.close();
		throw new Exception(
			"La commande n'existe pas. "
			+ "Veuillez r�essayer.");
	    }
	    int ligne = sc.nextInt() - 1;

	    if (!sc.hasNextInt()) {
		sc.close();
		throw new Exception(
			"La commande n'existe pas. "
			+ "Veuillez r�essayer.");
	    }
	    int colonne = sc.nextInt() - 1;

	    if (sc.hasNext()) {
		sc.close();
		throw new Exception(
			"La commande n'existe pas. "
			+ "Veuillez r�essayer.");
	    }

	    if (!(((lettre >= 'a' || lettre >= 'A')
	         && (lettre <= 'i' || lettre <= 'I')))) {
		sc.close();
		throw new Exception(
			"La lettre ne correspond � aucun carreau. "
			+ "Veuillez r�essayer.");
	    }

	    if (ligne < 0 || ligne > m.getTerrain().size() || colonne < 0
	        || colonne >= Mur.getColonne()) {
		sc.close();
		throw new Exception(
			"Les coordonn�es sont invalides. "
			+ "Veuillez r�essayer.");
	    }

	    for (int j = 0; j < PiecesAJouer.getNbCarreaux(); j++) {
		if (pions.getPieces()[j].getLettre() == lettre) {
		    Carreau c = pions.getPieces()[j];
		    if (!(piocher.conditionCarte(c)) || c.getUtilis�()) {
			sc.close();
			throw new Exception(
			"Le carreau ne fait pas partie des carreaux propos�s. "
			+ "Veuillez r�essayer.");
		    }

		    if (!(c.estValide(m, ligne, colonne, pions))) {
			sc.close();
			throw new Exception(
				"Le placement du carreau est invalide. "
				+ "Veuillez r�essayer.");
		    }

		    m.placerCarreau(c, ligne, colonne);
		    b = true;
		}
	    }
	    
	    sc.close();
	}
	return b;
    }

    /**
     * Indique si une partie est termin�e
     * 
     * @param p
     * @param pieces
     * @return vrai si le paquet de cartes est vide ou s'il n'y a plus de pi�ces
     *              � jouer
     */
    public static boolean finDeJeu(PaquetDeCartes p, PiecesAJouer pieces) {
	return p.plusDeCartes() || pieces.plusDeCarreau();
    }

    /**
     * Cr�e une chaine de caract�res contenant le score et son d�tail
     * 
     * @param m      le mur, zone de jeu
     * @param pieces les pi�ces � jouer
     * @param p      le tas �cart�
     * @return phrase, indiquant le score , le nombre de niveaux compl�t�es et
     *                 le nombre de cartes �cart�es
     */
    public static String finDePartie(Mur m, PiecesAJouer pieces,
	    PaquetDeCartes p) {
	String phrase = "";
	int score = 0;
	/* 1) Calculer le nombre de niveaux compl�t�s */
	int cpt = 0;
	int nbNiveauxComplets = 0;
	for (int i = 0; i < m.getTerrain().size(); ++i) {
	    for (int j = 0; j < Mur.getColonne(); ++j) {
		if (m.getTerrain().get(i)[j] != Mur.getDefaultCase())
		    ++cpt;
	    }
	    if (cpt == Mur.getColonne())
		++nbNiveauxComplets;
	    cpt = 0;
	}
	/* 2) Compter le nombre de carreaux non pos�s */
	int nbCarreaux = 0;
	for (int i = 0; i < PiecesAJouer.getNbCarreaux(); ++i) {
	    if (!pieces.getPieces()[i].getUtilis�())
		++nbCarreaux;
	}

	/* 3) Compter le nombre de cartes dans le tas �cart� */
	score = nbNiveauxComplets * PTS_NIVEAU
		- (nbCarreaux + p.getPaquet().size());
	phrase = score 
		+ " points (" + nbNiveauxComplets + " niveaux complets, "
		+ nbCarreaux + " carreaux non pos�s, " 
		+ p.getPaquet().size() + " cartes �cart�es)";

	return phrase;
    }
}
