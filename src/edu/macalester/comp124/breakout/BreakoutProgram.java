package edu.macalester.comp124.breakout;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.util.ArrayList;

/**
 * Main GraphicsProgram for the breakout game described
 * in exercise 10.10 int the Roberts Textbook.
 *
 */
public class BreakoutProgram extends GraphicsProgram {
    /* Private constants */
    private static final int PAUSE_TIME = 5;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 600;

    private static final double BRICK_WIDTH = 30;
    private static final double BRICK_HEIGHT = 10;
    private static final double BRICK_DISTANCE = 5;

    private static final double BRICK_STARTING_X = 10;
    private static final double BRICK_STARTING_Y = 60;

    private static final double PADDLE_WIDTH = 65;
    private static final double PADDLE_HEIGHT = 10;
    private static final double PADDLE_FROM_BOTTOM = 60;

    private static final double PADDLE_Y = HEIGHT - PADDLE_FROM_BOTTOM;
    private static final double PADDLE_X = WIDTH/2 - PADDLE_WIDTH/2;

    private static final double BALL_SIZE = 10;
    private static final double BALL_XSTART = WIDTH/2 - BALL_SIZE/2;
    private static final double BALL_YSTART = PADDLE_Y - BALL_SIZE;



    private Ball ball;
    private double dx;
    private double dy;
    private GRect paddle;

    private ArrayList<GRect> bricks;

    public void init(){
        this.resize(WIDTH,HEIGHT+20);

        bricks = new ArrayList();
        createAllBricks();

        ball = new Ball(BALL_XSTART, BALL_YSTART, BALL_SIZE, Color.BLACK);
        ball.setFilled(true);
        add(ball);

        paddle = new GRect(PADDLE_X,PADDLE_Y,PADDLE_WIDTH,PADDLE_HEIGHT);
        paddle.setFilled(true);
        add(paddle);

        dx = 1;
        dy = -1;

    }

    public void run() {
        while(true){
            ball.move(dx, dy);
            pause(PAUSE_TIME);
            if(ball.getX() + BALL_SIZE >= WIDTH || ball.getX() <= 0){
                dx = -1*dx;
            }
            if(ball.getY()+BALL_SIZE >= HEIGHT || ball.getY() <= 0){
                dy = -1*dy;
            }
        }
    }

    private void createAllBricks(){
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

    private void createBrickRow(double startingY, Color color){
        double xDistance = BRICK_WIDTH + BRICK_DISTANCE;
        double x = BRICK_STARTING_X;
        for(int i = 0; i <= 10; i++){
            GRect brick = new GRect(x,startingY,BRICK_WIDTH,BRICK_HEIGHT);
            bricks.add(brick);
            brick.setFilled(true);
            brick.setFillColor(color);
            add(brick);
            x += xDistance;
        }
    }

}

