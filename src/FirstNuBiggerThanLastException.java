
/**
 * Signal que l'utilisateur a défini l'intervalle à afficher tel que
 * la valeur du premier nucléotide est supèrieur au dernier
 * @author Tayotaro
 *
 */
public class FirstNuBiggerThanLastException extends Exception{
	
	/**
	 * premier nucléotide à afficher
	 */
	
	private int infAA ;
	/**
	 * dernier nucléotide à afficher
	 */
	private int supAA ;
	
	/**
	 * Crée une instance de FirstNuBiggerThanLastException
	 * @param infAA	premier nucléotide à afficher
	 * @param supAA	dernier nucléotide à afficher
	 */
	public FirstNuBiggerThanLastException( int infAA , int supAA) {
		this.infAA = infAA ;
		this.supAA = supAA ;
	}
	
    @Override
    public String getMessage(){
    	String message = "\nErreur, FirstNuBiggerThanLastException\n"+
			"\nVous avez défini l'intervalle à afficher tel que\n"+
    		"La valeur du premier nucléotide est supèrieur au dernier\n\n"+
			"En effet: "+infAA+" > "+supAA+"\n" ;
    	return	message ;
    }
}
