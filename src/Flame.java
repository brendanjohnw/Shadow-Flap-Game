import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

/** This class defines the flames that will be shot out of the steel pipes
 * The flames will only be rendered if the pipe is of type Steel
 * */

public class Flame implements Collidable {
    private Point flameCoordinates;
    private final int FLAMEOFFSET = 168;
    private Rectangle flameRectangleTop;
    private Rectangle flameRectangleBottom;
    private static Image flame;
    private DrawOptions OPTIONS;

    /** Constructor to create a Flame object */
    public Flame(){
        flame = new Image("res/level-1/flame.png");
        flameCoordinates = new Point(0,0);
        flameRectangleTop = new Rectangle(flameCoordinates,flame.getWidth(),flame.getHeight());
        flameRectangleBottom= new Rectangle(flameCoordinates,flame.getWidth(),flame.getHeight());
        OPTIONS = new DrawOptions();
    }

    /** For the flame to be displayed to the screen
     * @param steelPipe Defines the steel pipe that contains points necessary for the calculation of the spawn location*/

    public void drawFlame(Pipe steelPipe){
        Point pipeCorner = steelPipe.getColliderdownCorner();
        double flameX = pipeCorner.x+flame.getWidth()/2;
        double flameY = pipeCorner.y-FLAMEOFFSET;
        flame.drawFromTopLeft(flameX, flameY);
        flameRectangleTop = new Rectangle(flameX,flameY,flame.getWidth(),flame.getHeight());
        // bottom flame
        flameY = pipeCorner.y-flame.getHeight();
        flame.drawFromTopLeft(flameX,flameY, OPTIONS.setRotation(Math.PI));
        flameRectangleBottom = new Rectangle(flameX, flameY,flame.getWidth(),flame.getHeight());
    }

   /** Updates the flame every 20 frames
    * @param flameFrame Parameter that defines how many frames have elapsed since the first frame for the flame
    * @param pipe Parameter that defines the pipe that is going to be mutated
    * @return int flameFrame*/

    public int flameUpdater(int flameFrame, Steel pipe){
        if(flameFrame < 30 ) {
            drawFlame(pipe);
            flameFrame++;
        }else if(flameFrame >=30 && flameFrame < 50){
            flameFrame ++;
        }else if(flameFrame >= 50){
            flameFrame = 0;
        }
        return flameFrame;
    }

    /** Detects colission between the flame and the bird
     * @param birdCoord defines the bird's coordinates that is being checked against
     * @return boolean Returns whether the bird collides with the flame or not*/


    @Override
    public boolean isCollide(Point birdCoord) {
        return flameRectangleBottom.intersects(birdCoord)||flameRectangleTop.intersects(birdCoord);
    }
}
