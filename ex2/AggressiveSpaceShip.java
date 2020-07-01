/**
 * This ship pursues other ships and tries to fire at them.
 * It will always accelerate, and turn towards the nearest ship.
 * When its angle to the nearest ship is accurate enough in order to hit the spaceship,
 * it will try to fire.
 */
public class AggressiveSpaceShip extends SpaceShip {
    double AGGRESSIVE_ANGLE = 0.21;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game){
       aggressiveAction(game);
       manageFiring();
       roundCurrentEnergyIncrease();
   }

    /**
     * Does the aggressive actions of this ship:
     * pursuing the closest spaceship and trying to fire at it.
     * @param game the game object to which this ship belongs.
     */
    private void aggressiveAction(SpaceWars game) {
        pursueClosestSpaceShip(game);
        double myAngle = angleToClosest(game);
        if (myAngle < AGGRESSIVE_ANGLE) {
            fire(game);
        }
    }
}