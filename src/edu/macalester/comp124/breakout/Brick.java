package edu.macalester.comp124.breakout;

import acm.graphics.GRect;

import java.awt.*;

/**
 * Brick class. A rectangle.
 * Created by Kelly on 3/19/16.
 */
public class Brick extends GRect {

    private Color color; //Color of brick fill.
    private double width; //Width of brick.
    private double height; //Height of brick.

    /**
     * Creates a brick (a rectangle to be destroyed by the ball in BreakOut).
     * @param x the top left x position of the brick.
     * @param y the top left y position of the brick.
     * @param width the width of the brick.
     * @param height the height of the brick.
     * @param color the color of the brick's fill.
     */

    public Brick(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.color = color;
        this.width = width;
        this.height = height;
        this.setFilled(true);
        this.setFillColor(color);
    }


    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
