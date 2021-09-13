public class AffichageTerminale{

// TODO Creer la map avec l'utilisateur

// TODO Placement de la nourriture avec l'utilisateur

  Grille grille=new Grille();
  Modele m;
  public AffichageTerminale(){
    grille=new Grille();
    m = new Modele(this.grille, 50);
  }

  public AffichageTerminale(int hauteur, int largeur, int tunnels, int max, int bord){
    grille=new Grille(hauteur, largeur, tunnels, max, bord);
    m = new Modele(this.grille, 1);
  }

  public AffichageTerminale(Grille grille){
    this.grille=grille;
  }

  //methodes get
  public Grille getGrille(){
    return grille;
  }

  //affichage de la grille/map sur Terminale
  public void affichageGrille(){
    for(int i=0;i<grille.getHauteur();i++){
      for(int j=0;j<grille.getLargeur();j++){
        if(grille.getCases()[i][j] instanceof CaseNormale){
          if(((CaseNormale)grille.getCases()[i][j]).getNourriture()){
            System.out.print("·");
          }else{
            if(((CaseNormale)grille.getCases()[i][j]).getColony()){
              System.out.print("X");
            }else{
                System.out.print(" ");
            }
          }
        }else{
          System.out.print("\u2588");
        }
      }
      System.out.println();
    }
  }

  //affichage de la grille/map sur Terminale
    public void affichageModele(){
        m.updateAll();
        for(int i=0;i<grille.getHauteur();i++){
          for(int j=0;j<grille.getLargeur();j++){
            if(grille.getCases()[i][j] instanceof CaseNormale){
                if (((CaseNormale)grille.getCases()[i][j]).getNourriture()){
                    System.out.print("·");
                } else  if (((CaseNormale)grille.getCases()[i][j]).getColony() ){
                  System.out.print("X");
                } else if (m.isInsecteAtPos(i,j)){
                // voir si y'a une fourmie à cette position dans quel cas on affiche un emoji
                    System.out.print("F");
                } else{
                    System.out.print(" ");
                }
            }else{
              System.out.print("\u2588");
            }
          }
          System.out.println();
        }
    }
    //affichage de la map phermone sur Terminale
    public void affichagePheroNid(){
      for(int i=0;i<grille.getHauteur();i++){
        for(int j=0;j<grille.getLargeur();j++){
          if(grille.getCases()[i][j] instanceof CaseNormale){
            if(grille.getCasesAt(i,j).getNiveauDePheromoneChercheMaison()<10){
              System.out.print("0"+grille.getCasesAt(i,j).getNiveauDePheromoneChercheMaison());
            }else{
              System.out.print(grille.getCasesAt(i,j).getNiveauDePheromoneChercheMaison());
            }
          }else{
            System.out.print("\u2588\u2588");
          }
        }
        System.out.println();
      }
    }
    public void affichagePheroBouffe(){
      for(int i=0;i<grille.getHauteur();i++){
        for(int j=0;j<grille.getLargeur();j++){
          if(grille.getC>>>>>>> origin/npases()[i][j] instanceof CaseNormale){
            if(grille.getCasesAt(i,j).getNiveauDePheromoneChercheBouffe()<10){
              System.out.print("0"+grille.getCasesAt(i,j).getNiveauDePheromoneChercheBouffe());
            }else{
              System.out.print(grille.getCasesAt(i,j).getNiveauDePheromoneChercheBouffe());
            }
          }else{
            System.out.print("\u2588\u2588");
          }
        }
        System.out.println();
      }
    }
}
