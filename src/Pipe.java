import bagel.*;
import bagel.DrawOptions;
import bagel.util.Point;
import bagel.util.Rectangle;
/** Abstract class that defines common pipe behaviours, applicable to any subclasses of Pipe */
public abstract class Pipe implements Randomizable, Collidable{

    protected static final double LENGTHFACTOR = 1;
    protected static final Point INITIAL_PT_UP = new Point(1024,-84 );
    protected final Point INITIAL_PIPE_UP = new Point(1024,0);
    protected static final int PIPE_DOWN_OFFSET = 936;
    protected static final int COLLIDER_OFFSET = 384;
    protected static int next_pipe = 100;
    protected final int SCOREOFFSET = 100;
    protected static double pipeSpeed = 5;
    private static final int INITIAL_NEXT_PIPE = 100;
    protected boolean isDestroyed = false;

    protected static final DrawOptions OPTIONS = new DrawOptions();
    protected Point pipeUpcoord;
    protected Flame flame;
    protected Rectangle colliderUp;
    protected Rectangle colliderDown;

    protected Rectangle scoreZone;
    protected Point colliderupCorner;
    protected Point colliderdownCorner;
    protected static Image pipe;

    /** Constructor for creating a Pipe object
     *  */
    public Pipe(){
        pipe = new Image("res/level/plasticPipe.png");
        pipeUpcoord = INITIAL_PT_UP;
        colliderupCorner = new Point(INITIAL_PIPE_UP.x-pipe.getWidth(),
                INITIAL_PIPE_UP.y/LENGTHFACTOR-COLLIDER_OFFSET);
        colliderUp = new Rectangle(colliderupCorner,
                pipe.getWidth(),pipe.getHeight());
        colliderdownCorner = new Point(INITIAL_PIPE_UP.x-pipe.getWidth(),
                INITIAL_PIPE_UP.y/LENGTHFACTOR+PIPE_DOWN_OFFSET-COLLIDER_OFFSET);
        colliderDown = new Rectangle(colliderdownCorner,pipe.getWidth(),pipe.getHeight()+SCOREOFFSET);
        scoreZone = new Rectangle(INITIAL_PIPE_UP.x-pipe.getWidth()+SCOREOFFSET,
                INITIAL_PIPE_UP.y/LENGTHFACTOR+COLLIDER_OFFSET-SCOREOFFSET,1,pipe.getHeight());
    }


    /** Updates the pipe movement on the screen
     *  */
    public void pipeMutator(){
        double pipeX = pipeUpcoord.x - pipeSpeed;
        double pipeY = pipeUpcoord.y;
        pipeUpcoord = new Point(pipeX,pipeY);
    }
    /** Draw the pipes on the screen
     * @param  coordinates the coordinates of the pipe
     * */
    public abstract void drawPipe(Point coordinates);
    /** Returns whether a weapon can destroy a specified pipe type
     * @param weapon A parameter that stores the weapon
     * */
    public abstract boolean canDestroy(Weapon weapon);

   /** Detects for collision between pipe and any entity (For this usecase it will be the Bird)
    * @param entity A parameter that stores the point of any entity*/
    public boolean isCollide(Point entity){
        return  colliderUp.intersects(entity)||
                colliderDown.intersects(entity)|| (flame != null && flame.isCollide(entity));
    }
    /** Detects for collision between pipe and weapon (Overloaded, takes in a Rectangle instead of a point)
     * @param entity A parameter that stores the Rectangle of any entity
      */
    public boolean isCollide(Rectangle entity){
        return  colliderUp.intersects(entity)||
                colliderDown.intersects(entity);
    }

    /** Returns true if a score is registered
     * @param birdCoord A parameter that stores the coordinates of the bird
     * @return Returns true iff the bird's coordinates intersects the score zone*/
    public boolean isScore(Point birdCoord){
        return scoreZone.intersects(birdCoord) && !this.isCollide(birdCoord);
    }


    public Point getPipeUpcoord(){
        return pipeUpcoord;
    }

    public void setPipeUpcoord(Point coordinates){
        this.pipeUpcoord = coordinates;
    }

    /** sets the next pipe's coordinates */
    public void setnextPipeUpcoord(){pipeUpcoord = new Point(pipeUpcoord.x+next_pipe, pipeUpcoord.y);}


    /** Gets the corner of the bottom collider */
    public Point getColliderdownCorner(){
        return colliderdownCorner;
    }

    /** Gets the image of the pipe */
    public Image getImage(){
        return pipe;
    }

    /** Gets the number of frames until the next pipe */
    public static int getNextPipe(){
        return next_pipe;
    }

    /** Gets the initial value of the next pipe */
    public static int getInitialNextPipe(){
        return INITIAL_NEXT_PIPE;
    }

    /** Sets the value for the next pipe for timescaling */
    public static void setNextPipe(int nextPipe){
        next_pipe= nextPipe;
    }

    /** Gets the speed of the pipes */
    public static double getpipeSpeed(){
        return pipeSpeed;
    }

    /** Sets the pipespeeds for timescaling */
    public static void setPipeSpeed(double speed){
        pipeSpeed = speed;
    }
}
