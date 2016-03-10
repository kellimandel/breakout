package edu.macalester.comp124.breakout;

import acm.graphics.GOval;

import java.awt.*;

/**
 * Created by Kelly on 3/10/16.
 */
public class Ball extends GOval{

    private Color color;
    private double diameter;

    public Ball(double x, double y, double diameter, Color color) {
        super(x, y, diameter, diameter);
        this.color = color;
        this.diameter = diameter;
        this.setFilled(true);
        this.setFillColor(color);
    }

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
