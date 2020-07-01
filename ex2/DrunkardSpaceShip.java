import oop.ex2.*;
import javax.swing.*;
import java.awt.Image;

/**
 * Its pilot had too much to drink.
 * It turns its shield on and off randomly.
 * It constantly accelerates and moves in circles.
 * If it gets hit, it fires, teleports, fires again and makes fire appear in random places.
 */
public class DrunkardSpaceShip extends SpaceShip {
    private static int RANDOM_FACTOR = 3;
    private static int RANDOM_MODULO = 2;

    /**
     * Gets the modulo of dividing a random number by RANDOM_FACTOR.
     * @return the random modulo.
     */
    private int generateRandomNumber() {
        double myDouble = Math.random() * RANDOM_FACTOR;
        return (int) myDouble % RANDOM_MODULO;
    }

    /**
     * Sets the image for the shield.
     * @return the shield image of this ship.
     */
    private Image myImage() {
        java.net.URL myPath = DrunkardSpaceShip.class.getResource("ex2Sticker.jpeg");
        ImageIcon myIcon = new ImageIcon(myPath);
        return myIcon.getImage();
    }

    /**
     * Gets the image of this ship.
     * This method should return the image of the ship with or without the shield.
     * This will be displayed on the GUI at the end of the round.
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        if (getIsShieldOn()) {
            return myImage();
        } else {
            return GameGUI.ENEMY_SPACESHIP_IMAGE;
        }
    }

    /**
     * Does the special action of this ship:
     * moving in circles, constantly accelerating, teleporting an firing when it gets hit.
     * @param game the game object to which this ship belongs.
     */
    public void drunkAction(SpaceWars game) {
        getPhysics().move(true, 1);
        if (generateRandomNumber() == 0) {
            turnShieldOn();

        } else {
            turnShieldOff();
        }
        if (isLifeReduced()) {
            game.addShot(getPhysics());
            game.addShot(new SpaceShipPhysics());
            game.addShot(new SpaceShipPhysics());
            myPhysicRenewal();
            game.addShot(getPhysics());
            game.addShot(new SpaceShipPhysics());
            game.addShot(new SpaceShipPhysics());

        }
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction (SpaceWars game){
        drunkAction(game);
        roundCurrentEnergyIncrease();
    }
}