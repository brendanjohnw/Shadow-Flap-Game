import bagel.*;
import bagel.util.Point;

public class Bird {

    private Point birdCoord;

    private final Image birdUp = new Image("res/birdWingUp.png");
    private final Image birdDown = new Image("res/birdWingDown.png");

    public Bird(){
        Point INITIAL_PT = new Point(200, 350);
        this.birdCoord = INITIAL_PT;
    }

    public void showBirdUp(Point birdCoord){
        birdUp.draw(birdCoord.x,birdCoord.y);
    }

    public void showBirdDown(Point birdCoord){
        birdDown.draw(birdCoord.x,birdCoord.y);
    }
}
