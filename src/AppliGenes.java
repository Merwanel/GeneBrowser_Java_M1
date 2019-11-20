/**
 *
 * 
 * @author Merwan
 * @purpose Projet
 *
 */


import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
 

 
/**
 * Dans cette classe est crée le hash genome; le cadre est affiché ainsi que la légende.
 * C'est ici que se trouve le main.
 * @author EL ASRI Merwan
 */
public class AppliGenes extends JFrame {
	
	
	/**
	 * largeur du cadre
	 */
	private final int X_MAX = 1000 ;
	
    /**
     * longueur du cadre
     */
    private final int Y_MAX = 600 ;
    
    

	/**
	 * En créant une instance AppliGenes la fenetre principale est crée avec un menu
	 * @param fileName	nom de la fenetre principale
	 */
	public AppliGenes(String fileName) {
		
		super(fileName) ;
		
	    setPreferredSize(new java.awt.Dimension(X_MAX, Y_MAX + 13));
		new Menu(this , X_MAX , Y_MAX) ;
		
	    pack() ;
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE); // quand on ferme la fenêtre, le programme s'arrête
		setResizable(false);// la taille de la fenêtre ne peut plus être modifié 

	}


	
    /**
     * initialise AppliGenes.
     * @param args	nom du fichier à parser
     */
    public static void main(String[] args) {

    	new AppliGenes("Allez dans Menu->Open  et choissisez un fichier GFF3") ;

    }
    
    /**
     * Affiche une légende
     * @see Menu#Menu
     */
    public static void legend() {
    	JFrame legendFrame = new JFrame("Code couleur") ;
    	
    	/**
    	 * Juste pour voir comment ça marche, une classe anonyme est utilisée.
    	 */
    	JPanel legendPanel = new JPanel() {
    		
    		/**
    		 * dessine les rectangles et strings qui composent la légende
    		 */
    		@Override
    		public void paintComponent(Graphics g) {
    			
    			// dessin des rectangles
    			g.setColor(Color.GREEN) ;
    			g.fillRect(0, 5, 40, 20);
    			g.setColor(Color.PINK) ;
    			g.fillRect(0, 40, 40, 20);
    			g.setColor(Color.CYAN) ;
    			g.fillRect(0, 75, 40, 20);
    			g.fillRect(0, 110, 40, 3); // drawLine dessine des lignes trop fines
    			g.fillRect(0, 140, 40, 3);g.fillRect(0, 130, 15, 20); g.fillRect(30, 130, 10, 20);
    			g.setColor(Color.YELLOW) ;
    			g.fillRect(0, 165, 40, 20);
    			g.setColor(Color.LIGHT_GRAY) ;
    			g.fillRect(0, 200, 40, 20);
    			
    			// dessin des strings
    			g.setColor(Color.BLACK) ;
    			g.drawString("gene" , 60 , 15 ) ;
    			g.drawString("mRNA" , 60 , 55 ) ;
    			g.drawString("exon" , 60 , 90 ) ;
    			g.drawString("intron" , 60 , 120 ) ;
    			g.drawString("CDS" , 60 , 145 ) ;
    			g.drawString("search results" , 60 , 180 ) ;
    			g.drawString("other features" , 60 , 215 ) ;
    		}
    	};
    	legendFrame.setPreferredSize(new java.awt.Dimension(210, 260));
    	legendFrame.setLocation(1000,0 ) ;
    	legendFrame.add(legendPanel) ;
    	legendFrame.pack();
    	legendFrame.setVisible(true);
    }
 }



