/**
Pheronome déposé par les insectes de la source de nourriture au nid.
Par défault, il est de 1.

*/

public class PheroBouffe extends Pheromone {
  /**
  * Default empty PheroBouffe constructor
  */
  public PheroBouffe() {
    super(0);
  }

  /**
  * Default PheroBouffe constructor
  */
  public PheroBouffe(int niveauDePheromone) {
    super(niveauDePheromone);
  }
}
