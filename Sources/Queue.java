
import java.util.ArrayList;
public class Queue<T> {
    
    private ArrayList<T> t;
    private int size;
    private int actualUsage;
    
    public Queue(int size){
       this.size = size;
       t = new ArrayList<T>(size);
       actualUsage = 0;     
    }

    public void add(T element){
        if(actualUsage == size){
            decal();
        }
        if(t.size() == size){
            t.set(actualUsage, element );
        }
        else{
            t.add(actualUsage, element );
        }
        actualUsage ++;
    }

    public void decal(){
        for(int i = 1; i < size; i++){
            t.set(i-1, t.get(i));
        }
        actualUsage --;
    }

    public T top(){
        if(actualUsage == 0){
            System.out.println("Empty queue\n");
            return null;
        } 
        return t.get(actualUsage-1);
    }

    public T get(int index){
        return t.get((index));
    }

    public void print(){
        String output = "";
        for (T t2 : t) {
            output += t2.toString();
            
        }
        System.out.println(output);
    }

    public int getNumberOf(T elem){
        int output = 0;
        for (T t2 : t) {
            if (t2.equals(elem)){
                output++;
            }
        }
        return output;
    }

    public int getNumberOfPrimType(T elem){
        int output = 0;
        for (T t2 : t) {
            if (t2 == elem){
                output++;
            }
        }
        return output;
    }
}
