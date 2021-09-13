    import java.awt.Color;
    import java.awt.Graphics;
    import javax.swing.JFrame;
    import javax.swing.JPanel;
    import java.awt.Graphics;
    import javax.swing.JFrame;
    import javax.swing.JPanel;

    public class AffichageGUI extends JPanel{
      Grille grille=new Grille();
      public AffichageGUI(){

      }
      public AffichageGUI(int hauteur, int largeur, int tunnels, int max, int bord){
        grille=new Grille(hauteur, largeur, tunnels, max, bord);
      }
      //methodes get
      public Grille getGrille(){
        return grille;
      }

    public void paint(Graphics g){    // Interface Graphique uitilisant java swing affiche obstacle , nourriture , et colonie

      int dx=200;
      int dy=200;
      int x;
      int y;

      for (int c = 0 ; c< grille.getHauteur();c++){
        for (int l=0 ; l< grille.getLargeur();l++){

          x = c*5;
          y = l*5;

          if(grille.getCases()[c][l] instanceof CaseNormale){
            if(((CaseNormale)grille.getCases()[c][l]).getNourriture()){
               g.setColor(Color.GREEN);
            }else{
              if (((CaseNormale)grille.getCases()[c][l]).getColony()){
                g.setColor(Color.RED);
              }else{
                g.setColor(Color.WHITE);
              }
            }
          }else{
            g.setColor(Color.BLACK);

         }

          g.fillRoundRect(y,x, dx, dy,1,1);
       }
     }
    }
    }
