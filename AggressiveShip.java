public class AggressiveShip extends SpaceShip {
    private static final double MAX_RADIANS_ANGLE_TO_FIRE = 0.2;

    @Override
    public void doAction(SpaceWars game) {
        roundNumber += 1;

        moveTowardsNearestShip(game);

        double angle = angleToNearestShip(game);
        if (angle <= MAX_RADIANS_ANGLE_TO_FIRE) {
            fire(game);
        }

        addEnergy(ENERGY_PER_ROUND);
    }
}
