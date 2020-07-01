/**
 * The factory pf spaceships.
 * SpaceWars uses it inorder to create spaceships for the game.
 */
public class SpaceShipFactory {
    private static String HUMAN_IDENTIFIER = Character.toString('h');
    private static String RUNNER_IDENTIFIER = Character.toString('r');
    private static String BASHER_IDENTIFIER = Character.toString('b');
    private static String AGGRESSIVE_IDENTIFIER = Character.toString('a');
    private static String DRUNKARD_IDENTIFIER = Character.toString('d');
    private static String SPECIAL_IDENTIFIER = Character.toString('s');

    /**
     * Returns an array of the ships required.
     * @param args The ships to create.
     * @return an array of the ships required.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        int spaceShipAmount = args.length;
        SpaceShip[] spaceShipArray = new SpaceShip[spaceShipAmount];
        for (int i = 0; i < spaceShipAmount; i++) {
            if (args[i].equals(HUMAN_IDENTIFIER)) {
                spaceShipArray[i] = new HumanSpaceShip();
            } else if (args[i].equals(RUNNER_IDENTIFIER)) {
                spaceShipArray[i] = new RunnerSpaceShip();
            } else if (args[i].equals(BASHER_IDENTIFIER)) {
                spaceShipArray[i] = new BasherSpaceShip();
            }
            else if (args[i].equals(AGGRESSIVE_IDENTIFIER)) {
                spaceShipArray[i] = new AggressiveSpaceShip();
            }
            else if (args[i].equals(DRUNKARD_IDENTIFIER)) {
                spaceShipArray[i] = new DrunkardSpaceShip();
            }
            else if (args[i].equals(SPECIAL_IDENTIFIER)) {
                spaceShipArray[i] = new SpecialSpaceShip();
            }
            else {
                spaceShipArray[i] = new SpaceShip();
            }
        }
        return spaceShipArray;
    }
}