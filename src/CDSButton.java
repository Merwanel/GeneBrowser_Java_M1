import javax.swing.JButton;
import java.awt.Graphics;
import java.util.ArrayList ;
import java.awt.Color ;
import java.awt.Font ;


/**
 * Bouton spécial pour les CDS. Hérite de JButton.
 * <p>
 * Les CDS sont traités différemments (voir la documentation de la classe CDS).
 * <p>
 * La classe CDSButton sert à customiser les boutons représentant les CDS.
 * Lamethode paintComponent y est redéfini pour pouvoir représenter les exons
 * (rectangles) et introns (lignes).
 * @author EL ASRI Merwan
 * @see CDS
 */
public class CDSButton extends JButton
{
	
    /**
     * id du CDS à afficher sur le CDSButton
     */
	private String idCDS="" ;
	
	
	/**
	 * largeur du CDSButton
	 */
	private int width ;
	
	/**
	 * hauteur du CDSButton
	 */
	private int height ;
	
	
	/**
	 * liste des starts en pixel composant ce CDS
	 */
	private ArrayList<Integer> listPixelStart = new ArrayList<Integer>() ; //liste des starts du CDS en pixel 
	
	
	/**
	 * liste des stops en pixel composant ce CDS
	 */
	private ArrayList<Integer> listPixelStop = new ArrayList<Integer>() ;
	
	
	/**
	 * boolean, true si le CDS est est dans les résultats de la recherche.
	 */
	private boolean isSequenceContainedInSearchResults ;
	
	/**
	* initialise une instance de la classe CDSButton
	* @param	idCDS	id du CDS à afficher sur le CDSButton
	* @param	width	largeur du CDSButton
	* @param	height	hauteur du CDSButton
	* @param	listPixelStart	liste des starts en pixel composant ce CDS
	* @param	listPixelStop	liste des stops en pixel composant ce CDS
	* @param	isSequenceContainedInSearchResults	boolean, true si le CDS est est dans les résultats de la recherche.
	* Dans ce cas le bouton sera de couleur jaune, cyan sinon.
	*/
    public CDSButton(String idCDS , int width , int height,
    	ArrayList<Integer> listPixelStart ,ArrayList<Integer> listPixelStop ,
    	boolean isSequenceContainedInSearchResults)
    {
        super(idCDS);
        this.idCDS = idCDS ;
        this.width = width ;
        this.height = height ;
    	this.listPixelStart = listPixelStart ;
    	this.listPixelStop = listPixelStop ;
    	this.isSequenceContainedInSearchResults = isSequenceContainedInSearchResults ;
    }
    
    /**
    * paint sur le CDSbutton des rectangles et des lignes représentant respectivement les exons et introns de la séquence.
    * @param	g	le contexte graphique sur lequel les figures sont dessinés
    */
	@Override
    public void paintComponent (Graphics g)
    {
		// textWidth est la largeur, en pixel, de l'identifiant à afficher
    	int textWidth = g.getFontMetrics().stringWidth(idCDS) ;
    	
    	// si le pattern recherché est trouvé dans le CDS alors la couleur pass du cyan au jaune
    	if(isSequenceContainedInSearchResults)	g.setColor(Color.YELLOW) ;
    	else	g.setColor(Color.CYAN);
    	
    	// dessin d'une ligne le long  du bouton représentant les introns.
    	g.drawLine(0 , height/2 ,  width , height/2) ;
    	
    	// dessin de rectangles représentant les exons
    	for(int i = 0 ; i < listPixelStart.size() ; i++ ){
    		g.fillRect(listPixelStart.get(i),0,listPixelStop.get(i),height);
		}
		g.setColor(Color.BLACK);
		this.setFont(new Font("Arial", Font.BOLD, 8));
		
		//le texte est placé au milieu du bouton
		g.drawString( idCDS, width/2 - textWidth/2 , 3*height/4 ) ;
    }
}
