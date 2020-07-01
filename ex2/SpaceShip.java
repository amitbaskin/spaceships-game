import java.awt.Image;
import oop.ex2.*;

/**
 * The general spaceShip object for the game.
 */
public class SpaceShip {
    private static int INITIAL_HEALTH_LEVEL = 22;
    private static int INITIAL_MAXIMAL_ENERGY_LEVEL = 210;
    private static int INITIAL_CURRENT_ENERGY = 190;
    private static int BASHING_ENERGY_INCREASE = 18;
    private static int HIT_ENERGY_DECREASE = 10;
    private static int TELEPORT_ENERGY_COST = 140;
    private static int FIRING_ENERGY_COST = 19;
    private static int SHIELD_ENERGY_COST = 3;
    private static int ENERGY_ROUND_INCREASE = 1;
    private static int HIT_HEALTH_DECREASE = 1;
    private static int ROUND_FIRE_POSTPONEMENT = 7;

    private SpaceShipPhysics myPhysics;
    private int maximalEnergyLevel;
    private int currentEnergyLevel;
    private int healthLevel;
    private boolean isShieldOn;
    private int fireRoundsPostponementCounter;
    private boolean isShotFired;
    private int priorHealthLevel;
    private boolean isCircleCompleted;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new ship with its initial values of energy and health.
     */
    public SpaceShip() {
        myPhysics = new SpaceShipPhysics();
        healthLevel = INITIAL_HEALTH_LEVEL;
        priorHealthLevel = INITIAL_HEALTH_LEVEL;
        maximalEnergyLevel = INITIAL_MAXIMAL_ENERGY_LEVEL;
        currentEnergyLevel = INITIAL_CURRENT_ENERGY;
        isShieldOn = false;
        fireRoundsPostponementCounter = 0; // used for restraining the fire frequency
        isCircleCompleted = true; // used for managing circle movement of the ship
    }

    /*----=  Getters and Setters  =-----*/

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     * @return the image of this ship.
     */
    public Image getImage() {
        if (!isShieldOn) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE;
        } else {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
    }

    /**
     * Gets the physics object that controls this ship.
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return myPhysics;
    }

    /**
     * Get the current health level of the ship.
     * @return the current health level of the ship.
     */
    public int getHealthLevel() {
        return healthLevel;
    }

    /**
     * Determines whether the ship's shield is on or off.
     * @return the state of the ship's shield: true if its on and false if its off.
     */
    public boolean getIsShieldOn() {
        return isShieldOn;
    }

    /**
     * Gets the amount of rounds to restrain the fire.
     * @return the amount of rounds to restrain the fire.
     */
    public int getFireRoundsPostponementCounter() {
        return fireRoundsPostponementCounter;
    }

    /**
     * Sets the amount of rounds to postpone the fire.
     * @param amountToSet the amount of rounds to postpone the fire.
     */
    public void setFireRoundsPostponementCounter(int amountToSet) {
        fireRoundsPostponementCounter = amountToSet;
    }

    /**
     * Determines whether or not the ship should keep moving in circles or not.
     * @return true if the ship should keep turning and false if it should stop.
     */
    public boolean getIsCircleCompleted() {
        return isCircleCompleted;
    }

    /**
     * Sets whether or nor the ship should keep turning.
     * @param determine true if the ship should keep turning and false if not.
     */
    public void setIsCircleCompleted(boolean determine) {
        isCircleCompleted = determine;
    }

    /*----=  Instance Methods  =-----*/

    /**
     * Decreases the health level according to the decrease amount.
     * If the decrease amount causes the health level get under zero, it is set to be zero.
     * @param decreaseAmount The amount to decrease.
     */
    private void decreaseHealthLevel(int decreaseAmount) {
        int newHealthLevel = healthLevel - decreaseAmount;
        if (newHealthLevel >= 0) {
            healthLevel = newHealthLevel;
        } else {
            healthLevel = 0;
            isDead();
        }
    }

    /**
     * Increases the amount of rounds to postpone fire.
     */
    public void increaseFireRoundsPostponementCounter() {
        fireRoundsPostponementCounter += 1;
    }

    /**
     * Increases The maximal energy level.
     *
     * @param increaseAmount The amount to increase the maximal energy by.
     */
    private void increaseMaximalEnergyLevel(int increaseAmount) {
        maximalEnergyLevel += increaseAmount;
    }

    /**
     * Decreases the maximal energy level according to the decrease amount.
     * If the decrease amount causes a decrease below zero, it is set to be zero.
     * @param decreaseAmount The amount of energy to decrease.
     */
    private void decreaseMaximalEnergyLevel(int decreaseAmount) {
        int newMaximalEnergyLevel = maximalEnergyLevel - decreaseAmount;
        if (newMaximalEnergyLevel >= 0) {
            maximalEnergyLevel = newMaximalEnergyLevel;
        } else {
            maximalEnergyLevel = 0;
        }
        if (currentEnergyLevel > maximalEnergyLevel) {
            currentEnergyLevel = maximalEnergyLevel;
        }
    }

    /**
     * Increases the current energy level according to the amount to increase.
     * If the increase amount causes a raise above the maximal energy level,
     * it is set to be as the maximal energy level.
     * @param increaseAmount The amount of energy to increase.
     */
    private void increaseCurrentEnergyLevel(int increaseAmount) {
        int newCurrentEnergyLevel = currentEnergyLevel + increaseAmount;
        if (newCurrentEnergyLevel <= maximalEnergyLevel) {
            currentEnergyLevel = newCurrentEnergyLevel;
        } else {
            currentEnergyLevel = maximalEnergyLevel;
        }
    }

    /**
     * Decreases the current energy level.
     * @param decreaseAmount The amount to decrease the current energy level by.
     */
    private void decreaseCurrentEnergyLevel(int decreaseAmount) {
        int newCurrentEnergyLevel = currentEnergyLevel - decreaseAmount;
        if (newCurrentEnergyLevel >= 0) {
            currentEnergyLevel = newCurrentEnergyLevel;
        } else {
            currentEnergyLevel = 0;
        }
    }

    /**
     * Randomly renews the position of the ship.
     */
    public void myPhysicRenewal() {
        myPhysics = new SpaceShipPhysics();
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        bashing();
        gotHit();
    }

    /**
     * If the shield is on then the energy levels go up.
     */
    private void bashing() {
        if (isShieldOn) {
            increaseMaximalEnergyLevel(BASHING_ENERGY_INCREASE);
            increaseCurrentEnergyLevel(BASHING_ENERGY_INCREASE);
        }
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!isShieldOn) {
            decreaseMaximalEnergyLevel(HIT_ENERGY_DECREASE);
            decreaseHealthLevel(HIT_HEALTH_DECREASE);
        }
    }

    /**
     * Determines whether or not the health level has been reduced.
     * @return true if the health level has been reduced and false if not.
     */
    public boolean isLifeReduced() {
        if (priorHealthLevel > healthLevel) {
            priorHealthLevel = healthLevel;
            return true;
        } else return false;
    }

    /**
     * Checks if this ship is dead.
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return healthLevel == 0;
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        myPhysics = new SpaceShipPhysics();
        priorHealthLevel = INITIAL_HEALTH_LEVEL;
        healthLevel = INITIAL_HEALTH_LEVEL;
        maximalEnergyLevel = INITIAL_MAXIMAL_ENERGY_LEVEL;
        currentEnergyLevel = INITIAL_CURRENT_ENERGY;
    }

    /**
     * Attempts to fire a shot.
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (currentEnergyLevel >= FIRING_ENERGY_COST) {
            if (fireRoundsPostponementCounter == 0) {
                isShotFired = true;
                game.addShot(myPhysics);
                decreaseCurrentEnergyLevel(FIRING_ENERGY_COST);
                fireRoundsPostponementCounter += 1;
            }
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (currentEnergyLevel >= SHIELD_ENERGY_COST) {
            isShieldOn = true;
            decreaseCurrentEnergyLevel(SHIELD_ENERGY_COST);
        }
        else turnShieldOff();
        }

    /**
     * Turns the shield as on.
     */
    public void turnShieldOn() {
        isShieldOn = true;
    }

    /**
     * Turns the shield off.
     */
    public void turnShieldOff() {
        isShieldOn = false;
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (currentEnergyLevel >= TELEPORT_ENERGY_COST) {
            myPhysics = new SpaceShipPhysics();
            decreaseCurrentEnergyLevel(TELEPORT_ENERGY_COST);
        }
    }

    /**
     * Makes the ship to go after the closest ship to it.
     * @param game the game object in which the ship is in.
     */
    public void pursueClosestSpaceShip(SpaceWars game) {
        SpaceShipPhysics myPhysics = getPhysics();
        double myAngle = angleToClosest(game);
        if (myAngle >= 0) {
            myPhysics.move(true, 1);
        } else {
            myPhysics.move(true, -1);
        }
    }

    /**
     * Increases the current energy in the amount that it should increase every round.
     */
    public void roundCurrentEnergyIncrease(){
        increaseCurrentEnergyLevel(ENERGY_ROUND_INCREASE);
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        getImage();
        myPhysics.move(false, 0);
        manageFiring();
    }

    /**
     * Manages the firing of this spaceship.
     */
    public void manageFiring() {
        if (fireRoundsPostponementCounter == ROUND_FIRE_POSTPONEMENT) {
            fireRoundsPostponementCounter = 0;
            isShotFired = false;
        } else if (isShotFired) {
            fireRoundsPostponementCounter += 1;
        }
    }

    /**
     * Calculates the distance from the closest ship.
     * @param game the game object in which the ship is in.
     * @return the distance from the closest ship.
     */
    public double distanceFromClosest(SpaceWars game) {
        SpaceShip closestSpaceShip = game.getClosestShipTo(this);
        SpaceShipPhysics closestSpaceShipPhysics = closestSpaceShip.getPhysics();
        double myCoordinateX = myPhysics.getX();
        double myCoordinateY = myPhysics.getY();
        double closestSpaceShipCoordinateX = closestSpaceShipPhysics.getX();
        double closestSpaceShipCoordinateY = closestSpaceShipPhysics.getY();
        double differenceX = myCoordinateX - closestSpaceShipCoordinateX;
        double differenceY = myCoordinateY - closestSpaceShipCoordinateY;
        double squareDifferenceX = differenceX * differenceX;
        double squareDifferenceY = differenceY * differenceY;
        return Math.sqrt(squareDifferenceX + squareDifferenceY);
    }

    /**
     * Gets the angle from the closest ship.
     * @param game the game object in which the ship is in.
     * @return the angle from the closest ship.
     */
    public double angleToClosest(SpaceWars game) {
        SpaceShipPhysics myPhysics = getPhysics();
        SpaceShip closestSpaceShip = game.getClosestShipTo(this);
        SpaceShipPhysics closestSpaceShipPhysics = closestSpaceShip.getPhysics();
        return myPhysics.angleTo(closestSpaceShipPhysics);
    }
}