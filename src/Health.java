import bagel.Image;
import bagel.util.Point;

/** This class defines the health of the birds, includes drawing the health bar and deducting health when
 * bird collides with pipe, flames or goes out of bounds
 */
public class Health {
    private static final int MAXHEALTHONE = 6;
    private static final int MAXHEALTHZERO = 3;
    private static Image LIFE;
    private static Image NOLIFE;
    private final Point LIFEBARCOORDSINIT= new Point(100,15);
    private final double XSPACE = 50;
    private Point LifeCoord;
    private boolean isNoLife = false;
    private static int currentMaxHealth = MAXHEALTHZERO;
    private static double spaces = 0;

    /** Constructor that initializes and creates health bar
     * */
    public Health(){
        LIFE = new Image("res/level/fullLife.png");
        NOLIFE = new Image("res/level/noLife.png");
        LifeCoord = new Point(LIFEBARCOORDSINIT.x+spaces, LIFEBARCOORDSINIT.y);
        spaces += XSPACE;
    }

    /** To display the health bar on the screen
     * */
    public void drawLife(){
        if(!isNoLife)
            LIFE.drawFromTopLeft(LifeCoord.x,LifeCoord.y);
        else if(isNoLife){
            NOLIFE.drawFromTopLeft(LifeCoord.x, LifeCoord.y);
        }
    }

    /** Returns the maximum health for level one
     * */
    public static int getMAXHEALTHONE() {
        return MAXHEALTHONE;
    }

    /** Returns the maximum health for level zero
     *  */
    public static int getMAXHEALTHZERO() {
        return MAXHEALTHZERO;
    }

    /** Sets the maximum health to MAXHEALTHONE when level up
     *  */
    public static void setCurrentMaxHealth(){
        currentMaxHealth = MAXHEALTHONE;
    }

    /** Gets the current health status
     * */
    public static int getCurrentMaxHealth(){
        return currentMaxHealth;
    }

    /** Reinitializes the spaces for the health
     * */
    public static void initializeSpaces(){
         spaces = 0;
    }

    /** indicates that the health is empty
     * */
    public void setNOLIFE(){
        isNoLife = true;
    }
}
