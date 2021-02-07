package composantDeJeu;

import static org.junit.Assert.*;

import org.junit.Test;

public class PiecesAJouerTest {

    @Test
    public void testPlusDeCarreau() {
	PiecesAJouer pion = new PiecesAJouer();

	assertFalse(pion.plusDeCarreau());

	for (int i = 0; i < 18; ++i) {
	    pion.getPieces()[i].setUtilisé(true);
	}

	assertTrue(pion.plusDeCarreau());
    }

    @Test
    public void testToString() {
	PiecesAJouer pion = new PiecesAJouer();
	Carte c = new Carte(ContenuCarte.TAILLE1);
	String attendu = "       e            E     \r\n" + 
			 "  b    e       B    E     \r\n" +
			 "a b cc e fff A B CC E FFF \r\n";
	assertEquals(pion.toString(c), attendu);

    }
}
