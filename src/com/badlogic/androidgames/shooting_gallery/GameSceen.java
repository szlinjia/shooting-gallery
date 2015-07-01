package com.badlogic.androidgames.shooting_gallery;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Screen;

public class GameSceen extends Screen 
{
	private int m_numBullets = 10;
	private Target[] m_Targets;
	private int m_numTargets = 8;
	private int m_x;
	private int m_y;
	private flyingbullet fly;
	private PlayAnimation animation;
	private int m_score = 0;

	/*
	 * This class is defined for drawing animation pictures
	 * when one of targets has been shorted by bullets.
	 * 
	 */
	class PlayAnimation
	{
		final static int duration = 10; ;	    //animation duration time, used loop to decrease this constant
		int m_index = 0;
		int curTime = 0;
		boolean isPlay = false;
		protected Pixmap[] m_bmpFaces;
		int m_maxBmp;
		int m_x;
		int m_y;
		
		PlayAnimation()
		{
			Stop();
	        m_bmpFaces = new Pixmap[]{
	        		Assets.icon1,
	        		Assets.icon1,
	        		Assets.icon1,
	        		Assets.icon2,
	        		Assets.icon2,
	        		Assets.icon2,
	        		Assets.icon3,
	        		Assets.icon3,
	        		Assets.icon3,
	        		Assets.icon4,
	        		Assets.icon4,
	        		Assets.icon4};
	            
	            m_maxBmp = 12;
		}
		void Start(int x, int y)
		{
			Stop();
			isPlay = true;
			m_x = x;
			m_y = y;
		}
		
		void Play()
		{
			if(!isPlay)
			{
				return;
			}
			
			Graphics g = game.getGraphics();
			
			if(curTime >= duration)
			{
				Stop();
				return;
			}
			
			g.drawPixmap(m_bmpFaces[m_index], m_x, m_y);
			
			if(m_index < m_maxBmp-1)
			{
				m_index++;
			}
			else
			{
				m_index = 0;
			}
			curTime++;
		}
		
		void Stop()
		{
			m_index = 0;
			curTime = 0;
			isPlay = false;
			m_x = 0;
			m_y = 0;
		}
		
		boolean IsPlaying()
		{
			return isPlay;
		}
	}
	
	
	/*
	 * This is a inner class that used describe attributes of continuous bullets.
	 * There are two core function here: one is calculate(), which is calculating
	 * bullets x, y, coordinate. The other one is isHitTarget(), which is defined
	 * for calculating whether current x, y is in bound of targets.
	 */
	class flyingbullet
	{
		private int dst_x;
		private int dst_y;
		private int org_x;
		private int org_y;
		private int cur_x;
		private int cur_y;
		public boolean isfly = false;
		private final int speed = 10;
		private int targetIndex = -1;
		private int totalTime;
		private int curTime;
		
		public void setTarget(int targetIndex, int dstx, int dsty)
		{
			Graphics g = game.getGraphics();

		    int x = 2+(Assets.bullet.getWidth() + 5)* m_numBullets;
	        int y = g.getHeight() - Assets.bullet.getHeight();
			this.targetIndex = targetIndex;
			dst_x = dstx;
			dst_y = dsty;
			fly.isfly = true;
			org_x = cur_x = x;
			org_y = cur_y = y;
			
			//Pythagorean Theorem
			double dx = dst_x - org_x;
			double dy = dst_y - org_y;
			
			//this is total len of Hypotenuse
			double totalLen = Math.sqrt(dx * dx + dy * dy);
			
			//this is current Hypotenuse based on the bullet speed;
			totalTime = (int)(totalLen/(double)speed);
			curTime = 0;
		}
		
	
		public boolean isHitTarget(Target target)
		{
			boolean isHit = false;
			
			if(target.m_index != targetIndex)
				return false;
	
			if(target.m_isDisplay && target.inBounds(cur_x, cur_y))
			{
				this.targetIndex = -1;
				target.m_isDisplay = false;
				
				//play animation
				animation.Start(target.m_rect.left, target.m_rect.top);
				m_score++;
				isHit = true;
				Assets.bomb.play(1);
				
				IsGameOver();
			}
			
			return isHit;
			
		}
		
		
		public void calculate()
		{
			double dx = dst_x - org_x;
			double dy = dst_y - org_y;
			
			//this is total len of Hypotenuse
			double totalLen = Math.sqrt(dx * dx + dy * dy);
			curTime++;
			int curLen = curTime * speed;
			
			//set target invisible
	        for(int i=0; i< m_numTargets; i++)
	        {
	        	boolean isHit = isHitTarget(m_Targets[i]);
	        	if(isHit)
	        	{
	        		isfly = false;
	        		break;
	        	}
	        }
	        
			//hit the target;
			cur_x = (int)(org_x + (curLen * (dx)) / totalLen);
			cur_y = (int)(org_y + (curLen * (dy)) / totalLen);
			

		}
		
		public int getPosx()
		{
			return cur_x;
		}
		
		public int getPosy()
		{
			return cur_y;
		}
		
		
	}
	
	/*
	 * this inner class used to recored targets attributes,
	 * such as targets coordinates, targets has been hit,
	 * targets index and so on.
	 * If java has structure definition like c++, I don't
	 * need to define so many inner classes that makes my
	 * code looks ugly.
	 */
	class Target
	{
		private Rect m_rect;
		public boolean m_isDisplay = true;
		private int m_index;
		
		Target(int x, int y, int index)
		{
			m_rect = new Rect();
			m_rect.left = x;
			m_rect.top = y;
			m_rect.right = x + Assets.target.getWidth();
			m_rect.bottom = y + Assets.target.getHeight();
			m_index = index;
		
		}
		
		
		public boolean inBounds(int x, int y)
		{
			boolean isInBound = false;
			
			Log.i("ShootingGallery","rect:"+String.valueOf(m_rect.left) + " " + String.valueOf(m_rect.right)
					+ " " + String.valueOf(m_rect.top) + " " + String.valueOf(m_rect.bottom));
			
			Log.i("ShootingGallery","x:"+String.valueOf(x) + " y:"+String.valueOf(y));
			
			if(x > m_rect.left && x < m_rect.right &&
					y > m_rect.top && y < m_rect.bottom)
			{
				Log.i("ShootingGallery","is In Bound:"+String.valueOf(m_index));
				isInBound = true;
			}
			return isInBound;
		}
	}
	
    public GameSceen(Game game) 
    {
        super(game);   
        m_Targets = new Target[m_numTargets];
        fly = new flyingbullet();
        
        //create animation 
        animation = new PlayAnimation();
        
        //create targets
        int startx = 10;
        int starty = 40;
        for(int i=0; i<m_numTargets/2; i++)
        {
        	if(i%2 != 0)
        	{
        		startx = Assets.target.getWidth() - 15;
        	}
        	else
        	{
        		startx = 15;
        	}
        	
        	for(int j=0; j< 2; j++)
        	{
	        	m_Targets[i*2+j] = new Target(startx, starty,i*2+j);
	        	startx += Assets.target.getWidth() + 45;
	        	
        	}

        	starty += Assets.target.getHeight() + 10;
        }
        Log.i("ShootingGallery","Create Targets.");
     
    }   

    public void update(float deltaTime) 
    {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();    
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++)
        {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) 
            {
            	Log.i("ShootingGallery","click here start game");
            	
            	IsGameOver();
            	Assets.bulletsound.play(1);
            	int targetIndex = -1;
            	for(int j=0;j<m_numTargets; j++)
            	{
            		if(m_Targets[j].inBounds(event.x, event.y))
            		{
            			m_numBullets--;
            			targetIndex = j;
            			break;
            		}
            	}
            	
            	fly.setTarget(targetIndex, event.x, event.y);
            	
            }
        }
    }

    public void present(float deltaTime)
    {
        Graphics g = game.getGraphics();
        
        //draw game bk
        g.drawPixmap(Assets.gamebk, 0, 0);
 
        //draw reserver bullets
        int x = 2;
        int y = g.getHeight() - Assets.bullet.getHeight();
        for(int i=0; i< m_numBullets; i++)
        {
        	g.drawPixmap(Assets.bullet, x, y);
        	x += Assets.bullet.getWidth() + 5;
        }
        
        //draw target
        for(int i=0; i< m_numTargets; i++)
        {
        	if(m_Targets[i].m_isDisplay)
        	{
        		g.drawPixmap(Assets.target, m_Targets[i].m_rect.left, m_Targets[i].m_rect.top);
        	}
        }
        
        //draw flying bullet
    	if(fly.isfly)
    	{
            fly.calculate();
            if(fly.dst_x > fly.org_x)
            {
            	g.drawPixmap(Assets.flybullet_right, fly.getPosx(), fly.getPosy());
            }
            else
            {
            	g.drawPixmap(Assets.flybullet_left, fly.getPosx(), fly.getPosy());
            }
    	}
    	
    	//draw animation
    	if(animation.IsPlaying())
    	{
    		animation.Play();
    	}
    	
    	//draw Score
    	int y1 = 3;
    	int x1 = g.getWidth() - Assets.score.getWidth() - 40;
    	g.drawPixmap(Assets.score, x1, y1);
    	x1 = g.getWidth() - 35;
    	drawText(g, String.valueOf(m_score), x1, y1);
    	
    }
    
    public void drawText(Graphics g, String line, int x, int y) 
    {
    	int len = line.length();
    	for (int i = 0; i < len; i++) 
    	{
    		char character = line.charAt(i);
    		if (character == ' ') 
    		{
		    	x += 20;
		    	continue;
    		}
	    	int srcX = 0;
	    	int srcWidth = 0;
	    	if (character == '.') 
	    	{
		    	srcX = 200;
		    	srcWidth = 10;
	    	} 
	    	else 
	    	{
		    	srcX = (character - '0') * 20;
		    	srcWidth = 20;
	    	}
	    	g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
	    	x += srcWidth;
    	}
    }
    
    public boolean IsGameOver()
    {
    	boolean isOver = false;
    	
    	if(m_numBullets <= 0)
    	{
    		isOver = true;
    	}
    
    	int hitTarget = 0;
    	for(int i=0;i<m_Targets.length;i++)
    	{
    		if(!m_Targets[i].m_isDisplay)
    		{
    			hitTarget++;
    		}
    	}
    	
    	
    	if(hitTarget == m_Targets.length)
    	{
    		isOver = true;
    	}
    	
    	if(isOver)
    	{
    		game.setScreen(new GameOverSceen(game));
    	}
    	return isOver;
    }

    public void pause() 
    {        
        
    }

    public void resume() 
    {

    }

    public void dispose() 
    {

    }
}
    
  

