import java.awt.Image;
import oop.ex2.*;

/**
 * Controlled by the user.
 * The keys are: left-arrow and right-arrow to turn, up-arrow to accelerate,
 * ’d’ to fire a shot, ’s’ to turn on the shield, ’a’ to teleport.
 */
public class HumanSpaceShip extends SpaceShip {

    /**
     * Gets the image of this ship.
     * This method should return the image of the ship with or without the shield.
     * This will be displayed on the GUI at the end of the round.
     * @return the image of this ship.
     */
    @Override
    public Image getImage() {
        if (!getIsShieldOn()) {
            return GameGUI.SPACESHIP_IMAGE;
        } else {
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        }
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        humanKeyControls(game);
        manageFiring();
        roundCurrentEnergyIncrease();
    }

    /**
     * Does the actions of this ship for this round according to the keys the user press.
     * @param game the game object to which this ship belongs.
     */
    private void humanKeyControls(SpaceWars game) {
        GameGUI gameGui = game.getGUI();

        if (gameGui.isTeleportPressed()) {
            teleport();
        }
        if (gameGui.isRightPressed()) {
            getPhysics().move(false, -1);
        } else if (gameGui.isLeftPressed()) {
            getPhysics().move(false, 1);
        } else {
            getPhysics().move(false, 0);
        }
        if (gameGui.isUpPressed()) {
            getPhysics().move(true, 0);
        }
        if (gameGui.isShieldsPressed()) {
            shieldOn();
        } else {
            turnShieldOff();
        }
        if (gameGui.isShotPressed()) {
            fire(game);
        }
    }
}