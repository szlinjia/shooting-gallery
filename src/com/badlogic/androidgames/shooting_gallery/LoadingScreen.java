package com.badlogic.androidgames.shooting_gallery;

import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.Graphics;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.gamebk = g.newPixmap("gamebk.png", PixmapFormat.RGB565);
        Assets.bullet = g.newPixmap("bullet.png", PixmapFormat.RGB565);
        Assets.target = g.newPixmap("target.png", PixmapFormat.RGB565);
        Assets.icon1 = g.newPixmap("icon1.png", PixmapFormat.RGB565);
        Assets.icon2 = g.newPixmap("icon2.png", PixmapFormat.RGB565);
        Assets.icon3 = g.newPixmap("icon3.png", PixmapFormat.RGB565);
        Assets.icon4 = g.newPixmap("icon4.png", PixmapFormat.RGB565);
        Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.score = g.newPixmap("score.png", PixmapFormat.ARGB4444);
        Assets.flybullet_left = g.newPixmap("flybullet_left.png", PixmapFormat.ARGB4444);
        Assets.flybullet_right = g.newPixmap("flybullet_right.png", PixmapFormat.ARGB4444);
        Assets.bulletsound = game.getAudio().newSound("bullet.mp3");
        Assets.bomb = game.getAudio().newSound("bomb.mp3");
        Assets.applause = game.getAudio().newSound("applause.mp3");
        
    
        game.setScreen(new StartSceen(game));
    }
    
    public void present(float deltaTime) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
