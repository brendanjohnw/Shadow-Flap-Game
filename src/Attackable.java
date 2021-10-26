/** An interface that defines behavior for classes that are attackable */
public interface Attackable {
    boolean takeDamage();
    void initializeHealth(int maxHealth);
}
