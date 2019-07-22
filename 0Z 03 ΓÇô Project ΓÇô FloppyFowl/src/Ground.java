import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;


public class Ground 
{
	private final int SPEED_DIVISOR = 5;
	private final int PIXELS_BETWEEN_HEIGHTS = 5;
	private final Color GROUND_COLOR = new Color(10,120,5);
	private AffineTransform transform;
	private int maxHeight, myWidth, myHeight, counter;
	private ArrayList<Integer> x, y;

	public Ground(int max, int w, int h)
	{
		maxHeight = max;
		myWidth = w;
		myHeight = h;
		x = new ArrayList<Integer>();
		y = new ArrayList<Integer>();
		for (int i = 0; i <= myWidth; i = i + PIXELS_BETWEEN_HEIGHTS)
		{
			x.add( i );
			y.add( makePoint(i) );
		}
		System.out.println("Made it");
 	}
	
	
    public void move()
    {
    	  if (++counter % SPEED_DIVISOR == 0)
      {
    		  y.remove( 0 );
    		  y.add( makePoint( myWidth / PIXELS_BETWEEN_HEIGHTS ) );
      }
    }
    
    public void draw(Graphics2D g2d)
    {
    	GeneralPath polygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
    	polygon.moveTo(0.0,myHeight);
    	
    	for (int index = 1; index < x.size(); index++) 
            polygon.lineTo( x.get(index), myHeight - y.get(index) );
    	
    	polygon.lineTo( myWidth, myHeight );
    	polygon.lineTo(0.0,myHeight);
    	polygon.closePath();
    	
    	g2d.setColor( GROUND_COLOR );
    	g2d.draw(polygon);
    	g2d.fill(polygon);
    }
    
    private int makePoint(int n)
    {
    	   int temp;
    	   if (n==0)
    		   return maxHeight / 2;
    	   else
    	   {
    		   temp = (int)(y.get(n/PIXELS_BETWEEN_HEIGHTS-1) + ((maxHeight/10.0)*(2.0*Math.random() - 1.000)));
    		   temp = Math.min(temp, maxHeight);
    		   temp = Math.max(temp, 0);
    		   //System.out.println(y.get(n/PIXELS_BETWEEN_HEIGHTS-1) + " " + temp);
    		   return temp;
    	   }
    }
    
    // Accessor method
    public int getHeight(int x)  //returns height of ground at x
    {
    	   return y.get(x/PIXELS_BETWEEN_HEIGHTS);
    }
    
}
