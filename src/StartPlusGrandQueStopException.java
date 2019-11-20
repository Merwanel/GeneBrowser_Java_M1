import java.lang.Exception ;

/**
 * Signale qu'il existe sur une séquence un start supèrieur au stop
 * <p>
 * La métode parser de la classe Parser lève une exception de type GeneNotInOrderException
 * @author EL ASRI Merwan
 * @see Parser#parser(String)
 */
public class StartPlusGrandQueStopException extends Exception {
	
	private String line ;
	private int start ;
	private int stop ;
	
	/**
	 * Crée une instance de StartPlusGrandQueStopException
	 * @param line ligne dont le start est plus grand que le stop
	 * @param start	start de la ligne posant problème 
	 * @param stop	stop de la ligne posant problème 
	 */
	public StartPlusGrandQueStopException(String line , int start , int stop) {
		this.line = line ;
		this.start = start ;
		this.stop = stop ;
		
		System.out.println("\nErreur, StartPlusGrandQueStopException.\n"+
				"\nIl existe sur une séquence un start supèrieur au stop, en effet: "+
				start+" > "+stop+
				"\n\nLe problème est sur la ligne suivante:\n"+line) ;
	}
	
    @Override
    public String getMessage(){
    	String message = "\nErreur, StartPlusGrandQueStopException.\n"+
				"\nIl existe sur une séquence un start supèrieur au stop, en effet: "+
				start+" > "+stop+
				"\n\nLe problème est sur la ligne suivante:\n"+line ;
				
    	return	message ;
    }
}
