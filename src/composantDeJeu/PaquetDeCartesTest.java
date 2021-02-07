package composantDeJeu;

import static org.junit.Assert.*;

import org.junit.Test;

public class PaquetDeCartesTest {

    @Test
    public void testPiocher() {
	PaquetDeCartes p = new PaquetDeCartes(
		PaquetDeCartes.getNbCartesCouleur(),
		PaquetDeCartes.getNbCartesTaille());
	String contenu = p.getPaquet().get(p.getPaquet().size() - 1)
		.getContenuCarte();
	Carte pioche = p.piocher();
	assertEquals(contenu, pioche.getContenuCarte());
    }

    @Test
    public void testRetirer() {
	PaquetDeCartes p = new PaquetDeCartes(
		PaquetDeCartes.getNbCartesCouleur(),
		PaquetDeCartes.getNbCartesTaille());
	assertEquals(p.getPaquet().size(), PaquetDeCartes.getMaxCartes());
	p.piocher();
	assertEquals(p.getPaquet().size(), PaquetDeCartes.getMaxCartes() - 1);
    }

    @Test
    public void testPlusDeCartes() {
	PaquetDeCartes pVide = new PaquetDeCartes();
	assertTrue(pVide.getPaquet().size() == 0);
    }

    @Test
    public void testAjouter() {
	PaquetDeCartes p = new PaquetDeCartes(
		PaquetDeCartes.getNbCartesCouleur(),
		PaquetDeCartes.getNbCartesTaille());
	PaquetDeCartes pVide = new PaquetDeCartes();
	Carte pioche = p.piocher();
	pVide.ajouter(pioche);
	assertEquals(pioche.getContenuCarte(),
		pVide.getPaquet().get(0).getContenuCarte());
    }
}
