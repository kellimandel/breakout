package edu.macalester.comp124.breakout;

import acm.graphics.GOval;

import java.awt.*;

/**
 * The ball in Breakout. A circle (GOval).
 * Created by Kelly on 3/10/16.
 */
public class Ball extends GOval{

    private Color color; //Color of ball fill.
    private double diameter; //Ball diameter.

    /**
     * Constructor to create a ball.
     * @param x the x position of the top left of the ball.
     * @param y the y position of the top left of the ball.
     * @param diameter the diameter of the ball.
     * @param color the color to fill the ball.
     */

    public Ball(double x, double y, double diameter, Color color) {
        super(x, y, diameter, diameter);
        this.color = color;
        this.diameter = diameter;
        this.setFilled(true);
        this.setFillColor(color);
    }

    /**
     * Constructor to create a ball. Will default x and y to 0,0.
     * @param diameter the diameter of the ball.
     * @param color the color to fill the ball.
     */

    public Ball(double diameter, Color color) {
        super(0, 0, diameter, diameter);
        this.color = color;
        this.diameter = diameter;
        this.setFilled(true);
        this.setFillColor(color);
    }

    public double getDiameter() {
        return diameter;
    }

    @Override
    public Color getColor() {
        return color;
    }


    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }


}


