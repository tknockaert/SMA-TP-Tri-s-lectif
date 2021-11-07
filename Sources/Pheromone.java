public class Pheromone {
    public double intensity;

    public Pheromone(double intensity){
        this.intensity = intensity;
    }

    public double getIntensity(){
        return intensity;
    }

    public void decreaseIntensity(){
        intensity --;
    }

    public void decreaseIntensity(double wind){
        intensity -= intensity*wind;
    }
}
