/**
Case de la grille. Voir les extensions pour les différents types de cases.

@return Case
@see CaseNormal
@version 0.1

*/

import java.lang.Math;
import java.io.*;

public abstract class Case implements Serializable {
  protected Pheromone niveauDePheromoneChercheMaison;
  protected Pheromone niveauDePheromoneChercheBouffe;
  //protected int pheromone=(int)(Math.random()*10);
  public Case(){
      this.niveauDePheromoneChercheBouffe = new PheroBouffe();
      this.niveauDePheromoneChercheMaison = new PheroNid();
  }

  public int getLimitPheromoneNid() {
    return niveauDePheromoneChercheMaison.getLimit();
  }

  public int getLimitPheromoneBouffe() {
    return niveauDePheromoneChercheBouffe.getLimit();
  }

  public Pheromone getPheromoneNid(){
    return niveauDePheromoneChercheMaison;
  }
  public Pheromone getPheromoneBouffe(){
    return niveauDePheromoneChercheBouffe;
  }

  public int getNiveauDePheromoneChercheMaison(){
      return niveauDePheromoneChercheMaison.getNiveauDePheromone();
  }
  public int getNiveauDePheromoneChercheBouffe(){
      return niveauDePheromoneChercheBouffe.getNiveauDePheromone();
  }

  public void addPheromone(int niveauDePheromone, boolean porteDeLaNourriture){
      if (!porteDeLaNourriture) {
          niveauDePheromoneChercheBouffe.setNiveauDePheromone(niveauDePheromoneChercheBouffe.getNiveauDePheromone() + 1);
      } else {
          niveauDePheromoneChercheMaison.setNiveauDePheromone(niveauDePheromoneChercheMaison.getNiveauDePheromone() + 1);
      }
  }

  /**
  Décrémente le niveau de phéromone de la case
  */
  public void updatePheromones(){
    niveauDePheromoneChercheMaison.diminue();
    niveauDePheromoneChercheBouffe.diminue();
  }


}
