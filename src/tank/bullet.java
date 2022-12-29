package tank;
import tank.Directions;
import tank.Directions2;


public class bullet {
    Directions direction;
    Directions2 direction2;
    int x, y;

    boolean collesion=false;
        boolean fired = true;




    bullet(Directions direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;

    }
    bullet(Directions2 direction2, int x, int y) {
        this.direction2 = direction2;
        this.x = x;
        this.y = y;

    }
}
