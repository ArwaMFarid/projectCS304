package tank;
import tank.Directions;
import java.util.Random;

public class enemy {
     int x = 60, size = 7;
    int y = 50 - size;
    int step = 2;
    public enemy(){
    }
    public enemy(int a,int b){
    x = a;
    y = b - size;
    }
    Directions direction = Directions.right;
      boolean isAlive=true;

    public void Random() {
        int rnd = new Random().nextInt(4);

        if (rnd == 0) {
            direction = Directions.up;
        }
        if (rnd == 1) {
            direction = Directions.down;
        }
        if (rnd == 2) {
            direction = Directions.left;
        }
        if (rnd == 3) {
            direction = Directions.right;
        }

    }
      public void moveup(){
     direction=Directions.up;
     y-=step;
     
     }
      
     public void moveDown(){
     direction=Directions.down;
     y+=step;
     
     }
      
     public void moveRight(){
     direction=Directions.right;
     x+=step;
     
     }
      
     public void moveLeft(){
     direction=Directions.left;
     x-=step;
     
     }  
     public void NoMove(){
     direction=Directions.ideal;
   
     
     }

}
