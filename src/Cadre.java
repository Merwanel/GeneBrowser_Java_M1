import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font ;
import java.util.ArrayList ;
import java.util.Iterator ;
import java.util.LinkedHashMap ;
import java.util.Map ;
import java.util.Map.Entry;



/**
 * Crée la mainWindow principale de l'application.
 * <p>
 * Lorsqu'un gene est cliqué, crée aussi la fenêtre de description et 
 * la fenêtre des produits du gène quand l'utilisateur clique sur un gène
 * @author EL ASRI Merwan
 *
 */
public class Cadre extends JPanel { 
	
	/**
	 * Bloc d'attribut décrivant les séquences à représenter
	 */
	private final int NB_NU_TOT ;//nombre de nucléotide total dans le génome
    private long nbNuToDisplay  ; // nombre de nucléotide à afficher  // long parce que nbNuToDisplay * X_MAX peut dépasser la valeur max des int
	private Sequence  seq  ;   // objet représentant le concept de séquence
	private int infAA ; // premier nucléotide à afficher
	private int supAA ; // dernier nucléotide à afficher
	private LinkedHashMap<String, Sequence> genome ; // objet de la classe LinkedHashMap 
													//	ayant pour clés les identifiants des séquences et comme valeurs les Sequences.

	/**
	 * Bloc d'attribut servant à placer les boutons représentant les séquences 
	 */
	private final int Y_PIXEL_MIN = 30 ; // ordonnée minimale partir de laquelle les séquences sont affichées
    private int H = 10 ; // Hauteur des boutons repésentant les séquences
    private int xPixelInf ;// Borne infèrieur du rectangle à dessiner, en pixel
    private int xPixelSup = 0;// Borne supèrieur du rectangle à dessiner, en pixel
    private int xPixelSupP = 0 ;// Borne supèrieur du rectangle précédemment dessiné, en pixel
    private int yPixelInf = Y_PIXEL_MIN ; // Borne infèrieur du rectangle à dessiner, en pixel
	private int yPixelInfFils ; // attribut servant à placer les séquences dans la fenêtre contenant les produits du gène cliqué

	/**
	 * Bloc d'attribut à propos des boutons en bas de la mainWindow et des actions associées
	 */
	private final int H_BOUTONS = 40 ; // Hauteur des boutons en bas de la mainWindow
	private String description_wanted = "default" ; // description prete à etre affichée, de la séquence sur laquelle l'utilisateur a cliqué
	private boolean isSeqClicked = false ; // true si une séquence a été cliqué
	private String clickedID = "" ; // identifiant de la séquence sur laquelle l'utilisateur a cliquée
    private String patternSearched = "Searched" ; // pattern entré dans la barre de recherche
    private ArrayList<String> resultSearch = new ArrayList<String>() ; // liste des identifiants matchant le pattern recherché
    
    /**
     * Bloc d'attribut définissant les bornes en pixel des fenêtres
     */
    private final int Y_MAX ; // hauteur en pixel de la mainWindow
	private final long X_MAX ; // largeur en pixel de la mainWindow
	private final long Y_MAX_3E_FRAME = 600 ; // hauteur en pixel de la fenêtre contenant les produits du gène cliqué
	private final long X_MAX_3E_FRAME = 700 ; // largeur en pixel de la fenêtre contenant les produits du gène cliqué
	
    /**
     * Bloc d'attribut pour dessiner le rectangle jaune et savoir où zoomer
     */
    private int xPixelPressed   ; // abscisse du pixel pressé sur la mainWindow
    private int xPixelDragged  ; // abscisse du pixel où se trouve la souris alors que son clic est maintenu, sur la mainWindow
    private boolean isMouseDragged = false ; // true si le clic de la souris continu d'etre pressé
    private boolean isMouseReleased = false ; // true si la souris a été pressé puis relaché, false sinon	
    private int xPixelReleased  ; // abscisse du pixel où le clic de la souris a été relaché sur la mainWindow
    
    
    /**
     * Crée une instance de la classe Cadre.
     * @param nbNuToDisplay	nombre de nucléotide à afficher
     * @param genome objet de la classe LinkedHashMap ayant pour clés les identifiants des séquences et comme valeurs les Sequences
     * @param X_MAX	largeur en pixel de la mainWindow
     * @param Y_MAX	hauteur en pixel de la mainWindow
     * @param infAA	position minimal en nucléotide à partir de laquelle les séquences sont affichées
     * @param supAA	position maximal en nucléotide au delà duquelle les séquences ne sont plus affichées
     */
    public Cadre( int nbNuToDisplay ,LinkedHashMap<String, Sequence> genome , int X_MAX , int Y_MAX , int infAA , int supAA  ) {
    	super() ;
    	this.NB_NU_TOT = nbNuToDisplay;
    	this.nbNuToDisplay = nbNuToDisplay ;
    	this.genome = genome ;
    	this.X_MAX = X_MAX ;
    	this.Y_MAX = Y_MAX ;
    	this.infAA = infAA ;
    	this.supAA = supAA ;
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	/**
    	 * instructions utiles après un appel de repaint()
    	 */
    	removeAll() ; // supprime tout, les composants sur JPanel, dans le cas où la fonction repaint() est appelée
    	updateBorneForYellowZoom();// mise à jour les valeurs infAA et supAA
    	
    	// Le premier nucléotide à afficher ne peut pas être infèrieur à 0
    	// et le dernier nucléotide à afficher ne peut pas être supèrieur à NB_NU_TOT 
    	if(supAA > NB_NU_TOT )	supAA = NB_NU_TOT ;
    	if(infAA < 0 )	infAA = 0 ;
    	nbNuToDisplay = supAA - infAA ; // mise à jour du nombre de nucléotide à afficher 
    	
    	/**
    	 * Gros blocs ajoutant les boutons et entrées de textes situées en bas 
    	 * de la mainWindow ainsi que les listeners nécessaires.
    	 */
    	// ajout de listeners pour les clics de la souris
		addMouseListener(new MouseAdapter(){ 
         	public void mousePressed(MouseEvent e){
         		
				xPixelPressed = e.getX() ;
				
        	}
        	public void mouseReleased(MouseEvent e){
            	xPixelReleased =  e.getX() ;
				if (isMouseDragged == true){
					isMouseReleased = true ;
					isMouseDragged = false ;
            		repaint() ;
				}	
        	}
        });
        
        addMouseMotionListener(new MouseAdapter(){ 
        	public void mouseDragged(MouseEvent e){
				xPixelDragged = e.getX() ;
				isMouseDragged = true ;
		        repaint(); 
        	}
        });
        
        //
        
		drawRectFollowingDrag(g);
                
        //////// Entrées de Texte:  Choix des bornes et fonction recherche
        /// Choix des bornes :
		// Initialisations du texte aux valeurs courantes des positions des nucléotides inf et sup
        JTextField firstNuText = new JTextField(String.valueOf(infAA)); 
        JTextField lastNuText = new JTextField(String.valueOf(supAA));
		
		firstNuText.setBounds(50, Y_MAX - 2*H_BOUTONS ,100,H_BOUTONS);
		lastNuText.setBounds(200, Y_MAX - 2*H_BOUTONS ,100,H_BOUTONS);
		
		firstNuText.addActionListener(new ActionListener() {
			@Override
      		public void actionPerformed(ActionEvent e) {
				try {
					int newInfAA = Integer.valueOf(firstNuText.getText()) ;
					int newSupAA = Integer.valueOf(lastNuText.getText()) ;
					if (newInfAA > newSupAA)	throw new FirstNuBiggerThanLastException(newInfAA, newSupAA) ;
					else {
						infAA = newInfAA ;
						supAA = newSupAA ;
						repaint() ;
					}
				} catch (NumberFormatException | FirstNuBiggerThanLastException except) {
					
					Menu.errorMessage(Menu.mainWindow, except);
				}
      		}
    	});
    	lastNuText.addActionListener(new ActionListener() {
			@Override
      		public void actionPerformed(ActionEvent e) {
				try {
					int newInfAA = Integer.valueOf(firstNuText.getText()) ;
					int newSupAA = Integer.valueOf(lastNuText.getText()) ;
					// le premier nucléotide doit être 
					if (newInfAA > newSupAA)	throw new FirstNuBiggerThanLastException(newInfAA, newSupAA) ;
					else {
						infAA = newInfAA ;
						supAA = newSupAA ;
						repaint() ;
					}
				} catch (NumberFormatException | FirstNuBiggerThanLastException except) {

					Menu.errorMessage(Menu.mainWindow, except);
				}
      		}
    	});
    	
		add(firstNuText) ;
		add(lastNuText) ;

		
		/// Fonction de recherche
		JTextField searchEntry = new JTextField(patternSearched) ; 

		searchEntry.setBounds(700, Y_MAX - 2*H_BOUTONS ,100,H_BOUTONS);
    	searchEntry.addActionListener(new ActionListener() {
			@Override
      		public void actionPerformed(ActionEvent e) {
				patternSearched = searchEntry.getText() ;
			 	resultSearch = Parser.search(  patternSearched , genome);
			 	if (resultSearch.size() == 0 )	patternSearched = "Not Found" ;
				else{
					infAA = genome.get(resultSearch.get(0)).getStart() ;
					supAA = genome.get(resultSearch.get(resultSearch.size() - 1) ).getStop() ;
				}
				repaint() ;
      		}
    	});
		add(searchEntry) ;
		
		// Bloc d'instruction ajoutant les boutons pour les bouton de ZOOM.
		JButton zoomIn = new JButton("+");    
		JButton zoomOut = new JButton("-");
		zoomIn.setBounds(400,Y_MAX - 2*H_BOUTONS,45, H_BOUTONS);
		zoomOut.setBounds(460,Y_MAX - 2*H_BOUTONS,45, H_BOUTONS);
		
		zoomIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				infAA = supAA/4 + 3*infAA/4 ;
				supAA = 3*supAA/4 + infAA/4  ;
				repaint() ; 
			}          
		});
		zoomOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				infAA = infAA - (int) nbNuToDisplay  ;
				supAA = supAA + (int ) nbNuToDisplay  ;
				if(supAA > NB_NU_TOT  ){ supAA =  NB_NU_TOT  ; }
				if(infAA < 0 ){ infAA = 0 ; }
				repaint();
			}          
		});
		add(zoomIn) ;
		add(zoomOut) ;
		
		g.drawString("From:", 5, Y_MAX - 55);
		g.drawString("To:", 170, Y_MAX - 55);
		g.drawString("nucleotides", 300, Y_MAX - 55);
        
        
        // Bloc d'instruction ajoutant les boutons pour les translations.
        JButton transGauche = new JButton("<-");
        JButton transDroite = new JButton("->");    
		transGauche.setBounds(540,Y_MAX - 2*H_BOUTONS,55, H_BOUTONS);
		transDroite.setBounds(600,Y_MAX - 2*H_BOUTONS,55, H_BOUTONS);
		
		transGauche.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				supAA = infAA ;
				infAA -= nbNuToDisplay  ;
				if(infAA < 0 ){ 
					infAA = 0 ;
					supAA = (int) nbNuToDisplay;
				}
				repaint() ; 
			}          
		});
		
		transDroite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("\nAVANT infaa= "+infAA+" supaa= "+supAA+" nbNuToDisplay= "+nbNuToDisplay+"NB_NU_TOT= "+NB_NU_TOT) ;

				infAA = supAA ;
				supAA += nbNuToDisplay  ;
				if(supAA > NB_NU_TOT  ){ 
					supAA =  NB_NU_TOT  ;
					infAA = NB_NU_TOT - (int) nbNuToDisplay ;
				}
				System.out.println("\nAPRES infaa= "+infAA+" supaa= "+supAA+" nbNuToDisplay= "+nbNuToDisplay+"NB_NU_TOT= "+NB_NU_TOT) ;
				repaint() ; 
			}          
		});
		
		add(transGauche) ;
		add(transDroite) ;
		
        
        // dessin de l'échelle
		drawScale(g , X_MAX , infAA , nbNuToDisplay , Y_MAX);
        
        Iterator<Entry<String, Sequence>> itr = this.genome.entrySet().iterator();
        while(itr.hasNext()) {
			Map.Entry<String ,Sequence> mapentry = itr.next();
			seq = mapentry.getValue() ;
			drawGene();
		}
        
        seqClicked();  // affichage de la description de la séquence et de ses produits dans le cas d'un gene 
        
		yPixelInf= Y_PIXEL_MIN ; //Réinitialisation de l'ordonnée à laquelle les séquences commencent à etre placé
		// c'est utile pour la prochaine fois que repaint() est appelée
    }


	/**
	 * Affiche le cadre de description de la séquence cliqué.
	 * <p>
	 * Dans le cas où cette séquence est un gene, la fonction affiche aussi la fenetre contenant les produits du gène cliqué 
	 */
	private void seqClicked() {
		if (isSeqClicked ){
			isSeqClicked = false ;
			JFrame cadreDescription = new CadreDescription(clickedID , description_wanted) ;
			Sequence seqClicked = genome.get(clickedID) ;
			cadreFils(cadreDescription, seqClicked);
        }
	}





    
	/**
	 * Crée le cadre contenant les descendants du gène cliqué
	 * @param cadreDescription	fenêtre dans lequel la description du gene est affichée
	 * @param seqClicked	Sequence sur lequel l'utilisateur a cliqué
	 * @see Cadre#makeSequenceIn3eFenetre(JPanel, Sequence, int, LinkedHashMap, long, long, long)
	 */
	private void cadreFils(JFrame cadreDescription, Sequence seqClicked) {
		if(seqClicked.getType().contains("gene")){
			JFrame thirdFrame = new JFrame("Produit(s) de "+clickedID);
			thirdFrame.setPreferredSize(new java.awt.Dimension((int) X_MAX_3E_FRAME,  (int) Y_MAX_3E_FRAME)) ;
			thirdFrame.setLocation( cadreDescription.getWidth()+cadreDescription.getX() ,0 ) ;
			JPanel panOfFrame3 = new JPanel() ;
			panOfFrame3.setLayout(null);
			yPixelInfFils = 0;
			makeSequenceIn3eFenetre( panOfFrame3 , seqClicked , H , genome 
					, Long.valueOf(seqClicked.getStop() - seqClicked.getStart()) , X_MAX_3E_FRAME-30 , Long.valueOf(seqClicked.getStart()) ) ;
			thirdFrame.add(panOfFrame3) ;
			thirdFrame.pack();
			
			thirdFrame.setVisible(true) ;
		}
	}


	
	/**
	 * Dessine une échelle.
	 * @param g	pinceau du cadre
	 * @param xPixelMax	largeur du cadre en pixel
	 * @param infAA	position minimal en nucléotide à partir de laquelle les séquences sont affichées
	 * @param nbNuToDisplay	nombre  de nucléotide à afficher
	 * @param yPixelMax	hauteur du cadre en pixel
	 */
	private void drawScale(Graphics g , long xPixelMax , int infAA 
		, long nbNu , int yPixelMax) {
		for(int i = 0; i <X_MAX ; i+= X_MAX / 10){
			g.setColor(Color.gray); 
			g.drawString( String.valueOf(infAA + (nbNu )  * i / X_MAX) , i , 10 ) ;
			g.drawLine( i , 0 , i , Y_MAX - 2*H_BOUTONS) ; // Lignes verticales
		}
        g.setColor(Color.black);
        g.drawLine( 0 , 10 , (int) X_MAX , 10 ) ;
	}


	/**
	 * Met à jour infAA et supAA les bornes nucléotidiques, suite à un zoom effectué en dessinant un rectangle jaune.
	 */
	private void updateBorneForYellowZoom() {
		if (isMouseReleased == true){
			if( xPixelPressed < xPixelReleased ){
				int infAA_old = infAA ;
		    	infAA = (int) (infAA+Long.valueOf( ( xPixelPressed * nbNuToDisplay)) / X_MAX ) ;
		    	supAA = (int)( infAA_old+Long.valueOf( ( xPixelReleased * nbNuToDisplay)) / X_MAX );
	    	}
        	isMouseReleased = false ;
    	}
	}


	/**
	 * Dessine un gene.
	 */
	private void drawGene() {
		if(seq.getType().contains("gene")){
			// Calcul des position en pixel de la séquence
			xPixelInf = calculXPixelInf( X_MAX , Long.valueOf(seq.getStart()) , infAA , nbNuToDisplay) ;
			xPixelSupP = xPixelSup ; 
			xPixelSup = calculXPixelSup( X_MAX , Long.valueOf(seq.getStop())  , infAA , nbNuToDisplay , xPixelInf) ;
			
			
			if( xPixelSup >= 0 && xPixelInf <= X_MAX) { 
				// si une partie du bouton seulement est dans la fênetre, alors les positions sont changé pour qu'l rentre complétement
				if( xPixelSup > X_MAX ){
					xPixelSup = (int) X_MAX ;
				}
				if( xPixelInf < 0 ){
					xPixelInf = 0 ;
				}
				// Si les séquences se chevauchent, un décallage est fait pour distinguer les boutons
				if( xPixelSupP >= xPixelInf ){
					yPixelInf += H ;
					if( yPixelInf + H > (Y_MAX - 2* H_BOUTONS) ){
						// une hauteur de 2* H_BOUTONS est laissé en bas de la mainWindow pour les boutons et entrées de textes
						yPixelInf = Y_PIXEL_MIN ;
					}
				}
				JButton button= new JButton(seq.getId()) ;
				makeClikableSequence ( this , seq , xPixelInf , yPixelInf , xPixelSup , H , button ) ;
			}
		}
	}


	/**
	 * Suite à un clic de l'utilisateur, et ce jusqu'à ce que le clic est relaché:
	 * Dessine un rectangle jaune d'une hauteur prédéfinit et dont la
	 * largeur suit les mouvements de la souris.
	 * @param g
	 * @warning Le rectangle est dessiné seulement si xPixelPressed < xPixelDragged
	 */
	private void drawRectFollowingDrag(Graphics g) {
		if (isMouseDragged == true){
    		g.setColor(Color.yellow);
        	g.fillRect(xPixelPressed , 20 , xPixelDragged - xPixelPressed , Y_MAX - 2* H_BOUTONS - 20 ) ;
        }
	}
    
    
	/**
	 * Affiche la séquence et tous ses descendants. La fonction est itérative.
	 * <p>
	 * En considérant que la liste des fils de chaque gène puis la liste des fils
	 * de ces fils, cela forme des arbres dont les noeuds sont les identifiants des séquences,
	 * et dont la racine est un gène. Il y a autant d'arbre que de gène.
	 * <p>
	 * Pour un gène donné, la fonction parcours l'arbre de façon préfixe et affiche pour chaque
	 * noeud un bouton le représentant.
	 * @param panel objet JPanel dans lequel les séquences sont affichés
	 * @param seq	objet de la classe Sequence
	 * @param H	hauteur d'un bouton
	 * @param genome	hash contenant tout les noms des séquences
	 * @param nbNuToDisplay	nombre  de nucléotide à afficher
	 * @param X_MAX_3E_FRAME largeur de la fenêtre contenant les séquences fils du gène cliqué
	 * @param infAA	position minimal en nucléotide à partir de laquelle les séquences sont affichées
	 */
	private void  makeSequenceIn3eFenetre( JPanel panel , Sequence seq , int H , LinkedHashMap<String, Sequence> genome ,
			long nbNuToDisplay , long X_MAX_3E_FRAME , long infAA) {
		ArrayList<String> listFils = (ArrayList<String> ) seq.getFils() ; 
		JButton sequenceButton ;
		ArrayList<Integer> listPixelStart = new ArrayList<Integer>() ; // dans le cas d'un cds liste des starts du CDS en pixels 
		ArrayList<Integer> listPixelStop = new ArrayList<Integer>() ;
		Sequence seqFils ;
		int xInf = calculXPixelInf( X_MAX_3E_FRAME , Long.valueOf(seq.getStart()) , infAA , nbNuToDisplay)  ;
		int xPixelSup = calculXPixelSup( X_MAX_3E_FRAME , Long.valueOf(seq.getStop()) , infAA , nbNuToDisplay , xInf) ;
		
		if( !seq.getType().equals("exon")){
			if(seq.getType().equals("CDS")){
				seq = (CDS) seq ;
				listPixelStart = computeListPixel( X_MAX_3E_FRAME  ,seq.getListStart() , infAA , nbNuToDisplay ) ;
				listPixelStop = computeListPixel(X_MAX_3E_FRAME  , seq.getListStop(), infAA , nbNuToDisplay) ;
				xInf = listPixelStart.get(0) ;
				xPixelSup = listPixelStop.get(listPixelStop.size()-1) ;
				sequenceButton = new CDSButton(seq.getId() , xPixelSup - xInf ,  H , listPixelStart , listPixelStop,
						 isSequenceContainedInSearchResults( seq.getId() , resultSearch) )  ;

			}
			else{
				sequenceButton = new JButton( seq.getId());
			}
			makeClikableSequence( panel , seq ,xInf , yPixelInfFils , xPixelSup , H , sequenceButton) ;
			yPixelInfFils += H ;	
			
		}
		Iterator<String> itr = listFils.iterator();
		while(itr.hasNext()) {
			String seqName = itr.next();
			seqFils = genome.get(seqName) ;
			
			makeSequenceIn3eFenetre( panel , seqFils  , H , genome , nbNuToDisplay , X_MAX_3E_FRAME , infAA ) ;
		}
    }
    
	
    /**
     * Un CDS est caractérisée par une liste de starts et une liste de stops correspondant aux exons;
     * cette fonction sert à calculer la position en pixel de toutes les valeurs de ces listes.
     * @param xPixelMax	largeur de la fenêtre contenant les séquences fils du gène cliqué
     * @param list	ArrayList contenant pour un CDS la liste de starts ou de stops 
     * @param infAA	position minimal en nucléotide à partir de laquelle les séquences sont affichées
     * @param nbNuToDisplay	nombre de nucléotide à afficher
     * @return	un ArrayList contenant les positions en pixel correspondant à l'attribut list.
     */
    private ArrayList<Integer> computeListPixel(long xPixelMax  ,ArrayList<Integer> list, long infAA , long nbNu){
    		 
		ArrayList<Integer> listPixel = new ArrayList<Integer>() ;
    	for(int i = 0 ; i < list.size() ; i++){
    		listPixel.add(calculXPixelInf( xPixelMax , list.get(i) , infAA , nbNu)) ;
    	}
    	return listPixel ;
    }
    

    /**
     * Cette fonction ajoute l'actionListener au bouton, décide de la couleur et l'ajoute
     * au panel.
     * <p>
     * L'actionListener est configuré tel que à chaque clic sur le bouton, l'identifiant de
     * la séquence est récupérée dans l'attribut clickedID et la description à affcicher dans
     * l'attribut description_wanted.
     * @param panel	instance JPanel dans lequel ajouter le bouton
     * @param seq	objet de la classe Sequence qu'il faut représenter par un bouton
     * @param xPixelInf	abscisse infèrieur du bouton à afficher
     * @param yPixelInf ordonnée infèrieur du bouton à afficher
     * @param xPixelSup	abscisse supèrieur du bouton à afficher
     * @param H	Hauteur du bouton
     * @param sequenceButton	Bouton à afficher
     */
    private void makeClikableSequence ( JPanel panel , Sequence seq , int xPixelInf , int yPixelInf , int xPixelSup , int H , JButton sequenceButton){
    		
		sequenceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				description_wanted = e.getActionCommand() ;
				JButton clickedButton = (JButton) e.getSource() ;
				clickedID = clickedButton.getText() ;
				isSeqClicked = true ;
				repaint() ; 
			}          
		});
		sequenceButton.setActionCommand(seq.makeDescription()) ;
		
		if( isSequenceContainedInSearchResults( seq.getId() , resultSearch) )	sequenceButton.setBackground(Color.YELLOW ); 
		else	sequenceButton.setBackground( decideColor(seq.getType()) ); 
		sequenceButton.setFont(new Font("Arial", Font.BOLD, 8));
		sequenceButton.setBounds(  xPixelInf , yPixelInf , xPixelSup - xPixelInf , H  ) ;   
		
		panel.add(sequenceButton) ;
    }
    
    
    /**
    * Retourne la couleur voulue du bouton d'apès le type de la séquence passé en paramètre.
    * Retourne la couleur verte si la séquence est un gène.
    * Retourne la couleur rose si la séquence est un mRNA.
    * Retourne la couleur gris clair sinon.
    * <p>
    * <b> Attention : </b>la couleur des types "CDS" est décidé dans la classe CDSButton
    * @param	type	type de la séquence: "gene" ou "mRNA" , ect
    * @return		la couleur voulue du bouton
    */
    
    private Color decideColor( String type ){
    
    	if( type.contains("gene") ){
    		return Color.GREEN ;
    	}
    	else if( type.equals("mRNA") ){
    		return Color.PINK ;
    	}
    	else{
    		return Color.LIGHT_GRAY ;
    	}
    } 
    
    
    /**
     * Retourne l'abscisse infèrieure du bouton.
     * @param xPixelMax	largeur de la fenêtre 
     * @param start	position du premier nucléotide de la séquence
     * @param infAA	position minimal en nucléotide à partir de laquelle les séquences sont affichées
     * @param nbNuToDisplay	nombre de nucléotide à afficher
     * @return	l'abscisse infèrieure du bouton
     */
    private int calculXPixelInf( long xPixelMax , long start , long infAA , long nbNuToDisplay){
    	return (int) (xPixelMax * (start - infAA) / (nbNuToDisplay)) ;
	}
	
	
	/**
	 * Retourne l'abscisse supèrieur du bouton.
	 * <p>
	 * Si l'abscisse infèrieur est égale à l'abscisse supèrieur alors ce dernier est augmenté de 1,
	 * sinon le bouton aura une largeur de 0 pixel et ne sera pas affiché
	 * @param xPixelMax	largeur de la fenêtre
	 * @param stop	position du dernier nucléotide de la séquence
	 * @param infAA	position minimal en nucléotide à partir de laquelle les séquences sont affichées
	 * @param nbNuToDisplay	nombre de nucléotide à afficher
	 * @param xPixelInf	abscisse infèrieure du bouton.
	 * @return		l'abscisse supèrieur du bouton
	 */
	private int calculXPixelSup( long xPixelMax , long stop , long infAA , long nbNu , int xPixelInf){
    	int xPixelSup = (int) (xPixelMax * (stop - infAA) / (nbNu )) ;
    	if (xPixelSup == xPixelInf){ xPixelSup++ ;}// sinon le bouton aura une largeur de 0 pixel et ne sera pas affiché
    	return xPixelSup ;
	}
	
	/**
	 * Retourne true si le String id est contenu dans l'ArrayList resultSearch et false sinon.
	 * @param id	String, identifiant de la séquence
	 * @param resultSearch	objet de la classe ArrayList contenant les id des séquences
	 * dont le contenu contient le pattern recherché. C'est le résultat de la fonction search(String, LinkedHashMap)
	 * @return		boolean, true si id est contenu dans resultSearch et false sinon.
	 * @see		Parser#search(String, LinkedHashMap)
	 */
	private boolean isSequenceContainedInSearchResults(String id , ArrayList<String> resultSearch ){
		boolean isSequenceContainedInSearchResults = false ;
		for(int i  = 0 ; i < resultSearch.size() ; i++) {
			if ( resultSearch.get(i).equals(id) ){
				isSequenceContainedInSearchResults = true ;
			}
		}
		return isSequenceContainedInSearchResults ;
	}
	

}


