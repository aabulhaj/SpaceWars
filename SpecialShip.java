public class SpecialShip extends SpaceShip {
    private static final double MIN_RADIANS_TO_SHIP_TO_FIRE = 0.15;

    private boolean restockEnergyMode = false;

    @Override
    public void doAction(SpaceWars game) {
        // If energy is less than 0.25 maximal energy, enable restock mode.
        // (Disable restock mode once the ship hits 0.7 of max energy).
        if (currentEnergyLevel <= maximalEnergyLevel / 4) {
            restockEnergyMode = true;
        } else if (currentEnergyLevel >= 0.7 * maximalEnergyLevel) {
            restockEnergyMode = false;
        }

        shieldActive = false;
        if (restockEnergyMode) {
            runFromNearestShip(game);
        } else {
            destroyNearestShip(game);
        }

        addEnergy(ENERGY_PER_ROUND);
    }

    private void destroyNearestShip(SpaceWars game) {
        double angle = angleToNearestShip(game);

        // Puts a shield on, bashes into the nearest ship, then shoots at it.
        shieldOn();

        moveTowardsNearestShip(game);

        if (angle < MIN_RADIANS_TO_SHIP_TO_FIRE) {
            fire(game);
        }
    }
}
