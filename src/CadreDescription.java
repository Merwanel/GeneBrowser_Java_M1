import javax.swing.JTextArea ; /* JTextArea est utilisé plutôt que jlabel pour
pouvoir sauter des lignes et pouvoir sélectionner du texte */
import javax.swing.JFrame;

/**
 * @author EL ASRI Merwan
 * Crée une fenêtre contenant la description de la séquence cliquée par l'utilisateur.
 * 
 */
public class CadreDescription extends JFrame { 
	
	/**
	 * initialise une instance de la classe CadreDescription. 
	 * <p>
	 * La classe JTexTArea est utilisée pour que les retours à la ligne soient pris en compte
	 * (la classe JLabel ne les prends pas compte).
	 * @param clickedName	nom de la séquence sur laquelle l'utilisateur a cliqué
	 * @param description_wanted	description à afficher
	 */
	public CadreDescription(String clickedName , String description_wanted){
		super("Description de "+clickedName) ;
		JTextArea labelArea = new JTextArea(description_wanted) ;
		labelArea.setEditable(false) ;
		labelArea.setOpaque(false);
		add(labelArea) ;
		pack() ;
		setVisible(true) ;
	}
	
}	
