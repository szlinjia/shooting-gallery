package com.badlogic.androidgames.shooting_gallery;

import java.util.List;

import android.util.Log;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class StartSceen extends Screen 
{
    public StartSceen(Game game) 
    {
        super(game);               
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
            	Log.i("ShootingGallery","click x:"+String.valueOf(event.x) + " y:" + String.valueOf(event.y));
            	game.setScreen(new GameSceen(game));
                return;
                
            }
        }
    }

    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) 
    {
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1) 
            return true;
        else
            return false;
    }
    
    public void present(float deltaTime) 
    {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.background, 0, 0);

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

