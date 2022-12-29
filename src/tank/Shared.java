/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tank;

import javax.media.opengl.GL;

/**
 *
 * @author MSI-
 */
public class Shared {
    public static void DrawSprite(GL gl, int x, int y, int index, int[] textures, int size) {
    gl.glEnable(GL.GL_BLEND);
    gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]); // Turn Blending On

    gl.glPushMatrix();
//    gl.glTranslated(-5, -5, 0);
    gl.glBegin(GL.GL_QUADS);
    // Front Face
    gl.glTexCoord2f(0.0f, 0.0f);
    gl.glVertex3f(x, y + size, -1.0f);

    gl.glTexCoord2f(1.0f, 0.0f);
    gl.glVertex3f(x + size, y + size, -1.0f);

    gl.glTexCoord2f(1.0f, 1.0f);
    gl.glVertex3f(x + size, y, -1.0f);

    gl.glTexCoord2f(0.0f, 1.0f);
    gl.glVertex3f(x, y, -1.0f);

    gl.glEnd();
    gl.glPopMatrix();

    gl.glDisable(GL.GL_BLEND);
}
    
}
