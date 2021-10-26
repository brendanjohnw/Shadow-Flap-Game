import bagel.Image;
import bagel.util.Point;
/** Class that defines a Bomb weapon, inherits from the Weapon class
 * */
public class Bomb extends Weapon{
    private static final Image BOMB = new Image("res/level-1/bomb.png");
    private static final double SHOOTING_RANGE = 50;
    /** Constructor to instantiate a Bomb weapon
     * */
    public Bomb(){
        super();
        weapon = BOMB;
    }

    /** Initiates and updates the coordinates when the weapon is triggered
     * */
    public void shootWeapon(){
        weapAttachedCoord = new Point(weapAttachedCoord.x+weaponSpeed, weapAttachedCoord.y);
        BOMB.draw(weapAttachedCoord.x+WEAPONDIM/2, weapAttachedCoord.y);
    }

    /** Draws the weapon on the screen
     * */
    @Override
    public void drawWeapon() {
        weaponMover();
        weapon = BOMB;
        weapon.draw(weapSpawnCoord.x, weapSpawnCoord.y);
    }

    /** Returns the type of weapon
     * @return String The name of the weapon type */
    public String getType(){
        return "Bomb";
    }

    /** Returns the shooting range of the weapon
     * @return double The shooting range of Bomb*/
    public static double getShootingRange(){
        return SHOOTING_RANGE;
    }

}
