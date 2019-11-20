import java.lang.Exception ;


/**
 * Signal que les gènes ne sont pas classés selon leur nculéotide start, dans l'ordre croissant
 * <p>
 * La métode parser de la classe Parser lève une exception de type GeneNotInOrderException
 * @author EL ASRI Merwan
 * @see Parser#parser(String)
 *
 */
public class GeneNotInOrderException extends Exception {
	
	private int start1 ;
	private int start2 ;
	
	/**
	 * Crée une instance de GeneNotInOrderException
	 * @param start1	premier nucléotide d'un des gènes mal classé
	 * @param start2	premier nucléotide d'un des gènes mal classé
	 */
	public GeneNotInOrderException(int start1 , int start2) {
		this.start1 = start1 ;
		this.start2 = start2 ;
		System.out.println("\nErreur, GeneNotInOrderException\n"+
					"Les gènes ne sont pas classés selon leur nucléotide start, dans l'ordre croissant") ;
		System.out.println("\nLe problème se situe entre un gène commençant au "+
		start1+" ieme nucléotide et un autre gène commençant au "+start2+" ieme nucléotide\n") ;
	}
	
    @Override
    public String getMessage(){
    	String message = "\nErreur, GeneNotInOrderException\n"+
			"Les gènes ne sont pas classés selon leur nucléotide start, dans l'ordre croissant"+
			"\nLe problème se situe entre un gène commençant au "+
			start1+" ieme nucléotide et un autre gène commençant au "+start2+" ieme nucléotide\n" ;
				
    	return	message ;
    }
}
