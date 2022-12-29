
package tank;


public class DrowBlocks {
    int x,y;
    boolean isAlive;
       int size=10;
       boolean isWhite=false;

    public DrowBlocks (int x,int y,int size){

        this.x=x;
        this.y=y;
        isAlive=true;
 this.size=size;
    };
    public DrowBlocks (int x,int y,int size,boolean isWhite){
this.isWhite=isWhite;
        this.x=x;
        this.y=y;
        isAlive=true;
 this.size=size;
    };
     public DrowBlocks (int x,int y,boolean isWhite){
this.isWhite=isWhite;
        this.x=x;
        this.y=y;
        isAlive=true;
    };
     public DrowBlocks (int x,int y){

               this.x=x;
            this.y=y;
             isAlive=true;
           
    };
}
