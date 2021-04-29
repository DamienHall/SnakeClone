package com.github.LilZcrazyG;

import com.github.LilZcrazyG.GameEngine.GraphicsEngine;

import java.awt.*;

public class Cell {

    // static variables
    public static Field field;

    // private variables
    private int x, y, size;
    private Color color;
    private boolean containsApple = false;
    private boolean containsSnake = false;

    public Cell( int x, int y, int size, Color color ) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public Cell[] getNeighbors() {
        return field.getNeighbors( (x-field.getX())/size, (y-field.getY())/size );
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public void setContainsApple( boolean containsApple ) {
        if ( this.containsApple && !containsApple ) {
            field.setEatenApple( true );
        }
        this.containsApple = containsApple;
    }

    public boolean getContainsApple() {
        return containsApple;
    }

    public void setContainsSnake( boolean containsSnake ) {
        this.containsSnake = containsSnake;
    }

    public boolean getContainsSnake() {
        return this.containsSnake;
    }

    public void tick() {

    }

    public void render() {
        Color oldColor = GraphicsEngine.setColor( color );
        if ( containsApple ) {
            GraphicsEngine.setColor( Color.RED );
        }
        GraphicsEngine.rectangleFilled( x, y, size, size );
        GraphicsEngine.setColor( oldColor );
    }
}
