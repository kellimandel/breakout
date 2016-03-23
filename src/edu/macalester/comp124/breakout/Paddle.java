package edu.macalester.comp124.breakout;

import acm.graphics.GRect;

import java.awt.*;

/**
 * The paddle.
 * Created by Kelly on 3/19/16.
 */
public class Paddle extends GRect {

    private Color color; //Color of paddle fill.
    private double width; //Width of paddle.
    private double height; //Height of paddle.

    /**
     * Creates the paddle.
     * @param x the x position of the top left of the paddle.
     * @param y the y position of the top left of the paddle.
     * @param width the width of the paddle.
     * @param height the height of the paddle.
     * @param color the fill color of the paddle.
     */

    public Paddle(double x, double y, double width, double height, Color color) {
        super(x, y, width, height);
        this.color = color;
        this.width = width;
        this.height = height;
        this.setFilled(true);
        this.setFillColor(color);
    }

    /**
     * Creates the paddle. Assumes top left position to be 0,0.
     * @param width the width of the paddle.
     * @param height the height of the paddle.
     * @param color the fill color of the paddle.
     */

    public Paddle(double width, double height, Color color) {
        super(0, 0, width, height);
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

