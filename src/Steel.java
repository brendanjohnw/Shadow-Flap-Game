import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;
/** This class represents a Steel pipe which inherits attributes and methods from the Pipe class
 *
 */

public class Steel extends Pipe{
    private static final Image STEEL = new Image("res/level-1/steelPipe.png");
    private final double MAXY = -284;
    private final double MINY = 116;


    private int flameFrame = 0;

    /** Constructor to create a Steel pipe object
     * */
    public Steel(){
        super();
        flame = new Flame();
    }

    /** Draws the pipes on the screen
     * @param  coordinates the coordinates of the pipe
     */
    @Override
    public void drawPipe(Point coordinates){
        pipe = STEEL;
        double pipeLength = coordinates.y/LENGTHFACTOR;
        pipe.draw(coordinates.x,pipeLength);
        pipe.draw(coordinates.x,pipeLength+PIPE_DOWN_OFFSET, OPTIONS.setRotation(Math.PI));
        // Pipe collider logic
        colliderUp = new Rectangle(coordinates.x-pipe.getWidth(),pipeLength,
                pipe.getWidth(),pipe.getHeight());
        colliderupCorner = new Point(coordinates.x-pipe.getWidth(),
                pipeLength-COLLIDER_OFFSET);
        colliderUp.moveTo(colliderupCorner);
        colliderDown = new Rectangle(coordinates.x-pipe.getWidth(),
                pipeLength+PIPE_DOWN_OFFSET,pipe.getWidth(),pipe.getHeight());
        colliderdownCorner = new Point(coordinates.x-pipe.getWidth(),
                pipeLength+PIPE_DOWN_OFFSET-COLLIDER_OFFSET);
        colliderDown.moveTo(colliderdownCorner);
        scoreZone = new Rectangle(coordinates.x+SCOREOFFSET,pipeLength+COLLIDER_OFFSET-SCOREOFFSET,1,pipe.getHeight());
        flameFrame = flame.flameUpdater(flameFrame, this);

    }

    /** Indicates whether the pipe can be destroyed by the specified weapon
     * @param weapon*/
    public boolean canDestroy(Weapon weapon){
        if(weapon.getType() == "Bomb")
            return true;
        else
            return false;
    }
    /** Randomizes the pipes heights
     * */
    @Override
    public void randomize(){
        Random rand = new Random();
        double randomLength = MINY + (MAXY-MINY)*rand.nextDouble();
        Point newLength = new Point(this.getPipeUpcoord().x, randomLength);
        this.setPipeUpcoord(newLength);
    }


}
