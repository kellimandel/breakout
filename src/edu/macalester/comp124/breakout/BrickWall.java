package edu.macalester.comp124.breakout;

import acm.graphics.GCompound;

import java.awt.*;
import java.util.ArrayList;

/**
 * A GCompound of 100 bricks (10 rows and 10 columns). Each row will have unique color.
 * Created by Kelly on 3/19/16.
 */
public class BrickWall extends GCompound {


    private static final double BRICK_WIDTH = 30; //The width of each brick.
    private static final double BRICK_HEIGHT = 10; //The height of each brick.
    private static final double BRICK_DISTANCE = 5; //The distance between each brick.

    private static final double BRICK_STARTING_X = 10; //The starting x position of the top left of the entire wall.
    private static final double BRICK_STARTING_Y = 60; //The starting y position of the top left of the entire wall.

    /**
     * Creates a 10 rows of the brick wall, each with different colors.
     * Calls the createBrickRow method for each row.
     */

    public BrickWall() {
        double y = BRICK_STARTING_Y;
        double yDistance = BRICK_DISTANCE + BRICK_HEIGHT;

        createBrickRow(y, Color.RED);
        y += yDistance;
        createBrickRow(y, new Color(255,128,0));
        y += yDistance;
        createBrickRow(y, Color.ORANGE);
        y += yDistance;
        createBrickRow(y, Color.YELLOW);
        y += yDistance;
        createBrickRow(y, new Color(128,255,0));
        y += yDistance;
        createBrickRow(y, Color.GREEN);
        y += yDistance;
        createBrickRow(y, Color.CYAN);
        y += yDistance;
        createBrickRow(y, new Color(0,128,255));
        y += yDistance;
        createBrickRow(y, Color.BLUE);
        y += yDistance;
        createBrickRow(y, new Color(128,0,255));


    }

    /**
     * Creates a row of 10 bricks.
     *
     * @param startingY the y position of the top of the row.
     * @param color the fill color for the bricks.
     */

    private void createBrickRow(double startingY, Color color){
        double xDistance = BRICK_WIDTH + BRICK_DISTANCE;
        double x = BRICK_STARTING_X;
        for(int i = 0; i <= 10; i++){
            Brick brick = new Brick(x,startingY,BRICK_WIDTH,BRICK_HEIGHT, color);
            add(brick);
            x += xDistance;
        }
    }

    public void getBrick(double row, double column){

    }

    public static double getBrickWidth() {
        return BRICK_WIDTH;
    }

    public static double getBrickHeight() {
        return BRICK_HEIGHT;
    }

    public static double getBrickDistance() {
        return BRICK_DISTANCE;
    }

    public static double getBrickStartingX() {
        return BRICK_STARTING_X;
    }

    public static double getBrickStartingY() {
        return BRICK_STARTING_Y;
    }

}
