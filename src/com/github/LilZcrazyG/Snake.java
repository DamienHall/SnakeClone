package com.github.LilZcrazyG;

import com.github.LilZcrazyG.GameEngine.GraphicsEngine;
import com.github.LilZcrazyG.GameEngine.InputManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Snake {

    // final variables
    static final int UP = 0;
    static final int LEFT = 1;
    static final int DOWN = 2;
    static final int RIGHT = 3;

    // private variables
    private ArrayList<BodySegment> body = new ArrayList<>();
    private int direction = UP, score = 0;

    // body segment class
    public static class BodySegment {

        // private variables
        private int direction;
        private Cell segmentCell;
        private ArrayList<BodySegment> queue = new ArrayList<>();
        private boolean[] walls = new boolean[] { false, false, false, false };
        private boolean inQueue = false;

        public BodySegment( Cell segmentCell, int direction ) {
            this.direction = direction;
            this.segmentCell = segmentCell;
        }

        public boolean getInQueue() {
            return inQueue;
        }

        public void setInQueue( boolean b ) {
            inQueue = b;
        }

        public int setDirection( int newDirection ) {
            int oldDirection = direction;
            direction = newDirection;
            return oldDirection;
        }

        public void setWalls( boolean n, boolean e, boolean s, boolean w ) {
            walls = new boolean[] { n, e, s, w };
        }

        public boolean[] getWalls() {
            return walls;
        }

        public int getDirection() {
            return direction;
        }

        public void updatePosition() {
            Cell newSegmentCell = segmentCell.getNeighbors()[direction];
            if (newSegmentCell != null) {
                segmentCell = newSegmentCell;
            }
        }

        public BodySegment add() {
            int oppositeDirection = 0;
            if ( direction == UP ) {
                oppositeDirection = DOWN;
            }
            if ( direction == DOWN ) {
                oppositeDirection = UP;
            }
            if ( direction == RIGHT ) {
                oppositeDirection = LEFT;
            }
            if ( direction == LEFT ) {
                oppositeDirection = RIGHT;
            }
            BodySegment b = new BodySegment( segmentCell, direction );
            b.setInQueue( true );
            queue.add( b );
            return b;
        }

        public Cell getCell() {
            return segmentCell;
        }
    }

    public Snake( Cell headCell ) {
        body.add( new BodySegment( headCell, direction ) );
    }

    public void eatApple() {
        score += 100;
        body.add( body.get( body.size()-1 ).add() );
    }

    public void tick() {
        char lastKeyPressed = InputManager.getLastKeyPressed();
        char lastKeyReleased = InputManager.getLastKeyReleased();
        if ( ( lastKeyPressed == 'w' || lastKeyPressed == 'W' ) && body.get( 0 ).getDirection() != DOWN ) {
            body.get( 0 ).setDirection( Snake.UP );
        }
        if ( ( lastKeyPressed == 's' || lastKeyPressed == 'S' ) && body.get( 0 ).getDirection() != UP ) {
            body.get( 0 ).setDirection( Snake.DOWN );
        }
        if ( ( lastKeyPressed == 'a' || lastKeyPressed == 'A' ) && body.get( 0 ).getDirection() != RIGHT ) {
            body.get( 0 ).setDirection( Snake.LEFT );
        }
        if ( ( lastKeyPressed == 'd' || lastKeyPressed == 'D' ) && body.get( 0 ).getDirection() != LEFT ) {
            body.get( 0 ).setDirection( Snake.RIGHT );
        }
        for ( int segment = body.size()-1; segment > -1; segment-- ) {
            BodySegment b = body.get( segment );
            if ( !b.getInQueue() ) {
                b.updatePosition();
                if ( segment != 0 ) {
                    b.setDirection( body.get( segment-1 ).getDirection() );
                }
            } else {
                b.setInQueue( false );
            }
            if ( b == body.get( body.size()-1 ) ) {
                if ( b.getDirection() == UP ) {
                    b.setWalls( false, true, true, true );
                } else if ( b.getDirection() == DOWN ) {
                    b.setWalls( true, true, false, true );
                } else if ( b.getDirection() == LEFT ) {
                    b.setWalls( true, true, true, false );
                } else {
                    b.setWalls( true, false, true, true );
                }
            } else if ( b == body.get( 0 ) ) {
                if (b.getDirection() == UP) {
                    b.setWalls(true, true, false, true);
                } else if (b.getDirection() == DOWN) {
                    b.setWalls(false, true, true, true);
                } else if (b.getDirection() == LEFT) {
                    b.setWalls(true, false, true, true);
                } else {
                    b.setWalls(true, true, true, false);
                }
            } else {
                if (body.get(segment - 1).getDirection() == body.get(segment + 1).getDirection()) {
                    if (b.getDirection() == UP) {
                        b.setWalls(false, true, false, true);
                    } else if (b.getDirection() == DOWN) {
                        b.setWalls(false, true, false, true);
                    } else if (b.getDirection() == LEFT) {
                        b.setWalls(true, false, true, false);
                    } else {
                        b.setWalls(true, false, true, false);
                    }
                } else if (body.get(segment - 1).getDirection() == UP && body.get(segment + 1).getDirection() == RIGHT) {
                    b.setWalls(false, true, true, false);
                } else if (body.get(segment - 1).getDirection() == RIGHT && body.get(segment + 1).getDirection() == UP) {
                    b.setWalls(true, false, false, true);
                } else if (body.get(segment - 1).getDirection() == UP && body.get(segment + 1).getDirection() == LEFT) {
                    b.setWalls(false, false, true, true);
                } else if (body.get(segment - 1).getDirection() == LEFT && body.get(segment + 1).getDirection() == UP) {
                    b.setWalls(true, true, false, false);
                } else if (body.get(segment - 1).getDirection() == DOWN && body.get(segment + 1).getDirection() == RIGHT) {
                    b.setWalls(true, true, false, false);
                } else if (body.get(segment - 1).getDirection() == RIGHT && body.get(segment + 1).getDirection() == DOWN) {
                    b.setWalls(false, false, true, true);
                } else if (body.get(segment - 1).getDirection() == DOWN && body.get(segment + 1).getDirection() == LEFT) {
                    b.setWalls(true, false, false, true);
                } else if (body.get(segment - 1).getDirection() == LEFT && body.get(segment + 1).getDirection() == DOWN) {
                    b.setWalls(false, true, true, false);
                }
            }
        }
        if ( body.get( 0 ).getCell().getContainsApple() ) {
            eatApple();
            body.get( 0 ).getCell().setContainsApple( false );
        }
    }

    public void render() {
        Color oldColor = GraphicsEngine.setColor( Color.GREEN );
        for ( int segmentIndex = body.size()-1; segmentIndex > -1; segmentIndex-- ) {
            BodySegment bodySegment = body.get( segmentIndex );
            Cell segmentCell = bodySegment.getCell();
            GraphicsEngine.rectangleFilled( segmentCell.getX(), segmentCell.getY(), segmentCell.getSize(), segmentCell.getSize() );
            GraphicsEngine.setColor( Color.BLACK );
            if ( bodySegment.getWalls()[0] ) {
                GraphicsEngine.line( segmentCell.getX(), segmentCell.getY(), segmentCell.getX()+segmentCell.getSize()-1, segmentCell.getY() );
            }
            if ( bodySegment.getWalls()[1] ) {
                GraphicsEngine.line( segmentCell.getX()+segmentCell.getSize()-1, segmentCell.getY(), segmentCell.getX()+segmentCell.getSize()-1, segmentCell.getY()+segmentCell.getSize()-1 );
            }
            if ( bodySegment.getWalls()[2] ) {
                GraphicsEngine.line( segmentCell.getX(), segmentCell.getY()+segmentCell.getSize()-1, segmentCell.getX()+segmentCell.getSize()-1, segmentCell.getY()+segmentCell.getSize()-1 );
            }
            if ( bodySegment.getWalls()[3] ) {
                GraphicsEngine.line( segmentCell.getX(), segmentCell.getY(), segmentCell.getX(), segmentCell.getY()+segmentCell.getSize()-1 );
            }
            GraphicsEngine.setColor( Color.GREEN );
        }
        GraphicsEngine.setColor( oldColor );
    }
}
