package jeu;

import java.util.*;
import composantDeJeu.Carreau;
import composantDeJeu.Carte;
import composantDeJeu.Mur;
import composantDeJeu.PaquetDeCartes;
import composantDeJeu.PiecesAJouer;

public class Appli {

    /** booléen contrôlant la boucle de jeu */
    private static boolean boucleDeJeu = false;
    /** points gagnées par niveaux complétés */
    public final static int PTS_NIVEAU = 5;
    /** nombre de carreleurs */
    public final static int NB_JOUEURS = 2;
    /** saisie à entrer pour arrêter la partie */
    public final static String STOP = "stop";
    /** saisie à entrer pour écarter une carte */
    public final static String NEXT = "next";

    public static void main(String[] args) {
	PaquetDeCartes p = new PaquetDeCartes
       (PaquetDeCartes.getNbCartesCouleur(),PaquetDeCartes.getNbCartesTaille());
	
	PaquetDeCartes tasEcarte = new PaquetDeCartes();
	p.mélanger();
	PiecesAJouer pion = new PiecesAJouer();

	Mur m = new Mur();

	Scanner saisie = new Scanner(System.in);

	while (!boucleDeJeu) {
	    String zone = m.toString();
	    /* Etape 1 : Affichage de la zone à carreler */
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
		("La carte « " + piocher + " » a été écarté. "
		+ "Aucun carreau ne satisfait cette condition.");
	    }

	    /* Etape 4 : Attendre la saisie du choix du carreleur */
	    if (!pions.isEmpty()) {
		boolean b = false;
		// indique la validité de la commande saisie par l'utilisateur
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
     * @param m         tableau de chaîne de caractères saisies par
     *                  l'utilisateur
     * @param scan      entrée de l'utilisateur
     * @param pions     le tableau statique des carreaux de la partie
     * @param piocher   la carte piochée
     * @param tasEcarte le paquet de cartes écartées
     * @return vrai si la commande est valide
     * @throws Exception si la commande est incorrecte
     */
    public static boolean saisie(Mur m, Scanner scan, PiecesAJouer pions,
	    Carte piocher, PaquetDeCartes tasEcarte) throws Exception {

	String saisie = scan.nextLine();
	boolean b = false;

	if ((saisie.trim().length() == 0) || saisie.isEmpty())
	 // Vérifie si l'utilisateur a entré des espaces ou un saut de ligne						
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
			+ "Veuillez réessayer.");
	    }
	    int ligne = sc.nextInt() - 1;

	    if (!sc.hasNextInt()) {
		sc.close();
		throw new Exception(
			"La commande n'existe pas. "
			+ "Veuillez réessayer.");
	    }
	    int colonne = sc.nextInt() - 1;

	    if (sc.hasNext()) {
		sc.close();
		throw new Exception(
			"La commande n'existe pas. "
			+ "Veuillez réessayer.");
	    }

	    if (!(((lettre >= 'a' || lettre >= 'A')
	         && (lettre <= 'i' || lettre <= 'I')))) {
		sc.close();
		throw new Exception(
			"La lettre ne correspond à aucun carreau. "
			+ "Veuillez réessayer.");
	    }

	    if (ligne < 0 || ligne > m.getTerrain().size() || colonne < 0
	        || colonne >= Mur.getColonne()) {
		sc.close();
		throw new Exception(
			"Les coordonnées sont invalides. "
			+ "Veuillez réessayer.");
	    }

	    for (int j = 0; j < PiecesAJouer.getNbCarreaux(); j++) {
		if (pions.getPieces()[j].getLettre() == lettre) {
		    Carreau c = pions.getPieces()[j];
		    if (!(piocher.conditionCarte(c)) || c.getUtilisé()) {
			sc.close();
			throw new Exception(
			"Le carreau ne fait pas partie des carreaux proposés. "
			+ "Veuillez réessayer.");
		    }

		    if (!(c.estValide(m, ligne, colonne, pions))) {
			sc.close();
			throw new Exception(
				"Le placement du carreau est invalide. "
				+ "Veuillez réessayer.");
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
     * Indique si une partie est terminée
     * 
     * @param p
     * @param pieces
     * @return vrai si le paquet de cartes est vide ou s'il n'y a plus de pièces
     *              à jouer
     */
    public static boolean finDeJeu(PaquetDeCartes p, PiecesAJouer pieces) {
	return p.plusDeCartes() || pieces.plusDeCarreau();
    }

    /**
     * Crée une chaine de caractères contenant le score et son détail
     * 
     * @param m      le mur, zone de jeu
     * @param pieces les pièces à jouer
     * @param p      le tas écarté
     * @return phrase, indiquant le score , le nombre de niveaux complétées et
     *                 le nombre de cartes écartées
     */
    public static String finDePartie(Mur m, PiecesAJouer pieces,
	    PaquetDeCartes p) {
	String phrase = "";
	int score = 0;
	/* 1) Calculer le nombre de niveaux complétés */
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
	/* 2) Compter le nombre de carreaux non posés */
	int nbCarreaux = 0;
	for (int i = 0; i < PiecesAJouer.getNbCarreaux(); ++i) {
	    if (!pieces.getPieces()[i].getUtilisé())
		++nbCarreaux;
	}

	/* 3) Compter le nombre de cartes dans le tas écarté */
	score = nbNiveauxComplets * PTS_NIVEAU
		- (nbCarreaux + p.getPaquet().size());
	phrase = score 
		+ " points (" + nbNiveauxComplets + " niveaux complets, "
		+ nbCarreaux + " carreaux non posés, " 
		+ p.getPaquet().size() + " cartes écartées)";

	return phrase;
    }
}
