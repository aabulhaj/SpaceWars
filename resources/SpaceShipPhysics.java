package resources;

import java.util.Random;

public class SpaceShipPhysics extends Physics {
    public static final double RADIUS = 0.03;

    private static Random randomGenerator = new Random(System.currentTimeMillis());
    private static final double MAX_VELOCITY = 0.005;
    private static final double ACCELERATION_RATE = 0.0002;
    private static final double TURN_RATE = 0.05;
    private static final double PLASTIC_COLLISION_MOD = 0.8;

    private double vx;
    private double vy;

    public SpaceShipPhysics() {
        super(randomGenerator.nextDouble(), randomGenerator.nextDouble(), randomGenerator.nextDouble() * Math.PI * 2);
        vx = (randomGenerator.nextDouble() - 0.5) * MAX_VELOCITY;
        vy = (randomGenerator.nextDouble() - 0.5) * MAX_VELOCITY;
    }

    public void move(boolean accelerate, int turn) {
        if (turn < -1 || turn > 1) {
            throw new IllegalArgumentException("Values of turn can only be -1,0, or 1");
        }

        angle += turn * TURN_RATE;
        angle = angle % (2 * Math.PI);
        x += vx;
        y += vy;
        loopCoord();

        if (accelerate) {
            vx += ACCELERATION_RATE * Math.cos(angle);
            vy += ACCELERATION_RATE * Math.sin(angle);
        }

        double vel = Math.sqrt(vx * vx + vy * vy);
        if (vel > MAX_VELOCITY) {
            vx *= MAX_VELOCITY / vel;
            vy *= MAX_VELOCITY / vel;
        }
    }

    public boolean testCollisionWith(SpaceShipPhysics other) {
        double dx = getDx(other);
        double dy = getDy(other);
        double norm = Math.sqrt(dx * dx + dy * dy);
        if (norm < SpaceShipPhysics.RADIUS / 2) {
            this.vx += ACCELERATION_RATE * 2;
            other.vx -= ACCELERATION_RATE;
        }

        // Relative speed.
        double dvx = correctDif(this.vx - other.vx);
        double dvy = correctDif(this.vy - other.vy);
        if (norm < SpaceShipPhysics.RADIUS * 2 && dx * dvx + dy * dvy < 0) {
            if (norm <= 0) {
                norm = 1;
                dx = 1;
                dy = 0;
            }

            dx = dx / norm;
            dy = dy / norm;

            double dot = dvx * dx + dvy * dy;
            this.vx -= dot * dx * PLASTIC_COLLISION_MOD;
            this.vy -= dot * dy * PLASTIC_COLLISION_MOD;

            other.vx += dot * dx * PLASTIC_COLLISION_MOD;
            other.vy += dot * dy * PLASTIC_COLLISION_MOD;

            return true;
        }
        return false;
    }

    public double angleTo(SpaceShipPhysics other) {
        double dx = -getDx(other);
        double dy = -getDy(other);
        double angleFromX = Math.asin(dy / (Math.sqrt(dy * dy + dx * dx)));
        if (dx < 0) {
            angleFromX = Math.PI - angleFromX;
        }
        double difAngle = angleFromX - this.angle;
        difAngle = difAngle % (2 * Math.PI);
        if (difAngle > Math.PI) {
            difAngle -= Math.PI * 2;
        } else if (difAngle < -Math.PI) {
            difAngle += Math.PI * 2;
        }
        return difAngle;
    }
}
