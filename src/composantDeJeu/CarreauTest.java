package composantDeJeu;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class CarreauTest {
    Mur m = new Mur();
    Carreau valide = new Carreau(1, 1, 'a', Couleur.BLEU);
    Carreau invalide = new Carreau(1, 1, 'A', Couleur.ROUGE);

    @Test
    public void testEstPlacable() {
	int ligneNeutre = 0;
	int colonneNeutre = -1;

	for (int i = 0; i < Mur.getColonne(); ++i) {
	    if (m.getTerrain().get(ligneNeutre)[i] == 'x') {
		colonneNeutre = i;
		break;
	    }
	}

	assertTrue(colonneNeutre != -1);

	assertTrue(valide.estPlacable(m,
		ligneNeutre + m.getPieceNeutre().getHauteur(), colonneNeutre));
	assertFalse(invalide.estPlacable(m, ligneNeutre, colonneNeutre));

    }

    @Test
    public void testToucheCarreau() {
	int ligneNeutre = 0;
	int colonneNeutre = -1;

	for (int i = 0; i < Mur.getColonne(); ++i) {
	    if (m.getTerrain().get(ligneNeutre)[i] == 'x') {
		colonneNeutre = i;
		break;
	    }
	}

	assertTrue(valide.toucheCarreau(m,
		ligneNeutre + m.getPieceNeutre().getHauteur(), colonneNeutre));

	if (colonneNeutre == 0)
	    assertFalse(valide.toucheCarreau(m, m.getPieceNeutre().getHauteur(),
		    colonneNeutre + m.getPieceNeutre().getLargeur() + 1));
	else {
	    assertFalse(invalide.toucheCarreau(m,
		    m.getPieceNeutre().getHauteur(), colonneNeutre - 1));
	}
    }

    @Test
    public void testBaseStable() {
	Carreau c = new Carreau(2, 1, 'c', Couleur.BLEU);
	int ligneNeutre = 0;
	int colonneNeutre = -1;

	for (int i = 0; i < Mur.getColonne(); ++i) {
	    if (m.getTerrain().get(ligneNeutre)[i] == 'x') {
		colonneNeutre = i;
		break;
	    }
	}

	assertTrue(valide.baseStable(m,
		ligneNeutre + m.getPieceNeutre().getHauteur(), colonneNeutre));

	if (colonneNeutre == 0)
	    assertFalse(c.baseStable(m, m.getPieceNeutre().getHauteur(),
		    colonneNeutre + m.getPieceNeutre().getLargeur() + 1));
	else {
	    assertFalse(c.baseStable(m, m.getPieceNeutre().getHauteur(),
		    colonneNeutre - 1));
	}
    }

    @Test // Ce test teste ACarreauAdjacent() et cloneCarreau()
    public void testACarreauAdjacent() {
	PiecesAJouer pion = new PiecesAJouer();
	Carreau f = new Carreau(3, 1, 'f', Couleur.BLEU);
	Carreau e = new Carreau(1, 3, 'e', Couleur.BLEU);

	int ligneNeutre = 0;
	int colonneNeutre = -1;
	for (int i = 0; i < Mur.getColonne(); ++i) {
	    if (m.getTerrain().get(ligneNeutre)[i] == 'x') {
		colonneNeutre = i;
		break;
	    }
	}
	if (m.getPieceNeutre().getHauteur() == 1) {
	    assertTrue(f.aCarreauAdjacent(m, ligneNeutre + 1, colonneNeutre,
		    pion));
	    assertFalse(invalide.aCarreauAdjacent(m, ligneNeutre + 1,
		    colonneNeutre, pion));
	} else {
	    if (colonneNeutre == 0) {
		assertTrue(e.aCarreauAdjacent(m, ligneNeutre, colonneNeutre + 1,
			pion));
		assertFalse(invalide.aCarreauAdjacent(m, ligneNeutre,
			colonneNeutre + 1, pion));
	    } 
	    else {
		assertTrue(e.aCarreauAdjacent(m, ligneNeutre, colonneNeutre - 1,
			pion));
		assertFalse(invalide.aCarreauAdjacent(m, ligneNeutre,
			colonneNeutre - 1, pion));
	    }
	}

    }
}
