import bagel.*;
import bagel.util.Point;

public class Background {
    private final Image background = new Image("res/background.png");
    private final Point backgroundPoint = new Point(Window.getWidth()/2,Window.getHeight()/2);

    public void drawBackground(){
        background.draw(backgroundPoint.x, backgroundPoint.y);
    }
}
