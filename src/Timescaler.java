/** Implements the timescaler for the game
 * DOES NOT WORK PROPERLY*/
public class Timescaler {
    private static int timescale;
    private static int newScalePipe;
    private static int newScaleWeapon;
    private static double newSpeed;
    public Timescaler(){
        timescale = 1;
    }

    /** Method to scale up the pipe speeds and weapon spawning speeds*/
    public static void scale(){

        if(timescale>=1 || timescale < 5){
            timescale++;
            newScalePipe += (50/100)*Pipe.getInitialNextPipe();
            newScaleWeapon += (50/100)*Weapon.getInitNextWeapon();
            Pipe.setPipeSpeed(Pipe.getpipeSpeed()+timescale);
            Weapon.setWeaponSpeed(Weapon.getWeaponSpeed()+timescale);
        }
        Weapon.setWeaponSpeed(newScaleWeapon);
        Pipe.setNextPipe(newScalePipe);
    }
    /** Method to downscale the pipe speeds and weapon spawning speeds*/
    public static void downscale(){
        newScalePipe = Pipe.getNextPipe();
        if(timescale>1){
            timescale--;
            newScalePipe = newScalePipe-((((timescale-1)*50)/100)*(Pipe.getInitialNextPipe()));
            newScaleWeapon = newScalePipe-((((timescale-1)*50)/100)*(Weapon.getInitNextWeapon()));
            Pipe.setPipeSpeed(Pipe.getpipeSpeed()-timescale);
            Weapon.setWeaponSpeed(Weapon.getWeaponSpeed()-timescale);
        }
        Pipe.setNextPipe(newScalePipe);
        Weapon.setWeaponSpeed(newScaleWeapon);
    }
}
