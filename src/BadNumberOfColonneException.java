import java.lang.Exception ;

/**
 * Signal qu'une des lignes du fichier gff3 a un nombre de colonne différent de 9.
 * <p>
 * Affiche également la ligne qui pose problème.
 * <p>
 * La méthode parser de la classe Parser lance une exception de type BadNumberOfColonneException
 * @author EL ASRI Merwan
 * @see Parser#parser(String)
 */
public class BadNumberOfColonneException extends Exception{
	
	 private int nbColonne ;
	 private String line ;

	/**
	 * Crée une instance de BadNumberOfColonneException
	 * @param nbColonne	nombre de colonne de la ligne
	 * @param line	ligne dont le nombre de colonne est différent de 9
	 */
	public BadNumberOfColonneException( int nbColonne , String line ){
		this.nbColonne = nbColonne ;
		this.line = line ;
		System.out.println("\nErreur, BadNumberOfColonneException\n"+
						"Une des lignes du fichier gff3 a un nombre de colonne différent de 9\n")  ;
		System.out.println("\nLa ligne suivante a "+nbColonne+" colonnes:\n")  ;
		System.out.println(line+"\n")  ;
	}
	
    @Override
    public String getMessage(){
    	String message = "\nErreur, BadNumberOfColonneException\n"+
			"Une des lignes du fichier gff3 a un nombre de colonne différent de 9\n"+
			"\nLa ligne suivante a "+nbColonne+" colonnes:\n"+line+"\n" ;
    	return	message ;
    }
}

