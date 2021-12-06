package Sources;

public class Case{
    public int x;
    public int y;
    public Objet o;
    public Robot r;

    private double limitPheromoneSensibility = 1.0;
    public Pheromone pheromone;

    public Case(int x, int y, Objet o, Robot r){
        this.x = x;
        this.y = y;
        this.o = o;
        this.r = r;
    }

    public Case(int x, int y,Objet o){
        this.x = x;
        this.y = y;
        this.o = o;
    }

    public Case(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void removeObjet(){
        this.o = null;
    }

    public void addObjet(Objet o){
        this.o = o;
    }

    public void removeRobot(){
        this.r = null;
    }

    public void addRobot(Robot r){
        this.r = r;
    }



    public void setPheromone(Pheromone p){
        this.pheromone = p;
    }


    public boolean hasPheromone(){
        if (pheromone != null){
            return true;
        }
        return false;
    }

    public boolean isObjetEmpty(){
        return o == null;
    }

    public void printCase(){
        if(o == null && r == null){
            System.out.println('X');
            return;
        }
        char oChar = 'X';
        if(o != null){
            oChar = o.type;
        }
        char rChar = 'X';
        if(r != null){
            rChar = 'R';
        }
        String output = ""+oChar + rChar;
        System.out.println(output);
    }

    public String whatsInToString(){
        if(o == null && r == null){
            return "X ";
        }
        char oChar = 'X';
        if(o != null){
            oChar = o.type;
        }
        char rChar = 'X';
        if(r != null){
            rChar = 'R';
        }
        String output = ""+oChar + rChar+" ";
        return output;
    }

    public boolean isAObjectOnCase(){
        return !(o == null ) && o.type == 'A';        
    }
    
    public boolean isBObjectOnCase(){
        return !(o == null )&& o.type == 'B';        
    }

    public boolean isCObjectOnCase(){
        return !(o == null )&& o.type == 'C';        
    }


    public char getObjetType(){
        if(o == null){
            return 'X';
        }
        else{
            return o.type;
        }
    }

    // Partie2
    public void updateCase(double wind){
        if (pheromone != null){
            pheromone.decreaseIntensity(wind);
            if(pheromone.getIntensity() < limitPheromoneSensibility){
                pheromone = null;
            }
        }
    }

    public boolean hasRobot(){
        return !(r == null);
    }
    @Override
    public String toString(){
        String xs = Integer.toString(x);
        String ys = Integer.toString(y);
        return "(" +xs + "," + ys +")";
    }
}