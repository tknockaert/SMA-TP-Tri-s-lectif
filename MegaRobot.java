import java.util.ArrayList;
import java.util.Random;

public class MegaRobot extends Robot {

    private Robot r1;
    private Robot r2;
    private boolean hasStarted = false;
    public boolean isActive = true;
    public MegaRobot(int nbCaseParPas, double kplus, double kminus, int memorySize, Robot r1, Robot r2){
        super(nbCaseParPas, kplus, kminus, memorySize);
        this.r1 = r1;
        this.r2 = r2;
    }
    
    private void deFusion(){
        ArrayList<Case> voisins = grid.getVoisins(caseOn);
        Case r2Destination = null; 
        for (Case c : voisins){
            if(!c.hasRobot()){
                r2Destination = c;
            }
        }

        if(r2Destination == null){ // Si pas de case voisine trouvée pour defusionner tant pis 
             return;
        }

        grid.robotList.add(r1);
        grid.robotList.add(r2);     //on ajoute les deux robots à la liste des robots actifs
        
        isActive = false;   
        r1.isActive = true; // on reset l'activité de chaque robot, pour que le gros se fasse éliminer et que les petits recommencent à bouger
        r2.isActive = true;

        caseOn.removeRobot();
        caseOn.addRobot(r1); // r1 prend la place du mega robot
        r1.caseOn = caseOn;
        r1.x= r1.caseOn.x;
        r1.y= r1.caseOn.y;


        r2Destination.addRobot(r2);
        r2.caseOn = r2Destination;  // r2 prend une place voisine
        r2.x= r2.caseOn.x;
        r2.y= r2.caseOn.y;
        r1.inHand = null;
        r2.inHand = null;

        r1.startCooldown();
        r2.startCooldown();
        
        /*
        print(r1.caseOn);
        print(r2.caseOn);
        print("fin defusion");
        */
    }

    public void start(){
        hasStarted = true;
        take(); // normalement le MegaRobot est créé sur une case avec un objet C
    }

    @Override
    protected void take() {
        if(caseOn.isCObjectOnCase()){
            inHand = caseOn.o;
        }
        else{
            print("Probleme, le megarobot n'est pas sur une case avec un objet C");
        }
    }

    @Override  
    void perception(){ 
        perceptionHandFull();
        return; 
    }

    @Override
    void action(){
        Random random = new Random();
        float nb;
       
        float fC =  cInMemory  / memorySize;

        if(inHand != null){ // Devrait toujours être le cas, étant donné que dès que l'on lache l'objet le megarobot defusionne            
            nb = random.nextFloat();
            if( nb < Math.pow((double)fC/(kminus+(double)fC), 2) ){
                drop();
            }            
        }

        if(inHand == null){
            deFusion();
            return; 
        }
            move();
    }

    @Override
    public void boucled() {
        if(!hasStarted){
            start();
        }
        perception();
        action();
        //print("fin boucle");
    }


    @Override
    protected void drop() {
        inHand = null;
        //deFusion();
    }

    public String toString(){
        return "MegaRobot";
    }
}
