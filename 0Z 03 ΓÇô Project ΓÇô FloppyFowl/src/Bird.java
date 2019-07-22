import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird 
{
	private final double SPEED = 2.0;
	private double myX, myY, fall_rate, speedMlt;
	private boolean flapping = false, diving = false, dead = false, isBirdDead = false, deadGameCall = false, deadGameCallAlreadyRun = false;
	private static BufferedImage img;
	private AffineTransform transform;
	private final String image_name = "floppy_fowl2.png";
	
	public Bird(int x, int y)
	{
		myX = x;
		myY = y;
		fall_rate = 1.0;
		transform = new AffineTransform();
	    try 
	    { 
	    	    img = ImageIO.read(new File( image_name ));
		} 
	    catch (IOException e) 
	    {
			System.out.println("Could not load " + image_name );
		} 
 	}
	
	// Accessor methods
	public int getX() { return (int)myX; }
	public int getY() { return (int)myY; }
	public boolean isDead() { return isBirdDead; }
	public void setAlive() { isBirdDead = false; }
	public void setSpeedMlt(double speed) { speedMlt = speed; }
	public BufferedImage getImage() { return img; }
	public AffineTransform getTransform() { transform.setToTranslation(myX, myY); return transform; }
	
	public void setFlapping(boolean b) { flapping = b; }
	public void setDiving(boolean b) { diving = b; }
	public void setDead(boolean b) { dead = b; }
	
    public void move(int b)
    {
    	/*if (flapping)
	    {
	    	  diving = false;
	    	  myY -= (SPEED*2); //move up screen
	    	  fall_rate = 1.0;
	    }
	    else
	    {
	    	  myY += (SPEED*fall_rate); //move down screen
	    	  fall_rate *= 1.02;        //increase fall rate each time
	    }
	
	    if (diving)
	    {
	    	  flapping = false;
	    	  myY += (SPEED*2); //move down screen
	    	  fall_rate = 1.0;
	    }*/
    	if(myY < 20){
    		if (flapping)
    	    {
    	    	  diving = false;
    	    	  fall_rate = 1.0;
    	    }
    	    else
    	    {
    	    	  myY += (SPEED*fall_rate); //move down screen
    	    	  fall_rate *= 1.02;        //increase fall rate each time
    	    }
    	
    	    if (diving)
    	    {
    	    	  flapping = false;
    	    	  myY += (SPEED*2); //move down screen
    	    	  fall_rate = 1.0;
    	    }
    	}else if(myY > (b - 80)){
    		if (flapping)
    	    {
    	    	  diving = false;
    	    	  myY -= (SPEED*2); //move up screen
    	    	  fall_rate = 1.0;
    	    }
    	    else
    	    {
    	    	  fall_rate *= 1.02;        //increase fall rate each time
    	    }
    	
    	    if (diving)
    	    {
    	    	  flapping = false;
    	    	  fall_rate = 1.0;
    	    }
    	    isBirdDead = true;
    	}else{
    	    if (flapping)
    	    {
    	    	  diving = false;
    	    	  myY -= (SPEED*2*speedMlt); //move up screen
    	    	  fall_rate = 1.0;
    	    }
    	    else
    	    {
    	    	  myY += (SPEED*fall_rate*speedMlt); //move down screen
    	    	  fall_rate *= 1.02;        //increase fall rate each time
    	    }
    	
    	    if (diving)
    	    {
    	    	  flapping = false;
    	    	  myY += (SPEED*2*speedMlt); //move down screen
    	    	  fall_rate = 1.0;
    	    }
    	    
    	    if (dead)
    	    {
    	    	flapping = false;
    	    	myY += (SPEED*8);
    	    	deadGameCall = true;
    	    	fall_rate = 1.0;
    	    }
    	}
    }
    
    public void draw(Graphics2D g2d)
    {
    	transform.setToTranslation(myX, myY);
    	if(deadGameCall == true && deadGameCallAlreadyRun == false){
    		transform.translate(0, -img.getHeight());
    		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
    		tx.translate(0, -img.getHeight(null));
    		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    		img = op.filter(img, null);
    		deadGameCallAlreadyRun = true;
    	}
    	g2d.drawImage( img, transform, null );
    }

}
