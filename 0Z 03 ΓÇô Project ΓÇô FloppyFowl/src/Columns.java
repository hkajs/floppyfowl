import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Columns 
{
	private final int SPEED_DIVISOR = 2; 
	private int currentCol = 0, pastCol, random, mySpace, myHeight, myWidth, myRandStart, myRandRange, myLevel, numPassed;
	private ArrayList<Integer> x1loc, x2loc, y1loc, y2loc;
	private static BufferedImage img;
	private AffineTransform transform;
	private final String image_name = "column.png";
	
	public Columns(int x1, int y1, int space, int l, int h, int w, int randStart, int randRange)
	{
		x1loc = new ArrayList<Integer>();
		x2loc = new ArrayList<Integer>();
		y1loc = new ArrayList<Integer>();
		y2loc = new ArrayList<Integer>();
		mySpace = space;
		myHeight = h;
		myWidth = w;
		myRandStart = randStart;
		myRandRange = randRange;
		myLevel = l;
		pastCol = 0;
		//for(int y = 50; y <= h/2; y += 100 )
		//	for(int x = 10; x < w; x += w/6 )
		//		makeCloud(x+3*(y%40),y);
		for(int i = 0; i < myLevel; i++){
			random = (int)(myRandStart + (myRandRange*Math.random()));
			makeColumns(x1 + (int)(((myWidth + 69.)/myLevel) * i), x1 + 69 + (int)(((myWidth + 69.)/myLevel) * i), random, random + mySpace);
		}
		//makeColumns(x1, x2, y1, y2);
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
	
	public void makeColumns(int x1, int x2, int y1, int y2)
	{
		x1loc.add(x1);
		x2loc.add(x2);
		y1loc.add(y1);
		y2loc.add(y2);
	}

    public void move(int birdX, int birdY, double speedMlt, int level)
    {
    	  //if (++counter % SPEED_DIVISOR == 0)  //move clouds
    	  //{
    		  int x;
    		  
    		  System.out.println(currentCol);
    		  System.out.println(x2loc.get(currentCol));
    		  
    		  for(int i = 0; i < x1loc.size(); i++)
    		  {
    			  x = x1loc.get(i);
    		      //if (--x <= -(img.getWidth()) ) x = myWidth;
    		      x1loc.set(i, x -= (int)(2 * speedMlt));
    		      x2loc.set(i, x -= (int)(2 * speedMlt));
        		  if((x1loc.get(i) + 69) < 0){
        			  x1loc.set(i, myWidth);
        			  x2loc.set(i, myWidth + 138);
        			  random = (int)(myRandStart + (myRandRange*Math.random()));
        			  y1loc.set(i, random);
        			  y2loc.set(i, random + mySpace);
        		  }
    		  }
    		  
    		  if(x2loc.get(currentCol) < birdX){
    			  if((currentCol + 1) >= myLevel){
    				  currentCol = 0;
    			  }else{
    				  currentCol += 1;
    			  }
    			  numPassed++;
    		  }
    	  //}
    }
    
    public int currentColumnX() { return x2loc.get(currentCol); } //returns left (lower) x value, add by 138 for x2
    public int currentColumnY() { return y1loc.get(currentCol); } //returns upper (lower) y value, add by "space" for y2
    public int getNumPassed() { return numPassed; }
    
    public void draw(Graphics2D g2d)
    {
    	  for(int i = 0; i < x1loc.size(); i++)
    	  {
    		  transform.setToTranslation(x1loc.get(i), y1loc.get(i));
    		  transform.scale(0.5, 0.5);
    		  g2d.drawImage(img, transform, null);
    		  transform.setToTranslation(x1loc.get(i) + 69, y1loc.get(i) - 100);
    		  transform.rotate(Math.PI);
    		  transform.scale(0.5, 0.5);
    		  g2d.drawImage(img, transform, null);
    	  }
    }
}
