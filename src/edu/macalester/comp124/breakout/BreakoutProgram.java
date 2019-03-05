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
    private static boolean POWER_UP_EXTRA_BALL = true;

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


    private static final int POWER_UP_DROP_RATE = 500; //higher numbers mean slower drop rates


    /* Instance variables */
    private ArrayList<Ball> balls = new ArrayList<>(); //balls, if extra ball power-up is enabled
    private ArrayList<Ball> boosts = new ArrayList<>();
    private Ball ball; //The ball.
    private double dx; //The ball's change of x during animation.
    private double dy; //The ball's change of y during animation.
    private Paddle paddle; //The paddle.
    private BrickWall wall; //The wall.
    private int lives; //The number of lives left.
    private GLabel showLives; //The lives display.
    private int boostcount; //counter for number of bricks after which boost shows up
    private GLabel showBoostCount; //brick boost count display
    private int boostdx = 0;
    private int boostdy = 1;
    private Random rand;
    private ExtraBallPowerUp ballPU;
    private boolean ballPUActive = false;
    /**
     * Initializes components of program.
     */

    public void init(){
        this.resize(WIDTH,HEIGHT+20); //Height + 20 due to lost pixels in window.

        ball = new Ball(BALL_X_START, BALL_Y_START, BALL_SIZE, Color.BLACK);

        if(POWER_UP_EXTRA_BALL){
            balls.add(ball);
            ballPU = new ExtraBallPowerUp(WIDTH/2 - 5, 200);
        }

        add(ball);


        wall = new BrickWall();
        add(wall);

        paddle = new Paddle(PADDLE_X,PADDLE_Y,PADDLE_WIDTH,PADDLE_HEIGHT, Color.BLACK);
        add(paddle);

        boosts.add(new Ball(0,0,BALL_SIZE, Color.RED)); //this adds a "boost" using the ball class

        lives = 3;
        showLives = new GLabel("Lives: " + Integer.toString(lives),5,20);
        showLives.setFont("Helvetica-18");
        add(showLives);

        boostcount = 5; //this counts the number of bricks that need to be broken for which a boost falls
        showBoostCount = new GLabel("Red Ball = Bomb; Green Ball = Extra Life",5,40);
        showBoostCount.setFont("Helvetica-18");
        add(showBoostCount);


        addMouseListeners();


        rand = new Random();

    }

    /**
     * Starts the program:
     *     Animates the ball and constantly checks for collisions, wins, and losses.
     */

    public void run() {
        int i = 0;
        while(true){

            if(i >= POWER_UP_DROP_RATE && POWER_UP_EXTRA_BALL){
                ballPUActive = true;
                dropPowerUp();
                i = 0;
            }
            if(!ballPUActive && POWER_UP_EXTRA_BALL) {
                i++;
            }

            if(POWER_UP_EXTRA_BALL){
                if(ballPUActive){
                    ballPU.move();
                }
                for(Ball b : balls){
                    b.move();
                }
            }
            else {
                ball.move();
            }

            pause(PAUSE_TIME);

            if(POWER_UP_EXTRA_BALL) {
                for(Ball b : balls){
                    checkForWallCollision(b);
                    checkForBrickCollision(b);
                }
                checkForPowerUpCollision();
            }
            else{
                checkForWallCollision(ball);
                checkForBrickCollision(ball);
            }

            if(checkForLoss() || checkForWin()){
                break;
            }
            for(Ball boost : boosts) {
                boost.move(boostdx, boostdy);
            }
            pause(0.15);

        }
    }

    /**
     * Checks to see if the ball has hit a brick. If the ball hits a brick from it's top or bottom, it'll change
     * its y movement. If it hits it from the left or right, it'll change its x movement.
     */

    private void checkForBrickCollision(Ball b){
        GObject Top = wall.getElementAt(b.getX() + BALL_SIZE/2,b.getY());
        GObject Bottom = wall.getElementAt(b.getX()+ BALL_SIZE/2,b.getY()+ BALL_SIZE);
        GObject Right = wall.getElementAt(b.getX()+ BALL_SIZE,b.getY() + BALL_SIZE/2);
        GObject Left = wall.getElementAt(b.getX(),b.getY() + BALL_SIZE/2);
        if(Top != null){
            wall.remove(Top);
            b.swapDy();
            boostcount--;
        }
        if(Bottom != null){
            wall.remove(Bottom);
            b.swapDy();
            boostcount--;
        }
        if(Right != null){
            wall.remove(Right);
            b.swapDx();
            boostcount--;
        }
        if(Left != null){
            wall.remove(Left);
            b.swapDx();
            boostcount--;
        }
        //showBoostCount.setLabel("Boost Count: " + Integer.toString(boostcount));

        if (boostcount==0){
            boostcount=5;

            int randomNum = rand.nextInt((2 - 1) + 1) + 1;
            if (randomNum==1) {
                Ball newBoost = new Ball(b.getX(), b.getY(), BALL_SIZE, Color.RED);
                boosts.add(newBoost);
                add(newBoost);
            }
            if (randomNum==2){
                Ball newBoost = new Ball(b.getX(), b.getY(), BALL_SIZE, Color.GREEN);

                boosts.add(newBoost);
                add(newBoost);
            }


        }

    }

    /**
     * Checks to see if the ball has hit a wall. If it hit the right or left wall, it'll change its
     * x movement. If it hits the top, it'll change its y movement.
     *
     * Does not account for bottom wall, as that is considered a lost life.
     */

    private void checkForWallCollision(Ball b){
        if(b.getX() + BALL_SIZE >= WIDTH || b.getX() <= 0){
            b.swapDx();
        }
        if(b.getY() <= 0){
            b.swapDy();
        }
        if(b.getY() + BALL_SIZE == paddle.getY() &&
                b.getX() + BALL_SIZE >= paddle.getX() &&
                b.getX() <=  paddle.getX() + paddle.getWidth()) {
            b.swapDy();
        }
        for(Ball boost : boosts) {
            if (boost.getY() + BALL_SIZE == paddle.getY() &&
                    boost.getX() + BALL_SIZE >= paddle.getX() &&
                    boost.getX() <= paddle.getX() + paddle.getWidth() && boost.isVisible()) {
                boost.setVisible(false);
                remove(boost);
                boostReaction(boost);

            }
        }

    }

    private void boostReaction(Ball boost){
        if (boost.getColor() == Color.RED){
            lives--;
        }
        if (boost.getColor() == Color.GREEN)
        {
            lives++;
        }
        showLives.setLabel("Lives: " + Integer.toString(lives));

    }

    /**
     * Checks to see if ball has hit the bottom of the screen as well as if the player has gotten a game over.
     * @return true for game over. Will decrement lives if ball hit bottom and player has not reached game over.
     */

    private boolean checkForLoss(){
        if(POWER_UP_EXTRA_BALL){
            return checkForLossWithPowerUp();
        }

        if(ball.getY() >= HEIGHT){
            if(lives > 0) {
                remove(ball);
                ball.setLocation(BALL_X_START, BALL_Y_START);
                lives--;
                showLives.setLabel("Lives: " + Integer.toString(lives));
                ball.setDy(-1);
                ball.setDx(1);
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

    private void checkForPowerUpCollision(){
        if(!ballPUActive){
            return;
        }
        if(ballPU.getY() + BALL_SIZE == paddle.getY() &&
                ballPU.getX() + BALL_SIZE >= paddle.getX() &&
                ballPU.getX() <=  paddle.getX() + paddle.getWidth()){
            ballPUActive = false;
            Ball newBall = addBall();
            newBall.setLocation(ballPU.getLocation());
            remove(ballPU);
            add(newBall);
        }
        else if (ballPU.getY() >= HEIGHT){
            ballPUActive = false;
            remove(ballPU);
        }
    }

    private boolean checkForLossWithPowerUp(){
        for(Ball b : balls){
            if(b.getY() >= HEIGHT){
                balls.remove(b);
                remove(b);

                break;
            }
        }
        if(balls.size() == 0 && lives > 0) {
            Ball b = addBall();
            b.setLocation(BALL_X_START, BALL_Y_START);
            lives--;
            showLives.setLabel("Lives: " + Integer.toString(lives));
            b.setDy(-1);
            b.setDx(1);
            add(b);
            pause(2000);
        }
        else if(balls.size() == 0){
            removeAll();
            GLabel gameOver = new GLabel("GAME OVER", WIDTH/2,HEIGHT/2);
            gameOver.setFont("Helvetica-24");
            gameOver.move(-1*gameOver.getWidth()/2,0);
            add(gameOver);
            return true;
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

    private Ball addBall(){
        Ball b = new Ball(BALL_X_START, BALL_Y_START, BALL_SIZE, Color.BLACK);
        balls.add(b);
        return b;
    }


    private void dropPowerUp(){
        ballPU.setLocation(WIDTH/2 - 5, 200);
        add(ballPU);

    }

    /**
     * Causes the paddle to follow the player's mouse.
     */

    public void mouseMoved(MouseEvent e){
        paddle.move(e.getX() - paddle.getX(), 0);
    }

}



