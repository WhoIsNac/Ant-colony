/**
Classe abstraite, Insecte permet de définir les quelques fonctions
primordiales qu'aura chacun des insectes.
Un insecte, concrètement, est un curseur dont le rôle est de parcourir la grille
en fonction de certaines règles. Ces règles sont définit dans la classe Modèle.
*/

import java.io.*;
public abstract class Insecte implements Serializable{
    private int[] coordonnees = new int[2];
    private boolean porteDeLaNourriture;

  /**
  * Default Insecte constructor
  */
  public Insecte(int[] coordonnees, boolean porteDeLaNourriture) {
    this.coordonnees =  coordonnees;
    this.porteDeLaNourriture = porteDeLaNourriture;
  }

  /**
  * Returns value of t
  * @return
  */
  public int[] getCoordonnees() {
    return coordonnees;
  }

    public int getX(){
        return coordonnees[0];
    }

    public int getY(){
        return coordonnees[1];
    }

  /**
  * Sets new value of t
  * @param int[] coordonnees x [0], y [1]
  */
  public void setCoordonnees(int[] coordonnees) {
    this.coordonnees = coordonnees;
  }

  /**
  * Returns value of porteDeLaNourriture
  * @return porteDeLaNourriture
  */
  public boolean porteDeLaNourriture() {
    return porteDeLaNourriture;
  }

  /**
  * Sets new value of porteDeLaNourriture
  * @param boolean porteDeLaNourriture
  */
  public void setPorteDeLaNourriture(boolean porteDeLaNourriture) {
    this.porteDeLaNourriture = porteDeLaNourriture;
  }
}
