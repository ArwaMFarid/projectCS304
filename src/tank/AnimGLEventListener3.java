package tank;

import com.sun.opengl.util.GLUT;
import tank.DrowBlocks;
import tank.bullet;
import com.sun.opengl.util.GLUT;
import java.awt.Component;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;
import tank.Texture.AnimListener;
import tank.Texture.TextureReader;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.*;

import java.util.BitSet;
import java.util.Random;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import static tank.AnimGLEventListener1.enm;
import static tank.playMusic.playMusic;

public class AnimGLEventListener3 extends AnimListener {

    public JFrame frame = null;
//     long Timer = 3600;
//    GLUT g = new GLUT();

    ArrayList<bullet> bul2 = new ArrayList<bullet>();

    static enemy2p enm = new enemy2p(60, 50);
    Tank tank = new Tank();

    DrowBlocks block;
    ArrayList<DrowBlocks> blocks = new ArrayList<DrowBlocks>();

    private void drawBlocks(GL gl) {
        blocks.forEach(b -> {
            if (b.isAlive) {
                Shared.DrawSprite(gl, b.x, b.y, b.isWhite ? 6 : 5, textures, b.size);
            }
        });

    }

    private void drawTank(GL gl) {
        Shared.DrawSprite(gl, tank.x, tank.y, animationIndex, textures, tank.size);
    }

    Directions direction = Directions.up;// 0=up ,1=right,2=down ,3=left
    Directions2 direction2 = Directions2.W;// 0=up ,1=right,2=down ,3=left
    boolean isDead = false;

    int animationIndex = 0, animationIndex2 = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth - 10, y = maxHeight / 99;
    int enx = 0, eny = 0;
    Directions enmdir = Directions.up;

    int animationenemy = 8;
    ArrayList<bullet> bullets = new ArrayList<bullet>();
    // ArrayList<enmbullet>enmbullets=new ArrayList<enmbullet>();

    String textureNames[] = {"player1_tank_up.png", "player1_tank_down.png", "player1_tank_left.png",
        "player1_tank_right.png", "bullet.png", "break_brick.png", "solid_brick.png", "enemy up.png",
        "player2_tank_right.png", "player2_tank_down.png", "player2_tank_up.png", "player2_tank_left.png", "index.jpg"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        FillBlocks();
        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(assetsFolderName + "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        gl.glOrtho(0, maxWidth, maxHeight, 0, -1, 1);
        gl.glClearColor(0, 0, 0, 1);

    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        DrawBackground(gl);
        drawBlocks(gl);
        drawTank(gl);
        drawBullets(gl);
        darwEnemy(gl);

        handleTankMove();
        clearBlocksArray();
        handleBulletFired();
        handleCollesionBullets();
        clearBulletsArray();
        handelTank2Bullet();
        handleEnemyMove();
        handleCollesisonTank();
        checkWinner();
//        gl.glRasterPos2i(0, 50);
//        g.glutBitmapString(8, Integer.toString((int) (Timer--)/60));

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    //Drow blocks
    public void FillBlocks() {

      //right
        block = new DrowBlocks(70, 100);
        blocks.add(block);
        block = new DrowBlocks(50, 50,true);
        blocks.add(block);
        block = new DrowBlocks(70, 90);
        blocks.add(block);
        block = new DrowBlocks(70, 80);
        blocks.add(block);
        block = new DrowBlocks(70, 70);
        blocks.add(block);
        block = new DrowBlocks(70, 50);
        blocks.add(block);
        block = new DrowBlocks(80, 50, true);
        blocks.add(block);
        block = new DrowBlocks(90, 50, true);
        blocks.add(block);

        block = new DrowBlocks(70, 30);
        blocks.add(block);
        block = new DrowBlocks(70, 20);
        blocks.add(block);
        block = new DrowBlocks(60, 30, true);
        blocks.add(block);
        block = new DrowBlocks(60, 20);
        blocks.add(block);
        block = new DrowBlocks(70, 40, true);
        blocks.add(block);
        block = new DrowBlocks(70, 10);
        blocks.add(block);
        block = new DrowBlocks(70, 0);
        blocks.add(block);
        //left
        block = new DrowBlocks(15, 100);
        blocks.add(block);
        block = new DrowBlocks(15, 90);
        blocks.add(block);

        block = new DrowBlocks(15, 80, true);
        blocks.add(block);
        block = new DrowBlocks(15, 70, true);
        blocks.add(block);
        block = new DrowBlocks(15, 60, true);
        blocks.add(block);
        block = new DrowBlocks(15, 50);
        blocks.add(block);

        block = new DrowBlocks(15, 40);
        blocks.add(block);
        block = new DrowBlocks(15, 30);
        blocks.add(block);
        block = new DrowBlocks(40, 50);
        blocks.add(block);
        block = new DrowBlocks(40, 60, true);
        blocks.add(block);
        block = new DrowBlocks(40, 70);
        blocks.add(block);

    }

    //move Roundom enemy
    public int getRandomDirection() {
        int random = new Random().nextInt(4);
        return random;
    }

    public void DrawBackground(GL gl) {
        Shared.DrawSprite(gl, 0, 0, textures.length - 1, textures, maxHeight);
    }

    /*
     * KeyListener
     */
    public void handleBulletFired() {

        if (isKeyPressed(KeyEvent.VK_SPACE)) {
 String filepath = "shoot.wav";
            playMusic(filepath);
            bullets.add(new bullet(tank.direction, tank.x, tank.y));

        }

        if (isKeyPressed(KeyEvent.VK_X)) {
 String filepath = "shoot.wav";
            playMusic(filepath);
            bul2.add(new bullet(enm.direction2, enm.x, enm.y));

        }

    }

    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }

    

    private void handleTankMove() {
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (tank.y + tank.step + tank.size > maxHeight) {
                return;
            }

            for (DrowBlocks b : blocks) {
                int f = tank.y + tank.step + tank.size;
                if (f >= b.y && f <= b.y + b.size - 1) {
                    int lB = b.x;
                    int rB = b.x + b.size;
                    int rT = tank.x + tank.size - 1;
                    int lT = tank.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int f = tank.y + tank.step + tank.size;
            if (f >= enm.y && f <= enm.y + enm.size-1) {
                int lB = enm.x;
                int rB = enm.x + enm.size-1;
                int rT = tank.x + tank.size-1;
                int lT = tank.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }

            tank.moveDown();
            animationIndex = 1;

        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (tank.y - tank.step < 0) {
                return;
            }
            for (DrowBlocks b : blocks) {
                int f = tank.y - tank.step;
                if (f >= b.y && f <= b.y + b.size - 1) {
                    int lB = b.x;
                    int rB = b.x + b.size;
                    int rT = tank.x + tank.size - 1;
                    int lT = tank.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int f = tank.y - tank.step;
            if (f >= enm.y && f <= enm.y + enm.size-1) {
                int lB = enm.x;
                int rB = enm.x + enm.size-1;
                int rT = tank.x + tank.size-1;
                int lT = tank.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }
            tank.moveup();
            animationIndex = 0;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {

            if (tank.x + tank.step + tank.size > maxWidth) {
                return;
            }

            for (DrowBlocks b : blocks) {
                int f = tank.x + tank.step + tank.size;
                if (f >= b.x && f <= b.x + b.size - 1) {
                    int tB = b.y;
                    int dB = b.y + b.size;
                    int tT = tank.y;
                    int dT = tank.y + tank.size - 1;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int f = tank.x + tank.step + tank.size;
            if (f >= enm.x && f <= enm.x + enm.size-1) {
                int tB = enm.y;
                int dB = enm.y + enm.size-1;
                int tT = tank.y;
                int dT = tank.y + tank.size-1;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            tank.moveRight();
            animationIndex = 3;
        }
        if (isKeyPressed(KeyEvent.VK_LEFT)) {

            if (tank.x - tank.step < 0) {
                return;
            }

            for (DrowBlocks b : blocks) {
                int f = tank.x - tank.step;
                if (f >= b.x && f <= b.x + b.size - 1) {
                    int tB = b.y;
                    int dB = b.y + b.size-1;
                    int tT = tank.y;
                    int dT = tank.y + tank.size - 1;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int f = tank.x - tank.step;
            if (f >= enm.x && f <= enm.x + enm.size-1) {
                int tB = enm.y;
                int dB = enm.y + enm.size-1;
                int tT = tank.y;
                int dT = tank.y + tank.size-1;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }

            tank.moveLeft();
            animationIndex = 2;
        }

    }

    private void clearBlocksArray() {
        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isAlive) {
                blocks.remove(i--);
            }

        }

    }

    private void drawBullets(GL gl) {
        for (bullet b : bullets) {
            switch (b.direction) {
                case up:
                    Shared.DrawSprite(gl, b.x + tank.size / 3, b.y--, 4, textures, 3);
                    break;
                case down:
                    Shared.DrawSprite(gl, b.x + tank.size / 3, b.y++, 4, textures, 3);
                    break;
                case right:
                    Shared.DrawSprite(gl, b.x++, b.y + tank.size / 3, 4, textures, 3);
                    break;
                case left:
                    Shared.DrawSprite(gl, b.x--, b.y + tank.size / 3, 4, textures, 3);
                    break;

            }

        }
        for (bullet b : bul2) {
            switch (b.direction2) {
                case W:
                    Shared.DrawSprite(gl, b.x + tank.size / 3, b.y--, 4, textures, 3);
                    break;
                case S:
                    Shared.DrawSprite(gl, b.x + tank.size / 3, b.y++, 4, textures, 3);
                    break;
                case D:
                    Shared.DrawSprite(gl, b.x++, b.y + tank.size / 3, 4, textures, 3);
                    break;
                case A:
                    Shared.DrawSprite(gl, b.x--, b.y + tank.size / 3, 4, textures, 3);
                    break;

            }
        }
    }

    private void handleCollesisonTank() {
        for (bullet bullet : bullets) {

            if (bullet.direction == Directions.up) {
                int f = bullet.y;
                if (f >= enm.y && f <= enm.y + enm.size) {
                    int lB = enm.x;
                    int rB = enm.x + enm.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                    }
                }
            }
            if (bullet.direction == Directions.down) {

                int f = bullet.y;
                if (f >= enm.y && f <= enm.y + enm.size) {
                    int lB = enm.x;
                    int rB = enm.x + enm.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                    }
                }

            }
            if (bullet.direction == Directions.right) {
                int f = bullet.x;
                if (f >= enm.x && f <= enm.x + enm.size) {
                    int tB = enm.y;
                    int dB = enm.y + enm.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;

                            enm.isAlive = false;

                        }

                    }
                }
            }
            if (bullet.direction == Directions.left) {
                int f = bullet.x;
                if (f >= enm.x && f <= enm.x + enm.size) {
                    int tB = enm.y;
                    int dB = enm.y + enm.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                    }
                    if (dBu <= dB && dBu >= tB) {
                        bullet.collesion = true;

                        enm.isAlive = false;

                    }
                }
            }

        }
        for (bullet bullet : bul2) {

            if (bullet.direction2 == Directions2.W) {
                int f = bullet.y;
                if (f >= tank.y && f <= tank.y + tank.size) {
                    int lB = tank.x;
                    int rB = tank.x + tank.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        System.out.println("ffff");
                        tank.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        tank.isAlive = false;

                    }
                }
            }
            if (bullet.direction2 == Directions2.S) {

                int f = bullet.y;
                if (f >= tank.y && f <= tank.y + tank.size) {
                    int lB = tank.x;
                    int rB = tank.x + tank.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        tank.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        tank.isAlive = false;

                    }
                }

            }
            if (bullet.direction2 == Directions2.D) {
                int f = bullet.x;
                if (f >= tank.x && f <= tank.x + tank.size) {
                    int tB = tank.y;
                    int dB = tank.y + tank.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        tank.isAlive = false;

                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;

                            tank.isAlive = false;

                        }

                    }
                }
            }
            if (bullet.direction2 == Directions2.A) {
                int f = bullet.x;
                if (f >= tank.x && f <= tank.x + tank.size) {
                    int tB = tank.y;
                    int dB = tank.y + tank.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        tank.isAlive = false;

                    }
                    if (dBu <= dB && dBu >= tB) {
                        bullet.collesion = true;

                        tank.isAlive = false;

                    }
                }
            }

        }

    }

    private void handleCollesionBullets() {

        for (DrowBlocks block : blocks) {
            for (bullet bullet : bullets) {

                if (bullet.direction == Directions.up) {
                    int f = bullet.y;
                    if (f >= block.y && f <= block.y + block.size) {
                        int lB = block.x;
                        int rB = block.x + block.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                    }
                }
                if (bullet.direction == Directions.down) {

                    int f = bullet.y;
                    if (f >= block.y && f <= block.y + block.size) {
                        int lB = block.x;
                        int rB = block.x + block.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                    }

                }
                if (bullet.direction == Directions.right) {
                    int f = bullet.x;
                    if (f >= block.x && f <= block.x + block.size) {
                        int tB = block.y;
                        int dB = block.y + block.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                            if (dBu <= dB && dBu >= tB) {
                                bullet.collesion = true;
                                if (block.isWhite) {

                                    System.out.println("ffff");
                                    block.isAlive = false;

                                }
                            }

                        }
                    }
                }
                if (bullet.direction == Directions.left) {
                    int f = bullet.x;
                    if (f >= block.x && f <= block.x + block.size) {
                        int tB = block.y;
                        int dB = block.y + block.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                    }
                }

            }
        }

        for (DrowBlocks block : blocks) {
            for (bullet bullet : bul2) {

                if (bullet.direction2 == Directions2.W) {
                    int f = bullet.y;
                    if (f >= block.y && f <= block.y + block.size) {
                        int lB = block.x;
                        int rB = block.x + block.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                    }
                }
                if (bullet.direction2 == Directions2.S) {

                    int f = bullet.y;
                    if (f >= block.y && f <= block.y + block.size) {
                        int lB = block.x;
                        int rB = block.x + block.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                    }

                }
                if (bullet.direction2 == Directions2.D) {
                    int f = bullet.x;
                    if (f >= block.x && f <= block.x + block.size) {
                        int tB = block.y;
                        int dB = block.y + block.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                            if (dBu <= dB && dBu >= tB) {
                                bullet.collesion = true;
                                if (block.isWhite) {

                                    System.out.println("ffff");
                                    block.isAlive = false;

                                }
                            }

                        }
                    }
                }
                if (bullet.direction2 == Directions2.A) {
                    int f = bullet.x;
                    if (f >= block.x && f <= block.x + block.size) {
                        int tB = block.y;
                        int dB = block.y + block.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;
                            if (block.isWhite) {

                                System.out.println("ffff");
                                block.isAlive = false;

                            }
                        }
                    }
                }

            }
        }

    }

    private void clearBulletsArray() {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).x >= 100 || bullets.get(i).y >= 100
                    || bullets.get(i).x < 0 || bullets.get(i).y < 0 || bullets.get(i).collesion) {

                bullets.remove(i--);
            }

        }
        for (int i = 0; i < bul2.size(); i++) {
            if (bul2.get(i).x >= 100 || bul2.get(i).y >= 100
                    || bul2.get(i).x < 0 || bul2.get(i).y < 0 || bul2.get(i).collesion) {

                bul2.remove(i--);
            }

        }
    }

    private void handelTank2Bullet() {
        //bullets.add(new bullet(tank2.direction, tank2.x, tank2.y));
    }

    private void darwEnemy(GL gl) {
        Shared.DrawSprite(gl, enm.x, enm.y, animationIndex2, textures, enm.size);

    }

    private void handleEnemyMove() {

        if (isKeyPressed(KeyEvent.VK_S)) {
           
            if (enm.y + enm.step + enm.size > maxHeight) {
                return;
            }

            for (DrowBlocks b : blocks) {
                int f = enm.y + enm.step + enm.size;
                if (f >= b.y && f <= b.y + b.size-1) {
                    int lB = b.x;
                    int rB = b.x + b.size-1;
                    int rT = enm.x + enm.size-1;
                    int lT = enm.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int c = enm.y + enm.step + enm.size;
            if (c >= tank.y && c <= tank.y + tank.size-1) {
                int lB = tank.x;
                int rB = tank.x + tank.size-1;
                int rT = enm.x + enm.size-1;
                int lT = enm.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }

            enm.moveDown2();
            animationIndex2 = 9;

        }
        if (isKeyPressed(KeyEvent.VK_W)) {
            if (enm.y - enm.step < 0) {
                return;
            }
            for (DrowBlocks b : blocks) {
                int f = enm.y - enm.step;
                if (f >= b.y && f <= b.y + b.size-1) {
                    int lB = b.x;
                    int rB = b.x + b.size-1;
                    int rT = enm.x + enm.size-1;
                    int lT = enm.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int m = enm.y - enm.step;
            if (m >= tank.y && m <= tank.y + tank.size-1) {
                int lB = tank.x;
                int rB = tank.x + tank.size-1;
                int rT = enm.x + enm.size-1;
                int lT = enm.x;

                if (rT >= lB && rT <= rB) {
                    System.out.println("hii");
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    System.out.println("oo");
                    return;
                }
            }
            enm.moveup2();
            animationIndex2 = 10;
        }
        if (isKeyPressed(KeyEvent.VK_D)) {

            if (enm.x + enm.step + enm.size > maxWidth) {
                return;
            }

            for (DrowBlocks b : blocks) {
                int f = enm.x + enm.step + enm.size;
                if (f >= b.x && f <= b.x + b.size-1) {
                    int tB = b.y;
                    int dB = b.y + b.size-1;
                    int tT = enm.y;
                    int dT = enm.y + enm.size-1;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int x = enm.x + enm.step + enm.size;
            if (x >= tank.x && x <= tank.x + tank.size-1) {
                int tB = tank.y;
                int dB = tank.y + tank.size-1;
                int tT = enm.y;
                int dT = enm.y + enm.size-1;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            enm.moveRight2();
            animationIndex2 = 8;
        }
        if (isKeyPressed(KeyEvent.VK_A)) {

            if (enm.x - enm.step < 0) {
                return;
            }

            for (DrowBlocks b : blocks) {
                int f = enm.x - enm.step;
                if (f >= b.x && f <= b.x + b.size-1) {
                    int tB = b.y;
                    int dB = b.y + b.size-1;
                    int tT = enm.y;
                    int dT = enm.y + enm.size-1;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int n = enm.x - enm.step;
            if (n >= tank.x && n <= tank.x + tank.size-1) {
                int tB = tank.y;
                int dB = tank.y + tank.size-1;
                int tT = enm.y;
                int dT = enm.y + enm.size-1;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }

            enm.moveLeft2();
            animationIndex2 = 11;
        }

    }

    private void checkWinner() {
//if(Timer == 0){
//    String filepath = "ooh-123103.wav";
//            playMusic(filepath);
//            frame.dispose();
//             new GameOver().setVisible(true);
//        }
        if (!tank.isAlive) {
            String filepath = "winer.wav";
            playMusic(filepath);
            // JOptionPane.showMessageDialog(null, "Player2 is  winner");
            frame.dispose();
            new win2().setVisible(true);
        }
        if (!enm.isAlive) {
            String filepath = "winer.wav";
            playMusic(filepath);
            //JOptionPane.showMessageDialog(null, "Winner");
            frame.dispose();
            new win1().setVisible(true);

        }

    }

}
