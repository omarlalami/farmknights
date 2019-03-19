package arenesolo;
/*
 * Code minimal pour créer son propre joueur.
 */


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;

import jeu.Plateau;
import jeu.astar.Node;

public class MonJoueur extends jeu.Joueur {
	
	public void affiche(String msg){
		System.out.println(msg);
	}
    
    public MonJoueur(String nom) { super(nom); }
    
    @Override
    public Action faitUneAction(Plateau etatDuJeu) {

    	
    	
    	// ici on fait un test si on est dans une yourt on reste dessus juska atteindre  vigueur_max <= this.vieguer+5
    	
    	
    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+etatDuJeu.donneGrillePourAstar().length);

    	int vigueur_max;
    	int taillePlateau;  				//taille du plateau lxH
    	int seuilVigeur;			//seuille de vigeur acceptable selon taille plateau 
    	int seuilVigeurUrgence;			//seuille de vigeur d'urgence selon taille plateau 
    	
    	
    	taillePlateau = etatDuJeu.donneGrillePourAstar().length;

    	vigueur_max=100;
    	seuilVigeur = taillePlateau;
    	seuilVigeurUrgence = taillePlateau/2; 
    	
    	
    	System.out.println("mon nom : " +this.donneNom());
    	//System.out.println();
    	
    	
    	
    	Utils utilitaire = new Utils();
    	
    	ArrayList<Node> listeChampAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_CHAMP);
    	ArrayList<Node> listeYourteAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_YOURTE);
    	ArrayList<Node> listeJoueurAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_JOUEUR);
    	
    	HashMap<Integer, ArrayList<Point>> listJoueurs = new HashMap<Integer, ArrayList<Point>>();
    	HashMap<Integer, ArrayList<Point>> listYourt = new HashMap<Integer, ArrayList<Point>>();
    	
    	/*
    	if( this.donneVigueur() <= seuilVigeurUrgence) {					// en cas d'urgence on cherche a gagner des pts vigueur avec des yourte ou joueurs (shifumi) peut importe
          	
    		affiche("notre joueur a atteint un niveau de seuil de vigueur necessitant de chercher un Yourt en n'evitant pas les joueurs");
    		
    		return utilitaire.ActionVigeurCritique(this.donnePosition(),listYourt, listJoueurs, taillePlateau, etatDuJeu);

    	}
    	
    	else*/ if (this.donneVigueur() <= seuilVigeur)    					// en cas de niveau minimum on cherche des yourte en evitant les joueurs
		{
    		affiche("notre joueur a atteint un niveau de seuil de vigueur necessitant de chercher un Yourt en evitant les joueurs");
        	
    		int plus_court_chemin = taillePlateau;
    		Point prochain_point = null;
    		
        	for(Node n : listeYourteAtteignable) {
        		System.out.println("yourt : "+n);
        		Point point = new Point(n.getPosX(),n.getPosY());

        		ArrayList<Node> cheminSansContrainte = etatDuJeu.donneCheminEntre(this.donnePosition(),point);
            	System.out.println(cheminSansContrainte);

            	ArrayList<Node> cheminAvecContrainteSupplementaires = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignable);
            	System.out.println(cheminAvecContrainteSupplementaires);
            	
            	int taille = etatDuJeu.donneGrillePourAstar().length;

            	
            	ArrayList<Node> listeJoueurAtteignableComplet = utilitaire.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
           	
            	System.out.println("**********");
            	
            	System.out.println("avant ajout obstacle : " +listeJoueurAtteignable);
            	System.out.println("apre ajout obstacle : " +listeJoueurAtteignableComplet);
            	
            	System.out.println("**********");
           	
            	
            	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
            	System.out.println("Chemin Yourt sans eviter joueur : >"+cheminAvecContrainteSupplementaires);    
            	System.out.println("Chemin Yourt evitant joueur : >>"+cheminAvecContrainteSupplementairesFinal);   
            	
            	if (cheminAvecContrainteSupplementaires !=null && cheminAvecContrainteSupplementairesFinal!=null) {
                	
		        	System.out.println("Chemin Yourt sans eviter joueur : >"+cheminAvecContrainteSupplementaires);    
		        	System.out.println("Chemin Yourt evitant joueur : >>"+cheminAvecContrainteSupplementairesFinal);    
		
		        	if ( cheminAvecContrainteSupplementairesFinal.size() < plus_court_chemin && cheminAvecContrainteSupplementairesFinal!=null)
		        	{
		        		plus_court_chemin = cheminAvecContrainteSupplementairesFinal.size();
		        		prochain_point = new Point(cheminAvecContrainteSupplementairesFinal.get(0).getPosX(),cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        		System.out.println("test : "+cheminAvecContrainteSupplementairesFinal.get(0).getPosX()+" "+cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
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
			
    		affiche("on cherche des champs");
        	
    		int plus_court_chemin = taillePlateau;
    		Point prochain_point = null;
    		
    		
    		
    		
    		ArrayList<Node> listeChampAtteignableEtranger = new ArrayList<Node>();
    		
    		
    		for(Node n : listeChampAtteignable) {
    			
    			int contenuCellule = etatDuJeu.	donneContenuCellule(n.getPosX(),n.getPosY());
    			
    			if( etatDuJeu.contientUnChampQuiNeLuiAppartientPas(this,contenuCellule))
    				listeChampAtteignableEtranger.add(n);
    		}
    		
    		
        	for(Node n : listeChampAtteignableEtranger) {
        		
        		System.out.println("champ : "+n);
        		Point point = new Point(n.getPosX(),n.getPosY());

        		ArrayList<Node> cheminSansContrainte = etatDuJeu.donneCheminEntre(this.donnePosition(),point);
            	System.out.println(cheminSansContrainte);

            	ArrayList<Node> cheminAvecContrainteSupplementaires = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignable);
            	System.out.println(cheminAvecContrainteSupplementaires);
            	
            	int taille = etatDuJeu.donneGrillePourAstar().length;

            	
            	ArrayList<Node> listeJoueurAtteignableComplet = utilitaire.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
           	
            	System.out.println("**********");
            	
            	System.out.println("avant ajout obstacle : " +listeJoueurAtteignable);
            	System.out.println("apre ajout obstacle : " +listeJoueurAtteignableComplet);
            	
            	System.out.println("**********");
           	
            	
            	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
            	
            	System.out.println("chemin avec contrainte null ? : "+cheminAvecContrainteSupplementaires);
            	System.out.println("chemin avec contrainte suplemntaire null ? : "+cheminAvecContrainteSupplementaires);
            	
            	
            	
            	if (cheminAvecContrainteSupplementaires !=null && cheminAvecContrainteSupplementairesFinal!=null) {
            	
		        	System.out.println("Chemin Yourt sans eviter joueur : >"+cheminAvecContrainteSupplementaires);    
		        	System.out.println("Chemin Yourt evitant joueur : >>"+cheminAvecContrainteSupplementairesFinal);    
		
		        	if ( cheminAvecContrainteSupplementairesFinal.size() < plus_court_chemin && cheminAvecContrainteSupplementairesFinal!=null)
		        	{
		        		plus_court_chemin = cheminAvecContrainteSupplementairesFinal.size();
		        		prochain_point = new Point(cheminAvecContrainteSupplementairesFinal.get(0).getPosX(),cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        		System.out.println("test : "+cheminAvecContrainteSupplementairesFinal.get(0).getPosX()+" "+cheminAvecContrainteSupplementairesFinal.get(0).getPosY());
		        	}
		        	
            	}
            	
            	
        	}
        	
        	System.out.println("!!!!!!!!!!!!!!!!!!! plus court chemin : "+plus_court_chemin);
        	System.out.println("!!!!!!!!!!!!!!!!!!! prochain point : "+prochain_point);
        	
        	System.out.println("test point : "+prochain_point);
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