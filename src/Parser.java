import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList ; 
import java.util.LinkedHashMap ;
import java.util.Iterator ; 
import java.util.Map ;
import java.util.Map.Entry;


/**
 * Parser est une classe utilitaire pour des méthodes dont le thème est la recherche
 * @author EL ASRI Merwan
 *
 */
public class Parser{
	
 	/**
 	 * position max trouvé dans la séquence.
 	 * Cet entier est utilisé pour initialiser le jombre de nucléotide à afficher (nbNuToDisplay).
 	 */
 	public static int maxStop ;    

 	
    /**
     * @param fileName	nom du fichier à parser
     * @return		objet de la classe LinkedHashMap ayant pour clés les identifiants des séquences
     * et comme valeurs les Sequences
     * <p>
     * La classe LinkedHashMap est utilisé parce que l'ordre d'ajout des clés est conservé.
     * C'est à dire que lors de l'itération la première clé ajoutée est la première à être appelée.
     * Cette propriété est utilisée dans paintComponent() de la classe Cadre.
     * <p>
     * Donc le fichier gff3 doit avoir ses genes classés selon leur start, par ordre croissant, pour que le parser puisse
     * bien fonctionner.
	 * @throws IOException	si le fichier n'a pas pu être lu ou si le fichier n'a pas été trouvé dans la méthode parser de la classe Parser
	 * @throws BadNumberOfColonneException	si le nombre de colonne des séquences est différente de 9, dans la méthode parser de la classe Parser
	 * @throws GeneNotInOrderException	si les gènes ne sont pas classé dans l'odre croissant, selon leur start, dans la méthode parser de la classe Parser
	 * @throws StartPlusGrandQueStopException	si il existe sur une séquence un start supèrieur au stop
	 * @throws NumberFormatException	si un des strings situés aux colonnes start et stop ne peut pas être convertit en entier, dans la méthode parser de la classe Parser
	 * @see Parser#parser(String)
	 * @see Cadre#paintComponent(java.awt.Graphics)
     */
    public static LinkedHashMap<String, Sequence> parser( String fileName) 
    		throws IOException , BadNumberOfColonneException , GeneNotInOrderException , StartPlusGrandQueStopException , NumberFormatException {
    	maxStop = 0 ;
    	int start = -1 ; // start de la séquence en nucléotide
    	int startGenePre = -1 ; // start du gene précédent
        int stop = -1 ; // stop de la séquence en nucléotide
        String line; // une ligne du fichier à parser
        String info[]  ; // tableau issu de la séparation de la ligne à parser selon la tabulation
        String source = "" ; // source de la séquence
        String type = "" ; // type de la séquence: "gene" ou "mRNA" ou "CDS", ect
        BufferedReader br = null  ;
        String description = ""; // description de la séquence récupérée telle quelle du fichier
        String id = "" ; // id de la séquence
        String strand = "" ; // sens sur laquelle la séquence est: "forward" ou "reverse"
        String score = "none" ; // score associé à la séquence
        LinkedHashMap<String, Sequence> genome = new LinkedHashMap<String, Sequence>() ; // objet de la classe LinkedHashMap ayant pour clés les identifiants des séquences et comme valeurs les Sequences
        Sequence seq ; // objet de la classe Sequence à ajouter dans le hash genome
    	
    	br =  new BufferedReader(new FileReader( fileName ));
	
    	while ((line = br.readLine()) != null) {
    	
    		line = line.trim() ; // enl�ve les espaces de fin de ligne

			// les lignes commen�ant par "#" sont des commentaires, elle ne nous interessent pas.
	        if( !(line.isEmpty() || line.substring(0,1).contains("#") ) ){
	        
		    	info = line.split("[\t]") ; // s�paration selon la tabulation
		    	
		    	
		    	
		    	// il est cens� y avoir 9 colonnes
		    	if( info.length == 9 ){ 
				
					// les identifiants qui seront les clés du hash sont trouvé grace à une expression régulière 
					String pattern2 = "[Ii][dD]=(.*?);.*";
					Pattern r2 = Pattern.compile(pattern2); 
					Matcher m2 = r2.matcher(line);
					if (m2.find( )) {
						id = m2.group(1) ;
						source = info[1] ;
						type = info[2] ;
						start = Integer.valueOf(info[3]) ;
						stop = Integer.valueOf(info[4]) ;
						if( !info[5].equals("."))	score = info[5];
						if( info[6].equals("-"))	strand = "reverse" ;
						else	strand = "forward" ;
						description = info[8] ;
						
						if( start > stop ) {
							br.close();
							throw new StartPlusGrandQueStopException(line , start , stop) ;
						}
						
						if(type.contains("gene")) {
							if( startGenePre > start) {
								br.close();
								throw new GeneNotInOrderException(startGenePre , start) ;
							}
							else	startGenePre = start ;
						}
						
						
						// si ce CDS se trouve déjà  dans le hash, alors la liste de ces stops
						// et la liste de ces starts sont mis à jour
						if( (type.equals("CDS")) && (genome.containsKey(id)) ){
								CDS cds = (CDS) genome.get(id) ;
								cds.updateListStartStop( start , stop ) ;
						}
						else if(!type.equals("exon")) {
							if( type.equals("CDS"))	seq = new CDS( start , stop , source , score , strand , id , type , description) ;
							else	seq = new Sequence( start , stop , source , score , strand ,id ,type , description ) ;
							genome.put(id, seq ) ;
							if(maxStop < stop)	maxStop = stop ;
						}
					}
				}
				else {
					br.close();
					throw new BadNumberOfColonneException(  info.length , line ) ;
				}
			}
		}
    	br.close();

        createGenealogy( genome ) ; // création de la liste des fils pour toutes les séquences du génome
        
        return (genome) ;
    }
    
    
    /**
     * Cherche le pattern parmi toutes les séquences et retourne un ArrayList 
     * contenant les identifiants des séquences dans lesquelles le pattern a été trouvé
     * @param pattern	String à chercher dans le hash genome
     * @param genome	 objet de la classe LinkedHashMap ayant pour clés les identifiants des séquences
     * et comme valeurs les Sequences. C'est dans ce hash que le pattern est cherché.
     * @return		un objet de la classe ArrayList contenant les identifiants des séquences dans lesquelles le pattern a été trouvé
     */
    public static ArrayList<String> search( String pattern , LinkedHashMap<String, Sequence> genome){
    
    	ArrayList<String> result = new ArrayList<String>() ;
    	
    	Iterator<Entry<String, Sequence>> iterator = genome.entrySet().iterator();
	    while (iterator.hasNext()) {
			Map.Entry<String ,Sequence> mapentry = iterator.next();
			Sequence seq =  mapentry.getValue() ;
			if( seq.getSource().matches(pattern) ||
			seq.getStrand().matches(pattern) ||
			seq.getDescription().contains(pattern) ||
			seq.getId().contains(pattern) ){
				
				result.add(seq.getId()) ;
			}
    	}
    	return result ;
    	
	}
	
	
	
	/**
	 * Crée, pour chaque instance Sequence se trouvant dans le hash, la liste des identifiants de ses fils.
	 * Pour chaque Sequence, si le parent existe (il n'y a pas de séquence parent pour les genes) alors
	 * la liste des fils de ce parent est mis à jour.
	 * @param genome	objet de la classe LinkedHashMap ayant pour clés les identifiants des séquences
	 */
	public static void  createGenealogy( LinkedHashMap<String, Sequence> genome ){
	
		
		String motifParent = "Parent=(.*?);.*"; // cette expression régulière permet de récupérer tout ce qui se trouve entre "Parent=" et ";"
		Pattern r2 = Pattern.compile(motifParent); 
		Iterator<Entry<String, Sequence>> iterator = genome.entrySet().iterator();
	    while (iterator.hasNext()) {
			Map.Entry<String ,Sequence> mapentry = iterator.next();
			Sequence seq = mapentry.getValue() ;
			
			
			Matcher parrent = r2.matcher(seq.getDescription());
			if (parrent.find( )) {
				Sequence seqParent = genome.get(parrent.group(1)) ;
				seq.updateParent(parrent.group(1)) ;
				seqParent.updateFils( seq.getId() ) ;
			}
		}
	}
 }







 
 
 
