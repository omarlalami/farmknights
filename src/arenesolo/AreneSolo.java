package arenesolo;
/*
 * Programme minimal pour lancer une partie en solo avec son propre joueur.
 */



import jeu.Plateau;
import jeu.MaitreDuJeuFK;
import gui.FenetreDeJeuFK;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;



import jeu.Joueur;
import jeu.astar.Node;

public class AreneSolo {
	public static final String PLATEAU_FK_PETIT = 
            "+----------------+\n"+
            "|$$  F-  @2  $$  |\n"+
            "|  @1    F-  @3$-|\n"+
            "|##  ##  ##  ##  |\n"+
            "|  ##  ##  ##  ##|\n"+
            "|                |\n"+
            "|  @4    F1      |\n"+
            "|              F3|\n"+
            "|  ##  ######    |\n"+
            "+----------------+";
    
    public static void main(String[] args) {
        //Plateau p = new Plateau( 1200, PLATEAU_FK_PETIT);
        Plateau p = new Plateau( 1200, MaitreDuJeuFK.PLATEAU_FK_DEFAUT);
        MaitreDuJeuFK jeu = new MaitreDuJeuFK(p);
        jeu.metJoueurEnPosition(0, new MonJoueur("Moi"));
        FenetreDeJeuFK f = new FenetreDeJeuFK(jeu, true, true);
        f.log = new java.io.File("/tmp/toto.log");
        
        f.addWindowListener( new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) { }

            @Override
            public void windowClosing(WindowEvent e) { }

            @Override
            public void windowClosed(WindowEvent e) { System.exit(0 );}

            @Override
            public void windowIconified(WindowEvent e) {  }

            @Override
            public void windowDeiconified(WindowEvent e) { }

            @Override
            public void windowActivated(WindowEvent e) { }

            @Override
            public void windowDeactivated(WindowEvent e) { }
        });
        
        /* Code facultatif pour récupérer les clics souris sur le plateau
         * Pour interagir avec votre algo pendant la phase de développement.
         * Par exemple : */
            f.setMouseClickListener( (int x, int y, int bt) -> {
              Joueur j = p.donneJoueur(p.donneJoueurCourant());
              ArrayList<Node> a = p.donneCheminEntre(j.donnePosition(), new Point(x,y));
              f.afficheAstarPath(a);
            });
        
        java.awt.EventQueue.invokeLater(() -> { f.setVisible(true); });
    }
    
}