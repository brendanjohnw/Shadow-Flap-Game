import bagel.util.Point;
/** The collidable interface is implemented if an object can be collided with
 * */
public interface Collidable {
    /** Method to check whether an an entity collides with another entity
     *
     * */
    boolean isCollide(Point coordinates);
}
