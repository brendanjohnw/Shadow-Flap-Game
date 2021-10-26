import bagel.*;
import bagel.DrawOptions;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Pipe {
    private final Point INITIAL_PT_UP = new Point(1024,0);
    private final Point INITIAL_PT_DOWN = new Point(1024,768);
    private final int PIPE_DOWN_OFFSET = 936;
    private final int COLLIDER_OFFSET = 384;
    private final int SCORE_ZONE_HEIGHT = 168;

    private DrawOptions options = new DrawOptions();
    private Point pipeUpcoord = INITIAL_PT_UP;
    private Point pipeDowncoord = INITIAL_PT_DOWN;

    private Rectangle colliderUp;
    private Rectangle colliderDown;
    private Rectangle scoreZone;
    private Point colliderupCorner;
    private Point colliderdownCorner;



    private final Image pipe = new Image("res/pipe.png");
    public Pipe(){

        pipeUpcoord = INITIAL_PT_UP;
        pipeDowncoord = INITIAL_PT_DOWN;
    }

    public void drawPipe(Point coordinates, double lengthFactor){
        double pipeLength = coordinates.y/lengthFactor;
        pipe.draw(coordinates.x,pipeLength);
        pipe.draw(coordinates.x,pipeLength+PIPE_DOWN_OFFSET, options.setRotation(Math.PI));

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

        scoreZone = new Rectangle(coordinates.x,pipeLength+COLLIDER_OFFSET-100,pipe.getWidth(),SCORE_ZONE_HEIGHT);



    }

    public boolean isCollide(Point birdCoord){
        return  colliderUp.intersects(birdCoord)||colliderDown.intersects(birdCoord);
    }

    public boolean isScore(Point birdCoord){
        return scoreZone.intersects(birdCoord) && !this.isCollide(birdCoord);
    }

    public Integer generateScore(){
        Integer score = 1;
        return score;
    }

}
