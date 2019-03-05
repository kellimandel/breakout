package edu.macalester.comp124.breakout;

import acm.graphics.GOval;

import java.awt.*;

/**
 * The ball in Breakout. A circle (GOval).
 * Created by Kelly on 3/10/16.
 */
public class Ball extends GOval{


    private double dx = 1;
    private double dy = -1;
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

    public void swapDx(){
        dx = -1*dx;
    }

    public void swapDy(){
        dy = -1*dy;
    }


    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void move(){
        this.move(dx,dy);
    }



}


