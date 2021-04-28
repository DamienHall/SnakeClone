package com.github.LilZcrazyG.GameEngine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public abstract class InputManager extends KeyAdapter {
    static ArrayList<Boolean> keys = new ArrayList<>();
    static char lastKeyPressed, lastKeyReleased;
    public static void initialize() {
        for ( int key = 0; key < 65536; key++ ) {
            keys.add( key,false );
        }
    };
    public static void setKey( int charCode, boolean pressed ) {
        keys.set( charCode, pressed );
    }
    public static void setLastKeyPressed( char c ) { lastKeyPressed = c; }
    public static char getLastKeyPressed() { return lastKeyPressed; }
    public static void setLastKeyReleased( char c ) { lastKeyReleased = c; }
    public static char getLastKeyReleased() { return lastKeyReleased; }
    public static boolean getKey( int charCode ) {
        return keys.get( charCode );
    }
    public static ArrayList<Boolean> getKeys() {
        return keys;
    }
    public abstract void keyTyped(KeyEvent e);
    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
}