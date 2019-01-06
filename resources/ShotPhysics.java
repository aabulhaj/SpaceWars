package resources;

public class ShotPhysics extends Physics {
    private static final double MAX_VELOCITY = 0.015;
    private static final double RADIUS = 0.01;
    private static final double EPS = 0.001;

    private int ttl;

    public ShotPhysics(SpaceShipPhysics shipPosition, int ttl) {
        super(shipPosition.getX() + Math.cos(shipPosition.getAngle()) * (RADIUS + SpaceShipPhysics.RADIUS + EPS),
                shipPosition.getY() + Math.sin(shipPosition.getAngle()) * (RADIUS + SpaceShipPhysics.RADIUS + EPS),
                shipPosition.getAngle());
        this.ttl = ttl;
    }

    public void move() {
        x += MAX_VELOCITY * Math.cos(angle);
        y += MAX_VELOCITY * Math.sin(angle);
        ttl--;
        loopCoord();
    }

    public boolean hits(SpaceShipPhysics spaceship) {
        double dx = getDx(spaceship);
        double dy = getDy(spaceship);
        return (Math.sqrt(dx * dx + dy * dy) <= (ShotPhysics.RADIUS + SpaceShipPhysics.RADIUS));
    }

    public boolean expired() {
        return ttl <= 0;
    }
}
