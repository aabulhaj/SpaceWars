public class SpaceShipFactory {
    public static SpaceShip[] createSpaceShips(String[] args) {
        SpaceShip[] spaceships = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "a":
                    spaceships[i] = new AggressiveShip();
                    break;
                case "h":
                    spaceships[i] = new HumanShip();
                    break;
                case "r":
                    spaceships[i] = new RunnerShip();
                    break;
                case "b":
                    spaceships[i] = new BasherShip();
                    break;
                case "d":
                    spaceships[i] = new DrunkardShip();
                    break;
                case "s":
                    spaceships[i] = new SpecialShip();
                    break;
                default:
                    spaceships[i] = null;
            }
        }
        return spaceships;
    }
}
