import bagel.*;
import bagel.util.Point;


/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Brendan John Lee (1166409)
 */
public class ShadowFlap extends AbstractGame {

    private final double RES_Y = 768;
    private final double RES_X = 1024;

    // Maximum vertical velocity due to gravity
    private final double VERTICAL_VEL_MAX = 10;
    // The wings of the bird changes every 10 frames
    private final int BIRD_FRAME = 10;

    private final Point INITIAL_PT = new Point(200,350);
    private final Point INITIAL_PIPE_UP = new Point(1024,-100);
    private double lengthFactor = 2;
    private final Font startMessage;
    private final Font scoreCount;
    private final Font gameOver;

    // Cumulative acceleration factor due to gravity
    private double cumulativeFactor = 0;

    private Integer score = 0;
    private Integer frameCount = 0;

    private final double PIPESPEED = 5;
    private final double BIRDSPEED = 7;
    private final double GRAVITY_FACTOR = 0.4;

    private Bird bird;
    private Pipe pipe;
    private Background background;

    private Point birdCoord = INITIAL_PT;
    private Point pipeCoord = INITIAL_PIPE_UP;


    private boolean isStart = false;
    private boolean isTen = false;
    private boolean isGameOver = false;
    private boolean isWin = false;

    public ShadowFlap() {
        super(1024,768,"Shadow Flap");
        final int FONT_SIZE = 48;

        bird = new Bird();
        pipe = new Pipe();
        background = new Background();


        startMessage = new Font("res/slkscr.ttf", FONT_SIZE);
        scoreCount = new Font("res/slkscr.ttf", FONT_SIZE);
        gameOver = new Font("res/slkscr.ttf", FONT_SIZE);


    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    public void startMessage(Input input){
        startMessage.drawString("PRESS SPACE TO START",Window.getWidth()/5,Window.getHeight()/2);
        if(input.wasPressed(Keys.SPACE)){
            isStart = true;
        }


    }
    /** On screen prompts **/
    public void gameOverMessage(){
        gameOver.drawString("GAME OVER",Window.getWidth()/2.8 ,Window.getHeight()/2);
        scoreCount.drawString("FINAL SCORE: "+ score.toString(),Window.getWidth()/3.2 ,(Window.getHeight()/2)+75);

    }

    public void winMessage(){
        gameOver.drawString("CONGRATULATIONS!",Window.getWidth()/3.9 ,Window.getHeight()/2);
        scoreCount.drawString("FINAL SCORE: "+ score.toString(),Window.getWidth()/3.2 ,(Window.getHeight()/2)+75);
    }


    public void scoreCounter(Integer score){
        scoreCount.drawString("SCORE: "+score.toString(),100,100);
    }

    /** Mutates the coordinates of in-game objects **/

    public Point pipeMutator(Point pipeCoord){
        double pipeX = pipeCoord.x - PIPESPEED;
        double pipeY = pipeCoord.y;
        return new Point(pipeX,pipeY);

    }

    public Point birdUp(Point birdCoord){
        double birdY = birdCoord.y - BIRDSPEED;
        cumulativeFactor = 0;
        return new Point(birdCoord.x,birdY);
    }

    /** Implementation of the physics of the bird **/

    public Point gravity(Point birdCoord){

        cumulativeFactor += GRAVITY_FACTOR;
        double birdY = birdCoord.y + cumulativeFactor;
        return new Point(birdCoord.x,birdY);
    }

    public Point terminalVelocity(Point birdCoord){

        double birdY = birdCoord.y + VERTICAL_VEL_MAX;
        return new Point(birdCoord.x,birdY);
    }

    /** Other functional methods **/

    public void exit(Input input){
        if(input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

    }




    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        background.drawBackground();
        if(isStart == false){
            startMessage(input);
            exit(input);
        }else
        if (isGameOver){
           gameOverMessage();
           exit(input);
        }else
        if (isWin){
            winMessage();
            exit(input);
        }
        else
        {
    /** Add input methods here **/
            scoreCounter(score);


            frameCount++;
            if(input.isDown(Keys.SPACE)){
                birdCoord = birdUp(birdCoord);
            }
            else{
                if(cumulativeFactor <=VERTICAL_VEL_MAX) {
                    birdCoord = gravity(birdCoord);
                } else {
                    birdCoord = terminalVelocity(birdCoord);
                }
            }
        /** Implements the flappy wings **/
            if(frameCount >= BIRD_FRAME){
                bird.showBirdUp(birdCoord);
                isTen = true;
            }
            if(isTen == false){
                bird.showBirdDown(birdCoord);
            }
            if(frameCount > BIRD_FRAME*2){
                isTen = false;
                frameCount = 0;
            }
        // Draws and animates the pipes

            pipeCoord = pipeMutator(pipeCoord);
            pipe.drawPipe(pipeCoord,lengthFactor);

        // Collider
            if(pipe.isCollide(birdCoord)){
                isGameOver = true;
            }

            if(pipe.isScore(birdCoord)){
                System.out.println("Score!");
                score = pipe.generateScore();
                isWin = true;
            }
        // Out of Bounds
            if(birdCoord.y > RES_Y || birdCoord.y < 0){
                isGameOver = true;
            }



            scoreCount.drawString("Score: "+ score.toString(),100,100);
            exit(input);

        }
    }

}
