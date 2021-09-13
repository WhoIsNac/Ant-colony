import java.io.*;
import java.lang.*;
public abstract class Pheromone implements Serializable{
    private int niveauDePheromone;
    private int limit=10;

    public Pheromone(int niveauDePheromone){
        this.niveauDePheromone = niveauDePheromone;
    }

  /**
  * Returns value of niveauDePheromone
  * @return int niveauDePheromone
  */
  public int getNiveauDePheromone() {
    return niveauDePheromone;
  }

  /**
  * Sets new value of niveauDePheromone
  * @param int niveauDePheromone
  */
  public void setNiveauDePheromone(int niveauDePheromone) {
    this.niveauDePheromone = Math.min(limit, niveauDePheromone);
    }
    /**
    * Create string representation of Pheromone for printing
    * @return
    */
    @Override
    public String toString() {
      return Integer.toString(niveauDePheromone);
    }
  //Les methodes suivantes permettent d'augmenter ou diminer le niveau de pheromone
  //d'un cran
  public void augmente(){
    niveauDePheromone = Math.min(niveauDePheromone+10, limit);
  }

  public void diminue(){
    if(niveauDePheromone != 0) niveauDePheromone--;
  }

  public int getLimit() {
    return limit;
  }
}
