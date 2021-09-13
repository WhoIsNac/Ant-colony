import java.util.*;
import java.lang.*;
import java.io.*;

public class Modele implements Serializable{
    private Grille terrain;
    private ArrayList<Insecte> insectes;
    private int[] positionDeLaColony;  // x = j = hauteur | y = i = largeur

  /**
  * Default empty Modele constructor
  */

    // TODO : faire en sorte que on puisse récuperer les données à partir de la GUI
  public Modele() {
        this.terrain = new Grille();
        this.insectes = new ArrayList<Insecte>();
        this.positionDeLaColony = this.terrain.getColonyPosition();
  }

  /**
  * Default Modele constructor
  */
  public Modele(Grille terrain, int nbrInsectes) {
    this.terrain = terrain;
    this.positionDeLaColony = this.terrain.getColonyPosition();
    this.insectes = generationInsectes(nbrInsectes, 1);
  }

    /**
    La méthode update se charge de mettre à jour la grille
    On va iterer sur les fourmis dans Insecte
    Sur chaque insecte, on va se charger de la faire avancer,
    en fonction de quels chemins sont disponibles devant, si la fourmie porte
    de la nourriture, et quels est le niveau de chaque phéromone sur les cases alentoures.
    Si la case est une nourriture, la fourmie la récupère, et va changer d'etat pour
    suivre les phéronomoes allant à la maison
    Si la case est une maison, la fourmie meurt, et est retirée  de la grille
    updateAll se charge de faire cela sur tous les insectes et aussi d'update les pheromones
    */

    public void updateAll(){
      for(int i=0; i<insectes.size(); i++) {
        updateInsecte(insectes.get(i));
      }
      updatePheromoneDeLaGrille();
    }

    //update sur un insecte individuelle
    public void updateInsecte(Insecte insecteSurLaGrille){
        //Cherche les positions autour de l'insecte
        ArrayList<int[]> positionPossibles= positionPossibles(insecteSurLaGrille);
        //s'il ya plus d'une position, regarde le taux de pheromone de ses positions
        if(positionPossibles.size()>1){
          positionPossibles=positionPheromone(insecteSurLaGrille,positionPossibles);
        }
        /*
        //Affichage des positons
        for(int[] coor : positionPossibles){
          System.out.print("["+coor[0]+","+coor[1]+"] ");
        }*/
        //Choisit une positions aleatire
        if(positionPossibles.size() == 0) {
          insectes.remove(insecteSurLaGrille);
        } else {
          int n=(int)(Math.random()*positionPossibles.size());
          int[] nouvellePosition=positionPossibles.get(n);

          //Placement de la pheromone sur l'ancienne place
          if(insecteSurLaGrille.porteDeLaNourriture()){
              terrain.getCasesAt(insecteSurLaGrille.getCoordonnees()).getPheromoneBouffe().augmente();
          }else{
              terrain.getCasesAt(insecteSurLaGrille.getCoordonnees()).getPheromoneNid().augmente();
          }
          // On vérifie que les cases adjacentes sont des cases vides
          //S'il l'inscete trouve son but (Colony/Nourriture)
          //change le boolean PorteDeLaNourriture
          if(((CaseNormale)terrain.getCasesAt(nouvellePosition)).getNourriture()){
              insecteSurLaGrille.setPorteDeLaNourriture(true);
              ((CaseNormale)terrain.getCasesAt(nouvellePosition)).diminueNourriture();
          } else if(((CaseNormale)terrain.getCasesAt(nouvellePosition)).getColony()){
              insecteSurLaGrille.setPorteDeLaNourriture(false);
          } else {
              //l'insecte est a sa nouvelle positions
              insecteSurLaGrille.setCoordonnees(nouvellePosition);              
          }

        }
    }
    //Regarde les 8 positions autour de l'insecete
    //Ajoute la posiiton dans la liste des positions possible si celle ci est une Case normale
    //Si l'une des cases est le but de l'insecte, alors la methode renvoie une liste contenant que cette position
    public ArrayList<int[]> positionPossibles(Insecte insecte){
      ArrayList<int[]> positionPossibles= new ArrayList<int[]>();
      for(int i=-1;i<2;i++){
        for(int j=-1;j<2;j++){
          if(insecte.getX()+i>=0 && insecte.getX()+i<terrain.getHauteur() && insecte.getY()+j>=0 && insecte.getY()+j<terrain.getLargeur() && terrain.getCasesAt(insecte.getX()+i,insecte.getY()+j) instanceof CaseNormale && (i==0 ^ j==0)){
            //Si l'insecte voie son but
            if(((CaseNormale)terrain.getCasesAt(insecte.getX()+i,insecte.getY()+j)).getColony()
                && insecte.porteDeLaNourriture()){
              positionPossibles=new ArrayList<int[]>();
              int[] c={insecte.getX()+i,insecte.getY()+j};
              positionPossibles.add(c);
              return positionPossibles;
            }
            if(((CaseNormale)terrain.getCasesAt(insecte.getX()+i,insecte.getY()+j)).getNourriture()
                && !insecte.porteDeLaNourriture()){
              positionPossibles=new ArrayList<int[]>();
              int[] c={insecte.getX()+i,insecte.getY()+j};
              positionPossibles.add(c);
              return positionPossibles;
            }
            //Posiiton normale
            int[] c={insecte.getX()+i,insecte.getY()+j};
            positionPossibles.add(c);
          }
        }
      }
      return positionPossibles;
    }

    //Regarde toutes les positions possible et rajoute une position n fois dependant de son taux de pheromone
    //Exemple: si une position a un taux de pheromone 4, cette position apparait 5 fois dans la liste
    public ArrayList<int[]> positionPheromone(Insecte insecte,ArrayList<int[]> positionPossibles){
      int n=positionPossibles.size();
      for (int j=0;j<n;j++){
        if(insecte.porteDeLaNourriture()){
          for(int i=0; i<Math.max(terrain.getCasesAt(positionPossibles.get(j)).getNiveauDePheromoneChercheMaison()-(terrain.getCasesAt(positionPossibles.get(j)).getNiveauDePheromoneChercheBouffe()/2),0);i++){
            positionPossibles.add(positionPossibles.get(j));
          }
        }else{
          for(int i=0; i<Math.max(terrain.getCasesAt(positionPossibles.get(j)).getNiveauDePheromoneChercheBouffe()-(terrain.getCasesAt(positionPossibles.get(j)).getNiveauDePheromoneChercheMaison()/2),0);i++){
            positionPossibles.add(positionPossibles.get(j));
          }
        }
      }
      return positionPossibles;
    }
    /**
    Créer les différents insectes
    Toutes les insectes commençent au niveau de la colonie.

    @param nbrInsectes Le nombre d'insecte qui doivent être générés
    @param typeInsecte Le type d'insecte. 1 pour les Fourmi, Insecte en cas par défault.
    */
    public ArrayList<Insecte> generationInsectes(int nbrInsectes, int typeInsecte){
        ArrayList<Insecte> ins = new ArrayList<Insecte>();
        switch (typeInsecte){
            case 1:
                for ( int i = 0; i < nbrInsectes; i++){
                    ins.add(new Fourmi(this.positionDeLaColony, false));
                }
                break;
            default:
                for ( int i = 0; i < nbrInsectes; i++){
                    ins.add(new Fourmi(this.positionDeLaColony, false));
                }
                break;
        }

        return ins;
    }
    /**

    @return positionPossibles ArrayList<int[]>
    */

    public void updatePheromoneDeLaGrille(){
        for (int x = 0; x < this.terrain.getHauteur(); x++ ) {
            for (int y = 0; y < this.terrain.getLargeur(); y++){
                this.terrain.getCasesAt(x,y).updatePheromones();
            }
        }
    }



  /**
  * Returns value of terrain
  * @return Grille
  */
  public Grille getTerrain() {
    return terrain;
  }

  /**
  * Sets new value of terrain
  * @param
  */
  public void setTerrain(Grille terrain) {
    this.terrain = terrain;
  }

  /**
  * Returns value of insectes
  * @return
  */
  public ArrayList<Insecte> getInsectes() {
    return insectes;
  }

  /**
  * Sets new value of insectes
  * @param
  */
  public void setInsectes(ArrayList<Insecte> insectes) {
    this.insectes = insectes;
  }

    public boolean isInsecteAtPos(int x, int y){
        for (Insecte ins : this.insectes ) {
            if (ins.getX() == x && ins.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public int getHauteur() {
  		return terrain.getHauteur();
  	}

  	public int getLargeur() {
  		return terrain.getLargeur();
  	}

  	public Case[][] getCases(){
    	return terrain.getCases();
  	}

  	public Case getCase(int x, int y){
  		return terrain.getCase(x, y);
  	}


    //SERIALISATION
  	public void serialiser(String nom){
  		try {
  	        FileOutputStream fs = new FileOutputStream("Terrains/"+nom+".ser");
  	        ObjectOutputStream os = new ObjectOutputStream(fs);
  	        os.writeObject(this);
  	        os.close();
  	        fs.close();
  		} catch (Exception e) {
  	    	e.printStackTrace();
  	  }
  	}
}
