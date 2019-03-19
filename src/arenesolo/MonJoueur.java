package arenesolo;
/*
 * Code minimal pour créer son propre joueur.
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
    	
		
		
		
		//recup�ration de la taille du plateau
    	taillePlateau = etatDuJeu.donneGrillePourAstar().length;
    	
    	//initialisation seuil de vigeur
    	seuilVigeur = taillePlateau/diviseur;    	
    	
    	//R�cup�ration des fonctions utiles depuis la Class Utils
    	Utils utilitaire = new Utils();
    	
    	
    	//recup�ration des emplacements des champs, yourtes et joueurs
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

    	
		// en cas de niveau minimum on cherche des yourte en evitant les joueurs
	
    	if (this.donneVigueur() <= seuilVigeur)
		{
    		utilitaire.affiche("Notre joueur "+ this.donneNom()+"a atteint un niveau de seuil de vigueur necessitant de chercher un Yourt en evitant les joueurs");
        	
    		// si on est dans le yourte
    		estDansYourt=true;
    		
    		// on met le max afin de pouvoir comparer les points obtenus
    		int plus_court_chemin = taillePlateau;
    		Point prochain_point = null;
    		
    		
    		// on itere sur nos Yourtes atteignable
        	for(Node yourte : listeYourteAtteignable) {

        		// on convertie le Node en Point
        		Point yourt_cible = new Point(yourte.getPosX(),yourte.getPosY());
         	
            	int taille = etatDuJeu.donneGrillePourAstar().length;

        		// nous cherchons a eviter les joueurs dans notre chemin
            	ArrayList<Node> listeJoueurAtteignableComplet = utilitaire.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
           	
            	// on recupere notre chemin vers la yourte en evitant les joueurs
            	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), yourt_cible, listeJoueurAtteignableComplet);
  
            	
            	// si un chemin existe
            	if ( cheminAvecContrainteSupplementairesFinal!=null) {
                	  
            		// et si ce chemin est plus court que notre chemin par default, alors on le choisi
		        	if ( cheminAvecContrainteSupplementairesFinal.size() < plus_court_chemin)
		        	{
		        		plus_court_chemin = cheminAvecContrainteSupplementairesFinal.size();
		        		prochain_point = new Point(cheminAvecContrainteSupplementairesFinal.get(0).getPosX(),cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        	}
		        	
            	}
        	}
        	
        	// si aucun chemin n'a etais trouver, on se deplace aleatoirement sans verifier ce qu'il y'a autour
        	if (prochain_point==null)
        		return super.faitUneAction(etatDuJeu);
        	
        	
        	// on recupere la prochaine action qui nous permet de se deplace vers le prochain point
        	Action prochaine_action = utilitaire.deplacement(this.donnePosition(), prochain_point);
        	
        	//si cette action est non null, on execute l'action, sinon on se deplace aleatoirement sans verifier ce qu'il y'a autour,
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
			
			utilitaire.affiche("Notre joueur "+ this.donneNom()+" cherche des champs");
        	
    		// on met le max afin de pouvoir comparer les points obtenus
    		int plus_court_chemin = taillePlateau;
    		Point prochain_point = null;
    		
    		
    		
    		// la liste des champs n'appartenant a personne ou appartenant aux adversaires
    		ArrayList<Node> listeChampAtteignableEtranger = new ArrayList<Node>();
    		
    		// sur touts les champs du plateau atteignable, si un champ n'appartient pas au joueur on l'ajoute a notre liste
    		for(Node n : listeChampAtteignable) {
    			
    			int contenuCellule = etatDuJeu.donneContenuCellule(n.getPosX(),n.getPosY());
    			
    			if( Plateau.contientUnChampQuiNeLuiAppartientPas(this,contenuCellule))
    				listeChampAtteignableEtranger.add(n);
    		}
    		
    		// on itere sur les champs atteignable ne nous appartenons pas
        	for(Node champ : listeChampAtteignableEtranger) {
        		
        		// on convertie le Node en Point
        		Point point = new Point(champ.getPosX(),champ.getPosY());
            	
            	int taille = etatDuJeu.donneGrillePourAstar().length;

        		// nous cherchons a eviter les joueurs dans notre chemin
            	ArrayList<Node> listeJoueurAtteignableComplet = utilitaire.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);

            	// on recupere notre chemin vers le champ en evitant les joueurs
            	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
            	

            	// si un chemin existe
            	if (cheminAvecContrainteSupplementairesFinal!=null) {

            		// et si ce chemin est plus court que notre chemin par default, alors on le choisi
		        	if ( cheminAvecContrainteSupplementairesFinal.size() < plus_court_chemin)
		        	{
		        		plus_court_chemin = cheminAvecContrainteSupplementairesFinal.size();
		        		prochain_point = new Point(cheminAvecContrainteSupplementairesFinal.get(0).getPosX(),cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        	}
		        	
            	}
            	
            	
        	}
        	

        	// si aucun chemin n'a etais trouver, on se deplace aleatoirement sans verifier ce qu'il y'a autour
        	if (prochain_point==null)
        		return super.faitUneAction(etatDuJeu);
        	
        	// on recupere la prochaine action qui nous permet de se deplace vers le prochain point
        	Action prochaine_action= utilitaire.deplacement(this.donnePosition(), prochain_point);
        	
        	//si cette action est non null, on execute l'action, sinon on se deplace aleatoirement sans verifier ce qu'il y'a autour,
        	if(prochaine_action !=null)
        		return prochaine_action;
        	else
        		return super.faitUneAction(etatDuJeu);
    		
    	}
    
    }
}