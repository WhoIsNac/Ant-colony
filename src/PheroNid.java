/**
Pheronome déposé par les insectes du nid à la source de nourriture.
Par défault, il est de 1.

*/

public class PheroNid extends Pheromone {
  /**
  * Default empty PheroNid constructor
  */
  public PheroNid() {
    super(0);
  }

  /**
  * Default PheroNid constructor
  */
  public PheroNid(int niveauDePheromone) {
    super(niveauDePheromone);
  }
}
