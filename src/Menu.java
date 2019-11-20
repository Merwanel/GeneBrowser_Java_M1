import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe servant à créer le menu dans la mainWindow
 * @author melasri
 *
 */
public class Menu {

	public static JFrame mainWindow ;

	/**
	 * Crée un menu dans la mainWindow
	 * @param mainWindow	fenêtre principale dans laquelle va etre ajouté le menu
	 * @param X_MAX	largeur en pixel de la mainWindow
	 * @param Y_MAX	hauteur en pixel de la mainWindow
	 */
	public Menu( JFrame mainWindow , int X_MAX , int Y_MAX ) {
		
		Menu.mainWindow = mainWindow ;
		JMenuBar mb = new JMenuBar();  
		JMenu menu = new JMenu("Menu");
		
		JMenuItem openItem = new JMenuItem("Open"); 
		openItem.addActionListener(new  ActionListener()  {		
			@Override
	  		public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser("."); //current folder 
			    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			            "fichiers Gff3", "GFF3", "gff3");
			    fileChooser.setFileFilter(filter); 
			    // lorsque l'utilisateur ouvrira l'explorateur de fichier, 
			    // seul les fichiers ayant une extensions gff3 ou GFF3 seront affichées

			    int userChoice = fileChooser.showOpenDialog(mainWindow); 
				if (userChoice == JFileChooser.APPROVE_OPTION) { 
	            	String fileName = fileChooser.getSelectedFile().getAbsolutePath(); 

						newFileChosen(mainWindow , fileName , X_MAX , Y_MAX );
					
	            	
				}
			} 
		});
		JMenuItem legendItem = new JMenuItem("Code couleur"); 
		legendItem.addActionListener(new  ActionListener() {		
			@Override
	  		public void actionPerformed(ActionEvent ae) {AppliGenes.legend() ;}
		});
		
		menu.add(openItem) ;
		menu.add(legendItem) ;
		mb.add(menu) ;
		mainWindow.setJMenuBar(mb);  
	}

	

	/**
	 * Lance le parser et instancie un objet de la classe Cadre dans le cas où l'utilisateur a choisi un fichier.
	 * <p><b>Catch</b>	IOException	si le fichier n'a pas pu être lu ou si le fichier n'a pas été trouvé dans la méthode parser de la classe Parser
	 * <p><b>Catch</b>	BadNumberOfColonneException	si le nombre de colonne des séquences est différente de 9, dans la méthode parser de la classe Parser
	 * <p><b>Catch</b> GeneNotInOrderException	si les gènes ne sont pas classé dans l'odre croissant, selon leur start, dans la méthode parser de la classe Parser
	 * <p><b>Catch</b> StartPlusGrandQueStopException	si il existe sur une séquence un start supèrieur au stop
	 * <p><b>Catch</b>  NumberFormatException	si un des strings situés aux colonnes start et stop ne peut pas être convertit en entier, dans la méthode parser de la classe Parser
	 * @param mainWindow	fenêtre principale dans laquelle va etre ajouté le menu
	 * @param fileName	nom du fichier choisi par l'utilisateur
	 * @param X_MAX	largeur en pixel de la mainWindow
	 * @param Y_MAX	hauteur en pixel de la mainWindow
	 */
	private static void newFileChosen(JFrame mainWindow , String fileName ,  int X_MAX , int Y_MAX ){
		try {
			mainWindow.setTitle("WAIT..."); // message à l'utilisateur à travers le titre
			LinkedHashMap<String, Sequence>  genome = Parser.parser(fileName) ;// hash ayant pour clés les identifiants des séquences et comme valeurs les Sequences
		    int nbNuToDisplay = Parser.maxStop  ;// nombre de nucléotide dans le genome	à afficher
		    JPanel canvas = new Cadre( nbNuToDisplay , genome , X_MAX , Y_MAX , 0 , nbNuToDisplay) ;
			canvas.setSize(X_MAX, Y_MAX) ;
			mainWindow.getContentPane().removeAll();
			mainWindow.add(canvas) ;
			mainWindow.revalidate() ;
			mainWindow.repaint() ;
			mainWindow.setTitle(fileName);
		}
		
		catch(IOException | BadNumberOfColonneException | GeneNotInOrderException 
				| StartPlusGrandQueStopException | NumberFormatException e) {
			errorMessage(mainWindow, e);
        }

		
	}



	/**
	 * crée un message d'erreur si une exception est attrapée
	 * @param mainWindow	fenêtre principale
	 * @param e	exception attrapée
	 */
	public static void errorMessage(JFrame mainWindow, Exception e) {
		mainWindow.setTitle("WAIT..."); // message à l'utilisateur à travers le titre
		JFrame errorFrame = new JFrame("ERREUR") ;
		JDialog errorDialog = new JDialog(errorFrame, "ERREUR"); 
		JTextArea errorMessage = new JTextArea("\n\n"+e.getMessage()+"\n\n") ;
		errorMessage.setEditable(false) ;
		errorMessage.setOpaque(false);
		errorMessage.setFont(new Font("Arial", Font.BOLD, 15));
		errorDialog.add(errorMessage); 
		errorDialog.pack();
		errorDialog.setVisible(true); 
		mainWindow.setTitle("ERREUR"); // message à l'utilisateur à travers le titre
	}
	
	
	
}
