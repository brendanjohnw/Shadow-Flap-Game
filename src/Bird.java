import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;
/** A class that defines the actions and behaviours of a bird for both levels
 * */
public class Bird implements Attackable{

    private static final double VERTICAL_VEL_MAX = 10;
    private final double BIRDSPEED = 6;
    private final double GRAVITY_FACTOR = 0.4;
    private boolean briefMoment = true;
    private Point birdCoord;
    private Point birdInit = new Point(200,350);
    private boolean isTen = false;

    // For the deceleration of the bird when it flies up
    private double cumulativeSpeed = BIRDSPEED;

    // For the acceleration of the bird when it flies down

    private double cumulativeFactor = 0;
    private Image birdUp;
    private Image birdDown;
    private ArrayList<Health> birdHealthList;
    private int maxHealth = 3;
    private int deletedLives = 0;
    private Weapon birdWeapon;
    private static double BIRDDIMX = 55;
    private static double BIRDDIMY = 39;

    /** Constructor to initialize the bird */
    public Bird(){
        birdUp = new Image("res/level-0/birdWingUp.png");
        birdDown = new Image("res/level-0/birdWingDown.png");
        if(ShadowFlap.getLevel() == 0){
            maxHealth = 3;
            birdHealthList = new ArrayList<>(Health.getMAXHEALTHZERO());
        }
        initializeHealth(maxHealth);
        this.birdCoord = new Point(200, 350);
    }

    /** Implementation of the gravity model of the bird **/
    public void gravity(){
        // At maximum height, the vertical acceleration is zero for a brief moment
        if (briefMoment){
            cumulativeFactor = 0;
            briefMoment = false;
        }
        else{
            cumulativeFactor += GRAVITY_FACTOR;
        }
        double birdY = birdCoord.y + cumulativeFactor;
        birdCoord = new Point(birdCoord.x,birdY);
    }

    /** Implements the maximum speed of the bird */
    public void terminalVelocity(){
        double birdY = this.birdCoord.y + VERTICAL_VEL_MAX;
        birdCoord = new Point(birdCoord.x,birdY);
    }

    /** Registers the weapon picked up by the bird *
     * @param weapon is the weapon picked up by the bird which can either be of type Rock or Bomb
     */

    public void setWeapon(Weapon weapon){
        birdWeapon = weapon;
    }
    private void showBirdUp(Point birdCoord){
        birdUp.draw(birdCoord.x,birdCoord.y);
    }

    private void showBirdDown(Point birdCoord){
        birdDown.draw(birdCoord.x,birdCoord.y);
    }
    /** For the bird to move up */
    public void birdUp(){
        cumulativeSpeed -= GRAVITY_FACTOR;
        double birdY = birdCoord.y - cumulativeSpeed;
        cumulativeFactor = 0;
        birdCoord =  new Point(birdCoord.x,birdY);
    }

    /** sets the bird's image specific to level 1 when 10 points have been reached */
    public void levelUpBird(){
        birdUp = new Image("res/level-1/birdWingUp.png");
        birdDown = new Image("res/level-1/birdWingDown.png");
    }

    /** draws the hearts defined in the health list */
    public void drawHealth(){
        for(Health heart: birdHealthList){
            heart.drawLife();
        }
    }

    /** Reinitializes the health bar
     * @param maxHealth Indicates the maximum health for the level*/
    public void initializeHealth(int maxHealth){
        birdHealthList.clear();
        birdHealthList = new ArrayList<>(maxHealth);
        Health.initializeSpaces();
        for(int index = 0; index < maxHealth; index++) {
            birdHealthList.add(new Health());
            System.out.println(maxHealth);
        }
    }

/** Registers the damage taken by the bird */
    public boolean takeDamage(){
        birdHealthList.get(deletedLives).setNOLIFE();
        birdHealthList.get(deletedLives).drawLife();
        deletedLives++;
        if(deletedLives == Health.getCurrentMaxHealth()){
            return true;
        }else
            return false;
    }

    /** Updates the bird's wings every 10 frames */
    public void BirdWings(){
        if(ShadowFlap.getframeCount() >= ShadowFlap.getMaxFrame()){
            this.showBirdUp(this.getBirdCoord());
            isTen = true;
        }
        if(isTen == false){
            this.showBirdDown(this.getBirdCoord());
        }
        if(ShadowFlap.getframeCount() > ShadowFlap.getMaxFrame()+10){
            isTen = false;
            ShadowFlap.setFrameCount(0);
        }

    }

    /** Sets shooting range for the weapon picked up by the bird */
   public void fireWeapon(){
       if(this.getWeapon() instanceof Rock
               && this.getWeapon().getFrameCount() < Rock.getShootingRange()){
           this.getWeapon().shootWeapon();
           this.getWeapon().setFrameCount();
       }else if(this.getWeapon() instanceof Bomb
               && this.getWeapon().getFrameCount() < Bomb.getShootingRange()){
           this.getWeapon().shootWeapon();
           this.getWeapon().setFrameCount();
       }if(this.getWeapon() instanceof Rock && this.getWeapon().getFrameCount() == Rock.getShootingRange()){
           this.getWeapon().initializeFrameCount();
           this.setWeapon(null);
       }else if(this.getWeapon() instanceof Bomb && this.getWeapon().getFrameCount() == Rock.getShootingRange()){
           this.getWeapon().initializeFrameCount();
           this.setWeapon(null);
       }
   }

    /** gets the cumulative speed of the bird when it moves down */
    public double getCumulativeSpeed(){
        return cumulativeSpeed;
    }

    /** The cumulative factor is how quickly the speed increases by */
    public double getCumulativeFactor(){
        return cumulativeFactor;
    }

    /** To initialize the speed back to the initial fly-up speed */
    public void initializeSpeed(){
        this.cumulativeSpeed = BIRDSPEED;
    }

    /** Returns the coordinates of the bird */
    public Point getBirdCoord(){
        return birdCoord;
    }

    /** Gets the x-dimension of the bird image */
    public static double getBIRDDIMX(){
        return BIRDDIMX;
    }

    /** Gets the y-dimension of the bird image */
    public static double getBIRDDIMY(){
        return BIRDDIMY;
    }

    /** Returns the weapon picked up by the bird */
    public Weapon getWeapon(){
        return birdWeapon;
    }

    protected void initializeHealth(){
        deletedLives = 0;
    }

    /** Indicates the Initialization of the brief moment when the upward acceleration of the bird is 0 */
    public void initializeBriefMoment(){
        briefMoment = true;
    }

    /** For the bird to return back to its initial spawn location */
    public void initializeCoord(){
        birdCoord = birdInit;
    }
}
