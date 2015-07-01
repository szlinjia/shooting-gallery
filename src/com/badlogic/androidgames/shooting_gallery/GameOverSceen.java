package com.badlogic.androidgames.shooting_gallery;

import java.util.List;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Input.TouchEvent;
import com.badlogic.androidgames.framework.Screen;

public class GameOverSceen extends Screen {
    public GameOverSceen(Game game)
    {
        super(game);    
        
        Assets.applause.play(1);
    }   

    public void update(float deltaTime) 
    {
        Graphics g = game.getGraphics();
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();       
        
        int len = touchEvents.size();
        for(int i = 0; i < len; i++) 
        {
            TouchEvent event = touchEvents.get(i);
            if(event.type == TouchEvent.TOUCH_UP) 
            {
               
            }
        }
    }
    
    private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
        if(event.x > x && event.x < x + width - 1 && 
           event.y > y && event.y < y + height - 1) 
            return true;
        else
            return false;
    }

    public void present(float deltaTime) {
        Graphics g = game.getGraphics();
        
        int x = g.getWidth()/2 - Assets.gameOver.getWidth()/2;
        int y = g.getHeight()/2 - Assets.gameOver.getHeight()/2;
        g.drawPixmap(Assets.gameOver, x, y);
    }

    public void pause() 
    {        
    }

    public void resume() {

    }

    public void dispose() 
    {

    }
}

