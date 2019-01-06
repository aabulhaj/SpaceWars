import resources.GameGUI;
import resources.ShotPhysics;
import resources.SpaceShipPhysics;

import java.util.ArrayList;

public class SpaceWars {
    private static final int SHOT_TIME_TO_LIVE = 40;

    private GameGUI gui;
    private SpaceShip[] ships;
    private ArrayList<ShotPhysics> shots;
    private int[] deathCount;

    public SpaceWars(String[] args) {
        ships = createSpaceShips(args);

        if (ships == null || ships.length < 2) {
            printUsageAndExit();
        }

        for (SpaceShip ship : ships) {
            if (ship == null) {
                printUsageAndExit();
            }
        }

        deathCount = new int[ships.length];

        gui = new GameGUI();
        shots = new ArrayList<>();

        postDeathCountToGUI();
    }

    public static void main(String[] args) {
        SpaceWars game = new SpaceWars(args);
        game.run();
    }

    public GameGUI getGUI() {
        return gui;
    }

    public void addShot(SpaceShipPhysics position) {
        shots.add(new ShotPhysics(position, SHOT_TIME_TO_LIVE));
    }

    public SpaceShip getClosestShipTo(SpaceShip ship) {
        double maxDistance = Double.MAX_VALUE;
        SpaceShip closest = null;
        for (SpaceShip closeShip : ships) {
            if (closeShip != ship) {
                double distance = closeShip.getPhysics().distanceFrom(ship.getPhysics());
                if (distance < maxDistance) {
                    closest = closeShip;
                    maxDistance = distance;
                }
            }
        }
        return closest;
    }

    private SpaceShip[] createSpaceShips(String[] args) {
        return SpaceShipFactory.createSpaceShips(args);
    }

    private static void printUsageAndExit() {
        System.out.println("Usage: \n" +
                "\tjava SpaceWars <spaceship types>\n\n" +
                "Available spaceship types:\n" +
                "\th - Human\n" +
                "\td - Drunkard\n" +
                "\tr - Runner\n" +
                "\ta - Aggressive\n" +
                "\tb - Basher\n" +
                "\ts - Special\n\n" +
                "You must supply at least two spaceship types," +
                " and the human type can only appear once.");
        System.exit(1);
    }

    private void run() {
        while (!gui.isEscPressed()) {
            moveSpaceShips();
            moveShots();
            checkCollisions();
            checkHits();
            drawAllObjects();
            removeDeadShots();
            resetDeadShips();
        }
    }

    private void moveSpaceShips() {
        for (SpaceShip ship : ships) {
            ship.doAction(this);
        }
    }

    private void removeDeadShots() {
        for (int i = shots.size() - 1; i >= 0; i--) {
            if (shots.get(i).expired()) {
                shots.remove(i);
            }
        }
    }

    private void checkHits() {
        for (int i = shots.size() - 1; i >= 0; i--) {
            for (SpaceShip ship : ships) {
                if (shots.get(i).hits(ship.getPhysics())) {
                    ship.gotHit();
                    shots.remove(i);
                    break;
                }
            }
        }
    }

    private void moveShots() {
        for (ShotPhysics shot : shots) {
            shot.move();
        }
    }

    private void resetDeadShips() {
        for (int i = 0; i < ships.length; i++) {
            if (ships[i].isDead()) {
                deathCount[i]++;
                ships[i].reset();
                postDeathCountToGUI();
            }
        }
    }

    private void postDeathCountToGUI() {
        String text = "";
        for (int i = 0; i < deathCount.length; i++) {
            text += "P" + (i + 1) + ": " + deathCount[i] + "   ";
        }
        gui.setText(text);
    }

    private void drawAllObjects() {
        for (SpaceShip ship : ships) {
            gui.addImageToBuffer(ship.getImage(), ship.getPhysics());
        }
        for (ShotPhysics shot : shots) {
            gui.addImageToBuffer(GameGUI.SHOT_IMAGE, shot);
        }
        gui.drawBufferToScreen();
    }

    private void checkCollisions() {
        for (int i = 0; i < ships.length; i++) {
            for (int j = i + 1; j < ships.length; j++) {
                if (ships[i].getPhysics().testCollisionWith(ships[j].getPhysics())) {
                    ships[i].collidedWithAnotherShip();
                    ships[j].collidedWithAnotherShip();
                }
            }
        }
    }
}
