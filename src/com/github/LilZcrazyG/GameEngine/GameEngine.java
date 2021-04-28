package com.github.LilZcrazyG.GameEngine;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameEngine {

    private static WindowEngine windowEngine;
    private static long tickspeed = 1000/60;
    private static GameStateManager gameStateManager = new GameStateManager() {
        @Override
        public void tick() {

        }

        @Override
        public void render() {

        }
    };
    private static TimerTask tickTask = new TimerTask() {
        @Override
        public void run() {
            try {
                gameStateManager.tick();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    };
    private static TimerTask renderTask = new TimerTask() {
        @Override
        public void run() {
            try {
                gameStateManager.render();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    };
    private static Timer timer = new Timer();

    /**
     * Creates a window that includes a canvas for the game
     * @param width The width of the window
     * @param height The height of the window
     * @param title The title of the window
     * @return The window
     */
    public static JFrame createWindow( int width, int height, String title ) {
        windowEngine = new WindowEngine( width, height, title );
        GraphicsEngine.initialize( windowEngine );
        return windowEngine.getWindow();
    }

    public static void addInputManager( InputManager inputManager ) {
        windowEngine.addInputManager( inputManager );
    }

    public static void setTickspeed( long tickspeed ) {
        tickTask.cancel();
        tickTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    gameStateManager.tick();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate( tickTask, 0, tickspeed );
    }

    /**
     * Runs the engine.
     * @param tickspeed The timing in ms between each tick
     */
    public static void run( long tickspeed, GameStateManager gameStateManager ) {
        GameEngine.gameStateManager = gameStateManager;
        tickTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    gameStateManager.tick();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        };
        renderTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    gameStateManager.render();
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate( tickTask, 0, tickspeed );
        timer.scheduleAtFixedRate( renderTask, 0, 1000/120 );
    }
}
