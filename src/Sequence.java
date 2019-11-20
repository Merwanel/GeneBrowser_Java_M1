import java.util.List ;
import java.util.ArrayList ; //ArrayList

/**
 * Représente une séquence
 * @author EL ASRI Merwan
 */
public class Sequence{
	
	/**
	 * position du premier nucléotide de la séquence
	 */
	private int start ;
	
	/**
	 * position du dernier nucléotide de la séquence, 
	 * protected pour pouvoir y accéder dans la classe CDS
	 * @see CDS
	 */
	protected int stop ;
	
	/**
	 * source de la séquence
	 */
	private String source ;
	
	/**
	 * score associé à la séquence
	 */
	private String score ; 
	
	/**
	 * identifiant de la séquence
	 */
	private String id ;
	
	/**
	 * type de la séquence, mRNA, tRNA , rRNA ou gene ect..
	 */
	private String type ; 
	
	/**
	 * description de la séquence (dernière colonne du fichier gff3)
	 * telle quelle est dans le fichier gff3.
	 */
	private String description ;
	
	/**
	 * sens du brin sur lequel est la séquence, "forward" ou "reverse".
	 */
	private String strand ;
	
	/**
	 * nom de la séquence parent
	 */
	private String parent ;
	
	/**
	 * liste des noms des séquences fils. 
	 */
	private List<String> listFils = new ArrayList<String>() ; 
	
	/**
	* initialise une instance de Sequence.
	* @param	start	start de la séquence en nucléotide
	* @param	stop	stop de la séquence en nucléotide
	* @param	strand	sens sur laquelle la séquence est: "forward" ou "reverse"
	* @param	source source de la séquence
	* @param	score	score associé à la séquence
	* @param	id	id de la séquence
	* @param	type	type de la séquence: "gene" ou "mRNA" ou "CDS", ect
	* @param	description	description de la séquence récupérée telle quelle du fichier
	*/
	public Sequence( int start , int stop , String source , String score , String strand ,String id 
	, String type , String description ){
		this.start = start ;
		this.stop = stop ;
		this.source = source ;
		this.score = score ;
		this.id = id ;
		this.type = type ;
		this.description = description ;
		this.strand = strand ;
	}	
	
	/**
	* Retourne le start de le séquence en nucléotide.
	* @return		start de le séquence en nucléotide
	*/
	public int getStart(){
		return this.start ;
	}
	
	/**
	* Retourne le stop de le séquence en nucléotide.
	* @return		stop de le séquence en nucléotide
	*/
	public int getStop(){
		return this.stop ;
	}
	
	/**
	 * Retourne la source de la séquence
	 * @return	source de la séquence
	 */
	public String getSource() {
		return this.source ;
	}
		
	/**
	* Retourne le nom de la séquence.
	* @return		nom de la séquence
	*/
	public String getId(){
		return this.id ;
	}
	
	/**
	* Retourne le sens de la séquence.
	* @return		sens de la séquence
	*/
	public String getStrand() {
		return this.strand ;
	}
	
	/**
	* Retourne le type de la séquence.
	* @return		type de la séquence
	*/
	public String getType() {
		return this.type ;
	}
	
	/**
	* Retourne la description de la séquence.
	* @return		la description de la séquence telle qu'elle est présentée dans le fichier gff3.
	*/
	public String getDescription() {
		return this.description ;
	}	
	
	/**
	* Retourne la description à afficher.
	* @return		un string qui est la description à afficher. 
	*/
	public String makeDescription() {
		String descr[] ;
		String descriptionOut = "\n" 
				+"start :\t"+String.valueOf(this.start)+"\n"
				+"stop :\t"+String.valueOf(this.stop)+"\n"
				+"source :\t"+this.source+"\n"
				+"score :\t"+this.score+"\n"
				+"strand :\t"+this.strand+"\n" 
				+"type :\t"+this.type+"\n";
		
		descr = this.description.split("[=;]") ; // séparation du string selon "=" et ";"
				
		for(int i=0; i<descr.length; i+= 2){
			descriptionOut += 
				descr[i]+" :\t"+
				descr[i+1]+"\n" ;
		}
		descriptionOut += "\n" ;
		return descriptionOut ;
	}
	
	/**
	* Retourne la liste des séquences fils.
	* @return		une liste des noms des séquences fils de cette séquence
	*/
	public List<String> getFils() {
		return this.listFils ;
	}
	
	/**
	* Ajoute le nom de séquence passé en paramétre à la liste des fils.
	* @param	fils	le nom de la séquence fils à ajouter
	*/
	public void updateFils( String fils ) {
		listFils.add(fils) ;
	}
	
	/**
	* Retourne le nom de la séquence parent de la séquence actuelle
	* @return		le nom de la séquence parent de la séquence actuelle
	*/
	public String getParent() {
		return this.parent ;
	}
	
	/**
	* Défini le nom de la séquence parent.
	* @param	parent	le nom de la séquence parent à définir
	*/
	public void updateParent( String parent ) {
		this.parent = parent ;
	}
	
	/**
	* Ne fait rien.
	* <p>
	* Méthode utile seulement pour les CDS. Elle a été défini 
	* ici pour utiliser les classes CDS et Sequence de façon polymorphique.
	* @param	start	entier 
	* @param	stop	entier
	*/
	public void updateListStartStop ( int start , int stop ){
	}
	
	/**
	 * Retourne une liste contenant le start de la séquence.
	 * <p>
	 * cette fonction est inutile pour cette classe car elle fait doublon
	 * avec la méthode getStart(). Cette méthode est utile pour un CDS. Elle a été défini 
	 * ici pour utiliser les classes CDS et Sequence de façon polymorphique.
	 * @return	un objet de la classe ArrayList contenant le start de la séquence
	 */
	public ArrayList<Integer> getListStart(){
		return new ArrayList<Integer>(this.start) ;
	}
	
	/**
	 * Retourne une liste contenant le stop de la séquence.
	 * <p>
	 * cette fonction est inutile pour cette classe car elle fait doublon
	 * avec la méthode getStop(). Cette méthode est utile pour un CDS. Elle a été défini 
	 * ici pour utiliser les classes CDS et Sequence de façon polymorphique.
	 * @return		un objet de la classe ArrayList contenant le stop de la séquence
	 */
	public ArrayList<Integer> getListStop(){
		return new ArrayList<Integer>(this.stop) ;
	}
}




