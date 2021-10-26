import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
/** A class that defines the rock weapon, inheits from the weapon class */
public class Rock extends Weapon{
    public static final Image ROCK = new Image("res/level-1/rock.png");
    private static final double SHOOTING_RANGE = 25;
    /** Constructor to instantiate a Rock weapon
     * */
    public Rock(){
        super();
        weapon = ROCK;
    }
    @Override
    public String getType(){
        return "Rock";
    }
    /** Initiates the weapon shooting
     * */
    @Override
    public void shootWeapon(){
        weapAttachedCoord = new Point(weapAttachedCoord.x+weaponSpeed, weapAttachedCoord.y);
        ROCK.draw(weapAttachedCoord.x+WEAPONDIM/2, weapAttachedCoord.y);
    }

    /** Draws the weapon on the screen
     * */
    @Override
    public void drawWeapon() {
        weaponMover();
        weapon = ROCK;
        weapon.draw(weapSpawnCoord.x, weapSpawnCoord.y);
    }

    /** Gets the shooting range for the rock weapon
     * */
    public static double getShootingRange(){
        return SHOOTING_RANGE;
    }
}
