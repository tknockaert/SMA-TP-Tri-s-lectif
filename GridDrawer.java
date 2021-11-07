
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Graphics;

import javax.swing.JFrame;



public class GridDrawer extends JFrame {
    public Grid grid;
    public Graphics2D g;
    public Color color1 = Color.blue;
    public Color color2 = Color.red;
    public Color color3 = Color.ORANGE;


    public GridDrawer(Grid grid){
        this.grid = grid;
        this.setSize(1200,1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        


    }
    //public void repaint()
    public GridDrawer(Grid grid,Color c1, Color c2){
        this.grid = grid;
        this.setSize(1200,1200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        color1 = c1;
        color2 = c2;



    }

    public void paint(Graphics gr){
        Graphics2D g2d = (Graphics2D) gr;
        this.g = g2d;
        for(int i = 0; i< grid.x; i++){
            for(int j = 0; j < grid.y; j++){
                if(!grid.getCase(i, j).isObjetEmpty()){               
                    if(grid.getCase(i, j).o.type == 'A'){
                        g2d.setColor(color1);
                    }
                    else if(grid.getCase(i, j).o.type == 'B'){
                        g2d.setColor(color2);
                    }
                    else if(grid.getCase(i, j).o.type == 'C'){
                        g2d.setColor(color3);
                    }
                }
                else{
                    g2d.setColor(Color.white);
                }
                
                g2d.fillRect(i*20 +100, j*20+100, 20, 20);  
                if(grid.getCase(i, j).r != null){
                    g2d.setColor(Color.black);
                    g2d.drawLine(i*20 +100, j*20+100, i*20 +100+20, j*20+100+20);
                    g2d.drawLine(i*20 +100, j*20+100+20, i*20 +100+20, j*20+100);
                    if(grid.getCase(i, j).r != null && grid.getCase(i, j).r.getClass() == MegaRobot.class){
                        g2d.drawLine(i*20 +100 + 10, j*20+100, i*20 +100+10, j*20+100+20);
                    }
                }
                if(grid.getCase(i, j).hasPheromone()){
                   
                    drawPheromone(g2d,i,j);
                }
            }
        }
    }


    public void drawPheromone(Graphics2D g2d,int i, int j){
        g2d.setColor(Color.black);
        Font f = g2d.getFont().deriveFont((float)20.0); // Set size of the font
        g2d.setFont(f);
        int alpha = 0;
        if(grid.getCase(i, j).pheromone.getIntensity() > 5){
            alpha = 255;
        }
        else{
            alpha = (int)(grid.getCase(i, j).pheromone.getIntensity()) * 50;
        }
        if(alpha > 255){
            alpha = 255;
        }
        if(alpha <0 ){
            alpha = 1;
        }
        Color c = new Color(0,255,150,alpha);
        g2d.setColor(c);
        g2d.fillRect(i*20 +100, j*20+100, 20, 20);

        //g2d.drawString(Double.toString(grid.getCase(i, j).pheromone.getIntensity()), i*20 +100, j*20+120);
    
    }



}