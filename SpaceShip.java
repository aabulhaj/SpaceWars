import resources.GameGUI;
import resources.SpaceShipPhysics;

import java.awt.*;

public abstract class SpaceShip {
    protected static final int ENERGY_PER_ROUND = 1;

    protected static final int NO_MOVE = 0;
    protected static final int MOVE_RIGHT = -1;
    protected static final int MOVE_LEFT = 1;

    protected static final int ENERGY_FOR_TELEPORT = 140;
    protected static final int ENERGY_FOR_SHIELD = 3;
    protected static final int ENERGY_FOR_FIRESHOT = 19;

    protected static final int EXTRA_ENERGY_FOR_COLLIDING = 18;
    protected static final int GOT_HIT_ENERGY_REDUCTION = 10;
    protected static final int NUMBER_OF_ROUNDS_TO_SHOOT_AGAIN = 7;
    protected static final int REDUCE_HEALTH_WHEN_SHOT_OR_COLLIDED = 1;

    protected static final int INITIAL_HEALTH = 7;
    protected static final int INITIAL_ENERGY_LEVEL = 190;

    protected int roundNumber = 0;
    protected int lastShotAtRound = 0;
    protected int health = INITIAL_HEALTH;
    protected int maximalEnergyLevel = INITIAL_ENERGY_LEVEL;
    protected int currentEnergyLevel = INITIAL_ENERGY_LEVEL;

    protected Image image;

    protected boolean shieldActive = false;

    protected SpaceShipPhysics physics = new SpaceShipPhysics();


    public abstract void doAction(SpaceWars game);

    public void collidedWithAnotherShip() {
        if (shieldActive) {
            currentEnergyLevel += EXTRA_ENERGY_FOR_COLLIDING;
            maximalEnergyLevel += EXTRA_ENERGY_FOR_COLLIDING;
        } else {
            health -= REDUCE_HEALTH_WHEN_SHOT_OR_COLLIDED;
        }
    }

    public void reset() {
        image = getImage();
        physics = new SpaceShipPhysics();

        health = INITIAL_HEALTH;
        currentEnergyLevel = INITIAL_ENERGY_LEVEL;
        lastShotAtRound = 0;
        roundNumber = 0;

        shieldActive = false;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public SpaceShipPhysics getPhysics() {
        return physics;
    }

    public void gotHit() {
        if (!shieldActive) {
            maximalEnergyLevel -= GOT_HIT_ENERGY_REDUCTION;
            health -= REDUCE_HEALTH_WHEN_SHOT_OR_COLLIDED;

            // If the player energy level is higher than the new maximum, change it to the new max.
            if (currentEnergyLevel > maximalEnergyLevel) {
                currentEnergyLevel = maximalEnergyLevel;
            }
        }
    }

    public Image getImage() {
        if (shieldActive) {
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        }
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    protected void fire(SpaceWars game) {
        if (currentEnergyLevel >= ENERGY_FOR_FIRESHOT
                && roundNumber - lastShotAtRound >= NUMBER_OF_ROUNDS_TO_SHOOT_AGAIN) {
            game.addShot(getPhysics());
            currentEnergyLevel -= ENERGY_FOR_FIRESHOT;
            lastShotAtRound = roundNumber;
        }
    }

    protected void shieldOn() {
        if (!shieldActive && currentEnergyLevel >= ENERGY_FOR_SHIELD) {
            currentEnergyLevel -= ENERGY_FOR_SHIELD;
            shieldActive = true;
        }
    }

    protected void teleport() {
        if (currentEnergyLevel >= ENERGY_FOR_TELEPORT) {
            currentEnergyLevel -= ENERGY_FOR_TELEPORT;
            physics = new SpaceShipPhysics();
        }
    }

    protected void addEnergy(int energy) {
        if (currentEnergyLevel < maximalEnergyLevel) {
            currentEnergyLevel += energy;
        }
    }

    protected SpaceShip getNearestShip(SpaceWars game) {
        return game.getClosestShipTo(this);
    }

    protected double distanceToNearestShip(SpaceWars game) {
        SpaceShip nearestShip = getNearestShip(game);
        return getPhysics().distanceFrom(nearestShip.getPhysics());
    }

    protected double angleToNearestShip(SpaceWars game) {
        SpaceShip nearestShip = getNearestShip(game);
        return getPhysics().angleTo(nearestShip.getPhysics());
    }

    protected void moveTowardsNearestShip(SpaceWars game) {
        double angle = angleToNearestShip(game);

        if (angle > 0) {
            getPhysics().move(true, MOVE_LEFT);
        } else if (angle < 0) {
            getPhysics().move(true, MOVE_RIGHT);
        } else {
            getPhysics().move(true, NO_MOVE);
        }
    }

    protected void runFromNearestShip(SpaceWars game) {
        double angle = angleToNearestShip(game);

        if (angle > 0) {
            getPhysics().move(true, MOVE_RIGHT);
        } else if (angle < 0) {
            getPhysics().move(true, MOVE_LEFT);
        } else {
            getPhysics().move(true, MOVE_RIGHT);
        }
    }
}
