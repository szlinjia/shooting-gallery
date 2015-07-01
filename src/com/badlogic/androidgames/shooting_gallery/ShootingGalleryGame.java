package com.badlogic.androidgames.shooting_gallery;

import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.AndroidGame;

public class ShootingGalleryGame extends AndroidGame {
    public Screen getStartScreen() {
        return new LoadingScreen(this); 
    }
} 
