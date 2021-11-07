

public class Main{

    
 
    public static boolean DEBUG = false;


    public static void main(String args[]){
        // PARAMETERS
        int nbA = 200;
        int nbB = 200;
        int nbRobot = 100;
        int width = 50;
        int height =  50;
        int speed = 1;
        double kplus = 0.1;
        double kminus = 0.3;
        int memorySize = 1 ;
        int nbIterations = 100000;
        int cooldown = 0;
        double errorPercent = 0.0;
        Modele m = new Modele(width, height, nbA, nbB, nbRobot, kplus, kminus, memorySize, speed, nbIterations, cooldown);

        m.setSimSpeed(0); // Vitesse de la simulation (en millisecondes de pause entre chaque itération)
        
        m.setError(errorPercent); // Partie 1 : Ajout d'une erreur de perception
        
        m.setObjetC(0); // Partie 2 : Objets de type C

        m.startModele();
        
    }


}