package edu.macalester.comp124.breakout;

import acm.graphics.GRect;

import java.awt.*;

/**
 * Created by Kelly on 3/4/19.
 */
public class ExtraBallPowerUp extends GRect {

    public ExtraBallPowerUp(double x, double y) {
        super(x, y, 10, 10);
        this.setFilled(true);
        this.setFillColor(Color.GREEN);
    }

    public void move(){
        this.move(0,1);
    }


}
