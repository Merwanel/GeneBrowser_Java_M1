import java.util.ArrayList ;

/**
 * Représente un CDS.
 * <p>
 * Dans le cas d'un eucaryote, un CDS peut être composé à partir de plusieurs exons.
 * Dans un fichier gff3, cela se traduit par le fait que un CDS donné aura souvent plusieurs starts et stops associés.
 * En d'autres termes, dans le fichier gff3, l'identifiant d'un CDS sera retrouvé sur plusieurs lignes successives
 * avec seulement les valeurs de start et de stop qui changent.
 * Donc les CDS doivent être traités un peu différemment des autres types de séquence. C'est à dire que l'initialisation
 * d'un CDS se fera de manière identique à celle des autres séquences, mais ensuite à chaque fois que le même identifiant 
 * est rencontré, il n'y a pas de nouvelle initialisation, seulement une mise à jour des listes de starts et de stops.
 * <p>
 * La classe CDS hérite de la classe Séquence.
 * @see	Sequence
 * @author EL ASRI Merwan
 */
public class CDS extends Sequence{
	
	/**
	 * liste des starts du CDS en nucleotides
	 */
	private ArrayList<Integer> listStart = new ArrayList<Integer>() ; 
	
	/**
	 * liste des stops du CDS en nucleotides
	 */
	private ArrayList<Integer> listStop = new ArrayList<Integer>() ;
	
	
	/**
	* initialise une instance de CDS.
	* @param	start	premier start du CDS en nucléotide
	* @param	stop	premier stop du CDS en nucléotide
	* @param	source source de la séquence
	* @param	score	score associé à la séquence
	* @param	strand	sens sur laquelle la CDS est: "forward" ou "reverse"
	* @param	id	id de la CDS
	* @param	type	type de la CDS: "CDS" ici
	* @param	description	description de la CDS récupéré telle quelle du fichier
	*/
	public CDS(int start , int stop , String source, String score ,String strand ,String id,
	 String type , String description){
		
		super(start , stop , source , score , strand , id ,type , description) ;
		this.listStart.add(start) ;
		this.listStop.add(stop) ;
		
	}
	
	/**
	* Ajoute aux listes de starts et de stops les deux entiers passés en paramètres.
	* Met également à jour le dernier stop du CDS.
	* @param	start	entier à ajouter à la liste des start
	* @param	stop	entier à ajouter à la liste des stop
	* @see CDS#getListStop()
	* @see CDS#getListStart()
	*/
	public void updateListStartStop ( int start , int stop ){
		this.listStart.add(start) ;
		this.listStop.add(stop) ;
		this.stop = listStop.get(listStop.size() - 1) ;
	}
	
	/**
	 * Retourne une liste contenant les starts du CDS
	 * @return		un objet de la classe ArrayList contenant les starts du CDS
	 * @see CDS#getListStop()
	 * @see CDS#updateListStartStop(int, int)
	 */
	public ArrayList<Integer> getListStart(){
		return this.listStart ;
	}
	
	/**
	 * Retourne une liste contenant les stops du CDS
	 * @return		un objet de la classe ArrayList contenant les stops du CDS
	 * @see CDS#getListStart()
	 * @see CDS#updateListStartStop(int, int)
	 */
	public ArrayList<Integer> getListStop(){
		return this.listStop ;
	}
}
	
