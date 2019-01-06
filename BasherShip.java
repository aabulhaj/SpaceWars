public class BasherShip extends SpaceShip {
    private static final double MAX_UNITS_FOR_COLLIDING = 0.19;

    @Override
    public void doAction(SpaceWars game) {
        moveTowardsNearestShip(game);

        double distance = distanceToNearestShip(game);

        shieldActive = false;
        if (distance <= MAX_UNITS_FOR_COLLIDING) {
            shieldOn();
        }

        addEnergy(ENERGY_PER_ROUND);
    }
}
