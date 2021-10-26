import bagel.*;
import bagel.util.Point;
/** Class tht defines the backgrounds for both levels
 * */
public class Background {
    private Image background = new Image("res/level-0/background.png");
    private final Point BACKGROUNDPOINT = new Point(Window.getWidth()/2.0,Window.getHeight()/2.0);
    /** Draws the background
     * */
    public void drawBackground(){
        background.draw(BACKGROUNDPOINT.x, BACKGROUNDPOINT.y);
    }
    /** Sets the background to level 1's background when level up
     */
    public void backgroundLevelUp(){
        background = new Image("res/level-1/background.png");
    }
}
