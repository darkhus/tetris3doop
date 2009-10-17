package main;



import java.awt.event.*;
import javax.media.opengl.glu.*;
import javax.media.opengl.*;

public class Camera
{
	private float speed;
	private GLU glu;
	
	public Vector v_pos;
	private Vector v_view;
	private Vector v_up;	
   
	private double angleX;
	private double angleY;

	public Camera()
   	{
    	glu = new GLU();
    	v_pos = new Vector();
    	v_view = new Vector();
    	v_up = new Vector(0.0f, 1.0f, 0.0f);
    	speed = 0.18f;
    	angleX = 0.0f;
    	angleY = 0.0f;
    }

    public void setPosition(float X, float Y, float Z)
    {
    	v_pos.x = X;
    	v_pos.y = Y;
    	v_pos.z = Z;
    }

  	private void rotateCamera()
  	{
  		if (angleY < -360 || angleY > 360) angleY = 0;
        Vector v_temp = v_pos.returnVector(); 
        v_pos.set(0, 0, 1);
		v_pos.rotateAroundX((float)angleX);
		v_pos.rotateAroundY((float)angleY);		
        v_pos.mulByScalar(v_temp.magnitude());
  	}

    public void setView(float aY, float aX)
    {
        angleY = aY;
    	angleX = aX;
    }

    public Vector getPos()
    {
    	return new Vector(v_pos.x,v_pos.y,v_pos.z);
    }

    public Vector getView()
    {
    	return new Vector(v_view.x, v_view.y, v_view.z);
    }

    public float getAngleX()
    {
    	return (float)angleX;
    }
    public float getAngleY()
    {
    	return (float)angleY;
    }

  	public void Update( boolean[] keys)
  	{
		if ( keys[KeyEvent.VK_W] == true) moveCamera(speed/100);
		if ( keys[KeyEvent.VK_S] == true) moveCamera(-speed/100);

		if (keys[KeyEvent.VK_A] == true) angleY-=(speed*2.0f);
     	if (keys[KeyEvent.VK_D] == true) angleY+=(speed*2.0f);

     	if (keys[KeyEvent.VK_Q] == true) angleX-=(speed*1.4f);
     	if (keys[KeyEvent.VK_E] == true) angleX+=(speed*1.4f);
     	if (angleX <=-80) angleX = -80;
        if (angleX >= 80) angleX = 80;
        if (angleY < -360 || angleY > 360) angleY = 0;        

     	rotateCamera();
  	}

  	private void moveCamera(float speed)
  	{
  		Vector v_direction = new Vector().subVectorFromVector(v_view,v_pos);
 		v_pos.y += v_direction.y * speed;
 		v_pos.x += v_direction.x * speed;
 		v_pos.z += v_direction.z * speed;
  	}

  	public void LookAt()
 	{ 
  		glu.gluLookAt(v_pos.x, v_pos.y, v_pos.z,
  				v_view.x, v_view.y, v_view.z,
  				v_up.x, v_up.y, v_up.z);
  	}

	public void setAngleY(float ay)
	{
		angleY = ay;
	}
}