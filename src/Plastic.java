import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.util.Random;
/** This class represents a Plastic pipe, which inherits methods and attributes from the Pipe class
 * */
public class Plastic extends Pipe{
    private static final Image PLASTIC = new Image("res/level/plasticPipe.png");
    private static final int HIGHLOWOFFSET = 200;

    /** Constructor to create a subtype of Pipe called Plastic
     * */
    public Plastic(){
        super();
    }

    /** Draws the pipe on the screen
     * */
    @Override
    public void drawPipe(Point coordinates){
        pipe = PLASTIC;
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

    }
    /** Randomizes the pipe heights based on 3 types
     *  */
    @Override
    public void randomize(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(3);
        switch (randomNumber){
            case 1:
                showHigh();
            case 2:
                showLow();
            case 3:
                showMid();
        }
    }
    /** Returns whether the pipe can be destroyed by a certain type of weapon
     * @param weapon the weapon that was shot by the player
     * @return whether the pipe can be destroyed */
    @Override
    public boolean canDestroy(Weapon weapon){
        if(weapon.getType() == "Rock" || weapon.getType() == "Bomb")
            return true;
        else
            return false;
    }


    private void showHigh(){

        Point point = new Point(this.getPipeUpcoord().x,this.getPipeUpcoord().y-2*HIGHLOWOFFSET);
        this.setPipeUpcoord(point);
    }

    private void showLow(){
        Point point = new Point(this.getPipeUpcoord().x,this.getPipeUpcoord().y+HIGHLOWOFFSET);
        this.setPipeUpcoord(point);
    }

    private void showMid(){
        this.setPipeUpcoord(this.getPipeUpcoord());
    }

}
