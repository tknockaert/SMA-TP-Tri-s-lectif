package Sources;
import java.awt.Color;
import java.util.ArrayList;


public class Modele{

    // HYPERPARAMETERS
    private  int nbA = 200;
    private  int nbB = 200;
    private  int nbRobot = 100;
    private  int xGrid = 50;
    private  int yGrid =  50;
    private  int speed = 1;
    private  double kplus = 0.1;
    private  double kminus = 0.3;
    private  int memorySize = 10 ;
    private  int nbIterations = 100000;
    private  boolean DEBUG = false;
    private  double error = 0.0;
    private int nbC = 0;
    private int cooldown = 0;
    private int millis = 5;

    public Modele(int width, int height, int nbAObjet, int nbBObjet, int nbAgents, double kplus, double kminus, int memorySize, int speed, int nbIterations, int cooldown){
        this.nbA = nbAObjet;
        this.nbB = nbBObjet;
        this.nbRobot = nbAgents;
        this.xGrid = width;
        this.yGrid = height;
        this.speed = speed;
        this.kplus = kplus;
        this.kminus = kminus;
        this.memorySize = memorySize;
        this.nbIterations = nbIterations;
        this.cooldown = cooldown;
    }
    
    

    public void startModele(){
        Grid grid = new Grid(xGrid, yGrid, nbA, nbB, nbRobot);
        grid.addObjetC(nbC);
        GridDrawer gdStart = new GridDrawer(grid,Color.red,Color.blue);
        gdStart.setTitle("Debut");
        GridDrawer gdd = new GridDrawer(grid,Color.red,Color.blue);
        gdd.setTitle("Live");


        for( Robot r : grid.robotList){
            r.setRobot(speed, kplus, kminus, memorySize);
            r.setCooldownTime(cooldown);
            r.setError(error);
        }



        for(int i = 0; i < nbIterations; i ++ ){
            ArrayList<Robot> toRemove = new ArrayList<Robot>();         // Partie 2 : \/
            ArrayList<MegaRobot> toMegaRemove = new ArrayList<MegaRobot>(); //  Gère les robots qui fusionnent/défusionnent, pour les enlever de la boucle
            for( Robot r : grid.robotList){
                if(r!= null && r.isActive){
                    r.boucled();
                }
                else if(r!= null &&!r.isActive){   // Partie 2 : Quand un robot fusionne avec un autre il devient inactif/ idem pour un Megarobot qui défusionne
                    toRemove.add(r);
                }
            }
            
            //////////////////////////////// PARTIE 2 //////////////////////////////////////////////////////////////////////////////////////////////////////
            for( MegaRobot mgr : grid.megaRobotList){
                if(mgr!= null && mgr.isActive){
                    mgr.boucled();
                }
                else if(mgr!= null &&!mgr.isActive){
                    toMegaRemove.add(mgr);  // Partie 2 : Quand un robot fusionne avec un autre il devient inactif/ idem pour un Megarobot qui défusionne
                }
            }

            for(Robot r : toRemove){
                grid.robotList.remove(r);  // Partie 2 
            }
            for(Robot r : toMegaRemove){
                grid.megaRobotList.remove(r); // Partie 2 
            }


            //////////////////////////////// FIN PARTIE 2 //////////////////////////////////////////////////////////////////////////////////////////////////////

            grid.updateGrid();
            if(i%100 == 0){
                gdd.repaint();
            }

            try {
                Thread.sleep(millis);
            } catch (InterruptedException ie) {
                // ...
            }

            
            if(i == nbIterations -1) System.out.println("\n \n Dernière iteration \n \n");
        }
        gdd.setTitle("Fini");
    }




    // ONLY GETTERS AND SETTERS FROM HERE

    public void setError(double error){
        this.error = error;
    }

    public void setObjetC(int nbC){
        this.nbC = nbC;
    }

    public void setSimSpeed(int waitTime){
        this.millis = waitTime;
    }

    public int getNbA() {
        return nbA;
    }

    public void setNbA(int nbA) {
        this.nbA = nbA;
    }

    public int getNbB() {
        return nbB;
    }

    public void setNbB(int nbB) {
        this.nbB = nbB;
    }

    public int getNbRobot() {
        return nbRobot;
    }

    public void setNbRobot(int nbRobot) {
        this.nbRobot = nbRobot;
    }

    public int getxGrid() {
        return xGrid;
    }

    public void setxGrid(int xGrid) {
        this.xGrid = xGrid;
    }

    public int getyGrid() {
        return yGrid;
    }

    public void setyGrid(int yGrid) {
        this.yGrid = yGrid;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getKplus() {
        return kplus;
    }

    public void setKplus(double kplus) {
        this.kplus = kplus;
    }

    public double getKminus() {
        return kminus;
    }

    public void setKminus(double kminus) {
        this.kminus = kminus;
    }

    public int getMemorySize() {
        return memorySize;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public int getNbIterations() {
        return nbIterations;
    }

    public void setNbIterations(int nbIterations) {
        this.nbIterations = nbIterations;
    }

    public boolean isDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(boolean dEBUG) {
        DEBUG = dEBUG;
    }

    public double getError() {
        return error;
    }

    public int getNbC() {
        return nbC;
    }

    public void setNbC(int nbC) {
        this.nbC = nbC;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
    