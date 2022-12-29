package tank;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class AnimLev3 extends JFrame {

    public static void main(String[] args) {
        new AnimLev3();
    }
    static GLCanvas glcanvas;
    static Animator animator;

    public AnimLev3() {

        AnimGLEventListener3 listener = new AnimGLEventListener3();
        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener(listener);
        glcanvas.addKeyListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(50);
        animator.add(glcanvas);
        animator.start();
        listener.frame=this;

        setTitle("Tank");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }
}
