package main;

import java.awt.event.*;
import javax.media.opengl.*;

/**
 * This calss is responsible to transform world
 * according to key pressed by user
 * @author Darek
 */
public class Camera {

    /**
     * speed of movment and rotation
     */
    private float speed;
    /**
     * angle nof rotation in X dimmension
     */
    private double angleX;
    /**
     * angle of rotation in Y dimmension
     */
    private double angleY;
    /**
     * specify distance form middle of world (0,0,0)
     */
    private float dist;

    /**
     * class non-parameters constructor, initaialize all varialbles
     */
    public Camera() {
        speed = 0.9f;
        angleX = 0.0f;
        angleY = 0.0f;
        dist = -14;
    }

    /**
     * sets new view according to params
     * @param aY angle Y dim
     * @param aX angle X dim
     */
    public void setView(float aY, float aX) {
        angleY = aY;
        angleX = aX;
    }

    /**
     * updates camera's angle and distance
     * @param keys array of keys pressed
     */
    public void Update(boolean[] keys) {
        if (keys[KeyEvent.VK_W] == true) {
            if (dist < -8) {
                dist += speed * 0.3;
            }
        }
        if (keys[KeyEvent.VK_S] == true) {
            dist -= speed * 0.3;
        }

        if (keys[KeyEvent.VK_A] == true) {
            angleY -= (speed * 2.0f);
        }
        if (keys[KeyEvent.VK_D] == true) {
            angleY += (speed * 2.0f);
        }

        if (keys[KeyEvent.VK_Q] == true) {
            angleX -= (speed * 1.4f);
        }
        if (keys[KeyEvent.VK_E] == true) {
            angleX += (speed * 1.4f);
        }
    }

    /**
     * change world accordin angles and dist
     * @param gl JOGL's opengl object
     */
    public void LookAt(GL gl) {
        gl.glTranslatef(0, 0, dist);
        gl.glRotatef((float) angleX, 0, 0, 1);
        gl.glRotatef((float) angleY + 90, 0, 1, 0);
    }
}
