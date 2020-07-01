/**
 * This ship attempts to collide with other ships.
 * It will always accelerate, and will constantly turn towards the closest ship.
 * If it gets close to another ship, it attempts to turn on its shields.
 */
public class BasherSpaceShip extends SpaceShip {
    static double SHIELD_DISTANCE = 0.19;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        basherAction(game);
        roundCurrentEnergyIncrease();
    }

    /**
     * The special actions of the basher:
     * pursuing other spaceships in order to bash into them while its shield is one
     * @param game the game object to which this ship belongs.
     */
    private void basherAction(SpaceWars game) {
        pursueClosestSpaceShip(game);
        double myDistance = distanceFromClosest(game);
        if (myDistance <= SHIELD_DISTANCE) {
            shieldOn();
        } else {
            turnShieldOff();
        }
    }
}