/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tank;

import com.sun.opengl.util.GLUT;
import tank.*;
import tank.enemy;

import tank.Texture.AnimListener;
import tank.Texture.TextureReader;
import com.sun.opengl.util.GLUT;
import java.awt.Component;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.swing.JOptionPane;
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
import tank.GameOver;
import tank.Win;
import static tank.playMusic.playMusic;

/**
 *
 * @author r
 */
public class AnimGLEventListener2 extends AnimListener {

    public JFrame frame = null;
//     long Timer = 3600;
//    GLUT g = new GLUT();

    ArrayList<bullet> bul = new ArrayList<bullet>();
   ArrayList<bullet> bu2 = new ArrayList<bullet>();

    Timer timer = new Timer(1000, (e) -> {
        enm1.Random();
        enm2.Random();

        

    });
    Timer timer2 = new Timer(3000, (e) -> {

        bul.add(new bullet(enm1.direction, enm1.x, enm1.y));
        bul.add(new bullet(enm2.direction, enm2.x, enm2.y));
          
    });
    static enemy enm1 = new enemy(60, 50);
    static enemy enm2 = new enemy(8, 90);
    Tank tank = new Tank();

    DrowBlocks block1;
    ArrayList<DrowBlocks> blocks1 = new ArrayList<DrowBlocks>();

    private void drawBlocks(GL gl) {
        blocks1.forEach(b1 -> {
            if (b1.isAlive) {
                Shared.DrawSprite(gl, b1.x, b1.y, b1.isWhite ? 6 : 5, textures, b1.size);
            }
        });

    }

    private void drawTank(GL gl) {
        Shared.DrawSprite(gl, tank.x, tank.y, animationIndex, textures, tank.size);
    }

    Directions direction = Directions.up;// 0=up ,1=right,2=down ,3=left
    boolean isDead = false;

    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int x = maxWidth - 10, y = maxHeight / 99;
    int enx = 0, eny = 0;
    Directions enmdir = Directions.up;
    int animationenemy = 8;
    ArrayList<bullet> bullets = new ArrayList<bullet>();

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
        timer.start();
        timer2.start();
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        DrawBackground(gl);
        drawBlocks(gl);
        drawTank(gl);
        drawBullets(gl);
        darwEnemy1(gl);
        darwEnemy2(gl);

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
        block1 = new DrowBlocks(80, 100);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 92);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 84);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 76);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 68, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 60, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(96, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(88, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(72, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(64, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(56, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(48, 52, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(40, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(40, 60);
        blocks1.add(block1);
        block1 = new DrowBlocks(40, 68);
        blocks1.add(block1);
        block1 = new DrowBlocks(40, 76);
        blocks1.add(block1);
        block1 = new DrowBlocks(32, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 44);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 36, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 28, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 20);
        blocks1.add(block1);
        block1 = new DrowBlocks(80, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(72, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(64, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(64, 4, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(64, 0, true);
        blocks1.add(block1);

        //left
        block1 = new DrowBlocks(40, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(32, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(24, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(16, 12);
        blocks1.add(block1);
        block1 = new DrowBlocks(8, 12, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(0, 12, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(24, 20);
        blocks1.add(block1);
        block1 = new DrowBlocks(24, 28, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(24, 36, true);
        blocks1.add(block1);
        block1 = new DrowBlocks(24, 44);
        blocks1.add(block1);
        block1 = new DrowBlocks(24, 52);
        blocks1.add(block1);
        block1 = new DrowBlocks(16, 76);
        blocks1.add(block1);
        block1 = new DrowBlocks(16, 84);
        blocks1.add(block1);
        block1 = new DrowBlocks(16, 92);
        blocks1.add(block1);

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

    public static void main(String[] args) {
        new AnimLev2();
    }

    private void handleTankMove() {
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (tank.y + tank.step + tank.size > maxHeight) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = tank.y + tank.step + tank.size;
                if (f >= b.y && f <= b.y + b.size-1) {
                    int lB = b.x;
                    int rB = b.x + b.size-1;
                    int rT = tank.x + tank.size-1;
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
            if (f >= enm1.y && f <= enm1.y + enm1.size-1) {
                int lB = enm1.x;
                int rB = enm1.x + enm1.size-1;
                int rT = tank.x + tank.size-1;
                int lT = tank.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }
            int c = tank.y + tank.step + tank.size;
            if (c >= enm2.y && c <= enm2.y + enm2.size-1) {
                int lB = enm2.x;
                int rB = enm2.x + enm2.size-1;
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
            for (DrowBlocks b : blocks1) {
                int f = tank.y - tank.step;
                if (f >= b.y && f <= b.y + b.size-1) {
                    int lB = b.x;
                    int rB = b.x + b.size-1;
                    int rT = tank.x + tank.size-1;
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
            if (f >= enm1.y && f <= enm1.y + enm1.size-1) {
                int lB = enm1.x;
                int rB = enm1.x + enm1.size-1;
                int rT = tank.x + tank.size-1;
                int lT = tank.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }
            int n = tank.y - tank.step;
            if (n >= enm2.y && n <= enm2.y + enm2.size-1) {
                int lB = enm2.x;
                int rB = enm2.x + enm2.size-1;
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

            for (DrowBlocks b : blocks1) {
                int f = tank.x + tank.step + tank.size;
                if (f >= b.x && f <= b.x + b.size-1) {
                    int tB = b.y;
                    int dB = b.y + b.size-1;
                    int tT = tank.y;
                    int dT = tank.y + tank.size-1;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int f = tank.x + tank.step + tank.size;
            if (f >= enm1.x && f <= enm1.x + enm1.size-1) {
                int tB = enm1.y;
                int dB = enm1.y + enm1.size-1;
                int tT = tank.y;
                int dT = tank.y + tank.size-1;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            int l = tank.x + tank.step + tank.size;
            if (l >= enm2.x && l <= enm2.x + enm2.size-1) {
                int tB = enm2.y;
                int dB = enm2.y + enm2.size-1;
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

            for (DrowBlocks b : blocks1) {
                int f = tank.x - tank.step;
                if (f >= b.x && f <= b.x + b.size-1) {
                    int tB = b.y;
                    int dB = b.y + b.size-1;
                    int tT = tank.y;
                    int dT = tank.y + tank.size-1;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int f = tank.x - tank.step;
            if (f >= enm1.x && f <= enm1.x + enm1.size-1) {
                int tB = enm1.y;
                int dB = enm1.y + enm1.size-1;
                int tT = tank.y;
                int dT = tank.y + tank.size-1;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            int v = tank.x - tank.step;
            if (v >= enm2.x && v <= enm2.x + enm2.size-1) {
                int tB = enm2.y;
                int dB = enm2.y + enm2.size-1;
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
        for (int i = 0; i < blocks1.size(); i++) {
            if (!blocks1.get(i).isAlive) {
                blocks1.remove(i--);
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
        for (bullet b : bul) {
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
    }

    private void handleCollesisonTank() {
        for (bullet bullet : bullets) {

            if (bullet.direction == Directions.up) {
                int f = bullet.y;
                if (f >= enm1.y && f <= enm1.y + enm1.size) {
                    int lB = enm1.x;
                    int rB = enm1.x + enm1.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                    }
                }
            }
            if (bullet.direction == Directions.down) {

                int f = bullet.y;
                if (f >= enm1.y && f <= enm1.y + enm1.size) {
                    int lB = enm1.x;
                    int rB = enm1.x + enm1.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                    }
                }

            }
            if (bullet.direction == Directions.right) {
                int f = bullet.x;
                if (f >= enm1.x && f <= enm1.x + enm1.size) {
                    int tB = enm1.y;
                    int dB = enm1.y + enm1.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;

                            enm1.isAlive = false;

                        }

                    }
                }
            }
            if (bullet.direction == Directions.left) {
                int f = bullet.x;
                if (f >= enm1.x && f <= enm1.x + enm1.size) {
                    int tB = enm1.y;
                    int dB = enm1.y + enm1.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                    }
                    if (dBu <= dB && dBu >= tB) {
                        bullet.collesion = true;

                        enm1.isAlive = false;

                    }
                }
            }

        }
         for (bullet bullet : bullets) {

            if (bullet.direction == Directions.up) {
                int f = bullet.y;
                if (f >= enm2.y && f <= enm2.y + enm2.size) {
                    int lB = enm2.x;
                    int rB = enm2.x + enm2.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                    }
                }
            }
            if (bullet.direction == Directions.down) {

                int f = bullet.y;
                if (f >= enm2.y && f <= enm2.y + enm2.size) {
                    int lB = enm2.x;
                    int rB = enm2.x + enm2.size;
                    int rBu = bullet.x + 3;
                    int lBu = bullet.x;

                    if (rBu >= lB && rBu <= rB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                    }
                    if (lBu >= lB && lBu <= rB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                    }
                }

            }
            if (bullet.direction == Directions.right) {
                int f = bullet.x;
                if (f >= enm2.x && f <= enm2.x + enm2.size) {
                    int tB = enm2.y;
                    int dB = enm2.y + enm2.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;

                            enm2.isAlive = false;

                        }

                    }
                }
            }
            if (bullet.direction == Directions.left) {
                int f = bullet.x;
                if (f >= enm2.x && f <= enm2.x + enm2.size) {
                    int tB = enm2.y;
                    int dB = enm2.y + enm2.size;
                    int tBu = bullet.y;
                    int dBu = bullet.y + 3;

                    if (tBu <= dB && tBu >= tB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                    }
                    if (dBu <= dB && dBu >= tB) {
                        bullet.collesion = true;

                        enm2.isAlive = false;

                    }
                }
            }

        }
        for (bullet bullet : bul) {

            if (bullet.direction == Directions.up) {
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
            if (bullet.direction == Directions.down) {

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
            if (bullet.direction == Directions.right) {
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
            if (bullet.direction == Directions.left) {
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

        for (DrowBlocks block1 : blocks1) {
            for (bullet bullet : bullets) {

                if (bullet.direction == Directions.up) {
                    int f = bullet.y;
                    if (f >= block1.y && f <= block1.y + block1.size) {
                        int lB = block1.x;
                        int rB = block1.x + block1.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                    }
                }
                if (bullet.direction == Directions.down) {

                    int f = bullet.y;
                    if (f >= block1.y && f <= block1.y + block1.size) {
                        int lB = block1.x;
                        int rB = block1.x + block1.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                    }

                }
                if (bullet.direction == Directions.right) {
                    int f = bullet.x;
                    if (f >= block1.x && f <= block1.x + block1.size) {
                        int tB = block1.y;
                        int dB = block1.y + block1.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                            if (dBu <= dB && dBu >= tB) {
                                bullet.collesion = true;
                                if (block1.isWhite) {

                                    System.out.println("ffff");
                                    block1.isAlive = false;

                                }
                            }

                        }
                    }
                }
                if (bullet.direction == Directions.left) {
                    int f = bullet.x;
                    if (f >= block1.x && f <= block1.x + block1.size) {
                        int tB = block1.y;
                        int dB = block1.y + block1.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                    }
                }

            }
        }

        for (DrowBlocks block1 : blocks1) {
            for (bullet bullet : bul) {

                if (bullet.direction == Directions.up) {
                    int f = bullet.y;
                    if (f >= block1.y && f <= block1.y + block1.size) {
                        int lB = block1.x;
                        int rB = block1.x + block1.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                    }
                }
                if (bullet.direction == Directions.down) {

                    int f = bullet.y;
                    if (f >= block1.y && f <= block1.y + block1.size) {
                        int lB = block1.x;
                        int rB = block1.x + block1.size;
                        int rBu = bullet.x + 3;
                        int lBu = bullet.x;

                        if (rBu >= lB && rBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                        if (lBu >= lB && lBu <= rB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                    }

                }
                if (bullet.direction == Directions.right) {
                    int f = bullet.x;
                    if (f >= block1.x && f <= block1.x + block1.size) {
                        int tB = block1.y;
                        int dB = block1.y + block1.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                            if (dBu <= dB && dBu >= tB) {
                                bullet.collesion = true;
                                if (block1.isWhite) {

                                    System.out.println("ffff");
                                    block1.isAlive = false;

                                }
                            }

                        }
                    }
                }
                if (bullet.direction == Directions.left) {
                    int f = bullet.x;
                    if (f >= block1.x && f <= block1.x + block1.size) {
                        int tB = block1.y;
                        int dB = block1.y + block1.size;
                        int tBu = bullet.y;
                        int dBu = bullet.y + 3;

                        if (tBu <= dB && tBu >= tB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

                            }
                        }
                        if (dBu <= dB && dBu >= tB) {
                            bullet.collesion = true;
                            if (block1.isWhite) {

                                System.out.println("ffff");
                                block1.isAlive = false;

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
        for (int i = 0; i < bul.size(); i++) {
            if (bul.get(i).x >= 100 || bul.get(i).y >= 100
                    || bul.get(i).x < 0 || bul.get(i).y < 0 || bul.get(i).collesion) {

                bul.remove(i--);
            }

        }
    }

    private void handelTank2Bullet() {
        //bullets.add(new bullet(tank2.direction, tank2.x, tank2.y));
    }

    private void darwEnemy1(GL gl) {
        Shared.DrawSprite(gl, enm1.x, enm1.y, animationenemy, textures, enm1.size);

    }

    private void darwEnemy2(GL gl) {
        Shared.DrawSprite(gl, enm2.x, enm2.y, animationenemy, textures, enm2.size);

    }

  private void handleEnemyMove() {

        if (enm1.direction == Directions.up) {
            if (enm1.y - enm1.step < 0) {
                return;
            }
            for (DrowBlocks b : blocks1) {
                int f = enm1.y - enm1.step;
                if (f >= b.y && f <= b.y + b.size-1) {
                    int lB = b.x;
                    int rB = b.x + b.size-1;
                    int rT = enm1.x + enm1.size-1;
                    int lT = enm1.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int m = enm1.y - enm1.step;
            if (m >= tank.y && m <= tank.y + tank.size-1) {
                int lB = tank.x;
                int rB = tank.x + tank.size-1;
                int rT = enm1.x + enm1.size-1;
                int lT = enm1.x;

                if (rT >= lB && rT <= rB) {
                    System.out.println("hii");
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    System.out.println("oo");
                    return;
                }
            }
            int o = enm1.y - enm1.step;
            if (o >= enm2.y && o <= enm2.y + enm2.size) {
                int lB = enm2.x;
                int rB = enm2.x + enm2.size;
                int rT = enm1.x + enm1.size;
                int lT = enm1.x;

                if (rT >= lB && rT <= rB) {
                    System.out.println("hii");
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    System.out.println("oo");
                    return;
                }
            }
            enm1.moveup();
            animationenemy = 10;
        }
        if (enm1.direction == Directions.down) {
            if (enm1.y + enm1.step + enm1.size > maxHeight) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = enm1.y + enm1.step + enm1.size;
                if (f >= b.y && f <= b.y + b.size) {
                    int lB = b.x;
                    int rB = b.x + b.size;
                    int rT = enm1.x + enm1.size;
                    int lT = enm1.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int c = enm1.y + enm1.step + enm1.size;
            if (c >= tank.y && c <= tank.y + tank.size) {
                int lB = tank.x;
                int rB = tank.x + tank.size;
                int rT = enm1.x + enm1.size;
                int lT = enm1.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }
            int p = enm1.y + enm1.step + enm1.size;
            if (p >= enm2.y && p <= enm2.y + enm2.size) {
                int lB = enm2.x;
                int rB = enm2.x + enm2.size;
                int rT = enm1.x + enm1.size;
                int lT = enm1.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }

            enm1.moveDown();
            animationenemy = 9;
        }
        if (enm1.direction == Directions.left) {
            if (enm1.x - enm1.step < 0) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = enm1.x - enm1.step;
                if (f >= b.x && f <= b.x + b.size) {
                    int tB = b.y;
                    int dB = b.y + b.size;
                    int tT = enm1.y;
                    int dT = enm1.y + enm1.size;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int n = enm1.x - enm1.step;
            if (n >= tank.x && n <= tank.x + tank.size) {
                int tB = tank.y;
                int dB = tank.y + tank.size;
                int tT = enm1.y;
                int dT = enm1.y + enm1.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            int k = enm1.x - enm1.step;
            if (k >= enm2.x && k <= enm2.x + enm2.size) {
                int tB = enm2.y;
                int dB = enm2.y + enm2.size;
                int tT = enm1.y;
                int dT = enm1.y + enm1.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }

            enm1.moveLeft();
            animationenemy = 11;
        }
        if (enm1.direction == Directions.right) {
            if (enm1.x + enm1.step + enm1.size > maxWidth) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = enm1.x + enm1.step + enm1.size;
                if (f >= b.x && f <= b.x + b.size) {
                    int tB = b.y;
                    int dB = b.y + b.size;
                    int tT = enm1.y;
                    int dT = enm1.y + enm1.size;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int x = enm1.x + enm1.step + enm1.size;
            if (x >= tank.x && x <= tank.x + tank.size) {
                int tB = tank.y;
                int dB = tank.y + tank.size;
                int tT = enm1.y;
                int dT = enm1.y + enm1.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            int u = enm1.x + enm1.step + enm1.size;
            if (u >= enm2.x && u <= enm2.x + enm2.size) {
                int tB = enm2.y;
                int dB = enm2.y + enm2.size;
                int tT = enm1.y;
                int dT = enm1.y + enm1.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            enm1.moveRight();
            animationenemy = 8;
        }

        if (enm2.direction == Directions.up) {
            if (enm2.y - enm2.step < 0) {
                return;
            }
            for (DrowBlocks b : blocks1) {
                int f = enm2.y - enm2.step;
                if (f >= b.y && f <= b.y + b.size) {
                    int lB = b.x;
                    int rB = b.x + b.size;
                    int rT = enm2.x + enm2.size;
                    int lT = enm2.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int m = enm2.y - enm2.step;
            if (m >= tank.y && m <= tank.y + tank.size) {
                int lB = tank.x;
                int rB = tank.x + tank.size;
                int rT = enm2.x + enm2.size;
                int lT = enm2.x;

                if (rT >= lB && rT <= rB) {
                    System.out.println("hii");
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    System.out.println("oo");
                    return;
                }
            }
            int o = enm2.y - enm2.step;
            if (o >= enm1.y && o <= enm1.y + enm1.size) {
                int lB = enm1.x;
                int rB = enm1.x + enm1.size;
                int rT = enm2.x + enm2.size;
                int lT = enm2.x;

                if (rT >= lB && rT <= rB) {
                    System.out.println("hii");
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    System.out.println("oo");
                    return;
                }
            }
            enm2.moveup();
            animationenemy = 10;
        }
        if (enm2.direction == Directions.down) {
            if (enm2.y + enm2.step + enm2.size > maxHeight) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = enm2.y + enm2.step + enm2.size;
                if (f >= b.y && f <= b.y + b.size) {
                    int lB = b.x;
                    int rB = b.x + b.size;
                    int rT = enm2.x + enm2.size;
                    int lT = enm2.x;

                    if (rT >= lB && rT <= rB) {
                        return;
                    }
                    if (lT >= lB && lT <= rB) {
                        return;
                    }
                }
            }
            int c = enm2.y + enm2.step + enm2.size;
            if (c >= tank.y && c <= tank.y + tank.size) {
                int lB = tank.x;
                int rB = tank.x + tank.size;
                int rT = enm2.x + enm2.size;
                int lT = enm2.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }
            int p = enm2.y + enm2.step + enm2.size;
            if (p >= enm1.y && p <= enm1.y + enm1.size) {
                int lB = enm1.x;
                int rB = enm1.x + enm1.size;
                int rT = enm2.x + enm2.size;
                int lT = enm2.x;

                if (rT >= lB && rT <= rB) {
                    return;
                }
                if (lT >= lB && lT <= rB) {
                    return;
                }
            }

            enm2.moveDown();
            animationenemy = 9;
        }
        if (enm2.direction == Directions.left) {
            if (enm2.x - enm2.step < 0) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = enm2.x - enm2.step;
                if (f >= b.x && f <= b.x + b.size) {
                    int tB = b.y;
                    int dB = b.y + b.size;
                    int tT = enm2.y;
                    int dT = enm2.y + enm2.size;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int n = enm2.x - enm2.step;
            if (n >= tank.x && n <= tank.x + tank.size) {
                int tB = tank.y;
                int dB = tank.y + tank.size;
                int tT = enm2.y;
                int dT = enm2.y + enm2.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            int k = enm2.x - enm2.step;
            if (k >= enm1.x && k <= enm1.x + enm1.size) {
                int tB = enm1.y;
                int dB = enm1.y + enm1.size;
                int tT = enm2.y;
                int dT = enm2.y + enm2.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }

            enm2.moveLeft();
            animationenemy = 11;
        }
        if (enm2.direction == Directions.right) {
            if (enm2.x + enm2.step + enm2.size > maxWidth) {
                return;
            }

            for (DrowBlocks b : blocks1) {
                int f = enm2.x + enm2.step + enm2.size;
                if (f >= b.x && f <= b.x + b.size) {
                    int tB = b.y;
                    int dB = b.y + b.size;
                    int tT = enm2.y;
                    int dT = enm2.y + enm2.size;

                    if (tT <= dB && tT >= tB) {
                        return;
                    }
                    if (dT <= dB && dT >= tB) {
                        return;
                    }
                }
            }
            int x = enm2.x + enm2.step + enm2.size;
            if (x >= tank.x && x <= tank.x + tank.size) {
                int tB = tank.y;
                int dB = tank.y + tank.size;
                int tT = enm2.y;
                int dT = enm2.y + enm2.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            int u = enm2.x + enm2.step + enm2.size;
            if (u >= enm1.x && u <= enm1.x + enm1.size) {
                int tB = enm1.y;
                int dB = enm1.y + enm2.size;
                int tT = enm2.y;
                int dT = enm2.y + enm2.size;

                if (tT <= dB && tT >= tB) {
                    return;
                }
                if (dT <= dB && dT >= tB) {
                    return;
                }
            }
            enm2.moveRight();
            animationenemy = 8;
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
            String filepath="ooh-123103.wav";
            playMusic(filepath);
            // JOptionPane.showMessageDialog(null, "Loser");
            frame.dispose();
            new GameOver().setVisible(true);
        }
        if (!enm1.isAlive && !enm2.isAlive) {
             String filepath="winer.wav";
            playMusic(filepath);
            //JOptionPane.showMessageDialog(null, "Winner");
            frame.dispose();
            new Win().setVisible(true);

        }

    }

}
