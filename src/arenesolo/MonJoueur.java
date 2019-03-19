package arenesolo;
/*
 * Code minimal pour cr√©er son propre joueur.
 */


import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import jeu.Plateau;
import jeu.astar.Node;

public class MonJoueur extends jeu.Joueur {

    

	//controle le nombre de passage dans la yourt
	int passagesDansYourt=0;
	
	// determine si le joueur est dans la yourt , et doit ou non y rester
	boolean estDansYourt=false;
	

    
    public MonJoueur(String nom) { super(nom); }

    @Override
    public Action faitUneAction(Plateau etatDuJeu) {

    	
    	int taillePlateau;  		//taille du plateau
    	int seuilVigeur;			//seuille de vigeur acceptable selon taille plateau 
    	int diviseur=1;
    	
  
    	// on optimise notre algo car apres de nombreux test 
    	//on s'est rendu compte que la meuilleur valeur du seuilVigueur est soit 'taillePlateau' diviser par 2 soit 'taillePlateau' sans etre diviser
		Random r = new Random();
		for(int i = 0 ; i<20 ; i++ ) {
			diviseur = r.nextInt(2) + 1 ;
		}
    	
		
		
		
		//recupÈration de la taille du plateau
    	taillePlateau = etatDuJeu.donneGrillePourAstar().length;
    	
    	//initialisation seuil de vigeur
    	seuilVigeur = taillePlateau/diviseur;    	
    	
    	//RÈcupÈration des fonctions utiles depuis la Class Utils
    	Utils utilitaire = new Utils();
    	
    	
    	//recupÈration des emplacements des champs, yourtes et joueurs
    	ArrayList<Node> listeChampAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_CHAMP);
    	ArrayList<Node> listeYourteAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_YOURTE);
    	ArrayList<Node> listeJoueurAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_JOUEUR);
    	
    	

    	
    	// Permet permet de rester dans une yourte 2 tours
    	if(estDansYourt) {
    		
    		if(passagesDansYourt==0) {
	    		passagesDansYourt=1;
	    		return Action.RIEN;
    		}
    		else{
    			passagesDansYourt=0;
    		}
    	}

	
	
    	if (this.donneVigueur() <= seuilVigeur)    					// en cas de niveau minimum on cherche des yourte en evitant les joueurs
		{
    		utilitaire.affiche("notre joueur a atteint un niveau de seuil de vigueur necessitant de chercher un Yourt en evitant les joueurs");
        	
    		// si on est dans le yourte
    		estDansYourt=true;
    		
    		// on met le max afin de pouvoir comparer les points obtenus
    		int plus_court_chemin = taillePlateau;
    		Point prochain_point = null;
    		
    		
    		// on itere sur notre liste d'objet
        	for(Node n : listeYourteAtteignable) {

        		Point point = new Point(n.getPosX(),n.getPosY());

            	ArrayList<Node> cheminAvecContrainteSupplementaires = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignable);
            	
            	int taille = etatDuJeu.donneGrillePourAstar().length;

            	ArrayList<Node> listeJoueurAtteignableComplet = utilitaire.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
           	
            	
            	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
  
            	
            	if (cheminAvecContrainteSupplementaires !=null && cheminAvecContrainteSupplementairesFinal!=null) {
                	  
		
		        	if ( cheminAvecContrainteSupplementairesFinal.size() < plus_court_chemin && cheminAvecContrainteSupplementairesFinal!=null)
		        	{
		        		plus_court_chemin = cheminAvecContrainteSupplementairesFinal.size();
		        		prochain_point = new Point(cheminAvecContrainteSupplementairesFinal.get(0).getPosX(),cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        	}
		        	
            	}
        	}
        	
        	if (prochain_point==null ||plus_court_chemin==100)
        		return super.faitUneAction(etatDuJeu);
        	
        	Action prochaine_action= utilitaire.deplacement(this.donnePosition(), prochain_point);
        	
        	if(prochaine_action !=null)
        		return prochaine_action;
        	else
        		return super.faitUneAction(etatDuJeu);

		}
    	// sinon par default on cherche a gagner des blobs/champs en evitant les joueurs
		else
		{
			
			// remet le compteur de passage dans yourt a 0
			passagesDansYourt=0;
			
			utilitaire.affiche("on cherche des champs");
        	
    		int plus_court_chemin = taillePlateau;
    		Point prochain_point = null;
    		
    		
    		
    		
    		ArrayList<Node> listeChampAtteignableEtranger = new ArrayList<Node>();
    		
    		
    		for(Node n : listeChampAtteignable) {
    			
    			int contenuCellule = etatDuJeu.	donneContenuCellule(n.getPosX(),n.getPosY());
    			
    			if( Plateau.contientUnChampQuiNeLuiAppartientPas(this,contenuCellule))
    				listeChampAtteignableEtranger.add(n);
    		}
    		
    		
        	for(Node n : listeChampAtteignableEtranger) {
        		
        		Point point = new Point(n.getPosX(),n.getPosY());

            	ArrayList<Node> cheminAvecContrainteSupplementaires = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignable);
            	
            	int taille = etatDuJeu.donneGrillePourAstar().length;

            	
            	ArrayList<Node> listeJoueurAtteignableComplet = utilitaire.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
           	

            	
            	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
            	

          
            	if (cheminAvecContrainteSupplementaires !=null && cheminAvecContrainteSupplementairesFinal!=null) {

		
		        	if ( cheminAvecContrainteSupplementairesFinal.size() < plus_court_chemin && cheminAvecContrainteSupplementairesFinal!=null)
		        	{
		        		plus_court_chemin = cheminAvecContrainteSupplementairesFinal.size();
		        		prochain_point = new Point(cheminAvecContrainteSupplementairesFinal.get(0).getPosX(),cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        	}
		        	
            	}
            	
            	
        	}
        	


        	if (prochain_point==null ||plus_court_chemin==100)
        		return super.faitUneAction(etatDuJeu);
        	
        	
        	Action prochaine_action= utilitaire.deplacement(this.donnePosition(), prochain_point);
        	
        	if(prochaine_action !=null)
        		return prochaine_action;
        	else
        		return super.faitUneAction(etatDuJeu);

    		
    	}
    	
    
    }
}