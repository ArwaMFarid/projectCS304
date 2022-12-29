package tank;

import com.sun.opengl.util.*;
import java.awt.*;
import javax.media.opengl.*;
import javax.swing.*;

public class AnimLev2 extends JFrame {

    public static void main(String[] args) {
        new AnimLev2();
    }
    static GLCanvas glcanvas;
    static Animator animator;

    public AnimLev2() {

        AnimGLEventListener2 listener = new AnimGLEventListener2();
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
