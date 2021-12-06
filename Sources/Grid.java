package Sources;

import java.util.ArrayList;
import java.util.Random;

public class Grid{
    public ArrayList<ArrayList<Case>> grid;
    public ArrayList<Robot> robotList;
    public ArrayList<MegaRobot> megaRobotList = new ArrayList<MegaRobot>();
    int x;
    int y;
    public Grid(int x, int y){
        this.x= x;
        this.y = y;
        grid = new ArrayList<ArrayList<Case>>();
        for(int i = 0; i < x; i++){
            ArrayList<Case> tmpList = new ArrayList<Case>();
            grid.add(tmpList);
            for(int j = 0; j< y; j++){
                tmpList.add(new Case(x,y));
            }
        }
    }

    public Grid(int x, int y, int nA, int nB, int nAg){
        this.x= x;
        this.y = y;
        //creation grille
        grid = new ArrayList<ArrayList<Case>>();
        robotList = new ArrayList<Robot>();
        for(int i = 0; i < x; i++){
            ArrayList<Case> tmpList = new ArrayList<Case>();
            grid.add(tmpList);
            for(int j = 0; j< y; j++){
                tmpList.add(new Case(i,j));
            }
        }

        //ajout objets et agents
        //ajout objets A
        int tmpX = -1;
        int tmpY = -1;
        for(int i =0; i<nA; i++){
            tmpX = genererInt(0,x-1);
            tmpY = genererInt(0,y-1);
            Objet o = new Objet('A');
            grid.get(tmpX).get(tmpY).addObjet(o);
        }
        //ajout objets B
        for(int i =0; i<nB; i++){
            boolean marqueur = true;
            while(marqueur || !grid.get(tmpX).get(tmpY).isObjetEmpty()){
                tmpX = genererInt(0,x-1);
                tmpY = genererInt(0,y-1);
                marqueur = false;
            }
            Objet o = new Objet('B');
            grid.get(tmpX).get(tmpY).addObjet(o);
        }

        //ajout Robots 
        for(int i =0; i<nAg; i++){
            tmpX = genererInt(0,x-1);
            tmpY = genererInt(0,y-1);
            Robot r = new Robot(tmpX,tmpY,this);
            robotList.add(r);
            grid.get(tmpX).get(tmpY).addRobot(r);
            r.setCaseOn(grid.get(tmpX).get(tmpY));
        }
    }

    // For newrobots
    public Grid(int x, int y, int nA, int nB, int nAg,int a){
        this.x= x;
        this.y = y;
        //creation grille
        grid = new ArrayList<ArrayList<Case>>();
        robotList = new ArrayList<Robot>();
        for(int i = 0; i < x; i++){
            ArrayList<Case> tmpList = new ArrayList<Case>();
            grid.add(tmpList);
            for(int j = 0; j< y; j++){
                tmpList.add(new Case(i,j));
            }
        }

        //ajout objets et agents
        //ajout objets A
        int tmpX = -1;
        int tmpY = -1;
        for(int i =0; i<nA; i++){
            tmpX = genererInt(0,x-1);
            tmpY = genererInt(0,y-1);
            Objet o = new Objet('A');
            if(grid.get(tmpX).get(tmpY).isObjetEmpty()){
                grid.get(tmpX).get(tmpY).addObjet(o);
            }
            else{
                i--;
            }
        }
        //ajout objets B
        for(int i =0; i<nB; i++){
            boolean marqueur = true;
            while(marqueur || !grid.get(tmpX).get(tmpY).isObjetEmpty()){
                tmpX = genererInt(0,x-1);
                tmpY = genererInt(0,y-1);
                marqueur = false;
            }
            Objet o = new Objet('B');
            if(grid.get(tmpX).get(tmpY).isObjetEmpty()){
                grid.get(tmpX).get(tmpY).addObjet(o);
            }
            else{
                i--;
            }
        }

        //ajout Robots 
        for(int i =0; i<nAg; i++){
            tmpX = genererInt(0,x-1);
            tmpY = genererInt(0,y-1);
            Robot r = new Robot(tmpX,tmpY,this);
            if(!grid.get(tmpX).get(tmpY).hasRobot()){
                grid.get(tmpX).get(tmpY).addRobot(r);
                robotList.add(r);
                r.setCaseOn(grid.get(tmpX).get(tmpY));
            }
            else{
                i--;
            }
        }
    }

    int genererInt(int borneInf, int borneSup){
        Random random = new Random();
        int nb;
        nb = borneInf+random.nextInt(1+borneSup-borneInf);
        return nb;
     }

    void printGrid(){
        for(ArrayList<Case> l : grid){
            String output = "";
            for(Case c : l){
                output += c.toString();
            }
            System.out.println(output);
        }
    }

    public Case getCase(int x, int y){
        if(x < 0 || x >= this.x  || y < 0 || y >= this.y){
            if(Main.DEBUG) System.out.println(x + "; "+ y +" Alors ? this x,y :" +this.x + "; "+this.y);
            return null;
        }
        return grid.get(x).get(y);
    }

    // Partie 2 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private double wind = 0.1; // r dans le sujet, gère la vitesse d'évaporation des phéromones
    public void setWind(double wind){
        this.wind = wind;
    }
    /**
     * Demande aux cases de s'update puis 
     * diffuse les phéromones
     * 
     */

    public void updateGrid(){
        for(ArrayList<Case> l : grid){            
            for(Case c : l){
                c.updateCase(wind);
            }
        }

        for(ArrayList<Case> l : grid){            
            for(Case c : l){
                if( c.hasPheromone() ){                
                    difusePheromone(c);
                }
            }
        }
    }

    /**
     * diffuse le phéromone de voisin en voisin de façon récursive
     * @param c case de départ
     */
    public void difusePheromone(Case c){
        double intensity = c.pheromone.getIntensity();
        int x = c.x;
        int y = c.y;
        if(intensity > 1){
            for(int i = -1; i<2; i++){ // on va aller chopper tous les voisins
                for(int j = -1; j<2; j++){
                    if( x+i >= 0 && x+i < this.x && y+j >= 0 && y+j < this.y){ // on verifie qu'on est dans le plateau
                        Case voisin = getCase(x+i, y+j);
                        if( (!voisin.hasPheromone()) || voisin.pheromone.intensity < intensity *0.75  ){   // on donne le pheromone uniquement si le voisin n'en a pas ou en a un plus faible
                            voisin.setPheromone(new Pheromone(intensity*0.75));
                            difusePheromone(voisin); 
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Case> getVoisins(Case c){
        ArrayList<Case> output = new ArrayList<Case>();
        for(int i = -1; i<2; i++){ // on va aller chopper tous les voisins
            for(int j = -1; j<2; j++){
                if( c.x+i >= 0 && c.x+i < this.x && c.y+j >= 0 && c.y+j < this.y){
                    output.add(getCase(c.x+i, c.y+j));    
                }
            }
        }
        //print(output);
        return output;
    }
    public ArrayList<Case> getVoisinsAlt(Case c){
        ArrayList<Case> output = new ArrayList<Case>();
        for(int k = -1; k<4; k++){ // on va aller chopper tous les voisins
            int j =0 ;
            int i = 0;
            if(k%2 == 0 ){
                if(k ==0) i = -1;
                if(k ==2) i = 1;
            }
            if(k%2 == 1 ){
                if(k ==1) j = -1;
                if(k ==2) j = 3;
            }
            if( c.x+i >= 0 && c.x+i < this.x && c.y+j >= 0 && c.y+j < this.y){
                output.add(getCase(c.x+i, c.y+j));    
            }

        }
        //print(output);
        return output;
    }

    public void addObjetC(int nB){
        int tmpX = genererInt(0,x-1);
        int tmpY = genererInt(0,y-1);
        for(int i =0; i<nB; i++){
            boolean marqueur = true;
            while(marqueur || !grid.get(tmpX).get(tmpY).isObjetEmpty()){
                tmpX = genererInt(0,x-1);
                tmpY = genererInt(0,y-1);
                marqueur = false;
            }
            Objet o = new Objet('C');
            if(grid.get(tmpX).get(tmpY).isObjetEmpty()){
                grid.get(tmpX).get(tmpY).addObjet(o);
            }
            else{
                i--;
            }
        }
    }

    void print(Object o){
        System.out.println(o);
    }
}

