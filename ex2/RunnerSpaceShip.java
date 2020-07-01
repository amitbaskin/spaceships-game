import oop.ex2.SpaceShipPhysics;

/**
 * This spaceship attempts to run away from the fight. It will always accelerate, and
 * will constantly turn away from the closest ship. If the nearest ship is close enough,
 * the Runner feels threatened and attempts to teleport.
 */
public class RunnerSpaceShip extends SpaceShip {
    private static double THREATENING_DISTANCE = 0.25;
    private static double THREATENING_ANGLE = 0.23;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        runnerAction(game);
    }

    /**
     * The special actions of the runner: running, turning and teleporting away from close spaceships.
     * @param game the game object to which this ship belongs.
     */
    private void runnerAction(SpaceWars game) {
        SpaceShipPhysics runnerPhysics = getPhysics();
        double angleToClosest = angleToClosest(game);
        double distance = distanceFromClosest(game);

        if (distance < THREATENING_DISTANCE & angleToClosest < THREATENING_ANGLE) {
            teleport();
        }
        if (angleToClosest >= 0) {
            runnerPhysics.move(true, -1);
        }
        else {
            runnerPhysics.move(true, 1);
        }
    }
}