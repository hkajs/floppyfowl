import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Game extends JPanel implements Runnable, KeyListener
{
	Thread thread;
	private static final long serialVersionUID = 1;
	private final Color SKY_COLOR = new Color(200,220,250);
	private long startTime, endTime, framePeriod;
	private int width, height, level, lives, score, space, randStart, randRange;
	private double speedMlt;
	private boolean isDead = false;
	private Columns column;
	private Bird bird;
	private Ground ground;
	private Clouds clouds;
	
	public Game()
	{
		this.setSize(800, 800);
		this.setDoubleBuffered(true);
		width = this.getWidth();
		height = this.getHeight();
		framePeriod = 25; //milliseconds between updates
		speedMlt = 1;
		level = 1;
		lives = 3;
		space = 100;
		randStart = 200;
		randRange = 400;
		
		bird = new Bird(width/4, height/2); //place bird at this location
		ground = new Ground(150,width,height); //create ground with a maxHeight of 150 pixels
		clouds = new Clouds(width,height); //create clouds
		column = new Columns(630, 360, space, level + 2, getHeight(), getWidth(), randStart, randRange); // x1, y1, space, level, winHeight, winWidth, randStart, randRange

		addKeyListener(this); //to get commands from keyboard
		setFocusable(true); 

		thread = new Thread(this);
		thread.start();
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g);
		
		//give the graphics smooth edges
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);

		g2d.setColor( SKY_COLOR );
		g2d.fillRect(0, 0, width, height); //add a background

		//add code that draws bird, walls, and ground
		ground.draw(g2d);
		clouds.draw(g2d);
		column.draw(g2d);
		bird.draw(g2d);
     	
		
		//add text for lives, score, and level
		g2d.setColor(Color.BLACK);
		g2d.drawString("Level : " + level, 10, height - 100);
		g2d.drawString("Lives Left : " + lives, 110, height - 100);
		g2d.drawString("Score : " + score, 210, height - 100);
	}
	
	public void birdIsDead()
	{
		bird.setDead(true);
		isDead = true;
	}
	
	public void softRestart()
	{
		lives--;
		isDead = false;
		bird = new Bird(width/4, height/2); //place bird at this location
		ground = new Ground(150,width,height); //create ground with a maxHeight of 150 pixels
		clouds = new Clouds(width,height); //create clouds
		column = new Columns(630, 360, space, level + 2, getHeight(), getWidth(), randStart, randRange); // x1, y1, space, level, winHeight, winWidth, randStart, randRange
	}
	
	public void hardRestart()
	{
		System.exit(0);
		// set jframe defined below to a new jframe of a dialog, then if user wants to start, just set jframe to a new Game. Garbage collection should take care of the unassigned object
		// decrement lives
		// reset bird & columns
		// call setAlive();
	}

	public void run() 
	{
		while(true)  
		{
			startTime = System.currentTimeMillis(); //timestamp
         
			bird.setSpeedMlt(speedMlt);
			
			//Add code here that moves bird, walls, and ground
			bird.move(getHeight());
			ground.move();
			clouds.move();
			column.move(bird.getX(), bird.getY(), speedMlt, level);
			
			score = column.getNumPassed();
			
			boolean alreadyRun = false;
			int scoreChk = 1;
			
			if(alreadyRun == false){
				if(score == 25){
					speedMlt = 1.5;
					scoreChk = score;
					alreadyRun = true;
				}else if(score == 50){
					speedMlt = 2;
					scoreChk = score;
					alreadyRun = true;
				}
			}else{
				if(score == scoreChk++){
					alreadyRun = false;
				}
			}
			
			if(bird.isDead()){
				if(lives > 0){
					softRestart();
					bird.setAlive();
				}else{
					hardRestart();
				}
			}
			
			if((column.currentColumnX() <= (bird.getX() + 80) && (column.currentColumnX() + 138) >= (bird.getX() + 80)) || (column.currentColumnX() <= bird.getX() && (column.currentColumnX() + 138) >= bird.getX())){
				System.out.println("|| INFO || in between pipes");
  			    if((bird.getY() < column.currentColumnY() - space) || (bird.getY() + 38 > column.currentColumnY())){//38
  				    System.out.println("|| INFO || crashed into pipe");
  				    System.out.println("|| INFO || current column y:" + column.currentColumnY());
  					if(lives > 0)
  						birdIsDead();
  					else
  						hardRestart();
  			    }else{
  	  		    	System.out.println("|| INFO ||");
  	  		    	System.out.println("|| INFO ||");
  			    }
  		    }else{
  		    	System.out.println("|| INFO ||");
  		    	System.out.println("|| INFO ||");
  		    	System.out.println("|| INFO ||");
  		    }
			
			repaint();
			try 
			{
				endTime = System.currentTimeMillis(); //new timestamp
				if(framePeriod - (endTime-startTime) > 0)                               //if there is time left over after repaint, then sleep 
					Thread.sleep(framePeriod - (endTime - startTime));      //for whatever is  remaining in framePeriod
			} 
			catch(InterruptedException e) 
			{  } //oops there's an exception!, but do nothing...
		}
	}

	public void keyPressed(KeyEvent e) 
	{
		if(isDead == false){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_UP) 
				bird.setFlapping(true);
			if(key == KeyEvent.VK_DOWN)
				bird.setDiving(true);
		}
	}

	public void keyReleased(KeyEvent e) 
	{
		if(isDead == false){
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_UP) 
				bird.setFlapping(false);
			if(key == KeyEvent.VK_DOWN)
				bird.setDiving(false);
		}
	}

	public void keyTyped(KeyEvent e) { }


	public static void main(String[] args) 
	{
		//JPanel scrbrd = new JPanel();
		//scrbrd.add(new JButton());
		
		JFrame w = new JFrame("Floppy Fowl");
		w.setSize(800, 800);
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = w.getContentPane();
		c.add( new Game() );
		//c.add(scrbrd);
		w.setResizable(false);
		w.setVisible(true);
	}
}
