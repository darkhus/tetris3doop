package main;



import java.awt.event.*;
import javax.media.opengl.*;

public class Camera
{
	private float speed;
	private double angleX;
	private double angleY;
    private float dist;

	public Camera()
   	{
    	speed = 0.9f;
    	angleX = 0.0f;
    	angleY = 0.0f;
        dist = -14;
    }

    public void setView(float aY, float aX)
    {
        angleY = aY;
    	angleX = aX;
    }


  	public void Update( boolean[] keys)
  	{
		if ( keys[KeyEvent.VK_W] == true) if(dist < -8) dist+=speed*0.3;
		if ( keys[KeyEvent.VK_S] == true)  dist-=speed*0.3;

		if (keys[KeyEvent.VK_A] == true) angleY-=(speed*2.0f);
     	if (keys[KeyEvent.VK_D] == true) angleY+=(speed*2.0f);

     	if (keys[KeyEvent.VK_Q] == true) angleX-=(speed*1.4f);
     	if (keys[KeyEvent.VK_E] == true) angleX+=(speed*1.4f);
  	}

  	public void LookAt(GL gl)
 	{
        gl.glTranslatef(0, 0, dist);
        gl.glRotatef((float)angleX, 0, 0, 1);
        gl.glRotatef((float)angleY+90, 0, 1, 0);
  	}

}