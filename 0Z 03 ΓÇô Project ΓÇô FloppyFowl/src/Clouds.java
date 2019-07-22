import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Clouds 
{
	private final int SPEED_DIVISOR = 2; 
	private int myWidth, counter;
	private ArrayList<Integer> x_loc, y_loc, size;
	private static BufferedImage img;
	private AffineTransform transform;
	private final String image_name = "cloud.png";
	
	public Clouds(int w, int h)
	{
		myWidth = w;
		x_loc = new ArrayList<Integer>();
		y_loc = new ArrayList<Integer>();
		size = new ArrayList<Integer>();
		for(int y = 50; y <= h/2; y += 100 )
			for(int x = 10; x < w; x += w/6 )
				makeCloud(x+3*(y%40),y);
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
	
	public void makeCloud(int x, int y)
	{
		x_loc.add(x);
		y_loc.add(y);
		size.add( (int)(Math.random()*5) );
	}

    public void move()
    {
    	  if (++counter % SPEED_DIVISOR == 0)  //move clouds
    	  {
    		  int x;
    		  counter=0;
    		  for(int i = 0; i < x_loc.size(); i++)
    		  {
    			  x = x_loc.get(i);
    		      if (--x <= -(img.getWidth()) ) x = myWidth;
    		      x_loc.set(i, x);
    		  }
    	  }
    }
    
    public void draw(Graphics2D g2d)
    {
    	  for(int i = 0; i < x_loc.size(); i++)
    	  {
    		  transform.setToTranslation(x_loc.get(i), y_loc.get(i));
    		  g2d.drawImage( img, transform, null );
    	  }
    }

}
