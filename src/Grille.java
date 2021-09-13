/**
Case de la grille. Voir les extensions pour les différents types de cases.

@return Grille
@param hauteur Hauteur de la grille en y
@param largeur  Largeur de la grille en x
@param tunnels  Nombres de tunnels dans la grille
@param maxLength  Taille maximum d'un tunnel. La génération fera des tunnels aléatoires entre 1 et tunnnels
@param bord  Détermine si un bord sera fait autour de la grille ou pas.
@version 0.2

*/

import java.lang.Math;
import java.util.*;
import java.io.*;

public class Grille implements Serializable {
  Case[][] cases;
  int hauteur=15;
  int largeur=20;
  int tunnels=60;
  int maxLength=5;
  //x pour hauteur et y pour getLargeur
  //Attribut pour l'emplacemnt de la colony
  int colonyX;
  int colonyY;
  //Attribut pour rajouter des bords a la grille
  //0 pour non, 1+ pour oui et taille du bord
  int bord=1;

  //Les coordonnes de la getNourriture
  ArrayList<int[]> foods = new ArrayList<int[]>();

  //Grille de base
  public Grille(){
    cases=new Case[hauteur][largeur];
    initialization();
  }
  //Grille selon les parametres
  public Grille(int hauteur, int largeur, int tunnels, int max, int bord){
    this.hauteur=hauteur;
    this.largeur=largeur;
    this.tunnels=tunnels;
    maxLength=max;
    this.bord=bord;
    this.cases=new Case[hauteur][largeur];
    initialization();
  }

  //Les methodes get
  public Case[][] getCases(){
    return cases;
  }
  public int getHauteur(){
    return hauteur;
  }
  public int getLargeur(){
    return largeur;
  }

  public Case getCasesAt(int x, int y){
      return cases[x][y];
  }

  public Case getCasesAt(int[] a){
      return cases[a[0]][a[1]];
  }

  // Retourne la position de la colony.
  // x = i = hauteur
  // y = j = largeur
  public int[] getColonyPosition(){
      int[] i = new int[2];
      i[0] = colonyX;
      i[1] = colonyY;
      return i;
  }
  public Case getCase(int x, int y){
    return cases[y][x];
  }
  /**
  Constructeur la grille. Se charge de prendre la hauteur et la largeur donnée
  par l'utilisateurice, et à partir de ces valeurs, détermine le nombre de nourriture,
  de tunnels, etc..

  @param hauteur Hauteur de la grille en x
  @param largeur  Largeur de la grille en y
  @param densite Densité des chemins dans la grille entre
  @return Cases[][] Un tableau de tableau de cases
  */
  public Grille (int hauteur, int largeur, int densite){

    try {
    this.hauteur = hauteur;
    this.largeur = largeur;
    this.bord = 1;
    this.cases = new Case[hauteur][largeur];
    this.maxLength = Math.abs((Math.max(hauteur, largeur) / - densite));
    this.tunnels = (int) Math.round((( this.hauteur - (this.bord * 2) ) * (this.largeur - (this.bord * 2)) / Math.log(this.largeur + this.hauteur)));
    } catch (ArithmeticException arthEx){
        System.out.println("Erreur : Il faut que les valeurs données soient positives. Utilisation des valeurs par défault.");
    }
    initialization();
    placeNourriture((int) (Math.random() * maxLength) + 1, 5) ;
  }

  //Constructeur avec paramètre nourriture
  public Grille (int hauteur, int largeur, int densite, int nourriture){

    try {
    this.hauteur = hauteur;
    this.largeur = largeur;
    this.bord = 1;
    this.cases = new Case[hauteur][largeur];
    this.maxLength = Math.abs((Math.max(hauteur, largeur) / - densite));
    this.tunnels = (int) Math.round((( this.hauteur - (this.bord * 2) ) * (this.largeur - (this.bord * 2)) / Math.log(this.largeur + this.hauteur)));
    } catch (ArithmeticException arthEx){
        System.out.println("Erreur : Il faut que les valeurs données soient positives. Utilisation des valeurs par défault.");
    }
    initialization();
    placeNourriture(nourriture, 5) ;
  }

  //Remplie la grille d'obstacle
  public void allWall(){
    for(int i=0;i<hauteur;i++){
      for(int j=0;j<largeur;j++){
        cases[i][j]=new Obstacle();
      }
    }
  }

  //Genere le map aleatoire
  public void initialization(){
    allWall();
    //Case initiale choisie au hasard
    int x=(int)(Math.random()*(hauteur-bord*2))+bord;
    int y=(int)(Math.random()*(largeur-bord*2))+bord;
    colonyX = x;
    colonyY = y;
    //A chaque tour de boucle, genere au hasard une longeur
    //de tunnel compris entre 1 et maxLength compris puis une direction
    //et transforme tout le tunnel en case normale
    cases[x][y]=new CaseNormale();
    ((CaseNormale)cases[x][y]).setColony(true);
    int oldDirection=0;
    for(int i=0;i<tunnels;i++){
      //longeur aleatoire
      int length=(int)(Math.random()*maxLength) +1;
      //Direction: 1=droite, 2=bas, 3=gauche, 4=haut
      int direction=0;
      while(direction==0){
        direction=(int)(Math.random()*4) +1;
        //Change la direction si la prochaine case vers cette Direction
        //est un bord ou la prochaine direction va vers la precedente
        switch(direction){
          case 1:
            if(x+1>=hauteur-bord || oldDirection==3 || oldDirection==1){
              direction=0;
            }else{
              oldDirection=1;
              while(length>0 && !(x+1>=hauteur-bord)){
                x++;
                if(cases[x][y] instanceof Obstacle){
                  cases[x][y]=new CaseNormale();
                }
                length--;
              }
            }
            break;
          case 2:
            if(y+1>=largeur-bord || oldDirection==4 || oldDirection==2){
              direction=0;  //Placer n nourriture
            }else{
              oldDirection=2;
              while(length>0 && !(y+1>=largeur-bord)){
                y++;
                if(cases[x][y] instanceof Obstacle){
                  cases[x][y]=new CaseNormale();
                }
                length--;
              }
            }
            break;
          case 3:
            if(x-1<bord || oldDirection==1 || oldDirection==3){
              direction=0;
            }else{
              oldDirection=3;
              while(length>0 && !(x-1<bord)){
                x--;
                if(cases[x][y] instanceof Obstacle){
                  cases[x][y]=new CaseNormale();
                }
                length--;
              }
            }
            break;
          case 4:
            if(y-1<bord || oldDirection==2 || oldDirection==4){
              direction=0;
            }else{
              oldDirection=4;
              while(length>0 && !(y-1<bord)){
                y--;
                if(cases[x][y] instanceof Obstacle){
                  cases[x][y]=new CaseNormale();
                }
                length--;
              }
            }
            break;
        }
      }
    }
  }

  //Renvoie un tableau montrant a combien de cases de distance est une case de la colony
  public int[][] distance(){
    //initialisation du tableau distance
    int[][] distance=new int[hauteur][largeur];
    for(int i=0;i<hauteur;i++){
      for(int j=0;j<largeur;j++){
        if(cases[i][j] instanceof CaseNormale){
          distance[i][j]=-2;
        }else{
          distance[i][j]=-1;
        }
      }
    }
    //Determine les distances de chaque case
    //une liste pour determiner dans quel ordre visiter les cases
    ArrayList<int[]>prochainCase=new ArrayList<int[]>();
    int[] coor={colonyX,colonyY};
    prochainCase.add(coor);
    //Tant que la liste a au moins un element, on determine ladistance de chaque case
    //dans l'ordre
    while(!prochainCase.isEmpty()){
      int x=prochainCase.get(0)[0];
      int y=prochainCase.get(0)[1];
      //On cherche la plus petite valeur parmis ses voisins
      int petit=-1;
      if(x>0 && distance[x-1][y]>=0 && (distance[x-1][y]<petit || petit==-1)){
        petit=distance[x-1][y];
      }
      if(y>0 && distance[x][y-1]>=0 && (distance[x][y-1]<petit || petit==-1)){
        petit=distance[x][y-1];
      }
      if(x+1<hauteur && distance[x+1][y]>=0 && (distance[x+1][y]<petit || petit==-1)){
        petit=distance[x+1][y];
      }
      if(y+1<largeur && distance[x][y+1]>=0 && (distance[x][y+1]<petit || petit==-1)){
        petit=distance[x][y+1];
      }
      //on affecte la valeur+1 a la case
      distance[x][y]=petit+1;
      //On enleve les coordonnes de la case de la liste
      prochainCase.remove(0);
      //on regarde les voisins pour les rajouter a la liste s'ils
      //sont une caseNormale dont les coordonnes ne sont pas dans la liste(-2)
      //puis on lui donne une valeur -3 pour montrer que ses coordonnes sont dans la liste
      if(x>0 && distance[x-1][y]==-2){
        int[] c={x-1, y};
        distance[x-1][y]=-3;
        prochainCase.add(c);
      }
      if(y>0 && distance[x][y-1]==-2){
        int[] c={x, y-1};
        distance[x][y-1]=-3;
        prochainCase.add(c);
      }
      if(x+1<hauteur && distance[x+1][y]==-2 ){
        int[] c={x+1, y};
        distance[x+1][y]=-3;
        prochainCase.add(c);
      }
      if(y+1<largeur && distance[x][y+1]==-2){
        int[] c={x, y+1};
        distance[x][y+1]=-3;
        prochainCase.add(c);
      }
    }
    return distance;
  }

  //Determine la distance maximun de la grille
  public int determinerDistanceMax(int[][] distance){
    int distanceMax=0;
    for(int i=0;i<hauteur;i++){
      for(int j=0;j<largeur;j++){
        if(distance[i][j]>distanceMax){
          distanceMax=distance[i][j];
        }
      }
    }
    return distanceMax;
  }

  //Placer n nourriture et evite les eviter cases autour de la colony
  //si la valeur eviter est trop grande par rapport a n, place la nourriture
  //suffissament eloigne de la colony (mais peut ne pas marcher selon la forme de la grille)
  public void placeNourriture(int n, int eviter){
    int[][] distance=distance();
    int distanceMax=determinerDistanceMax(distance);
    if(eviter>=distanceMax-n/2){
      eviter=distanceMax-n/2-1;
    }
    while(n>0){
      int x=(int)(Math.random()*(hauteur-bord*2))+bord;
      int y=(int)(Math.random()*(largeur-bord*2))+bord;
      if(distance[x][y]>=eviter && !((CaseNormale)cases[x][y]).getNourriture()){
        ((CaseNormale)cases[x][y]).setNourriture(true);
        int[] c={x,y};
        foods.add(c);
        n--;
      }
    }
  }

  //Supprime tous les nourritures
  public void supprimerNourriture(){
    while(!foods.isEmpty()){
      ((CaseNormale)cases[foods.get(0)[0]][foods.get(0)[1]]).setNourriture(false);
      foods.remove(0);
    }
  }

  //affiche sur le Terminale les distance de chaque case par rapport
  //a la colony(0)
  public void afficheDistance(int[][] distance){
    for(int i=0;i<hauteur;i++){
        for(int j=0;j<largeur;j++){
            if(distance[i][j]>9 || distance[i][j]<0){
                if(distance[i][j]<0){
                    System.out.print("  ");
                }else{
                    System.out.print(distance[i][j]);
                }
            }else{
              System.out.print(" "+distance[i][j]);
            }
            }
        }
  }

  //Modifie la case pour que si il y a de la nourriture, elle disparait
  //et vice-versa si il n'y a pas de nourritures
  //il ne se passe rien si la case est un Obstacle
  public void caseNourriture(int x, int y){
    if(cases[x][y] instanceof CaseNormale){
      if(((CaseNormale)cases[x][y]).getNourriture()){
        ((CaseNormale)cases[x][y]).setNourriture(false);
      }else{
        ((CaseNormale)cases[x][y]).setNourriture(true);
      }
    }
  }

  public void setCaseNormale(int x, int y){
      this.cases[x][y] = new CaseNormale();
  }

  public void setObstacle(int x, int y){
      this.cases[x][y] = new Obstacle();
  }

  public void setEau(int x, int y){
      this.cases[x][y] = new Eau();
  }
}
