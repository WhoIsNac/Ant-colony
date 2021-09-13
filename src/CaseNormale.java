/**
Case de la grille. C'est un type normal qui à vocation à être rempli de phéromones. Un peu comme tout le monde quoi.

@return CaseNormale
@see Case
@version 0.1

*/
public class CaseNormale extends Case{
  public boolean nourriture=false;
  public int niveauDeNourriture;
  public boolean colony=false;


  public CaseNormale(){
    super();
    niveauDeNourriture = 50 + ((int) Math.random()*100);
  }

  public boolean getNourriture(){
    return nourriture;
  }

  public boolean getColony(){
    return colony;
  }

  public void setNourriture(boolean n){
    nourriture=n;
  }

  public void setColony(boolean c){
    colony=c;
  }

  public void diminueNourriture(){
    if(niveauDeNourriture > 0) niveauDeNourriture--;

    if(niveauDeNourriture == 0) nourriture = false;
  }
}
