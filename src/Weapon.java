import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;
/** An abstract class that defines common behaviors for weapon subtypes
 * */
public abstract class Weapon implements Randomizable, Collidable{
    // spawn point for the weapon for bird to pick up
    protected Point weapSpawnCoord;
    // whether weapon attached to the bird
    protected boolean isAttached;
    // coordinates when the weapon is attached to the bird
    protected Point weapAttachedCoord;

    protected Image weapon;
    protected Rectangle weaponRectangle;
    protected static double weaponSpeed = 5;
    protected int frameCount = 0;

    private static int NEXT_WEAPON = 100;
    private static int INIT_NEXT_WEAPON = 100;

    protected static final double UPPER_BOUND = 500;
    protected static final double LOWER_BOUND = 100;
    protected static final double LOWER_BOUND_X = 1300;
    protected static final double UPPER_BOUND_X = 1500;

    // Dimensions of the weapon x = 32, y = 32
    protected static final double WEAPONDIM = 32;
    public Weapon(){
        randomize();
        isAttached = false;
        NEXT_WEAPON = 100;
        INIT_NEXT_WEAPON = 100;

    }

    public abstract void drawWeapon();

    protected void weaponMover(){
        double weapSpawnCordx = weapSpawnCoord.x - this.weaponSpeed;
        weapSpawnCoord = new Point(weapSpawnCordx,weapSpawnCoord.y);
        weaponRectangle = new Rectangle(weapSpawnCoord.x-(WEAPONDIM/2),
                weapSpawnCoord.y-(WEAPONDIM/2),
                WEAPONDIM,WEAPONDIM);
    }
    /** Randomizes where the weapon spawns on the screen */
    public void randomize(){
        Random rand = new Random();
        double randomX = LOWER_BOUND_X + (UPPER_BOUND_X-LOWER_BOUND_X)*rand.nextDouble();
        double randomY = LOWER_BOUND + (UPPER_BOUND-LOWER_BOUND)*rand.nextDouble();
        weapSpawnCoord = new Point(randomX,randomY);
        weaponRectangle = new Rectangle(weapSpawnCoord.x-(WEAPONDIM/2),
                weapSpawnCoord.y-(WEAPONDIM/2),
                WEAPONDIM,WEAPONDIM);
    }

    /** returns an integer that represents the type of weapon spawned */
    public static int randomizeType(){
        Random rand = new Random();
        return rand.nextInt(2);
    }
   /**checks whether the bird collides with the weapon so the bird can pick up the weapon */
    public boolean isCollide(Point coordinates){
        return weaponRectangle.intersects(coordinates);
    }

    /** Attaches the weapon to the bird
     * @param birdCoord
     * @param bird*/
    public void attachWeapon(Point birdCoord, Bird bird){
        this.weapSpawnCoord = new Point(birdCoord.x+Bird.getBIRDDIMX()/2,
                                            birdCoord.y+Bird.getBIRDDIMY()/2);
        this.isAttached = true;
        bird.setWeapon(this);
    }

    /** Gets the weapon's rectangle */
    public Rectangle getWeaponRectangle(){
        return weaponRectangle;
    }

    /** Renders the weapon on the bird's beak
     * @params bird*/
    public static void renderAttachedWeapon(Bird bird){
        Weapon birdWeapon = bird.getWeapon();
        if(birdWeapon.isAttached){
            birdWeapon.weapAttachedCoord = new Point(bird.getBirdCoord().x, bird.getBirdCoord().y);
            birdWeapon.weapon.draw(birdWeapon.weapAttachedCoord.x+WEAPONDIM,
                    birdWeapon.weapAttachedCoord.y);
        }

    }

    /** Gets the type of weapon */
    public abstract String getType();

    /** Initializes the frame count for weapon spawning to 0 */
    public void initializeFrameCount(){
        frameCount = 0;
    }

    /** Gets the current frame count for weapon spawning */
    public int getFrameCount(){
        return frameCount;
    }

    /** Updates the frame counter for weapon spawning */
    public void setFrameCount(){
        frameCount+=1;
    }

    /** Abstract class that declares the shooting mechanism for the weapons */
    public abstract void shootWeapon();

    /** Returns the speed of the weapon */
    public static double getWeaponSpeed(){
        return weaponSpeed;
    }

    /** Sets the speed of the weapon for timescaling */
    public static void setWeaponSpeed(double Speed){ weaponSpeed = Speed; }

    /** gets the number of frames for the next wepaon to be spawned */
    public static int getNextWeapon(){
        return NEXT_WEAPON;
    }

    /** gets the initial value for the next weapon */
    public static int getInitNextWeapon(){
        return INIT_NEXT_WEAPON;
    }
}
