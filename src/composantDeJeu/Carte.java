package composantDeJeu;

/** Type de données représentant une carte */
public class Carte {
	/** Contenu d'une carte */
	private ContenuCarte CONTENU;
	
	/**
	 * Constructeur
	 * 
	 * @param c le contenu de la carte (obligatoire)
	 */
	public Carte(ContenuCarte c) {
		this.CONTENU = c;
	}

	/**
	 * Renvoie en chaîne de caractère le contenu de la carte
	 * @return le contenu d'une carte
	 */
	public String getContenuCarte() {
		String s = "";
		switch(this.CONTENU) {
		case ROUGE:
			s = "Rouge";
			break;
		case BLEU:
			s = "Bleu";
			break;
		case TAILLE1:
			s = "Taille 1";
			break;
		case TAILLE2:
			s = "Taille 2";
			break;
		case TAILLE3:
			s = "Taille 3";
			break;
		}
		return s;
	}
	
	/**
	 * Indique si le carreau respecte la condition de la carte piochée
	 * @param c le carreau
	 * @return vrai si le carreau respecte la condition
	 */
	public boolean conditionCarte (Carreau c) {
		boolean b = false;
		switch(this.CONTENU) {
		case ROUGE : 
			b = c.estRouge();
		break;
		case BLEU : 
			b = c.estBleu();
		break;
		case TAILLE1 :
			b = c.estTaille1();
		break;
		case TAILLE2 :
			b = c.estTaille2();
		break;
		case TAILLE3 :
			b = c.estTaille3();
		break;
		}
		return b;
	}
	
	/**
	 * Renvoie une chaîne de caractère représentant le contenu d'une carte
	 */
	public String toString() {
		return getContenuCarte();
	}
}
