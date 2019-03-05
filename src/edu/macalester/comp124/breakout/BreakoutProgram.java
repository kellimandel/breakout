package edu.macalester.comp124.breakout;

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


/**
 * Main GraphicsProgram for the breakout game described
 * in exercise 10.10 int the Roberts Textbook.
 *
 */
public class BreakoutProgram extends GraphicsProgram {

    /* Private constants */
    private static final int PAUSE_TIME = 5; //Time between each frame of animation.
    private static final int WIDTH = 400; //Width of the GraphicsProgram canvas.
    private static final int HEIGHT = 600; //Height of the GraphicsProgram canvas.

    private static final double PADDLE_WIDTH = 65; //Width of the paddle.
    private static final double PADDLE_HEIGHT = 10; //Height of the paddle.
    private static final double PADDLE_FROM_BOTTOM = 60; //The distance between the paddle and the bottom of the canvas.

    private static final double PADDLE_Y = HEIGHT - PADDLE_FROM_BOTTOM; //Starting Y position of top left of paddle.
    private static final double PADDLE_X = WIDTH/2 - PADDLE_WIDTH/2; //Starting X position of top left of paddle.

    private static final double BALL_SIZE = 10; //The diameter of the ball.
    private static final double BALL_X_START = WIDTH/2 - BALL_SIZE/2; //Starting X position of top left of ball.
    private static final double BALL_Y_START = PADDLE_Y - BALL_SIZE; //Starting Y position of top left of ball.



    /* Instance variables */
    private Ball ball; //The ball.
    private double dx; //The ball's change of x during animation.
    private double dy; //The ball's change of y during animation.
    private Paddle paddle; //The paddle.
    private BrickWall wall; //The wall.
    private int lives; //The number of lives left.
    private GLabel showLives; //The lives display.
    private int boostcount; //counter for number of bricks after which boost shows up
    private Ball boost; //bomb boost
    private GLabel showBoostCount; //brick boost count display
    private int boostdx = 0;
    private int boostdy = 1;
    private Random rand;
    /**
     * Initializes components of program.
     */

    public void init(){
        this.resize(WIDTH,HEIGHT+20); //Height + 20 due to lost pixels in window.

        ball = new Ball(BALL_X_START, BALL_Y_START, BALL_SIZE, Color.BLACK);
        add(ball);


        wall = new BrickWall();
        add(wall);

        paddle = new Paddle(PADDLE_X,PADDLE_Y,PADDLE_WIDTH,PADDLE_HEIGHT, Color.BLACK);
        add(paddle);

        boost = new Ball(0,0,BALL_SIZE, Color.RED); //this adds a "boost" using the ball class

        lives = 3;
        showLives = new GLabel("Lives: " + Integer.toString(lives),5,20);
        showLives.setFont("Helvetica-18");
        add(showLives);

        boostcount = 5; //this counts the number of bricks that need to be broken for which a boost falls
        showBoostCount = new GLabel("Red Ball = Bomb; Green Ball = Extra Life",5,40);
        showBoostCount.setFont("Helvetica-18");
        add(showBoostCount);


        addMouseListeners();

        dx = 1;
        dy = -1;

        rand = new Random();

    }

    /**
     * Starts the program:
     *     Animates the ball and constantly checks for collisions, wins, and losses.
     */

    public void run() {
        while(true){

            ball.move(dx, dy);
            pause(PAUSE_TIME);

            checkForWallCollision();

            checkForBrickCollision();

            if(checkForLoss() || checkForWin()){
                break;
            }
            boost.move(boostdx,boostdy);
            pause(0.15);

        }
    }

    /**
     * Checks to see if the ball has hit a brick. If the ball hits a brick from it's top or bottom, it'll change
     * its y movement. If it hits it from the left or right, it'll change its x movement.
     */

    private void checkForBrickCollision(){
        GObject Top = wall.getElementAt(ball.getX() + BALL_SIZE/2,ball.getY());
        GObject Bottom = wall.getElementAt(ball.getX()+ BALL_SIZE/2,ball.getY()+ BALL_SIZE);
        GObject Right = wall.getElementAt(ball.getX()+ BALL_SIZE,ball.getY() + BALL_SIZE/2);
        GObject Left = wall.getElementAt(ball.getX(),ball.getY() + BALL_SIZE/2);
        if(Top != null){
            wall.remove(Top);
            dy = -1*dy;
            boostcount--;
        }
        if(Bottom != null){
            wall.remove(Bottom);
            dy = -1*dy;
            boostcount--;
        }
        if(Right != null){
            wall.remove(Right);
            dx = -1*dx;
            boostcount--;
        }
        if(Left != null){
            wall.remove(Left);
            dx = -1*dx;
            boostcount--;
        }
        //showBoostCount.setLabel("Boost Count: " + Integer.toString(boostcount));

        if (boostcount==0){
            boostcount=5;
            int randomNum = rand.nextInt((2 - 1) + 1) + 1;
            if (randomNum==1) {
                boost = new Ball(ball.getX(), ball.getY(), BALL_SIZE, Color.RED);
            }
            if (randomNum==2){
                boost = new Ball(ball.getX(), ball.getY(), BALL_SIZE, Color.GREEN);
            }
            add(boost);
        }

    }

    /**
     * Checks to see if the ball has hit a wall. If it hit the right or left wall, it'll change its
     * x movement. If it hits the top, it'll change its y movement.
     *
     * Does not account for bottom wall, as that is considered a lost life.
     */

    private void checkForWallCollision(){
        if(ball.getX() + BALL_SIZE >= WIDTH || ball.getX() <= 0){
            dx = -1*dx;
        }
        if(ball.getY() <= 0){
            dy = -1*dy;
        }
        if(ball.getY() + BALL_SIZE == paddle.getY() &&
                ball.getX() + BALL_SIZE >= paddle.getX() &&
                ball.getX() <=  paddle.getX() + paddle.getWidth()) {
            dy = -1 * dy;
        }
        if(boost.getY() + BALL_SIZE == paddle.getY() &&
                boost.getX() + BALL_SIZE >= paddle.getX() &&
                boost.getX() <=  paddle.getX() + paddle.getWidth()){
            remove(boost);
            boostReaction();

        }

    }

    private void boostReaction(){
        if (boost.getColor() == Color.RED){
            lives--;
        }
        if (boost.getColor() == Color.GREEN)
        {
            lives++;
        }
    }

    /**
     * Checks to see if ball has hit the bottom of the screen as well as if the player has gotten a game over.
     * @return true for game over. Will decrement lives if ball hit bottom and player has not reached game over.
     */

    private boolean checkForLoss(){
        if(ball.getY() >= HEIGHT){
            if(lives > 0) {
                remove(ball);
                ball.setLocation(BALL_X_START, BALL_Y_START);
                lives--;
                showLives.setLabel("Lives: " + Integer.toString(lives));
                dy = -1;
                dx = 1;
                add(ball);
                pause(2000);
            }
            else{
                removeAll();
                GLabel gameOver = new GLabel("GAME OVER", WIDTH/2,HEIGHT/2);
                gameOver.setFont("Helvetica-24");
                gameOver.move(-1*gameOver.getWidth()/2,0);
                add(gameOver);
                return true;
            }
        }
        return false;

    }

    /**
     * Checks to see if any bricks remain.
     * @return true if all bricks are broken and displays a congratulatory message.
     */

    private boolean checkForWin(){
        if(wall.getElementCount() == 0){
            removeAll();
            GLabel congrats = new GLabel("YOU WON!", WIDTH/2,HEIGHT/2);
            congrats.setFont("Helvetica-24");
            congrats.move(-1*congrats.getWidth()/2,0);
            add(congrats);
            return true;
        }
        return false;
    }

    /**
     * Causes the paddle to follow the player's mouse.
     */

    public void mouseMoved(MouseEvent e){
        paddle.move(e.getX() - paddle.getX(), 0);
    }

}



