import bagel.*;

import java.util.ArrayList;

import java.util.Random;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2021
 * Please filling your name below
 * author: Brendan John Lee (1166409)
 */

public class ShadowFlap extends AbstractGame {

    private static final double VERTICAL_VEL_MAX = 10;
    public static final int RES_Y = 768;
    public static final int RES_X = 1024;
    private static final double SPACING = 75;
    private static final int MAX_FRAME = 10;
    private static final int FONT_SIZE = 48;

    private static int levelCallCount = 0;
    private static Integer level;
    private static final int NEXTLEVELFRAMES = 150;

    private final Font STARTMESSAGE;
    private final Font LEVELUP;
    private final Font SCORECOUNT;
    private final Font GAMEOVER;
    private final Font PRESS_S;

    /** Attributes relating to bird and pipe physics **/

    // Maximum vertical velocity due to gravity

    private static Integer score;
    private static Integer frameCount;
    private Integer pipeFrames;
    private Integer weapFrames;

    private final Bird BIRD;
    private Background background;
    private String prompt;

    private boolean isStart;
    private boolean isGameOver;
    private boolean isWin;
    private boolean isLevelUp;

    private ArrayList<Pipe> pipeList;
    private ArrayList<Weapon> weaponList;


    private ShadowFlap() {

        super(RES_X,RES_Y,"Shadow Flap");
        score = 0;
        frameCount = 0;
        pipeFrames = 0;
        weapFrames = 0;
        level = 0;

        isStart = false;
        isGameOver = false;
        isWin = false;
        isLevelUp = false;

        BIRD = new Bird();
        background = new Background();

        STARTMESSAGE = new Font("res/slkscr.ttf", FONT_SIZE);
        SCORECOUNT = new Font("res/slkscr.ttf", FONT_SIZE);
        GAMEOVER = new Font("res/slkscr.ttf", FONT_SIZE);
        LEVELUP = new Font("res/slkscr.ttf", FONT_SIZE);
        PRESS_S = new Font("res/slkscr.ttf", FONT_SIZE);

        pipeList = new ArrayList<>();
        weaponList = new ArrayList<>();

    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = new ShadowFlap();
        game.run();
    }

    private void startMessage(Input input) {
        double stringWidth = STARTMESSAGE.getWidth("PRESS SPACE TO START");

        STARTMESSAGE.drawString(
                "PRESS SPACE TO START",
                (Window.getWidth() / 2.0) - (stringWidth / 2.0),
                (Window.getHeight() / 2.0));
        if (level == 1) {
            stringWidth = PRESS_S.getWidth("PRESS 'S' TO SHOOT");
            PRESS_S.drawString(
                    "PRESS 'S' TO SHOOT",
                    (Window.getWidth() / 2.0) - (stringWidth / 2.0),
                    (Window.getHeight() / 2.0) + 68);
        }
        if (input.wasPressed(Keys.SPACE)) {
            isStart = true;
        }
    }

    private void gameOverMessage(boolean isWin) {

        if (!isWin)
            prompt = "GAME OVER";
        if (isWin)
            prompt = "CONGRATULATIONS!";
         
        double stringWidth = STARTMESSAGE.getWidth(prompt);
        GAMEOVER.drawString(prompt, (Window.getWidth() / 2.0) - (stringWidth / 2.0),
                Window.getHeight() / 2.0);

        stringWidth = STARTMESSAGE.getWidth("FINAL SCORE: " + score.toString());

        SCORECOUNT.drawString("FINAL SCORE: " + score.toString(),
                (Window.getWidth() / 2.0) - (stringWidth / 2.0), (Window.getHeight() / 2.0) + SPACING);

    }
    private void scoreCounter(Integer score){
        SCORECOUNT.drawString("SCORE: "+score.toString(),100,100);
    }
    private void exit(Input input){
        if(input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
    }
    public static Integer getLevel(){
        return level;
    }

    private void levelUp(){
        double stringWidth = LEVELUP.getWidth("LEVEL-UP!");
        LEVELUP.drawString("LEVEL-UP!",
                (Window.getWidth()/2.0)-(stringWidth/2.0),(Window.getHeight()/2.0));
        stringWidth = SCORECOUNT.getWidth("FINAL SCORE: " + score.toString());
        SCORECOUNT.drawString("FINAL SCORE: " + score.toString(),
                (Window.getWidth() / 2.0) - (stringWidth / 2.0), (Window.getHeight() / 2.0) + SPACING);
        if(levelCallCount > NEXTLEVELFRAMES){
            reinitializeAll();
        }
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        background.drawBackground();
        if(score==10 && !isLevelUp){
            frameCount = 0;
            //Show level 1 background
            levelUp();
            levelCallCount++;
            if(levelCallCount > NEXTLEVELFRAMES){
                isStart = false;
                startMessage(input);
            }
        }else if(score == 30){
            isGameOver = true;
            isWin = true;
            gameOverMessage(isWin);
        }else
        if(!isStart){
            startMessage(input);
            exit(input);
        }else
        if (isGameOver){
            gameOverMessage(isWin);
        }else{
            if((pipeFrames == 0 || pipeFrames == Pipe.next_pipe) || (weapFrames == Weapon.getNextWeapon())){
                Pipe pipe = new Steel();
                Weapon weapon = new Rock();
                if(level == 0 || (level == 1 && randomize() == 1)){
                    pipe = new Plastic();
                }else
                if(level == 1 && randomize() == 0){
                    pipe = new Steel();
                }
                pipe.randomize();
                pipeList.add(pipe);
                if(pipeFrames == Pipe.next_pipe){
                    pipe.setnextPipeUpcoord();
                    pipeFrames = 0;
                }
                // Updates the weapons
                if(level == 1 && weapFrames == Weapon.getNextWeapon()){
                    int randomInt = Weapon.randomizeType();
                    if(randomInt == 0){
                        weapon = new Rock();
                        weaponList.add(weapon);

                    }else if(randomInt == 1){
                        weapon = new Bomb();
                        weaponList.add(weapon);
                    }
                    weapon.randomize();
                    weapFrames = 0;
                }
            }

            pipeFrames++;
            weapFrames++;
            frameCount++;

            // Draws and animates the pipes

            if(level == 1){
                for(Weapon weapon: weaponList){
                    weapon.drawWeapon();
                }
            }

            for(Pipe pipe : pipeList){
                    pipe.pipeMutator();
                    pipe.drawPipe(pipe.getPipeUpcoord());
            }

            scoreCounter(score);


            if(input.wasPressed(Keys.SPACE)||BIRD.getCumulativeSpeed() > 0){
                BIRD.birdUp();
                if (input.wasPressed(Keys.SPACE)){
                    BIRD.initializeSpeed();
                }
            }
            BIRD.drawHealth();

            if(input.wasPressed(Keys.L)){
                Timescaler.scale();
            }else if(input.wasPressed(Keys.K)){
                Timescaler.downscale();
            }

            if(BIRD.getCumulativeFactor() <=VERTICAL_VEL_MAX) {
                BIRD.gravity();
            } else {
                BIRD.terminalVelocity();
            }

            BIRD.BirdWings();

            weaponUpdater(pipeList,BIRD);

            // between bird and weapon pick up
            if(level == 1){
                for(Weapon weapon: weaponList){
                    if(weapon.isCollide(BIRD.getBirdCoord())){
                        System.out.println("Picked up the weapon");
                        weapon.attachWeapon(BIRD.getBirdCoord(), BIRD);
                        BIRD.setWeapon(weapon);
                        weaponList.remove(weapon);
                        break;
                    }
                }
            }
            // Rendering attached weapon to bird
            if(BIRD.getWeapon() != null)
                Weapon.renderAttachedWeapon(BIRD);

            for(Pipe pipe: pipeList){
                if(pipe.isCollide(BIRD.getBirdCoord())){
                    pipeList.remove(pipe);
                    isGameOver = BIRD.takeDamage();
                    if(isGameOver){
                        isWin = false;
                    }
                    break;
                }else
                if(pipe.isScore(BIRD.getBirdCoord())){
                    System.out.println("Score!");
                    score += 1;
                    isGameOver = false;
                    isWin = false;
                    break;
                }
            }

            // Out of Bounds
            if(BIRD.getBirdCoord().y > RES_Y || BIRD.getBirdCoord().y < 0){
                BIRD.initializeCoord();
                isGameOver = BIRD.takeDamage();
                if(isGameOver){
                    isWin = false;
                }
            }

            if((input.wasPressed(Keys.S) && BIRD.getWeapon() != null)){
                BIRD.getWeapon().isAttached = false;

            }else if(BIRD.getWeapon()!=null && BIRD.getWeapon().isAttached==false){
                BIRD.fireWeapon();
            }

            for(Pipe pipe: pipeList){
                if(pipe.getPipeUpcoord().x == 0){
                    pipeList.remove(pipe);
                }
            }
            // Count score
            SCORECOUNT.drawString("Score: "+ score.toString(),100,100);
        }
        exit(input);
    }

    private static int randomize(){
        Random rand = new Random();
        return rand.nextInt(2);
    }

    private static void weaponUpdater(ArrayList<Pipe> pipeList, Bird BIRD){
        for(Pipe pipe: pipeList) {
            // checking collision between pipe and weapon
            if (BIRD.getWeapon() != null &&
                    (pipe.isCollide(BIRD.getWeapon().getWeaponRectangle())
                            && pipe.canDestroy(BIRD.getWeapon()) && BIRD.getWeapon().isAttached == false)) {
                pipe.isDestroyed = true;
                pipeList.remove(pipe);
                BIRD.setWeapon(null);
                score++;
                break;

            }
        }
    }

    private void reinitializeAll(){
        isLevelUp = true;
        level = 1;
        background.backgroundLevelUp();
        BIRD.initializeCoord();
        BIRD.initializeHealth(Health.getMAXHEALTHONE());
        BIRD.initializeBriefMoment();
        Health.setCurrentMaxHealth();
        BIRD.levelUpBird();
        BIRD.initializeHealth();
        weaponList.clear();
        pipeList.clear();
        pipeFrames = 0;
        weapFrames = 0;
        frameCount = 0;
    }

    public static int getMaxFrame(){
        return MAX_FRAME;
    }
    public static void setFrameCount(int count){
        frameCount = count;
    }
    public static int getframeCount(){
        return frameCount;
    }

}
