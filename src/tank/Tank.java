/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tank;

/**
 *
 * @author MSI-
 */
public class Tank {
    int size=7;
    int x=100-size,y=100-size;
    int step=2;
    Directions direction=Directions.ideal;
    boolean isAlive=true;
    
    public Tank(int x,int y){
    
    this.x=x;
    this.y=y;
    
    }
     public Tank(){
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
