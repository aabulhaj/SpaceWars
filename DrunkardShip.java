import java.util.Random;


public class DrunkardShip extends SpaceShip {
    private static final double MIN_VALUE_TO_PANIC = 0.20;

    private static final int NUM_OF_ACTIONS = 3;
    private static final int TELEPORT = 0;
    private static final int SHIELD = 1;
    private static final int SHOOT = 2;

    private final Random rand = new Random();


    @Override
    public void doAction(SpaceWars game) {
        roundNumber += 1;

        double angle = angleToNearestShip(game);
        double distance = distanceToNearestShip(game);

        if (angle <= MIN_VALUE_TO_PANIC && distance <= MIN_VALUE_TO_PANIC) {
            drunkardPanic(game);
        }

        // Especially when panicking, the drunkard ship moves randomly.
        int direction = rand.nextInt(3) - 1;
        getPhysics().move(true, direction);

        addEnergy(ENERGY_PER_ROUND);
    }

    private void drunkardPanic(SpaceWars game) {
        int action = rand.nextInt(NUM_OF_ACTIONS);

        if (action == TELEPORT) {
            teleport();
        } else if (action == SHIELD) {
            shieldOn();
        } else if (action == SHOOT) {
            fire(game);
        }
    }
}
