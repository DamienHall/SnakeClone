package com.github.LilZcrazyG;

import com.github.LilZcrazyG.GameEngine.GraphicsEngine;

import java.awt.*;
import java.util.ArrayList;

public class Field {

    // private variables
    private int x, y, width, height, rows, columns, cellSize;
    private ArrayList<ArrayList<Cell>> cells = new ArrayList<>();
    private int appleX, appleY;
    private boolean eatenApple = true;

    public Field( int x, int y, int width, int height, int cellSize ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.rows = width/cellSize;
        this.columns = height/cellSize;
        for ( int row = 0; row < rows; row++ ) {
            cells.add( new ArrayList<>() );
            for ( int column = 0; column < columns; column++ ) {
                if ( (row%2==0 && column%2==0) || (row%2==1 && column%2==1) ) {
                    cells.get( row ).add( new Cell( x+(row*cellSize), y+(column*cellSize), cellSize, new Color( 0, 181, 3 ) ) );
                } else {
                    cells.get( row ).add( new Cell( x+(row*cellSize), y+(column*cellSize), cellSize, new Color(0, 138, 2 ) ) );
                }
            }
        }
    }

    public void setEatenApple( boolean eatenApple ) {
        this.eatenApple = eatenApple;
    }

    public Cell[] getNeighbors( int x, int y ) {
        Cell[] neighbors = new Cell[4];
        if ( y-1!=-1 ) {
            neighbors[0] = cells.get( x ).get( y-1 );
        } else {
            neighbors[0] = null;
        }
        if ( x-1!=-1 ) {
            neighbors[1] = cells.get( x-1 ).get( y );
        } else {
            neighbors[1] = null;
        }
        if ( y+1!=columns ) {
            neighbors[2] = cells.get( x ).get( y+1 );
        } else {
            neighbors[2] = null;
        }
        if ( x+1!=rows ) {
            neighbors[3] = cells.get( x+1 ).get( y );
        } else {
            neighbors[3] = null;
        }
        return neighbors;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell getCell( int x, int y ) {
        return cells.get( x ).get( y );
    }

    public void generateApple() {
        if ( eatenApple ) {
            appleX = Utilities.randomInt( 0, rows-1 );
            appleY = Utilities.randomInt( 0, columns-1 );
            eatenApple = false;
            while ( cells.get( appleX ).get( appleY ).getContainsSnake() ) {
                appleX = Utilities.randomInt( 0, rows-1 );
                appleY = Utilities.randomInt( 0, columns-1 );
            }
            cells.get( appleX ).get( appleY ).setContainsApple( true );
        }
    }

    public void tick() {
        generateApple();
    }

    public void render() {
        for ( int x = 0; x < rows; x++ ) {
            for ( int y = 0; y < columns; y++ ) {
                cells.get( x ).get( y ).render();
            }
        }
    }
}
