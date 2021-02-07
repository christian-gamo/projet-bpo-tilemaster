package jeu;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import composantDeJeu.Carte;
import composantDeJeu.Mur;
import composantDeJeu.PaquetDeCartes;
import composantDeJeu.PiecesAJouer;

public class AppliTest {

    @Test
    public void testFinDeJeu() {
	PaquetDeCartes pVide = new PaquetDeCartes();
	PaquetDeCartes p = new PaquetDeCartes(
		PaquetDeCartes.getNbCartesCouleur(),
		PaquetDeCartes.getNbCartesTaille());
	PiecesAJouer pion = new PiecesAJouer();
	assertTrue(Appli.finDeJeu(pVide, pion));
	assertFalse(Appli.finDeJeu(p, pion));
    }

    @Test
    public void testFinDePartie() {
	Mur m = new Mur();
	PiecesAJouer pion = new PiecesAJouer();
	String phrase = "";
	PaquetDeCartes pVide = new PaquetDeCartes(); // paquet vide, 0 carte
						     // écartée
	int score = -18;
	phrase = score + " points (" + 0 + " niveaux complets, " + 18
		+ " carreaux non posés, " + pVide.getPaquet().size()
		+ " cartes écartées)";
	assertEquals(Appli.finDePartie(m, pion, pVide), phrase);

    }

    @Test
    public void testSaisie() {
	Mur m = new Mur();

	PaquetDeCartes p = new PaquetDeCartes(
		PaquetDeCartes.getNbCartesCouleur(),
		PaquetDeCartes.getNbCartesTaille());
	PaquetDeCartes tasEcarte = new PaquetDeCartes();
	p.mélanger();

	PiecesAJouer pion = new PiecesAJouer();
	pion.trier();

	Carte piocher = p.piocher();
	boolean b1 = false;
	boolean b2 = false;

	String correct = "next";
	String incorrect = "non";
	Scanner sc1 = new Scanner(correct);
	Scanner sc2 = new Scanner(incorrect);

	try {
	    b1 = Appli.saisie(m, sc1, pion, piocher, tasEcarte);
	    System.out.println("Tu as entré next");
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	}

	try {
	    b2 = Appli.saisie(m, sc2, pion, piocher, tasEcarte);
	} catch (Exception e) {
	    System.err.println(e.getMessage());
	}

	assertTrue(b1);
	assertFalse(b2);
    }

}
