public class RunnerShip extends SpaceShip {
    private static final double MAX_UNITS_TO_FEEL_THREATENED = 0.25;
    private static final double MAX_ANGLES_TO_RUN_AWAY = 0.23;

    @Override
    public void doAction(SpaceWars game) {
        double angle = angleToNearestShip(game);
        double distance = distanceToNearestShip(game);

        if (angle <= MAX_ANGLES_TO_RUN_AWAY && distance <= MAX_UNITS_TO_FEEL_THREATENED) {
            teleport();
        }

        runFromNearestShip(game);

        addEnergy(ENERGY_PER_ROUND);
    }
}
