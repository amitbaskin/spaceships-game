/**
 * This ship is special. If it gets hit it turns on its shield for a few rounds and fires in circles.
 * In the mean time it moves in circles without accelerating.
 * Furthermore, if its life is low enough then it teleports constantly.
 */
public class SpecialSpaceShip extends SpaceShip{
    private int SPECIAL_HEALTH_LEVEL = 17;
    private int SPECIAL_ROUNDS_NUMBER = 200;

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * @param game the game object to which this ship belongs.
     */
    @Override
    public void doAction(SpaceWars game) {
        specialAction(game);
    }

    /**
     * Does its special actions, moving and firing in circles.
     * If its life is low enough then it teleports constantly.
     * @param game the game object to which this ship belongs.
     */
    private void specialAction(SpaceWars game){
        if(getHealthLevel() < SPECIAL_HEALTH_LEVEL){ // if it's health is under this point it will teleport
            // constantly until it is dead.
            myPhysicRenewal();
        }

        // if it is not shooting already and
        // it got hit and the health is high enough then it fires in circles while its shield is on
        if(isLifeReduced() & getFireRoundsPostponementCounter() == 0
                & getHealthLevel() > SPECIAL_HEALTH_LEVEL) {
            setIsCircleCompleted(false);}
        if(!getIsCircleCompleted()){
            getPhysics().move(false, 1);
            turnShieldOn();
            game.addShot(getPhysics());
            increaseFireRoundsPostponementCounter();
        }
        // if it shouldn't fire now then it turns in the other direction.
        else {getPhysics().move(false, -1);}
        // if it fired for long enough then it stops firing and the shield is off
        if(getFireRoundsPostponementCounter() > SPECIAL_ROUNDS_NUMBER){
            setFireRoundsPostponementCounter(0);
            setIsCircleCompleted(true);
            turnShieldOff();
        }
    }
}