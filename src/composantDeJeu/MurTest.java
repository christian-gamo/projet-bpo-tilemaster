package composantDeJeu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MurTest {
    Mur m = new Mur();
    Carreau c = new Carreau(2, 1, 'c', Couleur.BLEU);
    PiecesAJouer pions = new PiecesAJouer();

    @Test
    public void testPlacerCaroNeutre() {
	int cpt = 0;
	for (int i = 0; i < m.getTerrain().size(); ++i) {
	    for (int j = 0; j < Mur.getColonne(); ++j) {
		if (m.getTerrain().get(i)[j] == 'x')
		    ++cpt;
	    }
	}
	assertTrue(cpt == 3);
    }

    @Test
    public void testPlacerCarreau() {
	int ligne = -1;
	int colonne = -1;
	for (int i = 0; i < m.getTerrain().size(); ++i) {
	    for (int j = 0; j < Mur.getColonne(); ++j) {
		if (!(c.getUtilisé()) && !(c.depasseZone(m, i, j))
			&& (c.estPlacable(m, i, j))
			&& (c.toucheCarreau(m, i, j)) && (c.baseStable(m, i, j))
			&& !(c.aCarreauAdjacent(m, i, j, pions))) {
		    m.placerCarreau(c, i, j);
		    ligne = i;
		    colonne = j;
		}
	    }
	}

	assertTrue(ligne != -1 && colonne != -1);

	for (int k = 0; k < c.getHauteur(); ++k) {
	    for (int l = 0; l < c.getLargeur(); ++l) {
		assertTrue(m.getTerrain().get(ligne + k)[colonne + l] 
			== c.getLettre());
	    }
	}
    }

    @Test
    public void testToString() {
	Mur m = new Mur();
	int ligneNeutre = 0;
	int colonneNeutre = -1;

	String cas1Attendu = " 2           \r\n" +
			     " 1 x x x     \r\n" +
			     "   1 2 3 4 5 ";

	String cas2Attendu = " 4           \r\n" +
			     " 3         x \r\n" +
			     " 2         x \r\n" + 
			     " 1         x \r\n" + 
			     "   1 2 3 4 5 ";

	String cas3Attendu = " 4           \r\n" + 
			     " 3 x         \r\n" + 
			     " 2 x         \r\n" + 
			     " 1 x         \r\n" + 
			     "   1 2 3 4 5 ";

	String cas4Attendu = " 2           \r\n" + 
			     " 1     x x x \r\n" +
			     "   1 2 3 4 5 ";

	System.out.print(m.toString());

	for (int i = 0; i < Mur.getColonne(); ++i) {
	    if (m.getTerrain().get(ligneNeutre)[i] == 'x') {
		colonneNeutre = i;
		break;
	    }
	}

	if (colonneNeutre == 0) {
	    if (m.getPieceNeutre().getHauteur() == 1)
		assertEquals(cas1Attendu, m.toString());
	    else
		assertEquals(cas3Attendu, m.toString());
	} else {
	    if (m.getPieceNeutre().getHauteur() == 1)
		assertEquals(cas4Attendu, m.toString());
	    else
		assertEquals(cas2Attendu, m.toString());
	}

    }

}
