/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tank;
import tank.Directions2;

/**
 *
 * @author r
 */


public class enemy2p {
    int x , size = 7;
    int y ;
    int step = 2;
    public enemy2p(int a,int b){
    x = a;
    y = b - size;
    }
    
    Directions2 direction2 = Directions2.W;
      boolean isAlive=true;
      
      
      public void moveup2(){
     direction2=Directions2.W;
     y-=step;
     
     }
      
     public void moveDown2(){
     direction2=Directions2.S;
     y+=step;
     
     }
      
     public void moveRight2(){
     direction2=Directions2.D;
     x+=step;
     
     }
      
     public void moveLeft2(){
     direction2=Directions2.A;
     x-=step;
     
     }  
     public void NoMove2(){
     direction2=Directions2.ideal;
   
     
     }  
}
