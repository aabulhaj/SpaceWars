import resources.GameGUI;

import java.awt.*;

public class HumanShip extends SpaceShip {
    @Override
    public void doAction(SpaceWars game) {
        roundNumber += 1;

        if (game.getGUI().isTeleportPressed()) {
            teleport();
        }

        boolean isUpPressed = game.getGUI().isUpPressed();
        if (game.getGUI().isLeftPressed()) {
            getPhysics().move(isUpPressed, MOVE_LEFT);
        } else if (game.getGUI().isRightPressed()) {
            getPhysics().move(isUpPressed, MOVE_RIGHT);
        } else if (game.getGUI().isEscPressed()) {
            System.exit(0);
        } else {
            getPhysics().move(isUpPressed, NO_MOVE);
        }


        shieldActive = false;
        if (game.getGUI().isShieldsPressed()) {
            shieldOn();
        }

        if (game.getGUI().isShotPressed()) {
            fire(game);
        }

        addEnergy(ENERGY_PER_ROUND);
    }


    @Override
    public Image getImage() {
        if (!shieldActive) {
            return GameGUI.SPACESHIP_IMAGE;
        }
        return GameGUI.SPACESHIP_IMAGE_SHIELD;
    }
}
