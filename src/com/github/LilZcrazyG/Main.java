package com.github.LilZcrazyG;

import com.github.LilZcrazyG.GameEngine.GameEngine;
import com.github.LilZcrazyG.GameEngine.GameStateManager;
import com.github.LilZcrazyG.GameEngine.GraphicsEngine;
import com.github.LilZcrazyG.GameEngine.InputManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Main {

    static final int WIN_WIDTH = 520, WIN_HEIGHT = 520, OFFSET = 20, CELLSIZE = 40;
    static int fps = 5;
    static Field gameField = new Field( OFFSET, OFFSET, WIN_WIDTH-(2*OFFSET), WIN_WIDTH-(2*OFFSET), CELLSIZE );
    static Snake player = new Snake( gameField.getCell( 10, 10 ));
    static GameStateManager gameStateManager = new GameStateManager() {
        @Override
        public void tick() {
            gameField.tick();
            player.tick();
        }

        @Override
        public void render() {
            GraphicsEngine.clearScreen();
            gameField.render();
            player.render();
            GraphicsEngine.show();
        }
    };
    static InputManager inputManager = new InputManager() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            InputManager.setKey( e.getKeyChar(), true );
            InputManager.setLastKeyPressed( e.getKeyChar() );
            if ( e.getKeyCode() == KeyEvent.VK_SHIFT && InputManager.getLastKeyPressed() != KeyEvent.VK_SHIFT) {
                GameEngine.setTickspeed(1000/(fps*2));
                System.out.println("TURBO");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            InputManager.setKey( e.getKeyChar(), false );
            InputManager.setLastKeyReleased( e.getKeyChar() );
            if ( e.getKeyCode() == KeyEvent.VK_SHIFT) {
                GameEngine.setTickspeed(1000/fps);
            }
        }
    };

    public static void main(String[] args) {
        Cell.field = gameField;
        InputManager.initialize();
        GameEngine.createWindow( WIN_WIDTH, WIN_HEIGHT, "Snake Clone" );
        GraphicsEngine.setBackgroundColor( Color.GRAY );
        GameEngine.addInputManager( inputManager );
        GameEngine.run( 1000/fps, gameStateManager );
    }
}
