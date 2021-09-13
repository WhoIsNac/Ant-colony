import javax.swing.*;

public class Test{
  public static void main(String[] args) {
    // AffichageTerminale a=new AffichageTerminale();
    // a.getGrille().placeNourriture(1);
    // a.affichageGrille(false);
    // System.out.println();

    // AffichageGUI a=new AffichageGUI(10,10,20,5,1);
    // // a.getGrille().placeNourriture(1);
    // // a.paint();
    // System.out.println();

    /*

    AffichageTerminale a = new AffichageTerminale();
    a.getGrille().placeNourriture(10);
    a.affichageModele(false);
    System.out.println();
    */
    // System.out.println("Test");
    //
    // AffichageTerminale a = new AffichageTerminale();
    // a.getGrille().placeNourriture(1,1);
    // for (int i=1;i<101;i++){
    //   System.out.println("Tour"+i);
    //   a.affichageModele();
    //   /*
    //   a.affichagePheroNid();
    //   a.affichagePheroBouffe();
    //   */
    // }
    SwingUtilities.invokeLater( () -> {
        new VueDebut();
    });
  }
}
