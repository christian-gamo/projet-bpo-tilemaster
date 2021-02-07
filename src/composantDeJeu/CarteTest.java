package composantDeJeu;

import static org.junit.Assert.*;

import org.junit.Test;

public class CarteTest {

	@Test
	public void testConditionCarte() {
		Carte rouge = new Carte(ContenuCarte.ROUGE);
		Carte taille1 = new Carte(ContenuCarte.TAILLE1);
		Carte taille2 = new Carte(ContenuCarte.TAILLE2);
		Carte taille3 = new Carte(ContenuCarte.TAILLE3);
		Carreau carRouge = new Carreau (1,1,'A',Couleur.ROUGE);
		Carreau carBleu = new Carreau(1,1,'a', Couleur.BLEU);
		Carreau carTaille2 = new Carreau (1,2,'b', Couleur.BLEU);
		Carreau carTaille3 = new Carreau (3,1,'F', Couleur.ROUGE);
		
		assertTrue(rouge.conditionCarte(carRouge));
		assertFalse(rouge.conditionCarte(carBleu));
		assertTrue(taille1.conditionCarte(carRouge));
		assertTrue(taille2.conditionCarte(carTaille2));
		assertTrue(taille3.conditionCarte(carTaille3));
	}
	
	@Test
	public void testToString() {
		Carte rouge = new Carte(ContenuCarte.ROUGE);
		Carte bleu = new Carte(ContenuCarte.BLEU);
		Carte taille1 = new Carte(ContenuCarte.TAILLE1);
		Carte taille2 = new Carte(ContenuCarte.TAILLE2);
		Carte taille3 = new Carte(ContenuCarte.TAILLE3);
		
		assertEquals(rouge.toString(),"rouge");
		assertEquals(bleu.toString(),"bleu");
		assertEquals(taille1.toString(),"taille 1");
		assertEquals(taille2.toString(),"taille 2");
		assertEquals(taille3.toString(),"taille 3");
		
	}

}
